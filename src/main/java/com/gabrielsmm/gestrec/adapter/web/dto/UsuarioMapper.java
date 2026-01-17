package com.gabrielsmm.gestrec.adapter.web.dto;

import com.gabrielsmm.gestrec.domain.model.Usuario;
import com.gabrielsmm.gestrec.domain.model.UsuarioPerfil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    default Usuario toDomain(UsuarioRequest req) {
        if (req == null) return null;
        return new Usuario(req.nome(), req.email(), req.senha(), UsuarioPerfil.USER);
    }

    UsuarioResponse toResponse(Usuario domain);

}
