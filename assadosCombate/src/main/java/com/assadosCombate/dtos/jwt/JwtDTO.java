package com.assadosCombate.dtos.jwt;

import com.assadosCombate.dtos.entities.UserDTO;
import com.assadosCombate.dtos.groups.AppGroup;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtDTO {

    @JsonView(AppGroup.Response.class)
    private String accessToken;

    @JsonView(AppGroup.Response.class)
    private String refreshToken;

    @JsonView(AppGroup.Response.class)
    private UserDTO usuario;
}
