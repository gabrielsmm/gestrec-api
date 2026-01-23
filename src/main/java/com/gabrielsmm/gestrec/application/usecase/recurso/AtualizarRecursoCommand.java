package com.gabrielsmm.gestrec.application.usecase.recurso;

public record AtualizarRecursoCommand(
        Long recursoId,
        String nome,
        String localizacao,
        Long tipoRecursoId
) {}

