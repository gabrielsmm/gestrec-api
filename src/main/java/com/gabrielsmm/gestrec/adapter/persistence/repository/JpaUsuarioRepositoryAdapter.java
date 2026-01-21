package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.UsuarioEntity;
import com.gabrielsmm.gestrec.adapter.persistence.mapper.UsuarioEntityMapper;
import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepository;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JpaUsuarioRepositoryAdapter implements UsuarioRepository {

    private final SpringDataUsuarioRepo repo;
    private final UsuarioEntityMapper mapper;

    @Override
    public Usuario salvar(Usuario usuario) {
        try {
            UsuarioEntity saved = repo.save(mapper.toEntity(usuario));
            return mapper.toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeDuplicadaException("Email já existe: " + usuario.getEmail());
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return repo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return repo.findByEmailIgnoreCase(email).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> buscarTodos() {
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
    public boolean existePorEmail(String email) {
        return repo.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existePorEmailIgnorandoId(String email, Long id) {
        return repo.existsByEmailIgnoreCaseAndIdNot(email, id);
    }

}

