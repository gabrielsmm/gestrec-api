package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.RecursoEntity;
import com.gabrielsmm.gestrec.adapter.persistence.mapper.RecursoEntityMapper;
import com.gabrielsmm.gestrec.application.port.repository.RecursoRepository;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaRecursoRepositoryAdapter implements RecursoRepository {

    private final SpringDataRecursoRepo repo;
    private final RecursoEntityMapper mapper;
//    private final SpringDataTipoRecursoRepo tipoRepo;
//
//    private Recurso toDomain(RecursoEntity e) {
//        if (e == null) return null;
//        TipoRecurso tipo = null;
//        if (e.getTipoRecurso() != null) {
//            tipo = new TipoRecurso(e.getTipoRecurso().getId(), e.getTipoRecurso().getNome(), e.getTipoRecurso().getDescricao());
//        }
//        return new Recurso(
//                e.getId(),
//                e.getNome(),
//                e.getLocalizacao(),
//                e.isAtivo(),
//                tipo
//        );
//    }
//
//    private RecursoEntity toEntity(Recurso r) {
//        if (r == null) return null;
//
//        RecursoEntity e = new RecursoEntity();
//        e.setId(r.getId());
//        e.setNome(r.getNome());
//        e.setLocalizacao(r.getLocalizacao());
//        e.setAtivo(r.isAtivo());
//
//        if (r.getTipoRecurso() != null) {
//            Long tipoId = r.getTipoRecurso().getId();
//            if (tipoId == null) {
//                throw new IllegalArgumentException("TipoRecurso deve possuir ID");
//            }
//
//            TipoRecursoEntity tr = tipoRepo.findById(tipoId)
//                    .orElseThrow(() -> new EntidadeNaoEncontradaException("TipoRecurso não encontrado: id=" + tipoId));
//
//            e.setTipoRecurso(tr);
//        }
//
//        return e;
//    }

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
        return repo.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
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
