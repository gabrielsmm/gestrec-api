package com.gabrielsmm.gestrec.application.usecase.tiporecurso;

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
    public TipoRecurso criar(CriarTipoRecursoCommand command) {
        // valida regra de unicidade de nome
        if (repository.existePorNome(command.nome())) {
            throw new EntidadeDuplicadaException("Nome já existe: " + command.nome());
        }

        TipoRecurso novo = TipoRecurso.criarNovo(command.nome(), command.descricao());

        return repository.salvar(novo);
    }

    @Transactional
    public TipoRecurso atualizar(AtualizarTipoRecursoCommand command) {
        TipoRecurso existente = repository.buscarPorId(command.tipoRecursoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + command.tipoRecursoId()));

        // valida unicidade ignorando o próprio id
        if (repository.existePorNomeIgnorandoId(command.nome(), command.tipoRecursoId())) {
            throw new EntidadeDuplicadaException("Nome já existe: " + command.nome());
        }

        existente.renomear(command.nome());
        existente.alterarDescricao(command.descricao());

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
