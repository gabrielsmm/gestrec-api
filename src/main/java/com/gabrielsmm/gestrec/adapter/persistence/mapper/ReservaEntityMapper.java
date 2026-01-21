package com.gabrielsmm.gestrec.adapter.persistence.mapper;

import com.gabrielsmm.gestrec.adapter.persistence.entity.ReservaEntity;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RecursoEntityMapper.class, UsuarioEntityMapper.class})
public interface ReservaEntityMapper {

    @Mapping(target = "status", ignore = true)
    Reserva toDomain(ReservaEntity entity);

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

    @ObjectFactory
    default Reserva createReserva(ReservaEntity entity) {
        if (entity == null) return null;

        RecursoEntityMapper recursoMapper = Mappers.getMapper(RecursoEntityMapper.class);
        UsuarioEntityMapper usuarioMapper = Mappers.getMapper(UsuarioEntityMapper.class);

        var recurso = entity.getRecurso() == null ? null : recursoMapper.toDomain(entity.getRecurso());
        var usuario = entity.getUsuario() == null ? null : usuarioMapper.toDomain(entity.getUsuario());
        var status = mapStatusToDomain(entity.getStatus());

        return new Reserva(entity.getId(), recurso, entity.getDataHoraInicio(), entity.getDataHoraFim(), status, usuario);
    }
}
