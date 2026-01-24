package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.EntidadeInvalidaException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TipoRecurso {

    private final Long id;
    private String nome;
    private String descricao;

    // Construtor privado: garante que a entidade só seja criada de forma válida
    private TipoRecurso(Long id, String nome, String descricao) {
        validarNome(nome);

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    // Fábrica para criação de um novo tipo de recurso válido
    public static TipoRecurso criarNovo(String nome, String descricao) {
        return new TipoRecurso(null, nome, descricao);
    }

    // Fábrica para reconstrução de um tipo de recurso já existente (ex: persistência)
    public static TipoRecurso reconstruir(Long id, String nome, String descricao) {
        if (id == null) {
            throw new EntidadeInvalidaException("Id é obrigatório para reconstrução do tipo de recurso");
        }

        return new TipoRecurso(id, nome, descricao);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoRecurso that = (TipoRecurso) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}