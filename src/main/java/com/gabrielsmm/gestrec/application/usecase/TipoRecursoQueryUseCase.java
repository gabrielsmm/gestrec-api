package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.TipoRecursoRepository;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class TipoRecursoQueryUseCase {

    private final TipoRecursoRepository repository;

    @Transactional(readOnly = true)
    public TipoRecurso buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<TipoRecurso> buscarTodos() {
        return repository.buscarTodos();
    }

}
