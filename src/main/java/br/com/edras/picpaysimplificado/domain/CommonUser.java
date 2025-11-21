package br.com.edras.picpaysimplificado.domain;

public class CommonUser extends User {

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
