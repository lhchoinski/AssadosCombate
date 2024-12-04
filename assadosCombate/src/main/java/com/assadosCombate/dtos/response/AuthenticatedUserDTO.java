package com.assadosCombate.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthenticatedUserDTO {

    private UUID id;
    private String username;
    private String email;
    private String role;

    public AuthenticatedUserDTO() {
    }

    public AuthenticatedUserDTO(UUID id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthenticatedUserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
