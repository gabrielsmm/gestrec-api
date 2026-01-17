package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import com.gabrielsmm.gestrec.domain.repository.RecursoRepository;
import com.gabrielsmm.gestrec.domain.repository.TipoRecursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecursoUseCase {

    private final RecursoRepository repository;
    private final TipoRecursoRepository tipoRepository;

    @Transactional
    public Recurso create(Recurso novo) {
        novo.validate();
        Long tipoId = novo.getTipoRecurso() != null ? novo.getTipoRecurso().getId() : null;
        TipoRecurso tipo = tipoRepository.buscarPorId(tipoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + tipoId));

        if (repository.existsByNomeIgnoreCase(novo.getNome())) {
            throw new EntidadeDuplicadaException("Nome já existe: " + novo.getNome());
        }

        Recurso toSave = novo.withTipoRecurso(tipo);
        return repository.save(toSave);
    }

    @Transactional
    public Recurso update(Long id, Recurso atualizado) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + id);
        }
        atualizado.validate();

        Long tipoId = atualizado.getTipoRecurso() != null ? atualizado.getTipoRecurso().getId() : null;
        TipoRecurso tipo = tipoRepository.buscarPorId(tipoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + tipoId));

        if (repository.existsByNomeIgnoreCaseAndIdNot(atualizado.getNome(), id)) {
            throw new EntidadeDuplicadaException("Nome já existe: " + atualizado.getNome());
        }

        Recurso withId = atualizado.withId(id).withTipoRecurso(tipo);
        return repository.save(withId);
    }

    @Transactional(readOnly = true)
    public Recurso findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Recurso> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

}
