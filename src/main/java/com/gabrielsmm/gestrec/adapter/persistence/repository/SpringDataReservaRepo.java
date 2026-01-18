package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.ReservaEntity;
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

}
