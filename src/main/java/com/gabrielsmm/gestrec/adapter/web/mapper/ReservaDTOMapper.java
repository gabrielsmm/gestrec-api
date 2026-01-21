package com.gabrielsmm.gestrec.adapter.web.mapper;

import com.gabrielsmm.gestrec.adapter.web.dto.ReservaRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaResponse;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import org.mapstruct.Mapper;

@Mapper(uses = {RecursoDTOMapper.class, UsuarioDTOMapper.class})
public interface ReservaDTOMapper {

    default Reserva toDomain(ReservaRequest req) {
        if (req == null) return null;
        Recurso recurso = toRecurso(req.recursoId());
        return new Reserva(recurso, req.dataHoraInicio(), req.dataHoraFim());
    }

    ReservaResponse toResponse(Reserva domain);

    default Recurso toRecurso(Long id) {
        return id != null ? Recurso.apenasComId(id) : null;
    }

}
