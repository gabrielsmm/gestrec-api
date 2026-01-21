package com.gabrielsmm.gestrec.adapter.persistence.mapper;

import com.gabrielsmm.gestrec.adapter.persistence.entity.RecursoEntity;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(uses = {TipoRecursoEntityMapper.class})
public interface RecursoEntityMapper {

    @Mapping(target = "nome", ignore = true)
    @Mapping(target = "localizacao", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "tipoRecurso", ignore = true)
    Recurso toDomain(RecursoEntity recursoEntity);

    RecursoEntity toEntity(Recurso recurso);

    @ObjectFactory
    default Recurso createRecurso(RecursoEntity e) {
        if (e == null) return null;
        TipoRecursoEntityMapper tipoMapper = org.mapstruct.factory.Mappers.getMapper(TipoRecursoEntityMapper.class);
        var tipo = e.getTipoRecurso() == null ? null : tipoMapper.toDomain(e.getTipoRecurso());
        return new Recurso(e.getId(), e.getNome(), e.getLocalizacao(), e.isAtivo(), tipo);
    }

}
