package br.com.edras.picpaysimplificado.dto.user;

import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.domain.enums.UserType;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private UserType userType;

    public UserResponseDTO() {}

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = user.getUserType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
