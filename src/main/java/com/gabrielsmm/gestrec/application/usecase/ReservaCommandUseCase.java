package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.RecursoRepositoryPort;
import com.gabrielsmm.gestrec.application.port.repository.ReservaRepositoryPort;
import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepositoryPort;
import com.gabrielsmm.gestrec.application.usecase.dto.AtualizarReservaCommand;
import com.gabrielsmm.gestrec.application.usecase.dto.CriarReservaCommand;
import com.gabrielsmm.gestrec.domain.exception.RegraNegocioException;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class ReservaCommandUseCase {

    private final ReservaRepositoryPort repository;
    private final RecursoRepositoryPort recursoRepository;
    private final UsuarioRepositoryPort usuarioRepository;

    @Transactional
    public Reserva criar(CriarReservaCommand command) {
        Recurso recurso = buscarRecursoPorId(command.recursoId());
        Usuario usuario = buscarUsuarioPorId(command.usuarioId());

        validarConflitoDeHorario(command.recursoId(), command.dataHoraInicio(), command.dataHoraFim(), null);

        Reserva reserva = Reserva.criarNova(recurso, usuario, command.dataHoraInicio(), command.dataHoraFim());

        return repository.salvar(reserva);
    }

    @Transactional
    public Reserva atualizar(AtualizarReservaCommand command) {
        Reserva existente = buscarReservaPorId(command.reservaId());
        Usuario usuarioAtual = buscarUsuarioPorId(command.usuarioId());

        validarPermissaoParaManipularReserva(existente, usuarioAtual);

        Long recursoId = existente.getRecurso().getId();

        validarConflitoDeHorario(recursoId, command.dataHoraInicio(), command.dataHoraFim(), command.reservaId());

        existente.reagendar(command.dataHoraInicio(), command.dataHoraFim());

        return repository.salvar(existente);
    }

    @Transactional
    public void excluir(Long id, Long usuarioId) {
        Reserva existente = buscarReservaPorId(id);
        Usuario usuarioAtual = buscarUsuarioPorId(usuarioId);

        validarPermissaoParaManipularReserva(existente, usuarioAtual);

        repository.excluirPorId(id);
    }

    @Transactional
    public Reserva cancelar(Long id, Long usuarioId) {
        Reserva existente = buscarReservaPorId(id);
        Usuario usuarioAtual = buscarUsuarioPorId(usuarioId);

        validarPermissaoParaManipularReserva(existente, usuarioAtual);

        existente.cancelar();
        return repository.salvar(existente);
    }

    // ===== Métodos auxiliares =====
    private Reserva buscarReservaPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Reserva não encontrada com id: " + id));
    }

    private Recurso buscarRecursoPorId(Long id) {
        return recursoRepository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + id));
    }

    private Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com id: " + id));
    }

    private void validarPermissaoParaManipularReserva(Reserva reserva, Usuario usuario) {
        if (usuario.getPerfil() != UsuarioPerfil.ADMIN) {
            if (reserva.getUsuario() == null || !usuario.getId().equals(reserva.getUsuario().getId())) {
                throw new RegraNegocioException("Permissão negada");
            }
        }
    }

    private void validarConflitoDeHorario(Long recursoId, java.time.LocalDateTime dataHoraInicio,
                                          java.time.LocalDateTime dataHoraFim, Long reservaIdExcluir) {
        if (repository.existeConflitoDeHorario(recursoId, dataHoraInicio, dataHoraFim, reservaIdExcluir, ReservaStatus.ATIVA)) {
            throw new RegraNegocioException("Já existe uma reserva ativa para o recurso nesse horário");
        }
    }

}
