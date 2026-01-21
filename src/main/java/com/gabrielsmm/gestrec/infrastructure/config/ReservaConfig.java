package com.gabrielsmm.gestrec.infrastructure.config;

import com.gabrielsmm.gestrec.application.usecase.ReservaCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.ReservaQueryUseCase;
import com.gabrielsmm.gestrec.domain.port.repository.RecursoRepository;
import com.gabrielsmm.gestrec.domain.port.repository.ReservaRepository;
import com.gabrielsmm.gestrec.domain.port.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservaConfig {

    @Bean
    public ReservaCommandUseCase reservaCommandUseCase(
            ReservaRepository reservaRepository,
            RecursoRepository recursoRepository,
            UsuarioRepository usuarioRepository
    ) {
        return new ReservaCommandUseCase(reservaRepository, recursoRepository, usuarioRepository);
    }

    @Bean
    public ReservaQueryUseCase reservaQueryUseCase(ReservaRepository reservaRepository) {
        return new ReservaQueryUseCase(reservaRepository);
    }

}
