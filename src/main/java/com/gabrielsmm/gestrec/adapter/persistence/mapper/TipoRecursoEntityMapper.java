package com.gabrielsmm.gestrec.adapter.persistence.mapper;

import com.gabrielsmm.gestrec.adapter.persistence.entity.TipoRecursoEntity;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import org.mapstruct.Mapper;

@Mapper
public interface TipoRecursoEntityMapper {

    default TipoRecurso toDomain(TipoRecursoEntity entity) {
        if (entity == null) return null;
        return TipoRecurso.reconstruir(entity.getId(), entity.getNome(), entity.getDescricao());
    }

    TipoRecursoEntity toEntity(TipoRecurso tipoRecurso);

}
