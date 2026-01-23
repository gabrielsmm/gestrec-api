package com.gabrielsmm.gestrec.application.usecase.recurso;

public record CriarRecursoCommand(
        String nome,
        String localizacao,
        Long tipoRecursoId
) {}

