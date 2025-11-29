package br.com.edras.picpaysimplificado.dto.wallet;

import br.com.edras.picpaysimplificado.domain.Wallet;

public class WalletResponseDTO {

    private Long id;
    private Double balance;

    public WalletResponseDTO() {}

    public WalletResponseDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.balance = wallet.getBalance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
