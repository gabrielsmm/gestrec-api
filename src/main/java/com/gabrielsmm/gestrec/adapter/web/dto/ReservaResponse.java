package com.gabrielsmm.gestrec.adapter.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ReservaResponse(
        Long id,

        RecursoResponse recurso,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Schema(type = "string", pattern = "yyyy-MM-dd'T'HH:mm", example = "2026-02-01T14:00")
        LocalDateTime dataHoraInicio,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Schema(type = "string", pattern = "yyyy-MM-dd'T'HH:mm", example = "2026-02-01T16:00")
        LocalDateTime dataHoraFim,

        ReservaStatus status,

        UsuarioResponse usuario
) { }
