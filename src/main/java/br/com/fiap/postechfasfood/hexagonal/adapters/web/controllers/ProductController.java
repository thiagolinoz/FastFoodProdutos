package br.com.fiap.postechfasfood.hexagonal.adapters.web.controllers;

import br.com.fiap.postechfasfood.hexagonal.adapters.commons.mappers.ProductMapper;
import br.com.fiap.postechfasfood.hexagonal.adapters.web.requests.ProductRequest;
import br.com.fiap.postechfasfood.hexagonal.adapters.web.responses.ProductResponse;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.in.ProductServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Service
@RestController
@RequestMapping("/api")
@Tag(name = "Produtos", description = "end-point para gerenciar os produtos")
public class ProductController {

    private final ProductServicePort productServicePort;
    private final ProductMapper mapper;

    public ProductController(ProductServicePort productServicePort, ProductMapper mapper) {
        this.productServicePort = productServicePort;
        this.mapper = mapper;
    }

    @PostMapping("/v1/product")
    @Operation(summary = "Cadastra produtos", description = "Cadastra os produtos")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        var response = productServicePort.create(mapper.toModel(request));
        return ResponseEntity.created(URI.create("/api/v1/produto/" + response.getCdProduct()))
                .body(new ProductResponse(response));
    }

//    @PutMapping("/v1/produto/{cdProduct}")
//    @Operation(summary = "Atualiza produtos", description = "Atualiza produtos existentes")
//    public ResponseEntity<ProdutoWebHandlerResponse> atualizarProduto(
//            @PathVariable String cdProduct,
//            @Valid @RequestBody ProdutoWebHandlerRequest request) {
//
//        final ProductController produtoController = new ProductController();
//        var response = produtoController.atualizarProduto(produtoRepository, cdProduct, request);
//
//        if (response == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PatchMapping("/v1/produto/{cdProduct}/desativar")
//    @Operation(summary = "Desativa produtos", description = "Desativa produtos existentes")
//    public ResponseEntity<Void> desativarProduto(@PathVariable String cdProduct) {
//        final ProductController produtoController = new ProductController();
//        produtoController.desativarProduto(produtoRepository, cdProduct);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PatchMapping("/v1/produto/{cdProduct}/ativar")
//    @Operation(summary = "Ativa produtos", description = "Ativa produtos existentes")
//    public ResponseEntity<Void> ativarProduto(@PathVariable String cdProduct) {
//        final ProductController produtoController = new ProductController();
//        produtoController.ativarProduto(produtoRepository, cdProduct);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/v1/produtos/categoria")
//    @Operation(summary = "Lista produtos", description = "Lista todos produtos existentes por categoria")
//    public ResponseEntity<List<ProdutoWebHandlerResponse>> listarPorCategoria(
//            @RequestParam TipoCategoriaProdutoEnum tpCategory) {
//        final ProductController produtoController = new ProductController();
//        produtoController.listarProdutosPorCategoria(produtoRepository, tpCategory);
//        List<ProdutoWebHandlerResponse> produtoResponse = produtoController.listarProdutosPorCategoria(produtoRepository, tpCategory);
//
//        if (produtoResponse.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(produtoResponse);
//    }
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