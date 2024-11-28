package com.assadosCombate.constants;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class PublicRoutes {
    public static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/", "GET"),
            new AntPathRequestMatcher("/manifest.json", "GET"),
            new AntPathRequestMatcher("/index.html"),
            new AntPathRequestMatcher("/actuator/**", "GET"),
            new AntPathRequestMatcher("/assets/**"),
            new AntPathRequestMatcher("/favicon.ico", "GET"),
            new AntPathRequestMatcher("/swagger-ui/**", "GET"),
            new AntPathRequestMatcher("/v3/api-docs", "GET"),
            new AntPathRequestMatcher("/v3/api-docs/**", "GET"),
            new AntPathRequestMatcher("/api", "GET"),
            new AntPathRequestMatcher("/api/auth/login", "POST"),
            new AntPathRequestMatcher("/api/auth/logout", "POST"),
            new AntPathRequestMatcher("/api/auth/check-token", "POST"),
            new AntPathRequestMatcher("/api/auth/refresh-token", "POST"),

            //Rotas front
            new AntPathRequestMatcher("/auth/login", "GET"),

            new AntPathRequestMatcher("/recibos", "GET"),
            new AntPathRequestMatcher("/recibos/cadastrar", "GET"),
            new AntPathRequestMatcher("/recibos/editar/{id}", "GET"),
            new AntPathRequestMatcher("/recibos/visualizar/{id}", "GET"),

            new AntPathRequestMatcher("/gestao-de-pessoas/pessoas", "GET"),
            new AntPathRequestMatcher("/pessoas/cadastrar", "GET"),
            new AntPathRequestMatcher("/pessoas/editar/{id}", "GET"),
            new AntPathRequestMatcher("/pessoas/visualizar/{id}", "GET"),

            new AntPathRequestMatcher("/fontes", "GET"),
            new AntPathRequestMatcher("/fontes/cadastrar", "GET"),
            new AntPathRequestMatcher("/fontes/editar/{id}", "GET"),
            new AntPathRequestMatcher("/fontes/visualizar/{id}", "GET"),

            new AntPathRequestMatcher("/relatorios/relatorio-mensal/periodo", "GET"),

            new AntPathRequestMatcher("/impostos", "GET"),
            new AntPathRequestMatcher("/impostos/cadastrar", "GET"),
            new AntPathRequestMatcher("/impostos/editar/{id}", "GET"),
            new AntPathRequestMatcher("/impostos/visualizar/{id}", "GET"),

            new AntPathRequestMatcher("/administrador/usuarios", "GET"),
            new AntPathRequestMatcher("/administrador/usuario/cadastrar", "GET"),
            new AntPathRequestMatcher("/administrador/usuario/editar/{id}", "GET"),
            new AntPathRequestMatcher("/administrador/usuario/visualizar/{id}", "GET")

    );

    public static boolean isPublic(HttpServletRequest request) {
        return PUBLIC_URLS.matches(request);
    }
}
