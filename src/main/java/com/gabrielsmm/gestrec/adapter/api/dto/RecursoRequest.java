package com.gabrielsmm.gestrec.adapter.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RecursoRequest {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @NotBlank
    @Size(max = 300)
    private String localizacao;

    @NotNull
    @Positive
    private Long tipoRecursoId;

    private Boolean ativo;

}
