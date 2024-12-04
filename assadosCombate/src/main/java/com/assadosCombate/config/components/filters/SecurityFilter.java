package com.assadosCombate.config.components.filters;

import com.assadosCombate.constants.PublicRoutes;
import com.assadosCombate.repositories.UserRepository;
import com.assadosCombate.services.impl.AuthenticationServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");

        if (PublicRoutes.isPublic(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        var token = this.recoverToken(request);

        if (token == null) {
            handleAuthenticationException(response, "Usuário não autenticado");
            return;
        }

        var login = authenticationServiceImpl.validateToken(token);
        if (login != null) {
            UserDetails user = userRepository.findByLogin(login);

            if (user != null) {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                handleAuthenticationException(response, "Usuário não encontrado.");
                return;
            }
        } else {
            handleAuthenticationException(response, "Token inválido.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        return authHeader.replace("Bearer ", "");
    }

    private void handleAuthenticationException(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        response.getWriter().flush();
    }
}
