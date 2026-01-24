package com.gabrielsmm.gestrec.adapter.web.mapper;

import com.gabrielsmm.gestrec.adapter.web.dto.TipoRecursoRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.TipoRecursoResponse;
import com.gabrielsmm.gestrec.application.usecase.dto.AtualizarTipoRecursoCommand;
import com.gabrielsmm.gestrec.application.usecase.dto.CriarTipoRecursoCommand;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import org.mapstruct.Mapper;

@Mapper
public interface TipoRecursoDTOMapper {

    default CriarTipoRecursoCommand toCommand(TipoRecursoRequest req) {
        if (req == null) return null;

        return new CriarTipoRecursoCommand(req.nome(), req.descricao());
    }

    default AtualizarTipoRecursoCommand toCommand(Long tipoRecursoId, TipoRecursoRequest req) {
        if (req == null) return null;

        return new AtualizarTipoRecursoCommand(tipoRecursoId, req.nome(), req.descricao());
    }

    TipoRecursoResponse toResponse(TipoRecurso domain);

}
