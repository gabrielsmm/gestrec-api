package com.gabrielsmm.gestrec.adapter.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String timestamp = OffsetDateTime.now().toString();
        String path = request.getRequestURI();
        String json = String.format(
                "{\"timestamp\":\"%s\",\"status\":401,\"error\":\"Não autorizado\",\"message\":\"%s\",\"path\":\"%s\",\"errors\":{}}",
                timestamp,
                "Acesso não autorizado",
                path
        );

        response.getWriter().write(json);
    }
}

