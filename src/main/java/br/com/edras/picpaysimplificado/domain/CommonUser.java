package br.com.edras.picpaysimplificado.domain;

import br.com.edras.picpaysimplificado.domain.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COMMON")
public class CommonUser extends User {

    @Column(unique = true)
    private String cpf;

    public CommonUser() {super();}

    public CommonUser(String name, String email, String password, String cpf) {
        super(name, email, password);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public UserType getUserType() {
        return UserType.COMMON;
    }

}
