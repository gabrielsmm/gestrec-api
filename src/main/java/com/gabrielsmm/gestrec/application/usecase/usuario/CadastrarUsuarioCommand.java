package com.gabrielsmm.gestrec.application.usecase.usuario;

public record CadastrarUsuarioCommand(
        String nome,
        String email,
        String senha
) {}

