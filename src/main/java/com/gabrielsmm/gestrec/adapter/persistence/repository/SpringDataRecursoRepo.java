package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.RecursoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataRecursoRepo extends JpaRepository<RecursoEntity, Long> {

    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);

    @Query("""
        select r from RecursoEntity r
        where (:tipoRecursoId is null or r.tipoRecurso.id = :tipoRecursoId)
          and (:nome is null or lower(r.nome) like lower(concat('%', :nome, '%')))
          and (:localizacao is null or lower(r.localizacao) like lower(concat('%', :localizacao, '%')))
          and (:ativo is null or r.ativo = :ativo)
    """)
    Page<RecursoEntity> findComFiltrosPaginado(
            @Param("tipoRecursoId") Long tipoRecursoId,
            @Param("nome") String nome,
            @Param("localizacao") String localizacao,
            @Param("ativo") Boolean ativo,
            Pageable pageable
    );

}
