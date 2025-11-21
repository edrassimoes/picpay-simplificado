package br.com.edras.picpaysimplificado.domain;

public class UsuarioComum extends Usuario {

    private String cpf;

    public UsuarioComum() {super();}

    public UsuarioComum(String nomeCompleto, String email, String senha, String cpf) {
        super(nomeCompleto, email, senha);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
