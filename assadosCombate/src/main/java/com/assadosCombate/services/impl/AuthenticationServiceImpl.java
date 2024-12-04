package com.assadosCombate.services.impl;

import com.assadosCombate.dtos.entities.UserDTO;
import com.assadosCombate.dtos.jwt.JwtDTO;
import com.assadosCombate.dtos.request.LoginRequestDTO;
import com.assadosCombate.entities.User;
import com.assadosCombate.exeptions.AuthenticationException;
import com.assadosCombate.exeptions.BadRequestException;
import com.assadosCombate.mappers.UserMapper;
import com.assadosCombate.repositories.UserRepository;
import com.assadosCombate.dtos.response.AuthenticatedUserDTO;
import com.assadosCombate.services.AuthenticationService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static com.assadosCombate.constants.Headers.ISSUER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    @Value("${api.security.token.accessTokenExpiration}")
    private Integer timeExpirationToken;

    @Value("${api.security.token.refreshTokenExpiration}")
    private Integer timeExpirationRefreshToken;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Getter
    private UserDTO currentUserDTO = null;
    @Getter
    private User currentUser = null;

    // Implementação de loadUserByUsername
    @Override
    public User loadUserByUsername(String login) throws AuthenticationException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new AuthenticationException("Usuário inexistente ou senha inválida");
        }
        return user;
    }

    // Implementação de getAuthenticatedUserDTO
    @Override
    public AuthenticatedUserDTO getCurrentUserDTO() {
        loadAuthenticatedUser();
        // Converte Usuario para AuthenticatedUserDTO
        if (currentUser != null) {
            return new AuthenticatedUserDTO(
                    currentUser.getId(),
                    currentUser.getLogin(),
                    currentUser.getEmail(),
                    currentUser.getRole().getRole()
            );
        }
        return null; // Retorna null ou lança uma exceção dependendo da sua lógica
    }

    @Override
    @Transactional
    public UserDTO getAuthenticatedUser() {
        loadAuthenticatedUser();
        if (currentUserDTO == null) {
            throw new BadCredentialsException("Token inválido");
        }
        return currentUserDTO;
    }

    // Implementação de loadAuthenticatedUser
    @Override
    public void loadAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialsException("Usuário não autenticado");
        }

        String username = authentication.getName();
        Optional<User> optionalUsuario = Optional.ofNullable(userRepository.findByLogin(username));

        if (optionalUsuario.isPresent()) {
            currentUser = optionalUsuario.get();
            currentUserDTO = compileByEntity(currentUser); // Aqui você está convertendo para UsuarioDTO
        }
    }

    private UserDTO compileByEntity(User user) {
        return userMapper.toDto(user);
    }

    @Override
    public JwtDTO getAccessToken(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByLogin(loginRequestDTO.getLogin());
        if (user == null) {
            throw new BadRequestException("Usuário não encontrado!");
        }

        UserDTO userDTO = userMapper.toDto(user);
        return JwtDTO.builder()
                .accessToken(generateTokenJwt(user, timeExpirationToken))
                .refreshToken(generateTokenJwt(user, timeExpirationRefreshToken))
                .usuario(userDTO)
                .build();
    }

    public String generateTokenJwt(User user, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate(expiration))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao tentar gerar o token! " + exception.getMessage());
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            log.info("Token inválido: {}", exception.getMessage());
            return null; // ou lance uma exceção
        }
    }

    @Override
    public JwtDTO getRefreshToken(String refreshToken) {
        String login = validateToken(refreshToken);
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new BadRequestException("Usuário não encontrado!");
        }

        UserDTO userDTO = userMapper.toDto(user);

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtDTO.builder()
                .accessToken(generateTokenJwt(user, timeExpirationToken))
                .refreshToken(generateTokenJwt(user, timeExpirationRefreshToken))
                .usuario(userDTO)
                .build();
    }

    private Instant generateExpirationDate(Integer expiration) {
        return LocalDateTime.now()
                .plusHours(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
