package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.EntidadeInvalidaException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Recurso {

    private final Long id;
    private String nome;
    private String localizacao;
    private boolean ativo = true;
    private TipoRecurso tipoRecurso;

    protected Recurso(Long id) {
        this.id = id;
    }

    // Construtor para criação (sem id)
    public Recurso(String nome, String localizacao, boolean ativo, TipoRecurso tipoRecurso) {
        this(null, nome, localizacao, ativo, tipoRecurso);
    }

    // Construtor para reconstrução (com id)
    public Recurso(Long id, String nome, String localizacao, boolean ativo, TipoRecurso tipoRecurso) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do recurso é obrigatório");
        }
        if (localizacao == null || localizacao.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Localização do recurso é obrigatória");
        }
        if (tipoRecurso == null) {
            throw new EntidadeInvalidaException("TipoRecurso é obrigatório");
        }
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.ativo = ativo;
        this.tipoRecurso = tipoRecurso;
    }

    public static Recurso apenasComId(Long id) {
        return new Recurso(id);
    }

    public void renomear(String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do recurso é obrigatório");
        }
        this.nome = novoNome;
    }

    public void alterarLocalizacao(String novaLocalizacao) {
        if (novaLocalizacao == null || novaLocalizacao.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Localização do recurso é obrigatória");
        }
        this.localizacao = novaLocalizacao;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void alterarTipo(TipoRecurso novoTipo) {
        if (novoTipo == null) {
            throw new EntidadeInvalidaException("TipoRecurso é obrigatório");
        }
        this.tipoRecurso = novoTipo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recurso recurso = (Recurso) o;
        return Objects.equals(id, recurso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
