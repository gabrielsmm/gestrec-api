package com.gabrielsmm.gestrec.infrastructure.config.beans;

import com.gabrielsmm.gestrec.infrastructure.security.BCryptPasswordEncoderAdapter;
import com.gabrielsmm.gestrec.infrastructure.security.jwt.JwtTokenServiceAdapter;
import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepositoryPort;
import com.gabrielsmm.gestrec.application.port.service.PasswordEncoderPort;
import com.gabrielsmm.gestrec.application.port.service.TokenServicePort;
import com.gabrielsmm.gestrec.application.usecase.AuthCommandUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthBeansConfig {

    @Bean
    @ConditionalOnMissingBean
    public AuthCommandUseCase authCommandUseCase(UsuarioRepositoryPort usuarioRepository,
                                                 PasswordEncoderPort passwordEncoder,
                                                 TokenServicePort tokenService) {
        return new AuthCommandUseCase(usuarioRepository, passwordEncoder, tokenService);
    }

    @Bean
    public PasswordEncoderPort passwordEncoderPort(PasswordEncoder passwordEncoder) {
        return new BCryptPasswordEncoderAdapter(passwordEncoder);
    }

    @Bean
    public JwtTokenServiceAdapter jwtTokenServiceAdapter() {
        return new JwtTokenServiceAdapter();
    }

}
