package com.gabrielsmm.gestrec.adapter.persistence.mapper;

import com.gabrielsmm.gestrec.adapter.persistence.entity.ReservaEntity;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RecursoEntityMapper.class, UsuarioEntityMapper.class})
public interface ReservaEntityMapper {

    default Reserva toDomain(ReservaEntity entity) {
        if (entity == null) return null;

        var recurso = Mappers.getMapper(RecursoEntityMapper.class).toDomain(entity.getRecurso());
        var usuario = Mappers.getMapper(UsuarioEntityMapper.class).toDomain(entity.getUsuario());
        var status = mapStatusToDomain(entity.getStatus());

        return Reserva.reconstruida(
                entity.getId(),
                recurso,
                usuario,
                entity.getDataHoraInicio(),
                entity.getDataHoraFim(),
                status
        );
    }

    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatusToEntity")
    ReservaEntity toEntity(Reserva domain);

    @Named("mapStatusToDomain")
    default ReservaStatus mapStatusToDomain(Integer statusCodigo) {
        return ReservaStatus.fromCodigo(statusCodigo);
    }

    @Named("mapStatusToEntity")
    default Integer mapStatusToEntity(ReservaStatus status) {
        return status == null ? ReservaStatus.ATIVA.getCodigo() : status.getCodigo();
    }

}
