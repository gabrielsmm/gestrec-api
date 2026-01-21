package com.gabrielsmm.gestrec.application.port.repository;

import com.gabrielsmm.gestrec.domain.model.TipoRecurso;

import java.util.List;
import java.util.Optional;

public interface TipoRecursoRepository {

    TipoRecurso salvar(TipoRecurso tipoRecurso);
    Optional<TipoRecurso> buscarPorId(Long id);
    List<TipoRecurso> buscarTodos();
    void excluirPorId(Long id);
    boolean existePorId(Long id);
    boolean existePorNome(String nome);
    boolean existePorNomeIgnorandoId(String nome, Long id);

}
