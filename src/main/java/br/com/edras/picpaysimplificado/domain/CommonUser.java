package br.com.edras.picpaysimplificado.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
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

}
