package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.security.auth.UserDetailsImpl;
import com.gabrielsmm.gestrec.adapter.web.dto.PaginaResponse;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaInsertRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaResponse;
import com.gabrielsmm.gestrec.adapter.web.dto.ReservaUpdateRequest;
import com.gabrielsmm.gestrec.adapter.web.mapper.ReservaDTOMapper;
import com.gabrielsmm.gestrec.application.usecase.ReservaCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.ReservaQueryUseCase;
import com.gabrielsmm.gestrec.shared.pagination.Pagina;
import com.gabrielsmm.gestrec.shared.pagination.ParametrosPaginacao;
import com.gabrielsmm.gestrec.domain.model.Reserva;
import com.gabrielsmm.gestrec.domain.model.ReservaStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
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
    @Operation(summary = "Listar reservas com filtros opcionais e paginação",
               description = "Busca reservas aplicando filtros opcionais: recursoId, usuarioId, período (dataInicio+dataFim), status, me=true. " +
                       "Suporta paginação através dos parâmetros page e size.")
    public ResponseEntity<PaginaResponse<ReservaResponse>> listar(
            @Parameter(description = "ID do recurso")
            @RequestParam(required = false) Long recursoId,
            @Parameter(description = "ID do usuário")
            @RequestParam(required = false) Long usuarioId,
            @Parameter(description = "Data/hora início do período (formato ISO-8601)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @Parameter(description = "Data/hora fim do período (formato ISO-8601)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @Parameter(description = "Status da reserva (ATIVA ou CANCELADA)")
            @RequestParam(required = false) ReservaStatus status,
            @Parameter(description = "Filtrar apenas minhas reservas (sobrescreve usuarioId)")
            @RequestParam(required = false) Boolean me,
            @Parameter(description = "Número da página (padrão: 0)")
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @Parameter(description = "Tamanho da página (padrão: 20, máximo: 100)")
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long usuarioIdFiltro = Boolean.TRUE.equals(me) ? userDetails.getId() : usuarioId;
        ParametrosPaginacao paginacao = ParametrosPaginacao.of(page, size);

        Pagina<Reserva> paginaReservas = queryUseCase.buscarComFiltrosPaginado(
                recursoId, dataInicio, dataFim, usuarioIdFiltro, status, paginacao
        );

        return ResponseEntity.ok(toPaginaResponse(paginaReservas));
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

    private PaginaResponse<ReservaResponse> toPaginaResponse(Pagina<Reserva> pagina) {
        List<ReservaResponse> conteudo = pagina.conteudo().stream()
                .map(mapper::toResponse)
                .toList();

        return new PaginaResponse<>(
                conteudo,
                pagina.numeroPagina(),
                pagina.tamanhoPagina(),
                pagina.totalElementos(),
                pagina.totalPaginas(),
                pagina.isPrimeira(),
                pagina.isUltima(),
                pagina.temProxima(),
                pagina.temAnterior()
        );
    }

}
