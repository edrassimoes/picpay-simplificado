package br.com.edras.picpaysimplificado.dto.transaction;

import br.com.edras.picpaysimplificado.domain.Transaction;
import br.com.edras.picpaysimplificado.domain.enums.TransactionStatus;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long transactionId;
    private Long payerId;
    private String payerName;
    private Long payeeId;
    private String payeeName;
    private LocalDateTime timestamp;
    private TransactionStatus transactionStatus;

    public TransactionDTO() {}

    public TransactionDTO(Transaction transaction) {
        this.transactionId = transaction.getId();
        this.payerId = transaction.getPayer().getId();
        this.payerName = transaction.getPayer().getName();
        this.payeeId = transaction.getPayee().getId();
        this.payeeName = transaction.getPayee().getName();
        this.timestamp = transaction.getTimestamp();
        this.transactionStatus = transaction.getStatus();
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

}
