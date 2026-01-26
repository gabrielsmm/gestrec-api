package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.RecursoEntity;
import com.gabrielsmm.gestrec.adapter.persistence.mapper.RecursoEntityMapper;
import com.gabrielsmm.gestrec.application.port.repository.RecursoRepositoryPort;
import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.Pagina;
import com.gabrielsmm.gestrec.domain.model.ParametrosPaginacao;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaRecursoRepositoryAdapter implements RecursoRepositoryPort {

    private final SpringDataRecursoRepo repo;
    private final RecursoEntityMapper mapper;

    @Override
    public Recurso salvar(Recurso recurso) {
        try {
            RecursoEntity savedEntity = repo.save(mapper.toEntity(recurso));
            return mapper.toDomain(savedEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeDuplicadaException("Nome já existe: " + recurso.getNome());
        }
    }

    @Override
    public Optional<Recurso> buscarPorId(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Recurso> buscarTodos() {
        return repo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Pagina<Recurso> buscarComFiltrosPaginado(Long tipoRecursoId, String nome, String localizacao, Boolean ativo, ParametrosPaginacao paginacao) {
        Pageable pageable = PageRequest.of(
                paginacao.numeroPagina(),
                paginacao.tamanhoPagina(),
                Sort.by("nome").ascending()
        );
        Page<RecursoEntity> page = repo.findComFiltrosPaginado(tipoRecursoId, nome, localizacao, ativo, pageable);
        return toPagina(page);
    }

    @Override
    public void excluirPorId(Long id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return repo.existsById(id);
    }

    @Override
    public boolean existePorNome(String nome) {
        return repo.existsByNomeIgnoreCase(nome);
    }

    @Override
    public boolean existePorNomeIgnorandoId(String nome, Long id) {
        return repo.existsByNomeIgnoreCaseAndIdNot(nome, id);
    }

    private Pagina<Recurso> toPagina(Page<RecursoEntity> page) {
        List<Recurso> conteudo = page.getContent().stream()
                .map(mapper::toDomain)
                .toList();

        return new Pagina<>(
                conteudo,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

}
