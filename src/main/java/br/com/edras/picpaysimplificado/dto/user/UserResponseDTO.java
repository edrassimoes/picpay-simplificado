package br.com.edras.picpaysimplificado.dto.user;

import br.com.edras.picpaysimplificado.domain.User;
import br.com.edras.picpaysimplificado.domain.enums.UserType;

public class UserResponseDTO {

    private Long id;
    private String name;
    private UserType userType;

    public UserResponseDTO() {}

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.userType = user.getUserType();
    }

    public UserResponseDTO(Long id, String name, UserType userType) {
        this.id = id;
        this.name = name;
        this.userType = userType;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
