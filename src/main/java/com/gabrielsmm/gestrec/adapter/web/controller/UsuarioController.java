package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.web.dto.UsuarioMapper;
import com.gabrielsmm.gestrec.adapter.web.dto.UsuarioRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.UsuarioResponse;
import com.gabrielsmm.gestrec.application.usecase.UsuarioUseCase;
import com.gabrielsmm.gestrec.domain.model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase useCase;
    private final UsuarioMapper mapper;

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest req) {
        Usuario novo = mapper.toDomain(req);
        Usuario salvo = useCase.cadastrar(novo);
        return ResponseEntity
                .created(URI.create("/api/usuarios/" + salvo.getId()))
                .body(mapper.toResponse(salvo));
    }
}

