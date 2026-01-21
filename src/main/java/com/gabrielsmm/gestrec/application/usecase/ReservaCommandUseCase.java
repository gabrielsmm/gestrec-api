package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.domain.exception.business.RegraNegocioException;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.*;
import com.gabrielsmm.gestrec.domain.port.repository.RecursoRepository;
import com.gabrielsmm.gestrec.domain.port.repository.ReservaRepository;
import com.gabrielsmm.gestrec.domain.port.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservaCommandUseCase {

    private final ReservaRepository repository;
    private final RecursoRepository recursoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Reserva criar(Reserva nova, Long usuarioId) {
        Long recursoId = nova.getRecurso().getId();
        Recurso recurso = recursoRepository.buscarPorId(recursoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + recursoId));

        Usuario usuario = usuarioRepository.buscarPorId(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + usuarioId));

        if (repository.existeConflitoDeHorario(
                recursoId,
                nova.getDataHoraInicio(),
                nova.getDataHoraFim(),
                null,
                ReservaStatus.ATIVA)) {
            throw new RegraNegocioException("Já existe uma reserva ativa para o recurso nesse horário");
        }

        Reserva reserva = new Reserva(null, recurso, nova.getDataHoraInicio(), nova.getDataHoraFim(), ReservaStatus.ATIVA, usuario);

        return repository.salvar(reserva);
    }

    @Transactional
    public Reserva atualizar(Long id, Reserva dadosAtualizados, Long usuarioId) {
        Reserva existente = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + id));

        Usuario usuarioAtual = usuarioRepository.buscarPorId(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + usuarioId));

        // Permissão: se não for ADMIN, só pode mexer em reservas próprias
        if (usuarioAtual.getPerfil() != UsuarioPerfil.ADMIN) {
            if (existente.getUsuario() == null || !usuarioAtual.getId().equals(existente.getUsuario().getId())) {
                throw new RegraNegocioException("Permissão negada");
            }
        }

        if (existente.getStatus() == ReservaStatus.CANCELADA) {
            throw new RegraNegocioException("Não é possível atualizar uma reserva cancelada");
        }

        Long recursoId = dadosAtualizados.getRecurso().getId();
        Recurso recurso = recursoRepository.buscarPorId(recursoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + recursoId));

        if (repository.existeConflitoDeHorario(
                recursoId,
                dadosAtualizados.getDataHoraInicio(),
                dadosAtualizados.getDataHoraFim(),
                id,
                ReservaStatus.ATIVA)) {
            throw new RegraNegocioException("Já existe uma reserva ativa para o recurso nesse horário");
        }

        existente.reagendar(dadosAtualizados.getDataHoraInicio(), dadosAtualizados.getDataHoraFim());
        existente.alterarRecurso(recurso);

        return repository.salvar(existente);
    }

    @Transactional
    public void excluir(Long id, Long usuarioId) {
        Reserva existente = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + id));

        Usuario usuarioAtual = usuarioRepository.buscarPorId(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + usuarioId));

        if (usuarioAtual.getPerfil() != UsuarioPerfil.ADMIN) {
            if (existente.getUsuario() == null || !usuarioAtual.getId().equals(existente.getUsuario().getId())) {
                throw new RegraNegocioException("Permissão negada");
            }
        }

        repository.excluirPorId(id);
    }

    @Transactional
    public Reserva cancelar(Long id, Long usuarioId) {
        Reserva existente = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + id));

        Usuario usuarioAtual = usuarioRepository.buscarPorId(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + usuarioId));

        if (usuarioAtual.getPerfil() != UsuarioPerfil.ADMIN) {
            if (existente.getUsuario() == null || !usuarioAtual.getId().equals(existente.getUsuario().getId())) {
                throw new RegraNegocioException("Permissão negada");
            }
        }

        existente.cancelar();
        return repository.salvar(existente);
    }

}
