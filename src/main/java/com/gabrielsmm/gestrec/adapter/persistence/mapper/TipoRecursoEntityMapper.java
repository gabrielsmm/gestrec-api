package com.gabrielsmm.gestrec.adapter.persistence.mapper;

import com.gabrielsmm.gestrec.adapter.persistence.entity.TipoRecursoEntity;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper
public interface TipoRecursoEntityMapper {

    @Mapping(target = "nome", ignore = true)
    @Mapping(target = "descricao", ignore = true)
    TipoRecurso toDomain(TipoRecursoEntity tipoRecursoEntity);

    TipoRecursoEntity toEntity(TipoRecurso tipoRecurso);

    @ObjectFactory
    default TipoRecurso createTipoRecurso(TipoRecursoEntity e) {
        if (e == null) return null;
        return new TipoRecurso(e.getId(), e.getNome(), e.getDescricao());
    }

}
