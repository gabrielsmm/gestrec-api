package com.gabrielsmm.gestrec.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoRecursoRequest(
        @NotBlank
        @Size(max = 150)
        String nome,

        @Size(max = 500)
        String descricao
) {}
