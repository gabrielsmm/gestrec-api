package com.gabrielsmm.gestrec.application.port.repository;

import com.gabrielsmm.gestrec.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {

    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> buscarTodos();
    void excluirPorId(Long id);
    boolean existePorId(Long id);
    boolean existePorEmail(String email);
    boolean existePorEmailIgnorandoId(String email, Long id);

}
