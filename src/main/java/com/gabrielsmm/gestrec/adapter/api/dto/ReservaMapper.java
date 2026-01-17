package com.gabrielsmm.gestrec.adapter.api.dto;

import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {RecursoMapper.class})
public interface ReservaMapper {

    default Reserva toDomain(ReservaRequest req) {
        if (req == null) return null;
        Recurso recurso = toRecurso(req.recursoId());
        return new Reserva(recurso, req.dataHoraInicio(), req.dataHoraFim());
    }

//    default Reserva applyToDomain(ReservaRequest req, Reserva existing) {
//        if (existing == null) return toDomain(req);
//
//        Recurso recurso = req.recursoId() != null ? toRecurso(req.recursoId()) : existing.getRecurso();
//        LocalDateTime inicio = req.dataHoraInicio() != null ? req.dataHoraInicio() : existing.getDataHoraInicio();
//        LocalDateTime fim = req.dataHoraFim() != null ? req.dataHoraFim() : existing.getDataHoraFim();
//        ReservaStatus status = existing.getStatus();
//
//        return new Reserva(existing.getId(), recurso, inicio, fim, status);
//    }

    ReservaResponse toResponse(Reserva domain);

    default Recurso toRecurso(Long id) {
        return id != null ? new Recurso(id, null, null, true, null) : null;
    }

}
