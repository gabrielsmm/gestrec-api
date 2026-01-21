package com.gabrielsmm.gestrec.adapter.web.mapper;

import com.gabrielsmm.gestrec.adapter.web.dto.TipoRecursoRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.TipoRecursoResponse;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import org.mapstruct.Mapper;

@Mapper
public interface TipoRecursoDTOMapper {

    default TipoRecurso toDomain(TipoRecursoRequest req) {
        if (req == null) return null;
        return TipoRecurso.novoTipoRecurso(req.nome(), req.descricao());
    }

    TipoRecursoResponse toResponse(TipoRecurso domain);

}
