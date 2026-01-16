package com.gabrielsmm.gestrec.adapter.api.controller;

import com.gabrielsmm.gestrec.adapter.api.dto.TipoRecursoMapper;
import com.gabrielsmm.gestrec.adapter.api.dto.TipoRecursoRequest;
import com.gabrielsmm.gestrec.adapter.api.dto.TipoRecursoResponse;
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
    public ResponseEntity<TipoRecursoResponse> create(@Valid @RequestBody TipoRecursoRequest req) {
        TipoRecurso domain = mapper.toDomain(req);
        TipoRecurso created = useCase.create(domain);
        TipoRecursoResponse resp = mapper.toResponse(created);
        return ResponseEntity.created(URI.create("/api/tipos-recurso/" + resp.getId())).body(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoRecursoResponse> getById(@PathVariable Long id) {
        TipoRecurso t = useCase.findById(id);
        return ResponseEntity.ok(mapper.toResponse(t));
    }

    @GetMapping
    public ResponseEntity<List<TipoRecursoResponse>> listAll() {
        List<TipoRecursoResponse> list = useCase.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoRecursoResponse> update(@PathVariable Long id, @Valid @RequestBody TipoRecursoRequest req) {
        TipoRecurso updatedDomain = new TipoRecurso(id, req.getNome(), req.getDescricao());
        TipoRecurso saved = useCase.update(id, updatedDomain);
        return ResponseEntity.ok(mapper.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

}
