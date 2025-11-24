package br.com.edras.picpaysimplificado.dto.user;

import jakarta.validation.constraints.Email;

public class UserUpdateDTO {

    private String name;

    @Email(message = "Email inválido")
    private String email;

    private String password;

    public UserUpdateDTO() {}

    public UserUpdateDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
