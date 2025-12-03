package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.client.AuthorizerClient;
import br.com.edras.picpaysimplificado.client.AuthorizerResponse;
import br.com.edras.picpaysimplificado.client.NotificationClient;
import br.com.edras.picpaysimplificado.client.NotificationRequest;
import br.com.edras.picpaysimplificado.domain.MerchantUser;
import br.com.edras.picpaysimplificado.domain.Transaction;
import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.domain.Wallet;
import br.com.edras.picpaysimplificado.domain.enums.TransactionStatus;
import br.com.edras.picpaysimplificado.domain.enums.UserType;
import br.com.edras.picpaysimplificado.dto.transaction.TransactionDTO;
import br.com.edras.picpaysimplificado.exception.transaction.MerchantCannotTransferException;
import br.com.edras.picpaysimplificado.exception.transaction.SameUserTransactionException;
import br.com.edras.picpaysimplificado.exception.transaction.TransactionNotAuthorizedException;
import br.com.edras.picpaysimplificado.exception.transaction.TransactionNotFoundException;
import br.com.edras.picpaysimplificado.exception.wallet.InvalidAmountException;
import br.com.edras.picpaysimplificado.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    private final AuthorizerClient authorizerClient;
    private final NotificationClient notificationClient;

    public TransactionService(TransactionRepository transactionRepository, WalletService walletService, 
                             AuthorizerClient authorizerClient, NotificationClient notificationClient) {
        this.transactionRepository = transactionRepository;
        this.walletService = walletService;
        this.authorizerClient = authorizerClient;
        this.notificationClient = notificationClient;
    }

    private void merchantDeposit(MerchantUser merchantUser, Double amount) {
        if (amount <= 0){
            throw new InvalidAmountException(amount);
        }
        Wallet wallet = walletService.getWalletByUserId(merchantUser.getId());
        wallet.setBalance(wallet.getBalance() + amount);
        walletService.createOrUpdateWallet(wallet);
    }

    private void validateTransfer(Transaction transaction) {
        // Validar amount
        if (transaction.getAmount() <= 0) {
            throw new InvalidAmountException(transaction.getAmount());
        }

        // Validar payer != payee
        if (transaction.getPayer().getId().equals(transaction.getPayee().getId())) {
            throw new SameUserTransactionException();
        }

        // Validar que payer não é MERCHANT
        if (transaction.getPayer().getUserType() == UserType.MERCHANT) {
            throw new MerchantCannotTransferException(transaction.getPayer().getId());
        }
    }

    private TransactionStatus checkAuthorization() {
        try {
            AuthorizerResponse response = authorizerClient.authorize();
            if (response.getData().isAuthorization()) {
                return TransactionStatus.AUTHORIZED;
            }
            return TransactionStatus.FAILED;
        } catch (Exception e) {
            return TransactionStatus.FAILED;
        }
    }

    private void sendNotification(Transaction transaction) {
        try {
            String message = String.format("Transação de R$ %.2f realizada com sucesso", 
                                          transaction.getAmount());
            NotificationRequest request = new NotificationRequest(message);
            notificationClient.notify(request);
        } catch (Exception e) {
            // Log mas não quebra a transação
            System.err.println("Falha ao enviar notificação: " + e.getMessage());
        }
    }

    @Transactional
    public TransactionDTO transfer(Transaction transaction) {
        // 1. Validar
        validateTransfer(transaction);

        // 2. Setar status inicial e timestamp
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setTimestamp(LocalDateTime.now());

        // 3. Salvar com status PENDING
        Transaction savedTransaction = transactionRepository.save(transaction);

        // 4. Consultar autorizador
        TransactionStatus authStatus = checkAuthorization();
        savedTransaction.setStatus(authStatus);

        if (authStatus != TransactionStatus.AUTHORIZED) {
            transactionRepository.save(savedTransaction);
            throw new TransactionNotAuthorizedException();
        }

        // 5. Realizar transferência
        User payer = savedTransaction.getPayer();
        User payee = savedTransaction.getPayee();
        Double amount = savedTransaction.getAmount();

        walletService.withdraw(payer.getId(), amount);

        if (payee instanceof MerchantUser) {
            merchantDeposit((MerchantUser) payee, amount);
        } else {
            walletService.deposit(payee.getId(), amount);
        }

        // 6. Finalizar
        savedTransaction.setStatus(TransactionStatus.COMPLETED);
        Transaction completedTransaction = transactionRepository.save(savedTransaction);

        // 7. Notificar (não quebra se falhar)
        sendNotification(completedTransaction);

        return new TransactionDTO(completedTransaction);
    }

    public TransactionDTO findById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        return new TransactionDTO(transaction);
    }

    public List<TransactionDTO> findTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream().map(TransactionDTO::new).toList();
    }

    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException(id);
        }
        transactionRepository.deleteById(id);
    }

}
