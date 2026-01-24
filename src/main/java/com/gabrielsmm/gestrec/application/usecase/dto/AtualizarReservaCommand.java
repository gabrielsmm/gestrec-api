package com.gabrielsmm.gestrec.application.usecase.dto;

import java.time.LocalDateTime;

public record AtualizarReservaCommand(
        Long reservaId,
        Long usuarioId,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim
) {}
