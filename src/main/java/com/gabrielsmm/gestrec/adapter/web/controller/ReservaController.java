package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.security.auth.UserDetailsImpl;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaInsertRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaResponse;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaUpdateRequest;
import com.gabrielsmm.gestrec.adapter.web.mapper.ReservaDTOMapper;
import com.gabrielsmm.gestrec.application.usecase.reserva.ReservaCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.reserva.ReservaQueryUseCase;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Tag(name = "2 - Reservas", description = "Operações de reserva de recursos")
public class ReservaController {

    private final ReservaCommandUseCase commandUseCase;
    private final ReservaQueryUseCase queryUseCase;
    private final ReservaDTOMapper mapper;

    @PostMapping
    @Operation(summary = "Criar reserva")
    public ResponseEntity<ReservaResponse> criar(@Valid @RequestBody ReservaInsertRequest req,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Reserva criada = commandUseCase.criar(mapper.toCommand(req, userDetails.getId()));
        return ResponseEntity
                .created(URI.create("/api/reservas/" + criada.getId()))
                .body(mapper.toResponse(criada));
    }

    @PreAuthorize("@securityService.isOwner(#id) or hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar reserva")
    public ResponseEntity<ReservaResponse> atualizar(@PathVariable Long id,
                                                     @Valid @RequestBody ReservaUpdateRequest req,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Reserva salva = commandUseCase.atualizar(mapper.toCommand(id, userDetails.getId(), req));
        return ResponseEntity.ok(mapper.toResponse(salva));
    }

    @PreAuthorize("@securityService.isOwner(#id) or hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por id")
    public ResponseEntity<ReservaResponse> buscarPorId(@PathVariable Long id) {
        Reserva r = queryUseCase.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponse(r));
    }

    @GetMapping
    @Operation(summary = "Listar reservas (use 'me=true' para apenas minhas reservas)")
    public ResponseEntity<List<ReservaResponse>> buscarTodos(@RequestParam(name = "me", required = false) Boolean apenasMeu,
                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ReservaResponse> lista;
        if (Boolean.TRUE.equals(apenasMeu)) {
            lista = queryUseCase.buscarPorUsuario(userDetails.getId()).stream()
                    .map(mapper::toResponse).toList();
        } else {
            lista = queryUseCase.buscarTodos().stream()
                    .map(mapper::toResponse).toList();
        }
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("@securityService.isOwner(#id) or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir reserva")
    public ResponseEntity<Void> excluir(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commandUseCase.excluir(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@securityService.isOwner(#id) or hasRole('ADMIN')")
    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar reserva")
    public ResponseEntity<ReservaResponse> cancelar(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Reserva cancelada = commandUseCase.cancelar(id, userDetails.getId());
        return ResponseEntity.ok(mapper.toResponse(cancelada));
    }

}
