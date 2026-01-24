package com.gabrielsmm.gestrec.application.usecase.dto;

public record AtualizarTipoRecursoCommand(
        Long tipoRecursoId,
        String nome,
        String descricao
) {}

