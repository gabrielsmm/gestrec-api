package com.gabrielsmm.gestrec.application.usecase;

import com.gabrielsmm.gestrec.domain.exception.business.RegraNegocioException;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import com.gabrielsmm.gestrec.domain.port.repository.UsuarioRepository;
import com.gabrielsmm.gestrec.domain.port.service.PasswordEncoderPort;
import com.gabrielsmm.gestrec.domain.port.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenService tokenService;

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