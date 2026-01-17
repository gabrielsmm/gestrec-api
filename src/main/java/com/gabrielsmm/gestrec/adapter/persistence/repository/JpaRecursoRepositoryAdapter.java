package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.exception.EntidadeNaoEncontradaException;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import com.gabrielsmm.gestrec.domain.repository.RecursoRepository;
import com.gabrielsmm.gestrec.adapter.persistence.entity.RecursoEntity;
import com.gabrielsmm.gestrec.adapter.persistence.entity.TipoRecursoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaRecursoRepositoryAdapter implements RecursoRepository {

    private final SpringDataRecursoRepo repo;
    private final SpringDataTipoRecursoRepo tipoRepo;

    private Recurso toDomain(RecursoEntity e) {
        if (e == null) return null;
        TipoRecurso tipo = null;
        if (e.getTipoRecurso() != null) {
            tipo = new TipoRecurso(e.getTipoRecurso().getId(), e.getTipoRecurso().getNome(), e.getTipoRecurso().getDescricao());
        }
        return new Recurso(
                e.getId(),
                e.getNome(),
                e.getLocalizacao(),
                e.isAtivo(),
                tipo
        );
    }

    private RecursoEntity toEntity(Recurso r) {
        if (r == null) return null;

        RecursoEntity e = new RecursoEntity();
        e.setId(r.getId());
        e.setNome(r.getNome());
        e.setLocalizacao(r.getLocalizacao());
        e.setAtivo(r.isAtivo());

        if (r.getTipoRecurso() != null) {
            Long tipoId = r.getTipoRecurso().getId();
            if (tipoId == null) {
                throw new IllegalArgumentException("TipoRecurso deve possuir ID");
            }

            TipoRecursoEntity tr = tipoRepo.findById(tipoId)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("TipoRecurso não encontrado: id=" + tipoId));

            e.setTipoRecurso(tr);
        }

        return e;
    }

    @Override
    public Recurso save(Recurso recurso) {
        try {
            RecursoEntity savedEntity = repo.save(toEntity(recurso));
            return toDomain(savedEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeDuplicadaException("Nome já existe: " + recurso.getNome());
        }
    }

    @Override
    public Optional<Recurso> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public List<Recurso> findAll() {
        return repo.findAll().stream().map(this::toDomain).collect(Collectors.toList());
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
