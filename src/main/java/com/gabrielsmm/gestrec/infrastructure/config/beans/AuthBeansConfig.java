package com.gabrielsmm.gestrec.infrastructure.config.beans;

import com.gabrielsmm.gestrec.adapter.security.BCryptPasswordEncoderAdapter;
import com.gabrielsmm.gestrec.adapter.security.jwt.JwtTokenServiceAdapter;
import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepository;
import com.gabrielsmm.gestrec.application.port.service.PasswordEncoderPort;
import com.gabrielsmm.gestrec.application.port.service.TokenService;
import com.gabrielsmm.gestrec.application.usecase.AuthCommandUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthBeansConfig {

    @Bean
    @ConditionalOnMissingBean
    public AuthCommandUseCase authCommandUseCase(UsuarioRepository usuarioRepository,
                                                 PasswordEncoderPort passwordEncoder,
                                                 TokenService tokenService) {
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
