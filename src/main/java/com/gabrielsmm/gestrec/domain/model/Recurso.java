package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.business.EntidadeInvalidaException;
import com.gabrielsmm.gestrec.domain.exception.business.RegraNegocioException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Recurso {

    private final Long id;
    private TipoRecurso tipoRecurso;
    private String nome;
    private String localizacao;
    private boolean ativo;

    // Construtor privado: garante que a entidade só seja criada de forma válida
    private Recurso(Long id,
                    String nome,
                    String localizacao,
                    boolean ativo,
                    TipoRecurso tipoRecurso) {
        validarNome(nome);
        validarLocalizacao(localizacao);
        validarTipo(tipoRecurso);

        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.ativo = ativo;
        this.tipoRecurso = tipoRecurso;
    }

    // Fábrica para criação de um novo recurso válido
    public static Recurso criarNovo(String nome,
                                    String localizacao,
                                    TipoRecurso tipoRecurso) {
        return new Recurso(null, nome, localizacao, true, tipoRecurso); // sempre nasce ativo
    }

    // Fábrica para reconstrução de um recurso já existente (ex: persistência)
    public static Recurso reconstruir(Long id,
                                      String nome,
                                      String localizacao,
                                      boolean ativo,
                                      TipoRecurso tipoRecurso) {
        if (id == null) {
            throw new EntidadeInvalidaException("Id é obrigatório para reconstrução do recurso");
        }

        return new Recurso(id, nome, localizacao, ativo, tipoRecurso);
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
    public void alterarTipo(TipoRecurso novoTipo) {
        validarTipo(novoTipo);
        this.tipoRecurso = novoTipo;
    }

    public void renomear(String novoNome) {
        validarNome(novoNome);
        this.nome = novoNome;
    }

    public void alterarLocalizacao(String novaLocalizacao) {
        validarLocalizacao(novaLocalizacao);
        this.localizacao = novaLocalizacao;
    }

    public void ativar() {
        if (this.ativo) {
            throw new RegraNegocioException("Recurso já está ativo");
        }
        this.ativo = true;
    }

    public void desativar() {
        if (!this.ativo) {
            throw new RegraNegocioException("Recurso já está inativo");
        }
        this.ativo = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recurso recurso = (Recurso) o;
        return id != null && id.equals(recurso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
