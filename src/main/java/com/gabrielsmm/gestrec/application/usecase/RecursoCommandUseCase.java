package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.RecursoRepository;
import com.gabrielsmm.gestrec.application.port.repository.TipoRecursoRepository;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class RecursoCommandUseCase {

    private final RecursoRepository repository;
    private final TipoRecursoRepository tipoRepository;

    @Transactional
    public Recurso criar(Recurso novo) {
        Long tipoId =  novo.getTipoRecurso().getId();
        TipoRecurso tipo = tipoRepository.buscarPorId(tipoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + tipoId));

        if (repository.existePorNome(novo.getNome())) {
            throw new EntidadeDuplicadaException("Nome já existe: " + novo.getNome());
        }

        Recurso recurso = new Recurso(novo.getNome(), novo.getLocalizacao(), novo.isAtivo(), tipo);
        return repository.salvar(recurso);
    }

    @Transactional
    public Recurso atualizar(Long id, Recurso atualizado) {
        Recurso existente = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + id));

        Long tipoId = atualizado.getTipoRecurso().getId();
        TipoRecurso tipo = tipoRepository.buscarPorId(tipoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + tipoId));

        if (repository.existePorNomeIgnorandoId(atualizado.getNome(), id)) {
            throw new EntidadeDuplicadaException("Nome já existe: " + atualizado.getNome());
        }

        existente.renomear(atualizado.getNome());
        existente.alterarLocalizacao(atualizado.getLocalizacao());
        existente.alterarTipo(tipo);

        return repository.salvar(existente);
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existePorId(id)) {
            throw new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + id);
        }
        repository.excluirPorId(id);
    }

}
