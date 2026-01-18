package com.gabrielsmm.gestrec.adapter.persistence.repository;

import com.gabrielsmm.gestrec.adapter.persistence.entity.UsuarioEntity;
import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import com.gabrielsmm.gestrec.domain.port.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JpaUsuarioRepositoryAdapter implements UsuarioRepository {

    private final SpringDataUsuarioRepo repo;

    private Usuario toDomain(UsuarioEntity e) {
        if (e == null) return null;
        return new Usuario(
                e.getId(),
                e.getNome(),
                e.getEmail(),
                e.getSenha(),
                e.getPerfil() == null ? null : e.getPerfil()
        );
    }

    private UsuarioEntity toEntity(Usuario u) {
        if (u == null) return null;
        UsuarioEntity e = new UsuarioEntity();
        e.setId(u.getId());
        e.setNome(u.getNome());
        e.setEmail(u.getEmail());
        e.setSenha(u.getSenha());
        e.setPerfil(u.getPerfil());
        return e;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        try {
            UsuarioEntity saved = repo.save(toEntity(usuario));
            return toDomain(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeDuplicadaException("Email já existe: " + usuario.getEmail());
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return repo.findByEmailIgnoreCase(email).map(this::toDomain);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return repo.findAll().stream().map(this::toDomain).collect(Collectors.toList());
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

