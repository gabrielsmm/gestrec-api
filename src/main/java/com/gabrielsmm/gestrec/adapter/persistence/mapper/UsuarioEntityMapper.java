package com.gabrielsmm.gestrec.adapter.persistence.mapper;

import com.gabrielsmm.gestrec.adapter.persistence.entity.UsuarioEntity;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper
public interface UsuarioEntityMapper {

    default Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;
        return Usuario.reconstruir(entity.getId(), entity.getNome(), entity.getEmail(), entity.getSenha(), entity.getPerfil());
    }

    UsuarioEntity toEntity(Usuario usuario);

}
