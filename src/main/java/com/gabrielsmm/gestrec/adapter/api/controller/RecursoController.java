package com.gabrielsmm.gestrec.adapter.api.controller;

import com.gabrielsmm.gestrec.adapter.api.dto.RecursoMapper;
import com.gabrielsmm.gestrec.adapter.api.dto.RecursoRequest;
import com.gabrielsmm.gestrec.adapter.api.dto.RecursoResponse;
import com.gabrielsmm.gestrec.application.usecase.RecursoUseCase;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recursos")
@RequiredArgsConstructor
public class RecursoController {

    private final RecursoUseCase useCase;
    private final RecursoMapper mapper;

    @PostMapping
    public ResponseEntity<RecursoResponse> create(@Valid @RequestBody RecursoRequest req) {
        Recurso domain = mapper.toDomain(req);
        Recurso created = useCase.create(domain);
        RecursoResponse resp = mapper.toResponse(created);
        return ResponseEntity.created(URI.create("/api/recursos/" + resp.getId())).body(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoResponse> getById(@PathVariable Long id) {
        Recurso r = useCase.findById(id);
        return ResponseEntity.ok(mapper.toResponse(r));
    }

    @GetMapping
    public ResponseEntity<List<RecursoResponse>> listAll() {
        List<RecursoResponse> list = useCase.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursoResponse> update(@PathVariable Long id, @Valid @RequestBody RecursoRequest req) {
        Recurso toUpdate = mapper.toDomain(req).withId(id);
        Recurso saved = useCase.update(id, toUpdate);
        return ResponseEntity.ok(mapper.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }

}
