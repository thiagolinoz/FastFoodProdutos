package br.com.fiap.postechfasfood.hexagonal.adapters.in.web.controllers;

import br.com.fiap.postechfasfood.apis.requests.ProdutoWebHandlerRequest;
import br.com.fiap.postechfasfood.apis.responses.ProdutoWebHandlerResponse;
import br.com.fiap.postechfasfood.controllers.ProdutoController;
import br.com.fiap.postechfasfood.hexagonal.adapters.in.web.requests.ProdutoControllerRequest;
import br.com.fiap.postechfasfood.hexagonal.adapters.in.web.responses.ProdutoControllerResponse;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.in.ProdutoServicePort;
import br.com.fiap.postechfasfood.interfaces.ProdutoRepositoryInterface;
import br.com.fiap.postechfasfood.types.TipoCategoriaProdutoEnum;
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
public class ProdutoController {

    private final ProdutoServicePort produtoServicePort;

    public ProdutoController(ProdutoServicePort produtoServicePort) {
        this.produtoServicePort = produtoServicePort;
    }

    @PostMapping("/v1/produto")
    @Operation(summary = "Cadastra produtos", description = "Cadastra os produtos")
    public ResponseEntity<ProdutoControllerResponse> cadastrarProduto(@Valid @RequestBody ProdutoControllerRequest request) {
        var response = new ProdutoControllerResponse(produtoServicePort.cadastrar(request));
        return ResponseEntity.created(URI.create("/api/v1/produto/" + response.cdProduto()))
                .body(response);
    }

//    @PutMapping("/v1/produto/{cdProduto}")
//    @Operation(summary = "Atualiza produtos", description = "Atualiza produtos existentes")
//    public ResponseEntity<ProdutoWebHandlerResponse> atualizarProduto(
//            @PathVariable String cdProduto,
//            @Valid @RequestBody ProdutoWebHandlerRequest request) {
//
//        final ProdutoController produtoController = new ProdutoController();
//        var response = produtoController.atualizarProduto(produtoRepository, cdProduto, request);
//
//        if (response == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PatchMapping("/v1/produto/{cdProduto}/desativar")
//    @Operation(summary = "Desativa produtos", description = "Desativa produtos existentes")
//    public ResponseEntity<Void> desativarProduto(@PathVariable String cdProduto) {
//        final ProdutoController produtoController = new ProdutoController();
//        produtoController.desativarProduto(produtoRepository, cdProduto);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PatchMapping("/v1/produto/{cdProduto}/ativar")
//    @Operation(summary = "Ativa produtos", description = "Ativa produtos existentes")
//    public ResponseEntity<Void> ativarProduto(@PathVariable String cdProduto) {
//        final ProdutoController produtoController = new ProdutoController();
//        produtoController.ativarProduto(produtoRepository, cdProduto);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/v1/produtos/categoria")
//    @Operation(summary = "Lista produtos", description = "Lista todos produtos existentes por categoria")
//    public ResponseEntity<List<ProdutoWebHandlerResponse>> listarPorCategoria(
//            @RequestParam TipoCategoriaProdutoEnum tpCategoria) {
//        final ProdutoController produtoController = new ProdutoController();
//        produtoController.listarProdutosPorCategoria(produtoRepository, tpCategoria);
//        List<ProdutoWebHandlerResponse> produtoResponse = produtoController.listarProdutosPorCategoria(produtoRepository, tpCategoria);
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
//        final ProdutoController produtoController = new ProdutoController();
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