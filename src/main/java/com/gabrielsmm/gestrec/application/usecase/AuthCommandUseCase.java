package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.application.port.repository.UsuarioRepositoryPort;
import com.gabrielsmm.gestrec.application.port.service.PasswordEncoderPort;
import com.gabrielsmm.gestrec.application.port.service.TokenServicePort;
import com.gabrielsmm.gestrec.domain.exception.RegraNegocioException;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCommandUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenServicePort tokenService;

    public String autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new RegraNegocioException("Credenciais inválidas"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RegraNegocioException("Credenciais inválidas");
        }

        return tokenService.gerarToken(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getPerfil().name()
        );
    }

}