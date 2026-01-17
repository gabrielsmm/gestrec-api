package com.gabrielsmm.gestrec.domain.repository;

import com.gabrielsmm.gestrec.domain.model.Recurso;

import java.util.List;
import java.util.Optional;

public interface RecursoRepository {

    Recurso salvar(Recurso recurso);
    Optional<Recurso> buscarPorId(Long id);
    List<Recurso> buscarTodos();
    void excluirPorId(Long id);
    boolean existePorId(Long id);
    boolean existePorNome(String nome);
    boolean existePorNomeIgnorandoId(String nome, Long id);

}
