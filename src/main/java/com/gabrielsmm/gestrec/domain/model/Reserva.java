package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.EntidadeInvalidaException;
import com.gabrielsmm.gestrec.domain.exception.RegraNegocioException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Reserva {

    private final Long id;
    private Recurso recurso;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private ReservaStatus status;

    // Construtor para novas reservas (sem id)
    public Reserva(Recurso recurso, LocalDateTime inicio, LocalDateTime fim) {
        this(null, recurso, inicio, fim, ReservaStatus.ATIVA);
    }

    // Construtor para reconstrução (com id)
    public Reserva(Long id, Recurso recurso, LocalDateTime inicio, LocalDateTime fim, ReservaStatus status) {
        if (recurso == null) throw new EntidadeInvalidaException("Recurso é obrigatório");
        if (!recurso.isAtivo()) throw new RegraNegocioException("Recurso inativo não pode ser reservado");
        if (inicio == null || fim == null) throw new EntidadeInvalidaException("Datas obrigatórias");
        if (!inicio.isBefore(fim)) throw new RegraNegocioException("Início deve ser antes do fim");

        this.id = id;
        this.recurso = recurso;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
        this.status = status != null ? status : ReservaStatus.ATIVA;
    }

    public void reagendar(LocalDateTime novoInicio, LocalDateTime novoFim) {
        if (!novoInicio.isBefore(novoFim)) throw new RegraNegocioException("Início deve ser antes do fim");
        this.dataHoraInicio = novoInicio;
        this.dataHoraFim = novoFim;
    }

    public void alterarRecurso(Recurso novoRecurso) {
        if (novoRecurso == null) {
            throw new EntidadeInvalidaException("Recurso é obrigatório");
        }
        if (!novoRecurso.isAtivo()) {
            throw new RegraNegocioException("Recurso inativo não pode ser reservado");
        }
        this.recurso = novoRecurso;
    }

    public void cancelar() {
        if (this.status == ReservaStatus.CANCELADA) {
            throw new RegraNegocioException("Reserva já cancelada");
        }
        this.status = ReservaStatus.CANCELADA;
    }

    public boolean isAtiva() {
        return this.status == ReservaStatus.ATIVA;
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