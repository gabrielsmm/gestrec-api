package com.gabrielsmm.gestrec.adapter.web.dto;

import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RecursoMapper.class})
public interface ReservaMapper {

    default Reserva toDomain(ReservaRequest req) {
        if (req == null) return null;
        Recurso recurso = toRecurso(req.recursoId());
        return new Reserva(recurso, req.dataHoraInicio(), req.dataHoraFim());
    }

    ReservaResponse toResponse(Reserva domain);

    default Recurso toRecurso(Long id) {
        return id != null ? new Recurso(id, null, null, true, null) : null;
    }

}
