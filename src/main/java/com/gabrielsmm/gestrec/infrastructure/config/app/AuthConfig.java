package com.gabrielsmm.gestrec.infrastructure.config.app;

import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepository;
import com.gabrielsmm.gestrec.application.port.service.PasswordEncoderPort;
import com.gabrielsmm.gestrec.application.port.service.TokenService;
import com.gabrielsmm.gestrec.application.usecase.AuthCommandUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    @ConditionalOnMissingBean
    public AuthCommandUseCase authCommandUseCase(UsuarioRepository usuarioRepository,
                                                 PasswordEncoderPort passwordEncoder,
                                                 TokenService tokenService) {
        return new AuthCommandUseCase(usuarioRepository, passwordEncoder, tokenService);
    }

}
