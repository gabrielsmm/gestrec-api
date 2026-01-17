package com.gabrielsmm.gestrec.domain.repository;

import com.gabrielsmm.gestrec.domain.model.TipoRecurso;

import java.util.List;
import java.util.Optional;

public interface TipoRecursoRepository {

    TipoRecurso save(TipoRecurso tipoRecurso);
    Optional<TipoRecurso> findById(Long id);
    List<TipoRecurso> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);

}
