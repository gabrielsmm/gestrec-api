package com.gabrielsmm.gestrec.adapter.persistence.jpa.repository;

import com.gabrielsmm.gestrec.adapter.persistence.jpa.entity.RecursoEntity;
import com.gabrielsmm.gestrec.adapter.persistence.jpa.entity.ReservaEntity;
import com.gabrielsmm.gestrec.adapter.persistence.jpa.entity.UsuarioEntity;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.*;
import com.gabrielsmm.gestrec.application.port.repository.ReservaRepository;
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
    private final SpringDataUsuarioRepo usuarioRepo;

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

        Usuario usuario = null;
        if (e.getUsuario() != null) {
            UsuarioEntity ue = e.getUsuario();
            usuario = new Usuario(
                    ue.getId(),
                    ue.getNome(),
                    ue.getEmail(),
                    ue.getSenha(),
                    ue.getPerfil()
            );
        }

        return new Reserva(
                e.getId(),
                recurso,
                e.getDataHoraInicio(),
                e.getDataHoraFim(),
                ReservaStatus.fromCodigo(e.getStatus()),
                usuario
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

        if (r.getUsuario() != null) {
            Long usuarioId = r.getUsuario().getId();
            if (usuarioId == null) {
                throw new IllegalArgumentException("Usuario deve possuir ID");
            }
            UsuarioEntity ue = usuarioRepo.findById(usuarioId)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario não encontrado: id=" + usuarioId));
            e.setUsuario(ue);
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

    @Override
    public List<Reserva> buscarPorUsuario(Long usuarioId) {
        return repo.findByUsuarioId(usuarioId).stream()
                .map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existePorIdEUsuarioId(Long id, Long usuarioId) {
        return repo.existsByIdAndUsuarioId(id, usuarioId);
    }

}
