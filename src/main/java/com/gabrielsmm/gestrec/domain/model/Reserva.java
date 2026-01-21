package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.business.EntidadeInvalidaException;
import com.gabrielsmm.gestrec.domain.exception.business.RegraNegocioException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Reserva {

    private final Long id;
    private final Recurso recurso;
    private final Usuario usuario;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private ReservaStatus status;

    // Construtor privado: só pode ser chamado pelas fábricas
    private Reserva(Long id,
                    Recurso recurso,
                    Usuario usuario,
                    LocalDateTime inicio,
                    LocalDateTime fim,
                    ReservaStatus status) {
        this.id = id;
        this.recurso = recurso;
        this.usuario = usuario;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
        this.status = status != null ? status : ReservaStatus.ATIVA;
    }

    // Fábrica para reservas novas (sem id e sem usuário ainda)
    public static Reserva novaReserva(Recurso recurso, LocalDateTime inicio, LocalDateTime fim) {
        validarRecurso(recurso);
        validarDatas(inicio, fim);
        return new Reserva(null, recurso, null, inicio, fim, ReservaStatus.ATIVA);
    }

    // Fábrica para reconstrução (com id e usuário obrigatórios)
    public static Reserva reconstruida(Long id, Recurso recurso, Usuario usuario,
                                       LocalDateTime inicio, LocalDateTime fim, ReservaStatus status) {
        validarRecurso(recurso);
        validarUsuario(usuario);
        validarDatas(inicio, fim);
        return new Reserva(id, recurso, usuario, inicio, fim, status);
    }

    // Fábrica para atualização (apenas datas)
    public static Reserva apenasComDatas(LocalDateTime inicio, LocalDateTime fim) {
        validarDatas(inicio, fim);
        return new Reserva(null, null, null, inicio, fim, null);
    }

    // Validações
    private static void validarRecurso(Recurso recurso) {
        if (recurso == null) throw new EntidadeInvalidaException("Recurso é obrigatório");
        if (!recurso.isAtivo()) throw new RegraNegocioException("Recurso inativo não pode ser reservado");
    }

    private static void validarUsuario(Usuario usuario) {
        if (usuario == null) throw new EntidadeInvalidaException("Usuário é obrigatório");
    }

    private static void validarDatas(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) throw new EntidadeInvalidaException("Datas obrigatórias");
        if (!inicio.isBefore(fim)) throw new RegraNegocioException("Início deve ser antes do fim");
    }

    // Regras de negócio
    public void reagendar(LocalDateTime novoInicio, LocalDateTime novoFim) {
        validarDatas(novoInicio, novoFim);
        this.dataHoraInicio = novoInicio;
        this.dataHoraFim = novoFim;
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

    public boolean isCancelada() {
        return this.status == ReservaStatus.CANCELADA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return id != null && id.equals(reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}