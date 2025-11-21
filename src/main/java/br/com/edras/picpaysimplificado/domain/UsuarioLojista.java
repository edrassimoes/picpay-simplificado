package br.com.edras.picpaysimplificado.domain;

public class UsuarioLojista extends Usuario {

    private String cnpj;

    public UsuarioLojista() {super();}

    public UsuarioLojista(String nomeCompleto, String email, String senha, String cnpj) {
        super(nomeCompleto, email, senha);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

}
