package br.com.edras.picpaysimplificado.event;

import br.com.edras.picpaysimplificado.domain.Transaction;

public class TransactionCompletedEvent {

    private final Transaction transaction;

    public TransactionCompletedEvent(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

}