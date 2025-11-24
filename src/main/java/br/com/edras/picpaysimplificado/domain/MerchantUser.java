package br.com.edras.picpaysimplificado.domain;

import br.com.edras.picpaysimplificado.domain.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MERCHANT")
public class MerchantUser extends User {

    @Column(unique = true)
    private String cnpj;

    public MerchantUser() {super();}

    public MerchantUser(String name, String email, String password, String cnpj) {
        super(name, email, password);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public UserType getUserType() {
        return UserType.MERCHANT;
    }

}
