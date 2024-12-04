package com.assadosCombate.dtos.entities;

import com.assadosCombate.dtos.groups.AppGroup;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class UserDTO implements Serializable {

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private UUID id;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "Preenchimento obrigatório", groups = {AppGroup.Request.class})
    private String nome;

    @JsonView({AppGroup.Request.class})
    @NotNull(message = "Preenchimento obrigatório")
    private String password;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "Preenchimento obrigatório")
    private String login;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    private String email;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class, AppGroup.Request.class})
    @NotNull(message = "Preenchimento obrigatório")
    private String role;

    @JsonView({AppGroup.Response.class, AppGroup.ResponsePage.class})
    private Boolean status;


}