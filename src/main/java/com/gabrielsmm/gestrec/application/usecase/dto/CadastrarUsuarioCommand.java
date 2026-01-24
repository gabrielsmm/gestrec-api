package com.gabrielsmm.gestrec.application.usecase.dto;

public record CadastrarUsuarioCommand(
        String nome,
        String email,
        String senha
) {}

