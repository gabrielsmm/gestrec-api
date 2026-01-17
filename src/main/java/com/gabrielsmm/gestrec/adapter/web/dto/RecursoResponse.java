package com.gabrielsmm.gestrec.adapter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecursoResponse {

    private Long id;
    private String nome;
    private String localizacao;
    private boolean ativo;
    private TipoRecursoResponse tipoRecurso;

}
