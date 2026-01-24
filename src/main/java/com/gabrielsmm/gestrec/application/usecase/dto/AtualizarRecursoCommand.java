package com.gabrielsmm.gestrec.application.usecase.dto;

public record AtualizarRecursoCommand(
        Long recursoId,
        String nome,
        String localizacao,
        Long tipoRecursoId
) {}

