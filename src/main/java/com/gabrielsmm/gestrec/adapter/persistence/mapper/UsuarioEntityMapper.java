package com.gabrielsmm.gestrec.adapter.persistence.mapper;

import com.gabrielsmm.gestrec.adapter.persistence.entity.UsuarioEntity;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper
public interface UsuarioEntityMapper {

    @Mapping(target = "nome", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "perfil", ignore = true)
    Usuario toDomain(UsuarioEntity usuarioEntity);

    UsuarioEntity toEntity(Usuario usuario);

    @ObjectFactory
    default Usuario createUsuario(UsuarioEntity e) {
        if (e == null) return null;
        return new Usuario(e.getId(), e.getNome(), e.getEmail(), e.getSenha(), e.getPerfil());
    }

}
