package com.gabrielsmm.gestrec.infrastructure.persistence.repository;

import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import com.gabrielsmm.gestrec.domain.port.ReservaRepository;
import com.gabrielsmm.gestrec.infrastructure.persistence.entity.RecursoEntity;
import com.gabrielsmm.gestrec.infrastructure.persistence.entity.ReservaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaReservaRepositoryAdapter implements ReservaRepository {

    private final SpringDataReservaRepo repo;
    private final SpringDataRecursoRepo recursoRepo;

    private Reserva toDomain(ReservaEntity e) {
        if (e == null) return null;
        Recurso recurso = null;
        if (e.getRecurso() != null) {
            recurso = new Recurso(
                    e.getRecurso().getId(),
                    e.getRecurso().getNome(),
                    e.getRecurso().getLocalizacao(),
                    e.getRecurso().isAtivo(),
                    e.getRecurso().getTipoRecurso() == null ? null : new TipoRecurso(
                            e.getRecurso().getTipoRecurso().getId(),
                            e.getRecurso().getTipoRecurso().getNome(),
                            e.getRecurso().getTipoRecurso().getDescricao()
                    )
            );
        }

        return new Reserva(
                e.getId(),
                recurso,
                e.getDataHoraInicio(),
                e.getDataHoraFim(),
                ReservaStatus.fromCodigo(e.getStatus())
        );
    }

    private ReservaEntity toEntity(Reserva r) {
        if (r == null) return null;

        ReservaEntity e = new ReservaEntity();
        e.setId(r.getId());

        if (r.getRecurso() != null) {
            Long recursoId = r.getRecurso().getId();
            if (recursoId == null) {
                throw new IllegalArgumentException("Recurso deve possuir ID");
            }

            RecursoEntity re = recursoRepo.findById(recursoId)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado: id=" + recursoId));

            e.setRecurso(re);
        }

        e.setDataHoraInicio(r.getDataHoraInicio());
        e.setDataHoraFim(r.getDataHoraFim());
        e.setStatus(r.getStatus() == null ? ReservaStatus.ATIVA.getCodigo() : r.getStatus().getCodigo());

        return e;
    }

    @Override
    public Reserva salvar(Reserva reserva) {
        try {
            ReservaEntity savedEntity = repo.save(toEntity(reserva));
            return toDomain(savedEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeDuplicadaException("Violação de integridade ao salvar reserva");
        }
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public List<Reserva> buscarTodos() {
        return repo.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void excluirPorId(Long id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return repo.existsById(id);
    }

    @Override
    public boolean existeConflitoDeHorario(Long recursoId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long idReservaIgnorada, ReservaStatus status) {
        Integer s = status == null ? null : status.getCodigo();
        return repo.existeConflitoDeHorario(recursoId, dataHoraInicio, dataHoraFim, idReservaIgnorada, s);
    }
}
