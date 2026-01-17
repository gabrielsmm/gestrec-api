package com.gabrielsmm.gestrec.adapter.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;

import java.time.LocalDateTime;

public record ReservaResponse(
        Long id,

        RecursoResponse recurso,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataHoraInicio,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataHoraFim,

        ReservaStatus status
) { }
