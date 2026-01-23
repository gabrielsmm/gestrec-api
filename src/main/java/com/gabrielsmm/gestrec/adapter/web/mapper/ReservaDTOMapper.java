package com.gabrielsmm.gestrec.adapter.web.mapper;

import com.gabrielsmm.gestrec.adapter.web.dto.ReservaInsertRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaResponse;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaUpdateRequest;
import com.gabrielsmm.gestrec.application.usecase.reserva.AtualizarReservaCommand;
import com.gabrielsmm.gestrec.application.usecase.reserva.CriarReservaCommand;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReservaDTOMapper {

    default CriarReservaCommand toCommand(ReservaInsertRequest req, Long usuarioId) {
        if (req == null) return null;

        return new CriarReservaCommand(req.recursoId(), usuarioId, req.dataHoraInicio(), req.dataHoraFim());
    }

    default AtualizarReservaCommand toCommand(Long reservaId, Long usuarioId, ReservaUpdateRequest req) {
        if (req == null) return null;

        return new AtualizarReservaCommand(reservaId, usuarioId, req.dataHoraInicio(), req.dataHoraFim());
    }

    @Mapping(source = "recurso", target = "recurso")
    @Mapping(source = "usuario", target = "usuario")
    ReservaResponse toResponse(Reserva domain);

}
