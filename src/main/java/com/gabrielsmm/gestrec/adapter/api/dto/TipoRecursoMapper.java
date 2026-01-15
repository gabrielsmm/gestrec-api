package com.gabrielsmm.gestrec.adapter.api.dto;

import com.gabrielsmm.gestrec.domain.model.TipoRecurso;

public class TipoRecursoMapper {

    public static TipoRecurso toDomain(TipoRecursoRequest req) {
        return new TipoRecurso(null, req.getNome(), req.getDescricao());
    }

    public static TipoRecursoResponse toResponse(TipoRecurso domain) {
        if (domain == null) return null;
        return new TipoRecursoResponse(domain.getId(), domain.getNome(), domain.getDescricao());
    }

}
