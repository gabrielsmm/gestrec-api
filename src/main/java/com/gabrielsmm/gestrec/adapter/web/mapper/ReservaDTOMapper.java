package com.gabrielsmm.gestrec.adapter.web.mapper;

import com.gabrielsmm.gestrec.adapter.web.dto.ReservaInsertRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaResponse;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaUpdateRequest;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {RecursoDTOMapper.class, UsuarioDTOMapper.class})
public interface ReservaDTOMapper {

    default Reserva toDomain(ReservaInsertRequest req) {
        if (req == null) return null;
        Recurso recurso = toRecurso(req.recursoId());
        return Reserva.novaReserva(recurso, req.dataHoraInicio(), req.dataHoraFim());
    }

    default Reserva toDomain(ReservaUpdateRequest req) {
        if (req == null) return null;
        return Reserva.apenasComDatas(req.dataHoraInicio(), req.dataHoraFim());
    }

    @Mapping(source = "recurso", target = "recurso")
    @Mapping(source = "usuario", target = "usuario")
    ReservaResponse toResponse(Reserva domain);

    default Recurso toRecurso(Long id) {
        return id != null ? Recurso.apenasComId(id) : null;
    }
}
