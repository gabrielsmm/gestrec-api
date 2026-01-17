package com.gabrielsmm.gestrec.adapter.web.dto;

import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {TipoRecursoMapper.class})
public interface RecursoMapper {

    default Recurso toDomain(RecursoRequest req) {
        if (req == null) return null;
        TipoRecurso tipo = toTipoRecurso(req.getTipoRecursoId());
        boolean ativo = toAtivo(req.getAtivo());
        return new Recurso(null, req.getNome(), req.getLocalizacao(), ativo, tipo);
    }

    default Recurso applyToDomain(RecursoRequest req, Recurso existing) {
        if (existing == null) return toDomain(req);
        String nome = req.getNome() != null ? req.getNome() : existing.getNome();
        String localizacao = req.getLocalizacao() != null ? req.getLocalizacao() : existing.getLocalizacao();
        boolean ativo = req.getAtivo() != null ? toAtivo(req.getAtivo()) : existing.isAtivo();
        TipoRecurso tipo = req.getTipoRecursoId() != null ? toTipoRecurso(req.getTipoRecursoId()) : existing.getTipoRecurso();
        return new Recurso(existing.getId(), nome, localizacao, ativo, tipo);
    }

    RecursoResponse toResponse(Recurso domain);

    default TipoRecurso toTipoRecurso(Long id) {
        return id != null ? new TipoRecurso(id, null, null) : null;
    }

    default boolean toAtivo(Boolean ativo) {
        return ativo != null ? ativo : true;
    }

}
