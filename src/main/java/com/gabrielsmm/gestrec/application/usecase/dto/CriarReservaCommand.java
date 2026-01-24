package com.gabrielsmm.gestrec.application.usecase.dto;

import java.time.LocalDateTime;

public record CriarReservaCommand(
        Long recursoId,
        Long usuarioId,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim
) {}
