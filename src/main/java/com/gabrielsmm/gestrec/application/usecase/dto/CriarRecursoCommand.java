package com.gabrielsmm.gestrec.application.usecase.dto;

public record CriarRecursoCommand(
        String nome,
        String localizacao,
        Long tipoRecursoId
) {}

