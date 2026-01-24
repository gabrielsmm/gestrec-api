package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.ReservaEntity;
import com.gabrielsmm.gestrec.adapter.persistence.mapper.ReservaEntityMapper;
import com.gabrielsmm.gestrec.application.port.repository.ReservaRepository;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaReservaRepositoryAdapter implements ReservaRepository {

    private final SpringDataReservaRepo repo;
    private final ReservaEntityMapper mapper;

    @Override
    public Reserva salvar(Reserva reserva) {
        try {
            ReservaEntity savedEntity = repo.save(mapper.toEntity(reserva));
            return mapper.toDomain(savedEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeDuplicadaException("Violação de integridade ao salvar reserva");
        }
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Reserva> buscarTodos() {
        return repo.findAll().stream().map(mapper::toDomain).toList();
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
                .map(mapper::toDomain).toList();
    }

    @Override
    public List<Reserva> buscarComFiltros(Long recursoId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long usuarioId) {
        return repo.findComFiltros(recursoId, dataHoraInicio, dataHoraFim, usuarioId).stream()
                .map(mapper::toDomain).toList();
    }

    @Override
    public boolean existePorIdEUsuarioId(Long id, Long usuarioId) {
        return repo.existsByIdAndUsuarioId(id, usuarioId);
    }

}
