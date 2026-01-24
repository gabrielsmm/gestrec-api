package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.ReservaRepository;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import com.gabrielsmm.gestrec.shared.pagination.Pagina;
import com.gabrielsmm.gestrec.shared.pagination.ParametrosPaginacao;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ReservaQueryUseCase {

    private final ReservaRepository repository;

    @Transactional(readOnly = true)
    public Reserva buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarTodos() {
        return repository.buscarTodos();
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarPorUsuario(Long usuarioId) {
        return repository.buscarPorUsuario(usuarioId);
    }

    @Transactional(readOnly = true)
    public Pagina<Reserva> buscarComFiltrosPaginado(Long recursoId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long usuarioId, ReservaStatus status, ParametrosPaginacao paginacao) {
        return repository.buscarComFiltrosPaginado(recursoId, dataHoraInicio, dataHoraFim, usuarioId, status, paginacao);
    }

}

