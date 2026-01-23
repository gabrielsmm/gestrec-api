package com.gabrielsmm.gestrec.infrastructure.config.app;

import com.gabrielsmm.gestrec.adapter.persistence.mapper.ReservaEntityMapper;
import com.gabrielsmm.gestrec.adapter.persistence.repository.JpaReservaRepositoryAdapter;
import com.gabrielsmm.gestrec.adapter.persistence.repository.SpringDataReservaRepo;
import com.gabrielsmm.gestrec.adapter.web.mapper.ReservaDTOMapper;
import com.gabrielsmm.gestrec.application.port.repository.RecursoRepository;
import com.gabrielsmm.gestrec.application.port.repository.ReservaRepository;
import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepository;
import com.gabrielsmm.gestrec.application.usecase.reserva.ReservaCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.reserva.ReservaQueryUseCase;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservaBeansConfig {

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

    @Bean
    public ReservaRepository reservaRepository(SpringDataReservaRepo springDataReservaRepo, ReservaEntityMapper reservaEntityMapper) {
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
