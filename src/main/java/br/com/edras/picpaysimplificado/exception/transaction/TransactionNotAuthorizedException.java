package br.com.edras.picpaysimplificado.exception.transaction;

public class TransactionNotAuthorizedException extends RuntimeException {
    public TransactionNotAuthorizedException() {
        super("Transação não autorizada pelo serviço externo");
    }
}
