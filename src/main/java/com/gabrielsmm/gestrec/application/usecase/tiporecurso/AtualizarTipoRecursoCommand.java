package com.gabrielsmm.gestrec.application.usecase.tiporecurso;

public record AtualizarTipoRecursoCommand(
        Long tipoRecursoId,
        String nome,
        String descricao
) {}

