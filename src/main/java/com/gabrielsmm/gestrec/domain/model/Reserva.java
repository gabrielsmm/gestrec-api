package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.EntidadeInvalidaException;
import com.gabrielsmm.gestrec.domain.exception.RegraNegocioException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Reserva {

    private Long id;
    private Recurso recurso;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private ReservaStatus status;

    public Reserva(
            Recurso recurso,
            LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim
    ) {
        this.recurso = recurso;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.status = ReservaStatus.ATIVA;
    }

    public Reserva(
            Long id,
            Recurso recurso,
            LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim,
            ReservaStatus status
    ) {
        this.id = id;
        this.recurso = recurso;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.status = status;
    }

    public void atualizarPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
    }

    public void definirRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public void definirStatus(ReservaStatus status) {
        this.status = status;
    }

    public void cancelar() {
        if (this.status == ReservaStatus.CANCELADA) {
            throw new RegraNegocioException("Reserva já está cancelada");
        }
        this.status = ReservaStatus.CANCELADA;
    }

    public boolean isAtiva() {
        return this.status == ReservaStatus.ATIVA;
    }

    public void validar() {
        if (recurso == null) {
            throw new EntidadeInvalidaException("Recurso é obrigatório");
        }
        if (!recurso.isAtivo()) {
            throw new RegraNegocioException("Recurso inativo não pode ser reservado");
        }
        if (dataHoraInicio == null) {
            throw new EntidadeInvalidaException("Data/hora de início é obrigatória");
        }
        if (dataHoraFim == null) {
            throw new EntidadeInvalidaException("Data/hora de fim é obrigatória");
        }
        if (!dataHoraInicio.isBefore(dataHoraFim)) {
            throw new RegraNegocioException("Data/hora de início deve ser anterior à data/hora de fim");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}

