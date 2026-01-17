package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import com.gabrielsmm.gestrec.domain.repository.TipoRecursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoRecursoUseCase {

    private final TipoRecursoRepository repository;

    @Transactional
    public TipoRecurso create(TipoRecurso novo) {
        // valida regra de unicidade de nome
        if (repository.existsByNomeIgnoreCase(novo.getNome())) {
            throw new EntidadeDuplicadaException("Nome já existe: " + novo.getNome());
        }
        return repository.save(novo);
    }

    @Transactional
    public TipoRecurso update(Long id, TipoRecurso atualizado) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + id);
        }
        // valida unicidade ignorando o próprio id
        if (repository.existsByNomeIgnoreCaseAndIdNot(atualizado.getNome(), id)) {
            throw new EntidadeDuplicadaException("Nome já existe: " + atualizado.getNome());
        }
        TipoRecurso withId = atualizado.withId(id);
        return repository.save(withId);
    }

    @Transactional(readOnly = true)
    public TipoRecurso findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<TipoRecurso> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

}
