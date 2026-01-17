package com.gabrielsmm.gestrec.adapter.web.dto;

import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TipoRecursoMapper {

    @Mapping(target = "id", ignore = true)
    TipoRecurso toDomain(TipoRecursoRequest req);

    TipoRecursoResponse toResponse(TipoRecurso domain);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void applyToDomain(TipoRecursoRequest req, @MappingTarget TipoRecurso existing);

}
