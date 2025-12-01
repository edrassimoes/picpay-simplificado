package br.com.edras.picpaysimplificado.service;

import br.com.edras.picpaysimplificado.domain.Transaction;
import br.com.edras.picpaysimplificado.dto.transaction.TransactionDTO;
import br.com.edras.picpaysimplificado.exception.transaction.TransactionNotFoundException;
import br.com.edras.picpaysimplificado.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionDTO create(Transaction transaction) {
        Transaction newTransaction = transactionRepository.save(transaction);
        return new TransactionDTO(newTransaction);
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
