package br.com.edras.picpaysimplificado.exception.transaction;

public class SameUserTransactionException extends RuntimeException {
    public SameUserTransactionException() {
        super("Não é possível transferir para si mesmo");
    }
}
