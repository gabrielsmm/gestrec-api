package com.gabrielsmm.gestrec.domain.repository;

import com.gabrielsmm.gestrec.domain.model.Recurso;

import java.util.List;
import java.util.Optional;

public interface RecursoRepository {

    Recurso save(Recurso recurso);
    Optional<Recurso> findById(Long id);
    List<Recurso> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);

}
