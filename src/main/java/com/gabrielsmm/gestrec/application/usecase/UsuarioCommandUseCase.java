package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepositoryPort;
import com.gabrielsmm.gestrec.application.port.service.PasswordEncoderPort;
import com.gabrielsmm.gestrec.application.usecase.dto.CadastrarUsuarioCommand;
import com.gabrielsmm.gestrec.domain.exception.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UsuarioCommandUseCase {

    private final UsuarioRepositoryPort repository;
    private final PasswordEncoderPort passwordEncoder;

    @Transactional
    public Usuario cadastrar(CadastrarUsuarioCommand command) {
        if (repository.existePorEmail(command.email())) {
            throw new EntidadeDuplicadaException("Email já cadastrado: " + command.email());
        }

        String senhaEncriptada = passwordEncoder.encode(command.senha());
        Usuario usuario = Usuario.criarNovo(command.nome(), command.email(), senhaEncriptada);

        return repository.salvar(usuario);
    }

}
