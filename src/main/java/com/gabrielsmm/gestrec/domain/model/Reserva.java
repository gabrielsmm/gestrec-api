package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.business.EntidadeInvalidaException;
import com.gabrielsmm.gestrec.domain.exception.business.RegraNegocioException;
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
    private Usuario usuario;

    // Construtor para novas reservas (sem id e sem usuário) - usado antes de associar o usuário autenticado
    public Reserva(Recurso recurso, LocalDateTime inicio, LocalDateTime fim) {
        if (recurso == null) throw new EntidadeInvalidaException("Recurso é obrigatório");
        if (!recurso.isAtivo()) throw new RegraNegocioException("Recurso inativo não pode ser reservado");
        if (inicio == null || fim == null) throw new EntidadeInvalidaException("Datas obrigatórias");
        if (!inicio.isBefore(fim)) throw new RegraNegocioException("Início deve ser antes do fim");

        this.id = null;
        this.recurso = recurso;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
        this.status = ReservaStatus.ATIVA;
        this.usuario = null;
    }

    // Construtor para reconstrução (com id e usuário)
    public Reserva(Long id, Recurso recurso, LocalDateTime inicio, LocalDateTime fim, ReservaStatus status, Usuario usuario) {
        if (usuario == null) throw new EntidadeInvalidaException("Usuário é obrigatório");
        if (recurso == null) throw new EntidadeInvalidaException("Recurso é obrigatório");
        if (!recurso.isAtivo()) throw new RegraNegocioException("Recurso inativo não pode ser reservado");
        if (inicio == null || fim == null) throw new EntidadeInvalidaException("Datas obrigatórias");
        if (!inicio.isBefore(fim)) throw new RegraNegocioException("Início deve ser antes do fim");

        this.id = id;
        this.recurso = recurso;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
        this.status = status != null ? status : ReservaStatus.ATIVA;
        this.usuario = usuario;
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