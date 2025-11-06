package br.com.fiap.postechfasfood.hexagonal.adapters.web.controllers;

import br.com.fiap.postechfasfood.hexagonal.adapters.commons.mappers.ProdutoMapper;
import br.com.fiap.postechfasfood.hexagonal.adapters.web.requests.ProdutoRequest;
import br.com.fiap.postechfasfood.hexagonal.adapters.web.responses.ProdutoResponse;
import br.com.fiap.postechfasfood.hexagonal.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.hexagonal.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.in.ProdutoServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Service
@RestController
@RequestMapping("/api")
@Tag(name = "Produtos", description = "end-point para gerenciar os produtos")
public class ProductController {

    private final ProdutoServicePort produtoServicePort;
    private final ProdutoMapper mapper;

    public ProductController(ProdutoServicePort produtoServicePort, ProdutoMapper mapper) {
        this.produtoServicePort = produtoServicePort;
        this.mapper = mapper;
    }

    @PostMapping("/v1/produto")
    @Operation(summary = "Cadastra produtos", description = "Cadastra os produtos")
    public ResponseEntity<ProdutoResponse> cadastrarProduto(@Valid @RequestBody ProdutoRequest request) {
        ProdutoModel novoProduto = produtoServicePort.cadastrar(mapper.toModel(request));
        return ResponseEntity.created(URI.create("/api/v1/produto/" + novoProduto.getCdProduto())).body(new ProdutoResponse(novoProduto));
    }

    @PutMapping("/v1/produto/{cdProduct}")
    @Operation(summary = "Atualiza produtos", description = "Atualiza produtos existentes")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable String cdProduto, @Valid @RequestBody ProdutoRequest request) {
        try {
            ProdutoModel produtoAtualizado = produtoServicePort.atualizarProduto(cdProduto, mapper.toModel(request));
            return ResponseEntity.ok(new ProdutoResponse(produtoAtualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/v1/produto/{cdProduct}/ativar")
    @Operation(summary = "Ativa produtos", description = "Ativa produtos existentes")
    public ResponseEntity<ProdutoResponse> ativarProduto(@PathVariable String cdProduto) {
        ProdutoModel produtoAtualizado = produtoServicePort.ativar(cdProduto);
        return ResponseEntity.ok(new ProdutoResponse(produtoAtualizado));
    }

    @PatchMapping("/v1/produto/{cdProduct}/desativar")
    @Operation(summary = "Desativa produtos", description = "Desativa produtos existentes")
    public ResponseEntity<ProdutoResponse> desativarProduto(@PathVariable String cdProduto) {
        ProdutoModel produtoAtualizado = produtoServicePort.desativar(cdProduto);
        return ResponseEntity.ok(new ProdutoResponse(produtoAtualizado));
    }


    @GetMapping("/v1/produtos/categoria")
    @Operation(summary = "Lista produtos", description = "Lista todos produtos existentes por categoria")
    public ResponseEntity<List<ProdutoResponse>> listarPorCategoria(
            @RequestParam ProdutoEnum tpCategoria) {
        List<ProdutoResponse> listaProdutos = produtoServicePort.listarProdutosPorCategoria(tpCategoria);

        if (listaProdutos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(produtoResponse.stream().map(mapper::t).toList(););
    }
//
//    @GetMapping("/v1/produtos")
//    @Operation(summary = "Lista produtos", description = "Lista todos produtos existentes")
//    public ResponseEntity<List<ProdutoWebHandlerResponse>> buscar() {
//        final ProductController produtoController = new ProductController();
//        produtoController.listarProdutos(produtoRepository);
//        List<ProdutoWebHandlerResponse> produtoResponse = produtoController.listarProdutos(produtoRepository);
//
//        if (produtoResponse.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(produtoResponse);
//    }
}