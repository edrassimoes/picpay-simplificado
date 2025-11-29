package br.com.edras.picpaysimplificado.dto.wallet;

import br.com.edras.picpaysimplificado.domain.Wallet;
import jakarta.validation.constraints.Positive;

public class AmountDTO {

    @Positive
    private Double amount;

    public AmountDTO() {}

    public AmountDTO(Wallet wallet) {
        this.amount = wallet.getBalance();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
