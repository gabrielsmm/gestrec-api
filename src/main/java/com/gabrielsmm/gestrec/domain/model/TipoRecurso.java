package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.EntidadeInvalidaException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TipoRecurso {

    private final Long id;
    private String nome;
    private String descricao;

    protected TipoRecurso(Long id) {
        this.id = id;
    }

    // Construtor para criação (sem id)
    public TipoRecurso(String nome, String descricao) {
        this(null, nome, descricao);
    }

    // Construtor para reconstrução (com id)
    public TipoRecurso(Long id, String nome, String descricao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do TipoRecurso é obrigatório");
        }
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public static TipoRecurso apenasComId(Long id) {
        return new TipoRecurso(id);
    }

    public void renomear(String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do TipoRecurso é obrigatório");
        }
        this.nome = novoNome;
    }

    public void alterarDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TipoRecurso that = (TipoRecurso) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}