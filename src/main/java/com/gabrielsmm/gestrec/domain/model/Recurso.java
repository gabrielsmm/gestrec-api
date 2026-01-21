package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.business.EntidadeInvalidaException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Recurso {

    private final Long id;
    private String nome;
    private String localizacao;
    private boolean ativo;
    private TipoRecurso tipoRecurso;

    // Construtor privado: só pode ser chamado pelas fábricas
    private Recurso(Long id, String nome, String localizacao, boolean ativo, TipoRecurso tipoRecurso) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.ativo = ativo;
        this.tipoRecurso = tipoRecurso;
    }

    // Fábrica para criação de recurso novo (sem id)
    public static Recurso novoRecurso(String nome, String localizacao, TipoRecurso tipoRecurso) {
        validarNome(nome);
        validarLocalizacao(localizacao);
        validarTipo(tipoRecurso);
        return new Recurso(null, nome, localizacao, true, tipoRecurso); // sempre nasce ativo
    }

    // Fábrica para reconstrução (com id e todos os dados)
    public static Recurso reconstruido(Long id, String nome, String localizacao, boolean ativo, TipoRecurso tipoRecurso) {
        validarNome(nome);
        validarLocalizacao(localizacao);
        validarTipo(tipoRecurso);
        return new Recurso(id, nome, localizacao, ativo, tipoRecurso);
    }

    // Fábrica para casos em que só precisamos do id (ex.: relacionamentos)
    public static Recurso apenasComId(Long id) {
        if (id == null) throw new EntidadeInvalidaException("Id do recurso é obrigatório");
        return new Recurso(id, null, null, true, null);
    }

    // Validações
    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do recurso é obrigatório");
        }
    }

    private static void validarLocalizacao(String localizacao) {
        if (localizacao == null || localizacao.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Localização do recurso é obrigatória");
        }
    }

    private static void validarTipo(TipoRecurso tipoRecurso) {
        if (tipoRecurso == null) {
            throw new EntidadeInvalidaException("TipoRecurso é obrigatório");
        }
    }

    // Métodos de negócio
    public void renomear(String novoNome) {
        validarNome(novoNome);
        this.nome = novoNome;
    }

    public void alterarLocalizacao(String novaLocalizacao) {
        validarLocalizacao(novaLocalizacao);
        this.localizacao = novaLocalizacao;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void alterarTipo(TipoRecurso novoTipo) {
        validarTipo(novoTipo);
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
