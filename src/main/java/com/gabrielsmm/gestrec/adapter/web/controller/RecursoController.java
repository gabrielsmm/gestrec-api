package com.gabrielsmm.gestrec.adapter.web.controller;

import com.gabrielsmm.gestrec.adapter.web.dto.PaginaResponse;
import com.gabrielsmm.gestrec.adapter.web.dto.RecursoRequest;
import com.gabrielsmm.gestrec.adapter.web.dto.RecursoResponse;
import com.gabrielsmm.gestrec.adapter.web.mapper.RecursoDTOMapper;
import com.gabrielsmm.gestrec.application.usecase.RecursoCommandUseCase;
import com.gabrielsmm.gestrec.application.usecase.RecursoQueryUseCase;
import com.gabrielsmm.gestrec.domain.model.Recurso;
import com.gabrielsmm.gestrec.domain.model.Pagina;
import com.gabrielsmm.gestrec.domain.model.ParametrosPaginacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
        Recurso salvo = commandUseCase.atualizar(mapper.toCommand(id, req));
        return ResponseEntity.ok(mapper.toResponse(salvo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar recurso por id")
    public ResponseEntity<RecursoResponse> buscarPorId(@PathVariable Long id) {
        Recurso r = queryUseCase.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponse(r));
    }

    @GetMapping
    @Operation(summary = "Listar recursos com filtros opcionais e paginação",
               description = "Busca recursos aplicando filtros opcionais: tipoRecursoId, nome (parcial), localizacao (parcial), ativo. " +
                       "Suporta paginação através dos parâmetros page e size.")
    public ResponseEntity<PaginaResponse<RecursoResponse>> listar(
            @Parameter(description = "ID do tipo de recurso")
            @RequestParam(required = false) Long tipoRecursoId,
            @Parameter(description = "Nome do recurso (busca parcial)")
            @RequestParam(required = false) String nome,
            @Parameter(description = "Localização do recurso (busca parcial)")
            @RequestParam(required = false) String localizacao,
            @Parameter(description = "Filtrar por recursos ativos ou inativos")
            @RequestParam(required = false) Boolean ativo,
            @Parameter(description = "Número da página (padrão: 0)")
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @Parameter(description = "Tamanho da página (padrão: 20, máximo: 100)")
            @RequestParam(required = false, defaultValue = "20") Integer size
    ) {
        ParametrosPaginacao paginacao = ParametrosPaginacao.of(page, size);

        Pagina<Recurso> paginaRecursos = queryUseCase.buscarComFiltrosPaginado(
                tipoRecursoId, nome, localizacao, ativo, paginacao
        );

        return ResponseEntity.ok(toPaginaResponse(paginaRecursos));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir recurso")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        commandUseCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar recurso")
    public ResponseEntity<RecursoResponse> ativar(@PathVariable Long id) {
        Recurso ativado = commandUseCase.ativar(id);
        return ResponseEntity.ok(mapper.toResponse(ativado));
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar recurso")
    public ResponseEntity<RecursoResponse> desativar(@PathVariable Long id) {
        Recurso desativado = commandUseCase.desativar(id);
        return ResponseEntity.ok(mapper.toResponse(desativado));
    }

    private PaginaResponse<RecursoResponse> toPaginaResponse(Pagina<Recurso> pagina) {
        List<RecursoResponse> conteudo = pagina.conteudo().stream()
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
