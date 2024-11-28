package com.assadosCombate.services;

import com.assadosCombate.dtos.entities.UsuarioDTO;
import com.assadosCombate.dtos.jwt.JwtDTO;
import com.assadosCombate.dtos.request.LoginRequestDTO;
import com.assadosCombate.entities.Usuario;
import com.assadosCombate.response.AuthenticatedUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    AuthenticatedUserDTO getCurrentUserDTO();
    Usuario getCurrentUser();
    JwtDTO getAccessToken(LoginRequestDTO loginRequestDTO);
    String validateToken(String token);
    JwtDTO getRefreshToken(String s);
    UsuarioDTO getAuthenticatedUser();
    void loadAuthenticatedUser();
}
