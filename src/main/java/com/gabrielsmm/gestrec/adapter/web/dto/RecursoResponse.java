package com.gabrielsmm.gestrec.adapter.web.dto;

public record RecursoResponse(
        Long id,
        String nome,
        String localizacao,
        boolean ativo,
        TipoRecursoResponse tipoRecurso
) {
}
