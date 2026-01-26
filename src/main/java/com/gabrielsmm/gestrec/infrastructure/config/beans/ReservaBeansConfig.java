package com.gabrielsmm.gestrec.infrastructure.config.beans;

import com.gabrielsmm.gestrec.adapter.persistence.mapper.ReservaEntityMapper;
import com.gabrielsmm.gestrec.adapter.persistence.repository.JpaReservaRepositoryAdapter;
import com.gabrielsmm.gestrec.adapter.persistence.repository.SpringDataReservaRepo;
import com.gabrielsmm.gestrec.adapter.web.mapper.ReservaDTOMapper;
import com.gabrielsmm.gestrec.application.port.repository.RecursoRepositoryPort;
import com.gabrielsmm.gestrec.application.port.repository.ReservaRepositoryPort;
import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepositoryPort;
import com.gabrielsmm.gestrec.application.usecase.ReservaCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.ReservaQueryUseCase;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservaBeansConfig {

    @Bean
    public ReservaCommandUseCase reservaCommandUseCase(
            ReservaRepositoryPort reservaRepository,
            RecursoRepositoryPort recursoRepository,
            UsuarioRepositoryPort usuarioRepository
    ) {
        return new ReservaCommandUseCase(reservaRepository, recursoRepository, usuarioRepository);
    }

    @Bean
    public ReservaQueryUseCase reservaQueryUseCase(ReservaRepositoryPort reservaRepository) {
        return new ReservaQueryUseCase(reservaRepository);
    }

    @Bean
    public ReservaRepositoryPort reservaRepository(SpringDataReservaRepo springDataReservaRepo, ReservaEntityMapper reservaEntityMapper) {
        return new JpaReservaRepositoryAdapter(springDataReservaRepo, reservaEntityMapper);
    }

    @Bean
    public ReservaDTOMapper reservaDTOMapper(AutowireCapableBeanFactory beanFactory) {
        ReservaDTOMapper mapper = Mappers.getMapper(ReservaDTOMapper.class);
        beanFactory.autowireBean(mapper);
        return mapper;
    }

    @Bean
    public ReservaEntityMapper reservaEntityMapper(AutowireCapableBeanFactory beanFactory) {
        ReservaEntityMapper mapper = Mappers.getMapper(ReservaEntityMapper.class);
        beanFactory.autowireBean(mapper);
        return mapper;
    }

}
