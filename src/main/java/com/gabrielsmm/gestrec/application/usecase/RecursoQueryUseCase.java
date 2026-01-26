package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.RecursoRepositoryPort;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.shared.pagination.Pagina;
import com.gabrielsmm.gestrec.shared.pagination.ParametrosPaginacao;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class RecursoQueryUseCase {

    private final RecursoRepositoryPort repository;

    @Transactional(readOnly = true)
    public Recurso buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Recurso> buscarTodos() {
        return repository.buscarTodos();
    }

    @Transactional(readOnly = true)
    public Pagina<Recurso> buscarComFiltrosPaginado(Long tipoRecursoId, String nome, String localizacao, Boolean ativo, ParametrosPaginacao paginacao) {
        return repository.buscarComFiltrosPaginado(tipoRecursoId, nome, localizacao, ativo, paginacao);
    }

}
