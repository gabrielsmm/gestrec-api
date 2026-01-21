package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.web.mapper.RecursoDTOMapper;
import com.gabrielsmm.gestrec.adapter.web.dto.RecursoRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.RecursoResponse;
import com.gabrielsmm.gestrec.application.usecase.RecursoUseCase;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recursos")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "3 - Recursos", description = "Operações sobre recursos")
public class RecursoController {

    private final RecursoUseCase useCase;
    private final RecursoDTOMapper mapper;

    @PostMapping
    @Operation(summary = "Criar recurso")
    public ResponseEntity<RecursoResponse> criar(@Valid @RequestBody RecursoRequest req) {
        Recurso dados = mapper.toDomain(req);
        Recurso criado = useCase.criar(dados);
        return ResponseEntity
                .created(URI.create("/api/recursos/" + criado.getId()))
                .body(mapper.toResponse(criado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar recurso")
    public ResponseEntity<RecursoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody RecursoRequest req) {
        Recurso dadosAtualizados = mapper.toDomain(req);
        Recurso salvo = useCase.atualizar(id, dadosAtualizados);
        return ResponseEntity.ok(mapper.toResponse(salvo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar recurso por id")
    public ResponseEntity<RecursoResponse> buscarPorId(@PathVariable Long id) {
        Recurso r = useCase.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponse(r));
    }

    @GetMapping
    @Operation(summary = "Listar todos os recursos")
    public ResponseEntity<List<RecursoResponse>> buscarTodos() {
        List<RecursoResponse> list = useCase.buscarTodos().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir recurso")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
