package com.assadosCombate.services;

import com.assadosCombate.dtos.entities.UserDTO;
import com.assadosCombate.dtos.jwt.JwtDTO;
import com.assadosCombate.dtos.request.LoginRequestDTO;
import com.assadosCombate.entities.User;
import com.assadosCombate.dtos.response.AuthenticatedUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    AuthenticatedUserDTO getCurrentUserDTO();
    User getCurrentUser();
    JwtDTO getAccessToken(LoginRequestDTO loginRequestDTO);
    String validateToken(String token);
    JwtDTO getRefreshToken(String s);
    UserDTO getAuthenticatedUser();
    void loadAuthenticatedUser();
}
