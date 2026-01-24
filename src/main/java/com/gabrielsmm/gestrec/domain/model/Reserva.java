package com.gabrielsmm.gestrec.domain.model;

import com.gabrielsmm.gestrec.domain.exception.EntidadeInvalidaException;
import com.gabrielsmm.gestrec.domain.exception.RegraNegocioException;
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

    // Construtor privado: garante que a entidade só seja criada de forma válida
    private Reserva(Long id,
                    Recurso recurso,
                    Usuario usuario,
                    LocalDateTime inicio,
                    LocalDateTime fim,
                    ReservaStatus status) {
        validarRecurso(recurso);
        validarUsuario(usuario);
        validarDatas(inicio, fim);

        this.id = id;
        this.recurso = recurso;
        this.usuario = usuario;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
        this.status = status;
    }

    // Fábrica para criação de uma nova reserva válida
    public static Reserva criarNova(Recurso recurso,
                                    Usuario usuario,
                                    LocalDateTime inicio,
                                    LocalDateTime fim) {
        return new Reserva(null, recurso, usuario, inicio, fim, ReservaStatus.ATIVA);
    }

    // Fábrica para reconstrução de uma reserva já existente (ex: persistência)
    public static Reserva reconstruir(Long id,
                                      Recurso recurso,
                                      Usuario usuario,
                                      LocalDateTime inicio,
                                      LocalDateTime fim,
                                      ReservaStatus status) {
        if (id == null) {
            throw new EntidadeInvalidaException("Id é obrigatório para reconstrução da reserva");
        }

        return new Reserva(id, recurso, usuario, inicio, fim, status);
    }

    // Validações
    private static void validarRecurso(Recurso recurso) {
        if (recurso == null) {
            throw new EntidadeInvalidaException("Recurso é obrigatório");
        }
        if (!recurso.isAtivo()) {
            throw new RegraNegocioException("Recurso inativo não pode ser reservado");
        }
    }

    private static void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new EntidadeInvalidaException("Usuário é obrigatório");
        }
    }

    private static void validarDatas(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) {
            throw new EntidadeInvalidaException("Datas de início e fim são obrigatórias");
        }
        if (!inicio.isBefore(fim)) {
            throw new RegraNegocioException("Data de início deve ser anterior à data de fim");
        }
        if (inicio.isBefore(LocalDateTime.now())) {
            throw new RegraNegocioException("Não é permitido fazer reservas no passado");
        }
    }

    // Regras de negócio
    public void reagendar(LocalDateTime novoInicio, LocalDateTime novoFim) {
        validarDatas(novoInicio, novoFim);

        if (this.status == ReservaStatus.CANCELADA) {
            throw new RegraNegocioException("Reserva cancelada não pode ser reagendada");
        }

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