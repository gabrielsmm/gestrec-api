package com.gabrielsmm.gestrec.application.usecase.reserva;

import com.gabrielsmm.gestrec.application.port.repository.RecursoRepository;
import com.gabrielsmm.gestrec.application.port.repository.ReservaRepository;
import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepository;
import com.gabrielsmm.gestrec.domain.exception.business.RegraNegocioException;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class ReservaCommandUseCase {

    private final ReservaRepository repository;
    private final RecursoRepository recursoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Reserva criar(CriarReservaCommand command) {
        Recurso recurso = recursoRepository.buscarPorId(command.recursoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + command.recursoId()));

        Usuario usuario = usuarioRepository.buscarPorId(command.usuarioId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + command.usuarioId()));

        if (repository.existeConflitoDeHorario(
                command.recursoId(),
                command.dataHoraInicio(),
                command.dataHoraFim(),
                null,
                ReservaStatus.ATIVA)) {
            throw new RegraNegocioException("Já existe uma reserva ativa para o recurso nesse horário");
        }

        Reserva reserva = Reserva.criarNova(recurso, usuario, command.dataHoraInicio(), command.dataHoraFim());

        return repository.salvar(reserva);
    }

    @Transactional
    public Reserva atualizar(AtualizarReservaCommand command) {
        Reserva existente = repository.buscarPorId(command.reservaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + command.reservaId()));

        Usuario usuarioAtual = usuarioRepository.buscarPorId(command.usuarioId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + command.usuarioId()));

        // Permissão: se não for ADMIN, só pode mexer em reservas próprias
        if (usuarioAtual.getPerfil() != UsuarioPerfil.ADMIN) {
            if (existente.getUsuario() == null || !usuarioAtual.getId().equals(existente.getUsuario().getId())) {
                throw new RegraNegocioException("Permissão negada");
            }
        }

        if (existente.getStatus() == ReservaStatus.CANCELADA) {
            throw new RegraNegocioException("Não é possível atualizar uma reserva cancelada");
        }

        Long recursoId = existente.getRecurso().getId();

        if (repository.existeConflitoDeHorario(
                recursoId,
                command.dataHoraInicio(),
                command.dataHoraFim(),
                command.reservaId(),
                ReservaStatus.ATIVA)) {
            throw new RegraNegocioException("Já existe uma reserva ativa para o recurso nesse horário");
        }

        // Só permite reagendamento
        existente.reagendar(command.dataHoraInicio(), command.dataHoraFim());

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
