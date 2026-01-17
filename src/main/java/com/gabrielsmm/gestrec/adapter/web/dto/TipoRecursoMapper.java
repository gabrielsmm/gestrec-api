package com.gabrielsmm.gestrec.adapter.web.dto;

import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoRecursoMapper {

    default TipoRecurso toDomain(TipoRecursoRequest req) {
        if (req == null) return null;
        return new TipoRecurso(req.getNome(), req.getDescricao());
    }

    TipoRecursoResponse toResponse(TipoRecurso domain);

}
