package br.com.edras.picpaysimplificado.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class UserUpdateDTO {

    @Pattern(regexp = "^(?!\\s*$).+", message = "Nome não pode estar em branco")
    private String name;

    @Email(message = "Email inválido")
    private String email;

    @Pattern(regexp = "^(?!\\s*$).+", message = "Senha não pode estar em branco")
    private String password;

    public UserUpdateDTO() {}

    public UserUpdateDTO(String name, String email, String password) {
        if (name != null) {
            this.name = name;
        }

        if (email != null) {
            this.email = email;
        }

        if (password != null) {
            this.password = password;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
