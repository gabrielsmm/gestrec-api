package com.gabrielsmm.gestrec.infrastructure.config.beans;

import com.gabrielsmm.gestrec.adapter.persistence.mapper.RecursoEntityMapper;
import com.gabrielsmm.gestrec.adapter.persistence.repository.JpaRecursoRepositoryAdapter;
import com.gabrielsmm.gestrec.adapter.persistence.repository.SpringDataRecursoRepo;
import com.gabrielsmm.gestrec.adapter.web.mapper.RecursoDTOMapper;
import com.gabrielsmm.gestrec.application.port.repository.RecursoRepositoryPort;
import com.gabrielsmm.gestrec.application.port.repository.TipoRecursoRepositoryPort;
import com.gabrielsmm.gestrec.application.usecase.RecursoCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.RecursoQueryUseCase;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecursoBeansConfig {

    @Bean
    public RecursoCommandUseCase recursoCommandUseCase(
            RecursoRepositoryPort recursoRepository,
            TipoRecursoRepositoryPort tipoRecursoRepository
    ) {
        return new RecursoCommandUseCase(recursoRepository, tipoRecursoRepository);
    }

    @Bean
    public RecursoQueryUseCase recursoQueryUseCase(RecursoRepositoryPort recursoRepository) {
        return new RecursoQueryUseCase(recursoRepository);
    }

    @Bean
    public RecursoRepositoryPort recursoRepository(SpringDataRecursoRepo springDataRecursoRepo, RecursoEntityMapper recursoEntityMapper) {
        return new JpaRecursoRepositoryAdapter(springDataRecursoRepo, recursoEntityMapper);
    }

    @Bean
    public RecursoDTOMapper recursoDTOMapper(AutowireCapableBeanFactory beanFactory) {
        RecursoDTOMapper mapper = Mappers.getMapper(RecursoDTOMapper.class);
        beanFactory.autowireBean(mapper);
        return mapper;
    }

    @Bean
    public RecursoEntityMapper recursoEntityMapper(AutowireCapableBeanFactory beanFactory) {
        RecursoEntityMapper mapper = Mappers.getMapper(RecursoEntityMapper.class);
        beanFactory.autowireBean(mapper);
        return mapper;
    }

}
