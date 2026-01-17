package com.gabrielsmm.gestrec.domain.model;

import lombok.Getter;

@Getter
public enum ReservaStatus {
    ATIVA(1),
    CANCELADA(2);

    private final int codigo;

    ReservaStatus(int codigo) {
        this.codigo = codigo;
    }

    public static ReservaStatus fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        for (ReservaStatus s : values()) {
            if (s.codigo == codigo) return s;
        }
        throw new IllegalArgumentException("Código de ReservaStatus inválido: " + codigo);
    }
}
