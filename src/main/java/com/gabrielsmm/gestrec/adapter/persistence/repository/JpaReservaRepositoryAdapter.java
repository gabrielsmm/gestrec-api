package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.ReservaEntity;
import com.gabrielsmm.gestrec.adapter.persistence.mapper.ReservaEntityMapper;
import com.gabrielsmm.gestrec.application.port.repository.ReservaRepositoryPort;
import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import com.gabrielsmm.gestrec.shared.pagination.Pagina;
import com.gabrielsmm.gestrec.shared.pagination.ParametrosPaginacao;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaReservaRepositoryAdapter implements ReservaRepositoryPort {

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
    public Pagina<Reserva> buscarComFiltrosPaginado(Long recursoId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long usuarioId, ReservaStatus status, ParametrosPaginacao paginacao) {
        Integer statusCodigo = status != null ? status.getCodigo() : null;
        Pageable pageable = PageRequest.of(
                paginacao.numeroPagina(),
                paginacao.tamanhoPagina(),
                Sort.by("dataHoraInicio").ascending()
        );
        Page<ReservaEntity> page = repo.findComFiltrosPaginado(recursoId, dataHoraInicio, dataHoraFim, usuarioId, statusCodigo, pageable);
        return toPagina(page);
    }

    @Override
    public boolean existePorIdEUsuarioId(Long id, Long usuarioId) {
        return repo.existsByIdAndUsuarioId(id, usuarioId);
    }

    private Pagina<Reserva> toPagina(Page<ReservaEntity> page) {
        List<Reserva> conteudo = page.getContent().stream()
                .map(mapper::toDomain)
                .toList();

        return new Pagina<>(
                conteudo,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

}
