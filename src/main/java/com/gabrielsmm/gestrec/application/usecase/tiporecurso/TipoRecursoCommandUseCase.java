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
        validarNomeDuplicado(command.nome(), null);

        TipoRecurso novo = TipoRecurso.criarNovo(command.nome(), command.descricao());

        return repository.salvar(novo);
    }

    @Transactional
    public TipoRecurso atualizar(AtualizarTipoRecursoCommand command) {
        TipoRecurso existente = buscarTipoRecursoPorId(command.tipoRecursoId());
        validarNomeDuplicado(command.nome(), command.tipoRecursoId());

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

    // ===== Métodos auxiliares =====
    private TipoRecurso buscarTipoRecursoPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Tipo de Recurso não encontrado com id: " + id));
    }

    private void validarNomeDuplicado(String nome, Long idExcluir) {
        boolean duplicado = (idExcluir == null)
                ? repository.existePorNome(nome)
                : repository.existePorNomeIgnorandoId(nome, idExcluir);

        if (duplicado) {
            throw new EntidadeDuplicadaException("Nome já existe: " + nome);
        }
    }

}
