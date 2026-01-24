package com.gabrielsmm.gestrec.shared.pagination;

public record ParametrosPaginacao(
        int numeroPagina,
        int tamanhoPagina
) {
    public ParametrosPaginacao {
        if (numeroPagina < 0) {
            throw new IllegalArgumentException("Número da página não pode ser negativo");
        }
        if (tamanhoPagina <= 0) {
            throw new IllegalArgumentException("Tamanho da página deve ser maior que zero");
        }
        if (tamanhoPagina > 100) {
            throw new IllegalArgumentException("Tamanho da página não pode exceder 100");
        }
    }

    public static ParametrosPaginacao of(int numeroPagina, int tamanhoPagina) {
        return new ParametrosPaginacao(numeroPagina, tamanhoPagina);
    }

    public static ParametrosPaginacao padrao() {
        return new ParametrosPaginacao(0, 20);
    }
}

