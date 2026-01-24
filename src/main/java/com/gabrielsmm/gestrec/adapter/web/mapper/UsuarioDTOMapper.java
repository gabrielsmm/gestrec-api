package com.gabrielsmm.gestrec.adapter.web.mapper;

import com.gabrielsmm.gestrec.adapter.web.dto.UsuarioRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.UsuarioResponse;
import com.gabrielsmm.gestrec.application.usecase.dto.CadastrarUsuarioCommand;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper
public interface UsuarioDTOMapper {

    default CadastrarUsuarioCommand toCommand(UsuarioRequest req) {
        if (req == null) return null;

        return new CadastrarUsuarioCommand(req.nome(), req.email(), req.senha());
    }

    UsuarioResponse toResponse(Usuario domain);

}
