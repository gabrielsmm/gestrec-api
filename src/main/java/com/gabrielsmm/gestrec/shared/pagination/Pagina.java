package com.gabrielsmm.gestrec.shared.pagination;

import java.util.List;

public record Pagina<T>(
        List<T> conteudo,
        int numeroPagina,
        int tamanhoPagina,
        long totalElementos,
        int totalPaginas
) {
    public boolean temProxima() {
        return numeroPagina < totalPaginas - 1;
    }

    public boolean temAnterior() {
        return numeroPagina > 0;
    }

    public boolean isPrimeira() {
        return numeroPagina == 0;
    }

    public boolean isUltima() {
        return numeroPagina >= totalPaginas - 1;
    }
}

