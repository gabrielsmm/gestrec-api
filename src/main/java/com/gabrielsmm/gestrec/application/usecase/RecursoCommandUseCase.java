package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.RecursoRepositoryPort;
import com.gabrielsmm.gestrec.application.port.repository.TipoRecursoRepositoryPort;
import com.gabrielsmm.gestrec.application.usecase.dto.AtualizarRecursoCommand;
import com.gabrielsmm.gestrec.application.usecase.dto.CriarRecursoCommand;
import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class RecursoCommandUseCase {

    private final RecursoRepositoryPort repository;
    private final TipoRecursoRepositoryPort tipoRepository;

    @Transactional
    public Recurso criar(CriarRecursoCommand command) {
        TipoRecurso tipo = buscarTipoRecursoPorId(command.tipoRecursoId());
        validarNomeDuplicado(command.nome(), null);

        Recurso recurso = Recurso.criarNovo(command.nome(), command.localizacao(), tipo);

        return repository.salvar(recurso);
    }

    @Transactional
    public Recurso atualizar(AtualizarRecursoCommand command) {
        Recurso existente = buscarRecursoPorId(command.recursoId());
        TipoRecurso tipo = buscarTipoRecursoPorId(command.tipoRecursoId());
        validarNomeDuplicado(command.nome(), command.recursoId());

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

    @Transactional
    public Recurso ativar(Long id) {
        Recurso recurso = buscarRecursoPorId(id);
        recurso.ativar();
        return repository.salvar(recurso);
    }

    @Transactional
    public Recurso desativar(Long id) {
        Recurso recurso = buscarRecursoPorId(id);
        recurso.desativar();
        return repository.salvar(recurso);
    }

    // ===== Métodos auxiliares =====
    private Recurso buscarRecursoPorId(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Recurso não encontrado com id: " + id));
    }

    private TipoRecurso buscarTipoRecursoPorId(Long id) {
        return tipoRepository.buscarPorId(id)
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
