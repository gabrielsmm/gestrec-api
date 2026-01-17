package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import com.gabrielsmm.gestrec.domain.repository.TipoRecursoRepository;
import com.gabrielsmm.gestrec.adapter.persistence.entity.TipoRecursoEntity;
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
    public TipoRecurso save(TipoRecurso tipoRecurso) {
        try {
            TipoRecursoEntity savedEntity = repo.save(toEntity(tipoRecurso));
            return toDomain(savedEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeDuplicadaException("Nome já existe: " + tipoRecurso.getNome());
        }
    }

    @Override
    public Optional<TipoRecurso> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public List<TipoRecurso> findAll() {
        return repo.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public boolean existsByNomeIgnoreCase(String nome) {
        return repo.existsByNomeIgnoreCase(nome);
    }

    @Override
    public boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id) {
        return repo.existsByNomeIgnoreCaseAndIdNot(nome, id);
    }

}
