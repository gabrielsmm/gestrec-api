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
    public Reserva criar(Reserva nova) {
        Long recursoId = nova.getRecurso().getId();
        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado: " + recursoId));

        if (!recurso.isAtivo()) {
            throw new RegraNegocioException("Recurso inativo não pode ser reservado");
        }

        if (repository.existeConflitoDeHorario(
                recursoId,
                nova.getDataHoraInicio(),
                nova.getDataHoraFim(),
                null,
                ReservaStatus.ATIVA)) {
            throw new RegraNegocioException("Conflito de horário para o recurso");
        }

        nova.definirRecurso(recurso);
        nova.definirStatus(ReservaStatus.ATIVA);

        nova.validar();

        return repository.salvar(nova);
    }

    @Transactional
    public Reserva atualizar(Long id, Reserva dadosAtualizados) {
        Reserva existente = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada: " + id));

        Long recursoId = dadosAtualizados.getRecurso().getId();
        Recurso recurso = recursoRepository.findById(recursoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado: " + recursoId));

        if (!recurso.isAtivo()) {
            throw new RegraNegocioException("Recurso inativo não pode ser reservado");
        }

        if (repository.existeConflitoDeHorario(
                recursoId,
                dadosAtualizados.getDataHoraInicio(),
                dadosAtualizados.getDataHoraFim(),
                id,
                ReservaStatus.ATIVA)) {
            throw new RegraNegocioException("Conflito de horário para o recurso");
        }

        existente.atualizarPeriodo(
                dadosAtualizados.getDataHoraInicio(),
                dadosAtualizados.getDataHoraFim()
        );

        existente.definirRecurso(recurso);

        existente.validar();

        return repository.salvar(existente);
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
        existente.cancelar();
        return repository.salvar(existente);
    }

}
