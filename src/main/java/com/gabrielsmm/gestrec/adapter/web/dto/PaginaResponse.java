package com.gabrielsmm.gestrec.adapter.web.dto;

import java.util.List;

public record PaginaResponse<T>(
        List<T> conteudo,
        int numeroPagina,
        int tamanhoPagina,
        long totalElementos,
        int totalPaginas,
        boolean primeira,
        boolean ultima,
        boolean temProxima,
        boolean temAnterior
) {}

