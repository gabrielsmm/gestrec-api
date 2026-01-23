package com.gabrielsmm.gestrec.application.usecase.recurso;

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
    public Recurso criar(CriarRecursoCommand command) {
        TipoRecurso tipo = tipoRepository.buscarPorId(command.tipoRecursoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + command.tipoRecursoId()));

        if (repository.existePorNome(command.nome())) {
            throw new EntidadeDuplicadaException("Nome já existe: " + command.nome());
        }

        Recurso recurso = Recurso.criarNovo(command.nome(), command.localizacao(), tipo);

        return repository.salvar(recurso);
    }

    @Transactional
    public Recurso atualizar(AtualizarRecursoCommand command) {
        Recurso existente = repository.buscarPorId(command.recursoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + command.recursoId()));

        TipoRecurso tipo = tipoRepository.buscarPorId(command.tipoRecursoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + command.tipoRecursoId()));

        if (repository.existePorNomeIgnorandoId(command.nome(), command.recursoId())) {
            throw new EntidadeDuplicadaException("Nome já existe: " + command.nome());
        }

        existente.renomear(command.nome());
        existente.alterarLocalizacao(command.localizacao());
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
