package com.gabrielsmm.gestrec.application.port.repository;

import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.shared.pagination.Pagina;
import com.gabrielsmm.gestrec.shared.pagination.ParametrosPaginacao;

import java.util.List;
import java.util.Optional;

public interface RecursoRepositoryPort {

    Recurso salvar(Recurso recurso);
    Optional<Recurso> buscarPorId(Long id);
    List<Recurso> buscarTodos();
    Pagina<Recurso> buscarComFiltrosPaginado(Long tipoRecursoId, String nome, String localizacao, Boolean ativo, ParametrosPaginacao paginacao);
    void excluirPorId(Long id);
    boolean existePorId(Long id);
    boolean existePorNome(String nome);
    boolean existePorNomeIgnorandoId(String nome, Long id);

}
