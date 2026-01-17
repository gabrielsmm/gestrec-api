package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.web.dto.ReservaMapper;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaResponse;
import com.gabrielsmm.gestrec.application.usecase.ReservaUseCase;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaUseCase useCase;
    private final ReservaMapper mapper;

    @PostMapping
    public ResponseEntity<ReservaResponse> criar(@Valid @RequestBody ReservaRequest req) {
        Reserva dados = mapper.toDomain(req);
        Reserva criada = useCase.criar(dados);
        return ResponseEntity
                .created(URI.create("/api/reservas/" + criada.getId()))
                .body(mapper.toResponse(criada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponse> atualizar(@PathVariable Long id, @Valid @RequestBody ReservaRequest req) {
        Reserva dadosAtualizados = mapper.toDomain(req);
        Reserva salva = useCase.atualizar(id, dadosAtualizados);
        return ResponseEntity.ok(mapper.toResponse(salva));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> buscarPorId(@PathVariable Long id) {
        Reserva r = useCase.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponse(r));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> buscarTodos() {
        List<ReservaResponse> lista = useCase.buscarTodos().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponse> cancelar(@PathVariable Long id) {
        Reserva cancelada = useCase.cancelar(id);
        return ResponseEntity.ok(mapper.toResponse(cancelada));
    }

}
