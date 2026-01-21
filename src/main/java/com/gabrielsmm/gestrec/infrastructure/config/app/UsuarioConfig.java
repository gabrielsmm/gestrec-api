package com.gabrielsmm.gestrec.infrastructure.config.app;

import com.gabrielsmm.gestrec.adapter.persistence.mapper.UsuarioEntityMapper;
import com.gabrielsmm.gestrec.adapter.persistence.repository.JpaUsuarioRepositoryAdapter;
import com.gabrielsmm.gestrec.adapter.persistence.repository.SpringDataUsuarioRepo;
import com.gabrielsmm.gestrec.adapter.web.mapper.UsuarioDTOMapper;
import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepository;
import com.gabrielsmm.gestrec.application.port.service.PasswordEncoderPort;
import com.gabrielsmm.gestrec.application.usecase.UsuarioCommandUseCase;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public UsuarioCommandUseCase usuarioCommandUseCase(UsuarioRepository repository, PasswordEncoderPort passwordEncoder) {
        return new UsuarioCommandUseCase(repository, passwordEncoder);
    }

    @Bean
    public UsuarioRepository usuarioRepository(SpringDataUsuarioRepo springDataUsuarioRepo) {
        return new JpaUsuarioRepositoryAdapter(springDataUsuarioRepo);
    }

    @Bean
    public UsuarioDTOMapper usuarioDTOMapper(AutowireCapableBeanFactory beanFactory) {
        UsuarioDTOMapper mapper = Mappers.getMapper(UsuarioDTOMapper.class);
        beanFactory.autowireBean(mapper);
        return mapper;
    }

    @Bean
    public UsuarioEntityMapper usuarioEntityMapper(AutowireCapableBeanFactory beanFactory) {
        UsuarioEntityMapper mapper = Mappers.getMapper(UsuarioEntityMapper.class);
        beanFactory.autowireBean(mapper);
        return mapper;
    }

}
