package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.domain.port.service.PasswordEncoderPort;
import com.gabrielsmm.gestrec.domain.exception.technical.EntidadeDuplicadaException;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import com.gabrielsmm.gestrec.domain.port.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioRepository repository;
    private final PasswordEncoderPort encoder;

    @Transactional
    public Usuario cadastrar(Usuario novo) {
        if (repository.existePorEmail(novo.getEmail())) {
            throw new EntidadeDuplicadaException("Email já cadastrado: " + novo.getEmail());
        }
        novo.alterarSenha(encoder.encode(novo.getSenha()));
        return repository.salvar(novo);
    }

}
