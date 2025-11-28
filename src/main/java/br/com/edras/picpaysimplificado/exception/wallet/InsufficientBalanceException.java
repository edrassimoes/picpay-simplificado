package br.com.edras.picpaysimplificado.exception.wallet;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Saldo insuficiente");
    }
}
