package com.gabrielsmm.gestrec.application.usecase.reserva;

import java.time.LocalDateTime;

public record AtualizarReservaCommand(
        Long reservaId,
        Long usuarioId,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim
) {}
