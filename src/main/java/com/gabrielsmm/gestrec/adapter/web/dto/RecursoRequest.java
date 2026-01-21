package com.gabrielsmm.gestrec.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RecursoRequest(
        @NotBlank
        @Size(max = 150)
        String nome,

        @NotBlank
        @Size(max = 300)
        String localizacao,

        @NotNull
        @Positive
        Long tipoRecursoId
) {}
