package br.com.edras.picpaysimplificado.dto.auth;

public class AuthResponseDTO {

    private String token;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
