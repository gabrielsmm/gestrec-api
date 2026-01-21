package com.gabrielsmm.gestrec.adapter.web.mapper;

import com.gabrielsmm.gestrec.adapter.web.dto.UsuarioRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.UsuarioResponse;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import com.gabrielsmm.gestrec.domain.model.UsuarioPerfil;
import org.mapstruct.Mapper;

@Mapper
public interface UsuarioDTOMapper {

    default Usuario toDomain(UsuarioRequest req) {
        if (req == null) return null;
        return Usuario.novoUsuario(req.nome(), req.email(), req.senha(), UsuarioPerfil.USER);
    }

    UsuarioResponse toResponse(Usuario domain);

}
