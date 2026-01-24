package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.TipoRecursoEntity;
import com.gabrielsmm.gestrec.adapter.persistence.mapper.TipoRecursoEntityMapper;
import com.gabrielsmm.gestrec.application.port.repository.TipoRecursoRepository;
import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaTipoRecursoRepositoryAdapter implements TipoRecursoRepository {

    private final SpringDataTipoRecursoRepo repo;
    private final TipoRecursoEntityMapper mapper;

//    private TipoRecurso toDomain(TipoRecursoEntity e) {
//        if (e == null) return null;
//        return new TipoRecurso(e.getId(), e.getNome(), e.getDescricao());
//    }
//
//    private TipoRecursoEntity toEntity(TipoRecurso t) {
//        if (t == null) return null;
//        TipoRecursoEntity e = new TipoRecursoEntity();
//        e.setId(t.getId());
//        e.setNome(t.getNome());
//        e.setDescricao(t.getDescricao());
//        return e;
//    }

    @Override
    public TipoRecurso salvar(TipoRecurso tipoRecurso) {
        try {
            TipoRecursoEntity savedEntity = repo.save(mapper.toEntity(tipoRecurso));
            return mapper.toDomain(savedEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeDuplicadaException("Nome já existe: " + tipoRecurso.getNome());
        }
    }

    @Override
    public Optional<TipoRecurso> buscarPorId(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<TipoRecurso> buscarTodos() {
        return repo.findAll().stream().map(mapper::toDomain).toList();
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
