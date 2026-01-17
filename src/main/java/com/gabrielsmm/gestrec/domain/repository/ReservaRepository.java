package com.gabrielsmm.gestrec.domain.repository;

import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository {

    Reserva salvar(Reserva reserva);
    Optional<Reserva> buscarPorId(Long id);
    List<Reserva> buscarTodos();
    void excluirPorId(Long id);
    boolean existePorId(Long id);
    boolean existeConflitoDeHorario(Long recursoId, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Long idReservaIgnorada, ReservaStatus status);

}
