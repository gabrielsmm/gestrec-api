package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.web.mapper.TipoRecursoMapper;
import com.gabrielsmm.gestrec.adapter.web.dto.TipoRecursoRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.TipoRecursoResponse;
import com.gabrielsmm.gestrec.application.usecase.TipoRecursoUseCase;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
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
@RequestMapping("/api/tipos-recursos")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "4 - Tipos de Recursos", description = "Operações de cadastro e consulta de tipos de recursos")
public class TipoRecursoController {

    private final TipoRecursoUseCase useCase;
    private final TipoRecursoMapper mapper;

    @PostMapping
    @Operation(summary = "Criar tipo de recurso")
    public ResponseEntity<TipoRecursoResponse> criar(@Valid @RequestBody TipoRecursoRequest req) {
        TipoRecurso dados = mapper.toDomain(req);
        TipoRecurso criado = useCase.criar(dados);
        return ResponseEntity
                .created(URI.create("/api/tipos-recurso/" + criado.getId()))
                .body(mapper.toResponse(criado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo de recurso")
    public ResponseEntity<TipoRecursoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody TipoRecursoRequest req) {
        TipoRecurso dadosAtualizados = mapper.toDomain(req);
        TipoRecurso salvo = useCase.atualizar(id, dadosAtualizados);
        return ResponseEntity.ok(mapper.toResponse(salvo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo por id")
    public ResponseEntity<TipoRecursoResponse> buscarPorId(@PathVariable Long id) {
        TipoRecurso t = useCase.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponse(t));
    }

    @GetMapping
    @Operation(summary = "Listar todos os tipos")
    public ResponseEntity<List<TipoRecursoResponse>> buscarTodos() {
        List<TipoRecursoResponse> list = useCase.buscarTodos().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tipo de recurso")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
