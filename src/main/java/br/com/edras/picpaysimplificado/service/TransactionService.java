package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.entity.MerchantUser;
import br.com.edras.picpaysimplificado.entity.Transaction;
import br.com.edras.picpaysimplificado.entity.User;
import br.com.edras.picpaysimplificado.entity.Wallet;
import br.com.edras.picpaysimplificado.entity.enums.TransactionStatus;
import br.com.edras.picpaysimplificado.entity.enums.UserType;
import br.com.edras.picpaysimplificado.dto.transaction.TransactionRequestDTO;
import br.com.edras.picpaysimplificado.dto.transaction.TransactionResponseDTO;
import br.com.edras.picpaysimplificado.event.TransactionCompletedEvent;
import br.com.edras.picpaysimplificado.exception.transaction.MerchantCannotTransferException;
import br.com.edras.picpaysimplificado.exception.transaction.SameUserTransactionException;
import br.com.edras.picpaysimplificado.exception.transaction.TransactionNotAuthorizedException;
import br.com.edras.picpaysimplificado.exception.transaction.TransactionNotFoundException;
import br.com.edras.picpaysimplificado.exception.user.UserNotFoundException;
import br.com.edras.picpaysimplificado.repository.TransactionRepository;
import br.com.edras.picpaysimplificado.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final WalletService walletService;
    private final AuthorizationService authorizationService;
    private final ApplicationEventPublisher eventPublisher;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, WalletService walletService, AuthorizationService authorizationService, ApplicationEventPublisher eventPublisher) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.walletService = walletService;
        this.authorizationService = authorizationService;
        this.eventPublisher = eventPublisher;
    }

    private void merchantDeposit(MerchantUser merchantUser, Double amount) {
        Wallet wallet = walletService.getWalletByUserId(merchantUser.getId());
        wallet.setBalance(wallet.getBalance() + amount);
        walletService.createOrUpdateWallet(wallet);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "transactions:user", key = "#dto.payerId"),
            @CacheEvict(value = "transactions:user", key = "#dto.payeeId")
    })
    public TransactionResponseDTO transfer(TransactionRequestDTO dto) {

        if (dto.getPayerId().equals(dto.getPayeeId())) {
            throw new SameUserTransactionException();
        }

        User payer = userRepository.findById(dto.getPayerId())
                .orElseThrow(() -> new UserNotFoundException(dto.getPayerId()));

        if (payer.getUserType() == UserType.MERCHANT) {
            throw new MerchantCannotTransferException(payer.getId());
        }

        User payee = userRepository.findById(dto.getPayeeId())
                .orElseThrow(() -> new UserNotFoundException(dto.getPayeeId()));

        // Cria a transação no banco.
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setPayer(payer);
        transaction.setPayee(payee);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING);
        Transaction savedTransaction = transactionRepository.save(transaction);

        // API de autenticação
        TransactionStatus authStatus = authorizationService.authorize();

        if (authStatus != TransactionStatus.AUTHORIZED) {
            savedTransaction.setStatus(authStatus);
            transactionRepository.save(savedTransaction);
            throw new TransactionNotAuthorizedException();
        }

        // Realiza a transação.
        walletService.withdraw(payer.getId(), dto.getAmount());

        if (payee instanceof MerchantUser) {
            merchantDeposit((MerchantUser) payee, dto.getAmount());
        } else {
            walletService.deposit(payee.getId(), dto.getAmount());
        }

        savedTransaction.setStatus(TransactionStatus.COMPLETED);
        Transaction completedTransaction = transactionRepository.save(savedTransaction);

        // Publica um evento -> API de notificação.
        eventPublisher.publishEvent(new TransactionCompletedEvent(completedTransaction));

        return new TransactionResponseDTO(completedTransaction);
    }

    @Cacheable(value = "transactions", key = "#id")
    public TransactionResponseDTO findById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        return new TransactionResponseDTO(transaction);
    }

    @Cacheable(value = "transactions:user", key = "#userId")
    public List<TransactionResponseDTO> findTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream().map(TransactionResponseDTO::new).toList();
    }

}
