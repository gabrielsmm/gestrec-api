package com.gabrielsmm.gestrec.infrastructure.config.app;

import com.gabrielsmm.gestrec.adapter.persistence.mapper.RecursoEntityMapper;
import com.gabrielsmm.gestrec.adapter.persistence.repository.JpaRecursoRepositoryAdapter;
import com.gabrielsmm.gestrec.adapter.persistence.repository.SpringDataRecursoRepo;
import com.gabrielsmm.gestrec.adapter.web.mapper.RecursoDTOMapper;
import com.gabrielsmm.gestrec.application.port.repository.RecursoRepository;
import com.gabrielsmm.gestrec.application.port.repository.TipoRecursoRepository;
import com.gabrielsmm.gestrec.application.usecase.RecursoCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.RecursoQueryUseCase;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecursoConfig {

    @Bean
    public RecursoCommandUseCase recursoCommandUseCase(
            RecursoRepository recursoRepository,
            TipoRecursoRepository tipoRecursoRepository
    ) {
        return new RecursoCommandUseCase(recursoRepository, tipoRecursoRepository);
    }

    @Bean
    public RecursoQueryUseCase recursoQueryUseCase(RecursoRepository recursoRepository) {
        return new RecursoQueryUseCase(recursoRepository);
    }

    @Bean
    public RecursoRepository recursoRepository(SpringDataRecursoRepo springDataRecursoRepo, RecursoEntityMapper recursoEntityMapper) {
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
