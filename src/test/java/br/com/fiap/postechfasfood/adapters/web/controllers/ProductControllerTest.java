package br.com.fiap.postechfasfood.adapters.web.controllers;

import br.com.fiap.postechfasfood.adapters.mappers.ProdutoMapper;
import br.com.fiap.postechfasfood.adapters.web.requests.ProdutoRequest;
import br.com.fiap.postechfasfood.adapters.web.responses.ProdutoResponse;
import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.domain.ports.in.ProdutoServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProdutoServicePort produtoServicePort;

    @Mock
    private ProdutoMapper mapper;

    @InjectMocks
    private ProductController productController;

    private ProdutoRequest produtoRequest;
    private ProdutoModel produtoModel;

    @BeforeEach
    void setUp() {
        produtoRequest = new ProdutoRequest(
                "X-Burger",
                "Hambúrguer com queijo",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        produtoModel = new ProdutoModel();
        produtoModel.setCdProduto("123e4567-e89b-12d3-a456-426614174000");
        produtoModel.setNmProduto("X-Burger");
        produtoModel.setDsDescricao("Hambúrguer com queijo");
        produtoModel.setVlPreco(25.90);
        produtoModel.setAtivo(true);
        produtoModel.setTpCategoria(ProdutoEnum.LANCHE);
    }

    @Test
    @DisplayName("Deve cadastrar um produto com sucesso")
    void deveCadastrarProdutoComSucesso() {
        // Arrange
        when(mapper.toModel(produtoRequest)).thenReturn(produtoModel);
        when(produtoServicePort.cadastrar(produtoModel)).thenReturn(produtoModel);

        // Act
        ResponseEntity<ProdutoResponse> response = productController.cadastrarProduto(produtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("123e4567-e89b-12d3-a456-426614174000", response.getBody().cdProduto());
        assertEquals("X-Burger", response.getBody().nmProduto());
        assertTrue(response.getHeaders().getLocation().toString().contains("/api/v1/produto/123e4567-e89b-12d3-a456-426614174000"));
        verify(mapper).toModel(produtoRequest);
        verify(produtoServicePort).cadastrar(produtoModel);
    }

    @Test
    @DisplayName("Deve atualizar um produto existente com sucesso")
    void deveAtualizarProdutoExistenteComSucesso() {
        // Arrange
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        when(mapper.toModel(produtoRequest)).thenReturn(produtoModel);
        when(produtoServicePort.atualizarProduto(cdProduto, produtoModel)).thenReturn(produtoModel);

        // Act
        ResponseEntity<ProdutoResponse> response = productController.atualizarProduto(cdProduto, produtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("X-Burger", response.getBody().nmProduto());
        verify(mapper).toModel(produtoRequest);
        verify(produtoServicePort).atualizarProduto(cdProduto, produtoModel);
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar atualizar produto inexistente")
    void deveRetornar404AoAtualizarProdutoInexistente() {
        // Arrange
        String cdProduto = "inexistente";
        when(mapper.toModel(produtoRequest)).thenReturn(produtoModel);
        when(produtoServicePort.atualizarProduto(cdProduto, produtoModel))
                .thenThrow(new RuntimeException("Produto não encontrado"));

        // Act
        ResponseEntity<ProdutoResponse> response = productController.atualizarProduto(cdProduto, produtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(mapper).toModel(produtoRequest);
        verify(produtoServicePort).atualizarProduto(cdProduto, produtoModel);
    }

    @Test
    @DisplayName("Deve ativar um produto com sucesso")
    void deveAtivarProdutoComSucesso() {
        // Arrange
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        when(produtoServicePort.ativar(cdProduto)).thenReturn(produtoModel);

        // Act
        ResponseEntity<ProdutoResponse> response = productController.ativarProduto(cdProduto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().snAtivo());
        verify(produtoServicePort).ativar(cdProduto);
    }

    @Test
    @DisplayName("Deve desativar um produto com sucesso")
    void deveDesativarProdutoComSucesso() {
        // Arrange
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        produtoModel.setAtivo(false);
        when(produtoServicePort.desativar(cdProduto)).thenReturn(produtoModel);

        // Act
        ResponseEntity<ProdutoResponse> response = productController.desativarProduto(cdProduto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().snAtivo());
        verify(produtoServicePort).desativar(cdProduto);
    }

    @Test
    @DisplayName("Deve listar produtos por categoria com sucesso")
    void deveListarProdutosPorCategoriaComSucesso() {
        // Arrange
        ProdutoModel produto2 = new ProdutoModel();
        produto2.setCdProduto("456");
        produto2.setNmProduto("Big Burger");
        produto2.setDsDescricao("Hambúrguer grande");
        produto2.setVlPreco(35.90);
        produto2.setAtivo(true);
        produto2.setTpCategoria(ProdutoEnum.LANCHE);

        List<ProdutoModel> produtos = Arrays.asList(produtoModel, produto2);
        List<ProdutoResponse> responses = Arrays.asList(
                new ProdutoResponse(produtoModel),
                new ProdutoResponse(produto2)
        );

        when(produtoServicePort.listarProdutosPorCategoria(ProdutoEnum.LANCHE)).thenReturn(produtos);
        when(mapper.toResoponse(produtos)).thenReturn(responses);

        // Act
        ResponseEntity<List<ProdutoResponse>> response = productController.listarPorCategoria(ProdutoEnum.LANCHE);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("X-Burger", response.getBody().get(0).nmProduto());
        assertEquals("Big Burger", response.getBody().get(1).nmProduto());
        verify(produtoServicePort).listarProdutosPorCategoria(ProdutoEnum.LANCHE);
        verify(mapper).toResoponse(produtos);
    }

    @Test
    @DisplayName("Deve retornar 404 quando não houver produtos da categoria")
    void deveRetornar404QuandoNaoHouverProdutosDaCategoria() {
        // Arrange
        when(produtoServicePort.listarProdutosPorCategoria(ProdutoEnum.SOBREMESA))
                .thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ProdutoResponse>> response = productController.listarPorCategoria(ProdutoEnum.SOBREMESA);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(produtoServicePort).listarProdutosPorCategoria(ProdutoEnum.SOBREMESA);
        verify(mapper, never()).toResoponse(any());
    }

    @Test
    @DisplayName("Deve listar todos os produtos com sucesso")
    void deveListarTodosProdutosComSucesso() {
        // Arrange
        ProdutoModel produto2 = new ProdutoModel();
        produto2.setCdProduto("456");
        produto2.setNmProduto("Coca-Cola");
        produto2.setDsDescricao("Refrigerante 350ml");
        produto2.setVlPreco(5.50);
        produto2.setAtivo(true);
        produto2.setTpCategoria(ProdutoEnum.BEBIDA);

        List<ProdutoModel> produtos = Arrays.asList(produtoModel, produto2);
        List<ProdutoResponse> responses = Arrays.asList(
                new ProdutoResponse(produtoModel),
                new ProdutoResponse(produto2)
        );

        when(produtoServicePort.listarProdutos()).thenReturn(produtos);
        when(mapper.toResoponse(produtos)).thenReturn(responses);

        // Act
        ResponseEntity<List<ProdutoResponse>> response = productController.listarProdutos();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("X-Burger", response.getBody().get(0).nmProduto());
        assertEquals("Coca-Cola", response.getBody().get(1).nmProduto());
        verify(produtoServicePort).listarProdutos();
        verify(mapper).toResoponse(produtos);
    }

    @Test
    @DisplayName("Deve retornar 404 quando não houver produtos cadastrados")
    void deveRetornar404QuandoNaoHouverProdutosCadastrados() {
        // Arrange
        when(produtoServicePort.listarProdutos()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ProdutoResponse>> response = productController.listarProdutos();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(produtoServicePort).listarProdutos();
        verify(mapper, never()).toResoponse(any());
    }

    @Test
    @DisplayName("Deve listar apenas produtos BEBIDA")
    void deveListarApenasProdutosBebida() {
        // Arrange
        ProdutoModel bebida = new ProdutoModel();
        bebida.setCdProduto("789");
        bebida.setNmProduto("Coca-Cola");
        bebida.setDsDescricao("Refrigerante");
        bebida.setVlPreco(5.50);
        bebida.setAtivo(true);
        bebida.setTpCategoria(ProdutoEnum.BEBIDA);

        List<ProdutoModel> produtos = Arrays.asList(bebida);
        List<ProdutoResponse> responses = Arrays.asList(new ProdutoResponse(bebida));

        when(produtoServicePort.listarProdutosPorCategoria(ProdutoEnum.BEBIDA)).thenReturn(produtos);
        when(mapper.toResoponse(produtos)).thenReturn(responses);

        // Act
        ResponseEntity<List<ProdutoResponse>> response = productController.listarPorCategoria(ProdutoEnum.BEBIDA);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("BEBIDA", response.getBody().get(0).tpCategoria());
        verify(produtoServicePort).listarProdutosPorCategoria(ProdutoEnum.BEBIDA);
    }

    @Test
    @DisplayName("Deve retornar URI correta ao cadastrar produto")
    void deveRetornarURICorretaAoCadastrarProduto() {
        // Arrange
        when(mapper.toModel(produtoRequest)).thenReturn(produtoModel);
        when(produtoServicePort.cadastrar(produtoModel)).thenReturn(produtoModel);

        // Act
        ResponseEntity<ProdutoResponse> response = productController.cadastrarProduto(produtoRequest);

        // Assert
        assertNotNull(response.getHeaders().getLocation());
        String expectedUri = "/api/v1/produto/123e4567-e89b-12d3-a456-426614174000";
        assertEquals(expectedUri, response.getHeaders().getLocation().toString());
    }

    @Test
    @DisplayName("Deve listar produtos de todas as categorias")
    void deveListarProdutosDeTodasAsCategorias() {
        // Arrange
        for (ProdutoEnum categoria : ProdutoEnum.values()) {
            ProdutoModel produto = new ProdutoModel();
            produto.setCdProduto("id-" + categoria.name());
            produto.setNmProduto("Produto " + categoria.name());
            produto.setTpCategoria(categoria);
            produto.setAtivo(true);

            when(produtoServicePort.listarProdutosPorCategoria(categoria))
                    .thenReturn(Arrays.asList(produto));
            when(mapper.toResoponse(anyList()))
                    .thenReturn(Arrays.asList(new ProdutoResponse(produto)));

            // Act
            ResponseEntity<List<ProdutoResponse>> response = productController.listarPorCategoria(categoria);

            // Assert
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertFalse(response.getBody().isEmpty());
        }

        verify(produtoServicePort, times(ProdutoEnum.values().length)).listarProdutosPorCategoria(any());
    }

    @Test
    @DisplayName("Deve retornar 204 ao consultar produto inexistente")
    void deveRetornar204AoConsultarProdutoInexistente() {
        // Arrange
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        when(produtoServicePort.consultarProduto(cdProduto)).thenReturn(null);

        // Act
        ResponseEntity<ProdutoResponse> response = productController.consultarProduto(cdProduto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(produtoServicePort).consultarProduto(cdProduto);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Deve consultar produto com sucesso e retornar 200")
    void deveConsultarProdutoComSucesso() {
        // Arrange
        String cdProduto = produtoModel.getCdProduto();
        when(produtoServicePort.consultarProduto(cdProduto)).thenReturn(produtoModel);

        // Act
        ResponseEntity<ProdutoResponse> response = productController.consultarProduto(cdProduto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(produtoModel.getCdProduto(), response.getBody().cdProduto());
        assertEquals(produtoModel.getNmProduto(), response.getBody().nmProduto());
        assertEquals(produtoModel.getTpCategoria(), ProdutoEnum.valueOf(response.getBody().tpCategoria()));
        verify(produtoServicePort).consultarProduto(cdProduto);
        verifyNoInteractions(mapper);
    }
}
