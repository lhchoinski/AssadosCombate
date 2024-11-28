package com.assadosCombate.config.components.filters;

import com.assadosCombate.constants.PublicRoutes;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@SuppressWarnings("NullableProblems")
@Component
@RequiredArgsConstructor
@Slf4j
public class PublicRoutesAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        boolean isPublic = PublicRoutes.isPublic(request);

        request.setAttribute("isPublic", isPublic);

        log.info(":::::> LOAD PublicRoutesAuthenticationFilter | isPublic {}", isPublic);

        filterChain.doFilter(request, response);
    }
}
