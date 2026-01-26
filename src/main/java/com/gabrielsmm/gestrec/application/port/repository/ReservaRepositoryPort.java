package com.gabrielsmm.gestrec.application.port.repository;

import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import com.gabrielsmm.gestrec.shared.pagination.Pagina;
import com.gabrielsmm.gestrec.shared.pagination.ParametrosPaginacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepositoryPort {

    Reserva salvar(Reserva reserva);
    Optional<Reserva> buscarPorId(Long id);
    List<Reserva> buscarTodos();
    List<Reserva> buscarPorUsuario(Long usuarioId);
    Pagina<Reserva> buscarComFiltrosPaginado(Long recursoId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long usuarioId, ReservaStatus status, ParametrosPaginacao paginacao);
    void excluirPorId(Long id);
    boolean existePorId(Long id);
    boolean existePorIdEUsuarioId(Long id, Long usuarioId);
    boolean existeConflitoDeHorario(Long recursoId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long idReservaIgnorada, ReservaStatus status);

}
