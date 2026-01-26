package com.gabrielsmm.gestrec.infrastructure.config.beans;

import com.gabrielsmm.gestrec.adapter.persistence.mapper.TipoRecursoEntityMapper;
import com.gabrielsmm.gestrec.adapter.persistence.repository.JpaTipoRecursoRepositoryAdapter;
import com.gabrielsmm.gestrec.adapter.persistence.repository.SpringDataTipoRecursoRepo;
import com.gabrielsmm.gestrec.adapter.web.mapper.TipoRecursoDTOMapper;
import com.gabrielsmm.gestrec.application.port.repository.TipoRecursoRepositoryPort;
import com.gabrielsmm.gestrec.application.usecase.TipoRecursoCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.TipoRecursoQueryUseCase;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TipoRecursoBeansConfig {

    @Bean
    public TipoRecursoCommandUseCase tipoRecursoCommandUseCase(TipoRecursoRepositoryPort repository) {
        return new TipoRecursoCommandUseCase(repository);
    }

    @Bean
    public TipoRecursoQueryUseCase tipoRecursoQueryUseCase(TipoRecursoRepositoryPort repository) {
        return new TipoRecursoQueryUseCase(repository);
    }

    @Bean
    public TipoRecursoRepositoryPort tipoRecursoRepository(SpringDataTipoRecursoRepo springDataTipoRecursoRepo, TipoRecursoEntityMapper tipoRecursoEntityMapper) {
        return new JpaTipoRecursoRepositoryAdapter(springDataTipoRecursoRepo, tipoRecursoEntityMapper);
    }

    @Bean
    public TipoRecursoDTOMapper tipoRecursoDTOMapper(AutowireCapableBeanFactory beanFactory) {
        TipoRecursoDTOMapper mapper = Mappers.getMapper(TipoRecursoDTOMapper.class);
        beanFactory.autowireBean(mapper);
        return mapper;
    }

    @Bean
    public TipoRecursoEntityMapper tipoRecursoEntityMapper(AutowireCapableBeanFactory beanFactory) {
        TipoRecursoEntityMapper mapper = Mappers.getMapper(TipoRecursoEntityMapper.class);
        beanFactory.autowireBean(mapper);
        return mapper;
    }

}
