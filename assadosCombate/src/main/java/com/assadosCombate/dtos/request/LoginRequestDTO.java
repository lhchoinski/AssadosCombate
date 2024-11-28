package com.assadosCombate.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    @NotNull(message = "Preenchimento obrigatório")
    private String login;

    @NotNull(message = "Preenchimento obrigatório")
    private String password;
}
