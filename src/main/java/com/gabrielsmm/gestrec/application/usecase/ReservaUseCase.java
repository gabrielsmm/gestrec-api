package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.exception.RegraNegocioException;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import com.gabrielsmm.gestrec.domain.port.RecursoRepository;
import com.gabrielsmm.gestrec.domain.port.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaUseCase {

    private final ReservaRepository repository;
    private final RecursoRepository recursoRepository;

    @Transactional
    public Reserva criar(Reserva novo) {
        Long recursoId = novo.getRecurso() != null ? novo.getRecurso().getId() : null;
        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + recursoId));

        // checar conflito de horário
        if (repository.existeConflitoDeHorario(recursoId, novo.getDataHoraInicio(), novo.getDataHoraFim(), null, ReservaStatus.ATIVA)) {
            throw new RegraNegocioException("Conflito de horário para o recurso");
        }

        Reserva reservaParaSalvar = novo.comRecurso(recurso);
        reservaParaSalvar.validar();
        return repository.salvar(reservaParaSalvar);
    }

    @Transactional
    public Reserva atualizar(Long id, Reserva atualizado) {
        if (!repository.existePorId(id)) {
            throw new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + id);
        }

        Long recursoId = atualizado.getRecurso() != null ? atualizado.getRecurso().getId() : null;
        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + recursoId));

        // checar conflito ignorando a própria reserva
        if (repository.existeConflitoDeHorario(recursoId, atualizado.getDataHoraInicio(), atualizado.getDataHoraFim(), id, ReservaStatus.ATIVA)) {
            throw new RegraNegocioException("Conflito de horário para o recurso");
        }

        Reserva reservaParaAtualizar = atualizado.comId(id).comRecurso(recurso);
        reservaParaAtualizar.validar();
        return repository.salvar(reservaParaAtualizar);
    }

    @Transactional(readOnly = true)
    public Reserva buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarTodos() {
        return repository.buscarTodos();
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existePorId(id)) {
            throw new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + id);
        }
        repository.excluirPorId(id);
    }

    @Transactional
    public Reserva cancelar(Long id) {
        Reserva existente = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + id));
        Reserva cancelada = existente.cancelar();
        return repository.salvar(cancelada);
    }

}
