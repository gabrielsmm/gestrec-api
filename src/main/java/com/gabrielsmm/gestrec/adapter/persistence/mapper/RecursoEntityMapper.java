package com.gabrielsmm.gestrec.adapter.persistence.mapper;

import com.gabrielsmm.gestrec.adapter.persistence.entity.RecursoEntity;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TipoRecursoEntityMapper.class})
public interface RecursoEntityMapper {

    default Recurso toDomain(RecursoEntity entity) {
        if (entity == null) return null;

        var tipo = entity.getTipoRecurso() == null
                ? null
                : Mappers.getMapper(TipoRecursoEntityMapper.class).toDomain(entity.getTipoRecurso());

        return Recurso.reconstruir(entity.getId(), entity.getNome(), entity.getLocalizacao(), entity.isAtivo(), tipo);
    }

    RecursoEntity toEntity(Recurso domain);
}