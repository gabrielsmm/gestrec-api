package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.web.dto.TipoRecursoMapper;
import com.gabrielsmm.gestrec.adapter.web.dto.TipoRecursoRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.TipoRecursoResponse;
import com.gabrielsmm.gestrec.application.usecase.TipoRecursoUseCase;
import com.gabrielsmm.gestrec.domain.model.TipoRecurso;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-recursos")
@RequiredArgsConstructor
public class TipoRecursoController {

    private final TipoRecursoUseCase useCase;
    private final TipoRecursoMapper mapper;

    @PostMapping
    public ResponseEntity<TipoRecursoResponse> criar(@Valid @RequestBody TipoRecursoRequest req) {
        TipoRecurso domain = mapper.toDomain(req);
        TipoRecurso created = useCase.criar(domain);
        TipoRecursoResponse resp = mapper.toResponse(created);
        return ResponseEntity.created(URI.create("/api/tipos-recurso/" + resp.getId())).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoRecursoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody TipoRecursoRequest req) {
        TipoRecurso updatedDomain = new TipoRecurso(id, req.getNome(), req.getDescricao());
        TipoRecurso saved = useCase.atualizar(id, updatedDomain);
        return ResponseEntity.ok(mapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoRecursoResponse> buscarPorId(@PathVariable Long id) {
        TipoRecurso t = useCase.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponse(t));
    }

    @GetMapping
    public ResponseEntity<List<TipoRecursoResponse>> buscarTodos() {
        List<TipoRecursoResponse> list = useCase.buscarTodos().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
