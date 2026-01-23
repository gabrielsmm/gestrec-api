package com.gabrielsmm.gestrec.infrastructure.config.app;

import com.gabrielsmm.gestrec.adapter.persistence.mapper.TipoRecursoEntityMapper;
import com.gabrielsmm.gestrec.adapter.persistence.repository.JpaTipoRecursoRepositoryAdapter;
import com.gabrielsmm.gestrec.adapter.persistence.repository.SpringDataTipoRecursoRepo;
import com.gabrielsmm.gestrec.adapter.web.mapper.TipoRecursoDTOMapper;
import com.gabrielsmm.gestrec.application.port.repository.TipoRecursoRepository;
import com.gabrielsmm.gestrec.application.usecase.tiporecurso.TipoRecursoCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.tiporecurso.TipoRecursoQueryUseCase;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TipoRecursoBeansConfig {

    @Bean
    public TipoRecursoCommandUseCase tipoRecursoCommandUseCase(TipoRecursoRepository repository) {
        return new TipoRecursoCommandUseCase(repository);
    }

    @Bean
    public TipoRecursoQueryUseCase tipoRecursoQueryUseCase(TipoRecursoRepository repository) {
        return new TipoRecursoQueryUseCase(repository);
    }

    @Bean
    public TipoRecursoRepository tipoRecursoRepository(SpringDataTipoRecursoRepo springDataTipoRecursoRepo, TipoRecursoEntityMapper tipoRecursoEntityMapper) {
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
