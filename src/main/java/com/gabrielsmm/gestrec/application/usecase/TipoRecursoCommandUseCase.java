package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.TipoRecursoRepository;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class TipoRecursoCommandUseCase {

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

    @Transactional
    public void excluir(Long id) {
        if (!repository.existePorId(id)) {
            throw new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + id);
        }
        repository.excluirPorId(id);
    }

}
