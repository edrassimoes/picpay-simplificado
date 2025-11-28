package br.com.edras.picpaysimplificado.exception.wallet;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(Double amount) {
        super("Valor inválido: R$" + amount);
    }
}
