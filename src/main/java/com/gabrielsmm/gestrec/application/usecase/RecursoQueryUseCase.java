package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.RecursoRepository;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class RecursoQueryUseCase {

    private final RecursoRepository repository;

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
    public List<Recurso> buscarComFiltros(Long tipoRecursoId, String nome, String localizacao, Boolean ativo) {
        return repository.buscarComFiltros(tipoRecursoId, nome, localizacao, ativo);
    }

}
