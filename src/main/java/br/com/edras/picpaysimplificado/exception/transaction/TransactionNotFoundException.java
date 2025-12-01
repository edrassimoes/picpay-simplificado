package br.com.edras.picpaysimplificado.exception.transaction;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Long id) {
        super("Transação não encontrada com ID: " + id);
    }
}
