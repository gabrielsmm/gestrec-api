package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.EntidadeInvalidaException;
import com.gabrielsmm.gestrec.domain.exception.RegraNegocioException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class Reserva {

    private final Long id;
    private final Recurso recurso;
    private final LocalDateTime dataHoraInicio;
    private final LocalDateTime dataHoraFim;
    private final ReservaStatus status;

    public Reserva comId(Long id) {
        return new Reserva(id, this.recurso, this.dataHoraInicio, this.dataHoraFim, this.status);
    }

    public Reserva comRecurso(Recurso recurso) {
        return new Reserva(this.id, recurso, this.dataHoraInicio, this.dataHoraFim, this.status);
    }

    public Reserva comDataHoraInicio(LocalDateTime dataHoraInicio) {
        return new Reserva(this.id, this.recurso, dataHoraInicio, this.dataHoraFim, this.status);
    }

    public Reserva comDataHoraFim(LocalDateTime dataHoraFim) {
        return new Reserva(this.id, this.recurso, this.dataHoraInicio, dataHoraFim, this.status);
    }

    public Reserva comStatus(ReservaStatus status) {
        return new Reserva(this.id, this.recurso, this.dataHoraInicio, this.dataHoraFim, status);
    }

    public Reserva cancelar() {
        if (this.status == ReservaStatus.CANCELADA) {
            throw new RegraNegocioException("Reserva já está cancelada");
        }
        return comStatus(ReservaStatus.CANCELADA);
    }

    public boolean isAtiva() {
        return this.status == ReservaStatus.ATIVA;
    }

    public void validar() {
        if (this.recurso == null) {
            throw new EntidadeInvalidaException("Recurso é obrigatório");
        }
        if (!this.recurso.isAtivo()) {
            throw new EntidadeInvalidaException("Recurso inativo não pode ser reservado");
        }
        if (this.dataHoraInicio == null) {
            throw new EntidadeInvalidaException("Data/hora de início é obrigatória");
        }
        if (this.dataHoraFim == null) {
            throw new EntidadeInvalidaException("Data/hora de fim é obrigatória");
        }
        if (!this.dataHoraInicio.isBefore(this.dataHoraFim)) {
            throw new EntidadeInvalidaException("Data/hora de início deve ser anterior à data/hora de fim");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
