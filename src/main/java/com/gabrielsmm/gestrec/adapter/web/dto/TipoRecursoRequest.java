package com.gabrielsmm.gestrec.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TipoRecursoRequest {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @Size(max = 500)
    private String descricao;

}
