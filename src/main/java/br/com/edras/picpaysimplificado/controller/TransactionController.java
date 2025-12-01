package br.com.edras.picpaysimplificado.controller;

import br.com.edras.picpaysimplificado.domain.Transaction;
import br.com.edras.picpaysimplificado.dto.transaction.TransactionDTO;
import br.com.edras.picpaysimplificado.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public  ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody Transaction transaction) {
        TransactionDTO newTransaction = transactionService.create(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.findById(id);
        return ResponseEntity.ok().body(transaction);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<TransactionDTO>> findTransactionsByUserId(@PathVariable Long id) {
        List<TransactionDTO> transactions = transactionService.findTransactionsByUserId(id);
        return ResponseEntity.ok().body(transactions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
