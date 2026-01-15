package com.gabrielsmm.gestrec.infrastructure.persistence.repository;

import com.gabrielsmm.gestrec.infrastructure.persistence.entity.TipoRecursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataTipoRecursoRepo extends JpaRepository<TipoRecursoEntity, Long> {

    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);

}
