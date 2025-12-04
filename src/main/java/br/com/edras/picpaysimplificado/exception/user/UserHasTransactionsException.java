package br.com.edras.picpaysimplificado.exception.user;

public class UserHasTransactionsException extends RuntimeException {

    public UserHasTransactionsException() {
        super("Usuário não pode ser removido pois possui transações vinculadas");
    }
}