package com.gabrielsmm.gestrec.adapter.web.mapper;

import com.gabrielsmm.gestrec.adapter.web.dto.RecursoRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.RecursoResponse;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import org.mapstruct.Mapper;

@Mapper(uses = {TipoRecursoDTOMapper.class})
public interface RecursoDTOMapper {

    default Recurso toDomain(RecursoRequest req) {
        if (req == null) return null;
        TipoRecurso tipo = toTipoRecurso(req.tipoRecursoId());
        return Recurso.novoRecurso(req.nome(), req.localizacao(), tipo);
    }

    RecursoResponse toResponse(Recurso domain);

    default TipoRecurso toTipoRecurso(Long id) {
        return id != null ? TipoRecurso.apenasComId(id) : null;
    }

    default boolean toAtivo(Boolean ativo) {
        return ativo == null || ativo;
    }

}
