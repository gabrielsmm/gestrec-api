package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.web.dto.LoginRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.TokenResponse;
import com.gabrielsmm.gestrec.application.usecase.AuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "1 - Usuários", description = "Operações de usuário e autenticação")
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    @Operation(summary = "Autenticar e receber token JWT")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        String token = authUseCase.autenticar(req.email(), req.senha());

        return ResponseEntity.ok(new TokenResponse(token));
    }

}
