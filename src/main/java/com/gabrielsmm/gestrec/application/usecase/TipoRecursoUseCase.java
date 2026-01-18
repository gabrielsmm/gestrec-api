package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import com.gabrielsmm.gestrec.domain.port.repository.TipoRecursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoRecursoUseCase {

    private final TipoRecursoRepository repository;

    @Transactional
    public TipoRecurso criar(TipoRecurso novo) {
        // valida regra de unicidade de nome
        if (repository.existePorNome(novo.getNome())) {
            throw new EntidadeDuplicadaException("Nome já existe: " + novo.getNome());
        }
        return repository.salvar(novo);
    }

    @Transactional
    public TipoRecurso atualizar(Long id, TipoRecurso atualizado) {
        TipoRecurso existente = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + id));

        // valida unicidade ignorando o próprio id
        if (repository.existePorNomeIgnorandoId(atualizado.getNome(), id)) {
            throw new EntidadeDuplicadaException("Nome já existe: " + atualizado.getNome());
        }

        existente.renomear(atualizado.getNome());
        existente.alterarDescricao(atualizado.getDescricao());

        return repository.salvar(existente);
    }

    @Transactional(readOnly = true)
    public TipoRecurso buscarPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<TipoRecurso> buscarTodos() {
        return repository.buscarTodos();
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existePorId(id)) {
            throw new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + id);
        }
        repository.excluirPorId(id);
    }

}
