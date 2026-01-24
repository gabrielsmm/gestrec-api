package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.ReservaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataReservaRepo extends JpaRepository<ReservaEntity, Long> {

    @Query("""
        select case when count(r) > 0 then true else false end
        from ReservaEntity r
        where r.recurso.id = :recursoId
          and r.status = :status
          and r.dataHoraInicio < :dataHoraFim
          and r.dataHoraFim > :dataHoraInicio
          and (:idReservaIgnorada is null or r.id <> :idReservaIgnorada)
    """)
    boolean existeConflitoDeHorario(
            @Param("recursoId") Long recursoId,
            @Param("dataHoraInicio") LocalDateTime dataHoraInicio,
            @Param("dataHoraFim") LocalDateTime dataHoraFim,
            @Param("idReservaIgnorada") Long idReservaIgnorada,
            @Param("status") Integer status
    );

    List<ReservaEntity> findByUsuarioId(Long usuarioId);

    @Query("""
        select r from ReservaEntity r
        where (:recursoId is null or r.recurso.id = :recursoId)
          and (:usuarioId is null or r.usuario.id = :usuarioId)
          and (:status is null or r.status = :status)
          and (:dataHoraInicio is null or :dataHoraFim is null or
               (r.dataHoraInicio < :dataHoraFim and r.dataHoraFim > :dataHoraInicio))
    """)
    Page<ReservaEntity> findComFiltrosPaginado(
            @Param("recursoId") Long recursoId,
            @Param("dataHoraInicio") LocalDateTime dataHoraInicio,
            @Param("dataHoraFim") LocalDateTime dataHoraFim,
            @Param("usuarioId") Long usuarioId,
            @Param("status") Integer status,
            Pageable pageable
    );

    boolean existsByIdAndUsuarioId(Long id, Long usuarioId);

}
