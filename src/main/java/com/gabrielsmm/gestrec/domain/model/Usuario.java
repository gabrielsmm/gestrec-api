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

    protected Usuario(Long id) {
        this.id = id;
    }

    // Construtor para criação (sem id)
    public Usuario(String nome, String email, String senha, UsuarioPerfil perfil) {
        this(null, nome, email, senha, perfil);
    }

    // Construtor para reconstrução (com id)
    public Usuario(Long id, String nome, String email, String senha, UsuarioPerfil perfil) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do usuário é obrigatório");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Email do usuário é obrigatório");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Senha do usuário é obrigatória");
        }
        if (perfil == null) {
            throw new EntidadeInvalidaException("Perfil do usuário é obrigatório");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    public static Usuario apenasComId(Long id) {
        return new Usuario(id);
    }

    public void alterarNome(String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do usuário é obrigatório");
        }
        this.nome = novoNome;
    }

    public void alterarEmail(String novoEmail) {
        if (novoEmail == null || novoEmail.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Email do usuário é obrigatório");
        }
        this.email = novoEmail;
    }

    public void alterarSenha(String novaSenha) {
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Senha do usuário é obrigatória");
        }
        this.senha = novaSenha;
    }

    public void alterarPerfil(UsuarioPerfil novoPerfil) {
        if (novoPerfil == null) {
            throw new EntidadeInvalidaException("Perfil do usuário é obrigatório");
        }
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
