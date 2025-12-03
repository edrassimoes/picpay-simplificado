package br.com.edras.picpaysimplificado.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransactionRequestDTO {

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    private Long payerId;

    @NotNull
    private Long payeeId;

    public TransactionRequestDTO() {}

    public TransactionRequestDTO(Double amount, Long payerId, Long payeeId) {
        this.amount = amount;
        this.payerId = payerId;
        this.payeeId = payeeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

}
