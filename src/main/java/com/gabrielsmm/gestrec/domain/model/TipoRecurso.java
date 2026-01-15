package com.gabrielsmm.gestrec.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TipoRecurso {

    private final Long id;
    private final String nome;
    private final String descricao;

    public TipoRecurso withId(Long id) {
        return new TipoRecurso(id, this.nome, this.descricao);
    }

    public TipoRecurso withNome(String nome) {
        return new TipoRecurso(this.id, nome, this.descricao);
    }

    public TipoRecurso withDescricao(String descricao) {
        return new TipoRecurso(this.id, this.nome, descricao);
    }

}
