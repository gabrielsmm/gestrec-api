package com.gabrielsmm.gestrec.adapter.web.mapper;

import com.gabrielsmm.gestrec.adapter.web.dto.RecursoRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.RecursoResponse;
import com.gabrielsmm.gestrec.application.usecase.recurso.AtualizarRecursoCommand;
import com.gabrielsmm.gestrec.application.usecase.recurso.CriarRecursoCommand;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import org.mapstruct.Mapper;

@Mapper
public interface RecursoDTOMapper {

    default CriarRecursoCommand toCommand(RecursoRequest req) {
        if (req == null) return null;

        return new CriarRecursoCommand(req.nome(), req.localizacao(), req.tipoRecursoId());
    }

    default AtualizarRecursoCommand toCommand(Long recursoId, RecursoRequest req) {
        if (req == null) return null;

        return new AtualizarRecursoCommand(recursoId, req.nome(), req.localizacao(), req.tipoRecursoId());
    }

    RecursoResponse toResponse(Recurso domain);

}
