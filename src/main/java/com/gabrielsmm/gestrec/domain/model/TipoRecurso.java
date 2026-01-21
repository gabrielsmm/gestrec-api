package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.business.EntidadeInvalidaException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TipoRecurso {

    private final Long id;
    private String nome;
    private String descricao;

    // Construtor privado: só pode ser chamado pelas fábricas
    private TipoRecurso(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Fábrica para criação (sem id)
    public static TipoRecurso novoTipoRecurso(String nome, String descricao) {
        validarNome(nome);
        return new TipoRecurso(null, nome, descricao);
    }

    // Fábrica para reconstrução (com id e todos os dados)
    public static TipoRecurso reconstruido(Long id, String nome, String descricao) {
        validarNome(nome);
        return new TipoRecurso(id, nome, descricao);
    }

    // Fábrica para casos em que só precisamos do id (ex.: relacionamentos)
    public static TipoRecurso apenasComId(Long id) {
        if (id == null) throw new EntidadeInvalidaException("Id do TipoRecurso é obrigatório");
        return new TipoRecurso(id, null, null);
    }

    // Validações
    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do TipoRecurso é obrigatório");
        }
    }

    // Métodos de negócio
    public void renomear(String novoNome) {
        validarNome(novoNome);
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