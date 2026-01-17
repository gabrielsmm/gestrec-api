package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.TipoRecursoEntity;
import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import com.gabrielsmm.gestrec.domain.repository.TipoRecursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaTipoRecursoRepositoryAdapter implements TipoRecursoRepository {

    private final SpringDataTipoRecursoRepo repo;

    private TipoRecurso toDomain(TipoRecursoEntity e) {
        if (e == null) return null;
        return new TipoRecurso(e.getId(), e.getNome(), e.getDescricao());
    }

    private TipoRecursoEntity toEntity(TipoRecurso t) {
        if (t == null) return null;
        TipoRecursoEntity e = new TipoRecursoEntity();
        e.setId(t.getId());
        e.setNome(t.getNome());
        e.setDescricao(t.getDescricao());
        return e;
    }

    @Override
    public TipoRecurso salvar(TipoRecurso tipoRecurso) {
        try {
            TipoRecursoEntity savedEntity = repo.save(toEntity(tipoRecurso));
            return toDomain(savedEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeDuplicadaException("Nome já existe: " + tipoRecurso.getNome());
        }
    }

    @Override
    public Optional<TipoRecurso> buscarPorId(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public List<TipoRecurso> buscarTodos() {
        return repo.findAll().stream().map(this::toDomain).toList();
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

}
