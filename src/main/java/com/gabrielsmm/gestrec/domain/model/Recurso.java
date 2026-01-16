package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.EntidadeInvalidaException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class Recurso {

    private final Long id;
    private final String nome;
    private final String localizacao;
    private final boolean ativo;
    private final TipoRecurso tipoRecurso;

    public Recurso withId(Long id) {
        return new Recurso(id, this.nome, this.localizacao, this.ativo, this.tipoRecurso);
    }

    public Recurso withNome(String nome) {
        return new Recurso(this.id, nome, this.localizacao, this.ativo, this.tipoRecurso);
    }

    public Recurso withLocalizacao(String localizacao) {
        return new Recurso(this.id, this.nome, localizacao, this.ativo, this.tipoRecurso);
    }

    public Recurso withAtivo(boolean ativo) {
        return new Recurso(this.id, this.nome, this.localizacao, ativo, this.tipoRecurso);
    }

    public Recurso withTipoRecurso(TipoRecurso tipoRecurso) {
        return new Recurso(this.id, this.nome, this.localizacao, this.ativo, tipoRecurso);
    }

    public Recurso ativar() {
        return withAtivo(true);
    }

    public Recurso desativar() {
        return withAtivo(false);
    }

    public void validate() {
        if (this.nome == null || this.nome.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Nome do recurso é obrigatório");
        }
        if (this.localizacao == null || this.localizacao.trim().isEmpty()) {
            throw new EntidadeInvalidaException("Localização do recurso é obrigatória");
        }
        if (this.tipoRecurso == null) {
            throw new EntidadeInvalidaException("TipoRecurso é obrigatório");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recurso recurso = (Recurso) o;
        return Objects.equals(id, recurso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
