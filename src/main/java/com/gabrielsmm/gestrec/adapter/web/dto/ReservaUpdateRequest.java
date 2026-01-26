package com.gabrielsmm.gestrec.adapter.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservaUpdateRequest(
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Schema(type = "string", pattern = "yyyy-MM-dd'T'HH:mm", example = "2026-02-01T14:00")
        LocalDateTime dataHoraInicio,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        @Schema(type = "string", pattern = "yyyy-MM-dd'T'HH:mm", example = "2026-02-01T16:00")
        LocalDateTime dataHoraFim
) {}

