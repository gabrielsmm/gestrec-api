package com.gabrielsmm.gestrec.application.usecase.reserva;

import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.application.port.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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

}
