package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.web.dto.RecursoRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.RecursoResponse;
import com.gabrielsmm.gestrec.adapter.web.mapper.RecursoDTOMapper;
import com.gabrielsmm.gestrec.application.usecase.recurso.RecursoCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.recurso.RecursoQueryUseCase;
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

    private final RecursoCommandUseCase commandUseCase;
    private final RecursoQueryUseCase queryUseCase;
    private final RecursoDTOMapper mapper;

    @PostMapping
    @Operation(summary = "Criar recurso")
    public ResponseEntity<RecursoResponse> criar(@Valid @RequestBody RecursoRequest req) {
        Recurso criado = commandUseCase.criar(mapper.toCommand(req));
        return ResponseEntity
                .created(URI.create("/api/recursos/" + criado.getId()))
                .body(mapper.toResponse(criado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar recurso")
    public ResponseEntity<RecursoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody RecursoRequest req) {
        Recurso salvo = commandUseCase.atualizar(mapper.toCommand(req, id));
        return ResponseEntity.ok(mapper.toResponse(salvo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar recurso por id")
    public ResponseEntity<RecursoResponse> buscarPorId(@PathVariable Long id) {
        Recurso r = queryUseCase.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponse(r));
    }

    @GetMapping
    @Operation(summary = "Listar todos os recursos")
    public ResponseEntity<List<RecursoResponse>> buscarTodos() {
        List<RecursoResponse> list = queryUseCase.buscarTodos().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir recurso")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        commandUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
