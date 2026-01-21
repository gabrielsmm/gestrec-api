package com.gabrielsmm.gestrec.adapter.persistence.jpa.repository;

import com.gabrielsmm.gestrec.adapter.persistence.jpa.entity.RecursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataRecursoRepo extends JpaRepository<RecursoEntity, Long> {

    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);

}
