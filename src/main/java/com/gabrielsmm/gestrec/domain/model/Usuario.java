package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.business.EntidadeInvalidaException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Usuario {

    private final Long id;
    private String nome;
    private String email;
    private String senha;
    private UsuarioPerfil perfil;

    // Construtor privado: só pode ser chamado pelas fábricas
    private Usuario(Long id, String nome, String email, String senha, UsuarioPerfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    // Fábrica para criação (sem id)
    public static Usuario novoUsuario(String nome, String email, String senha, UsuarioPerfil perfil) {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarPerfil(perfil);
        return new Usuario(null, nome, email, senha, perfil);
    }

    // Fábrica para reconstrução (com id e todos os dados)
    public static Usuario reconstruido(Long id, String nome, String email, String senha, UsuarioPerfil perfil) {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarPerfil(perfil);
        return new Usuario(id, nome, email, senha, perfil);
    }

    // Fábrica para casos em que só precisamos do id (ex.: relacionamentos)
    public static Usuario apenasComId(Long id) {
        if (id == null) throw new EntidadeInvalidaException("Id do usuário é obrigatório");
        return new Usuario(id, null, null, null, null);
    }

    // Validações
    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do usuário é obrigatório");
        }
    }

    private static void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Email do usuário é obrigatório");
        }
    }

    private static void validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Senha do usuário é obrigatória");
        }
    }

    private static void validarPerfil(UsuarioPerfil perfil) {
        if (perfil == null) {
            throw new EntidadeInvalidaException("Perfil do usuário é obrigatório");
        }
    }

    // Métodos de negócio
    public void alterarNome(String novoNome) {
        validarNome(novoNome);
        this.nome = novoNome;
    }

    public void alterarEmail(String novoEmail) {
        validarEmail(novoEmail);
        this.email = novoEmail;
    }

    public void alterarSenha(String novaSenha) {
        validarSenha(novaSenha);
        this.senha = novaSenha;
    }

    public void alterarPerfil(UsuarioPerfil novoPerfil) {
        validarPerfil(novoPerfil);
        this.perfil = novoPerfil;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
