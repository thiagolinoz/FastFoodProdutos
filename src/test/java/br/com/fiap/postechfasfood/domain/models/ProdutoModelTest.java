package br.com.fiap.postechfasfood.domain.models;

import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProdutoModel - Testes do Builder")
class ProdutoModelTest {

    @Test
    @DisplayName("Deve criar ProdutoModel usando Builder com todos os campos")
    void deveCriarProdutoModelComBuilderCompleto() {
        // Arrange & Act
        ProdutoModel produto = new ProdutoModel.Builder()
                .setCdProduto("123e4567-e89b-12d3-a456-426614174000")
                .setNmProduto("X-Burger")
                .setDsDescricao("Hambúrguer com queijo")
                .setVlPreco(25.90)
                .setSnActive(true)
                .setTpCategoria(ProdutoEnum.LANCHE)
                .build();

        // Assert
        assertNotNull(produto);
        assertEquals("123e4567-e89b-12d3-a456-426614174000", produto.getCdProduto());
        assertEquals("X-Burger", produto.getNmProduto());
        assertEquals("Hambúrguer com queijo", produto.getDsDescricao());
        assertEquals(25.90, produto.getVlPreco());
        assertTrue(produto.getAtivo());
        assertEquals(ProdutoEnum.LANCHE, produto.getTpCategoria());
    }

    @Test
    @DisplayName("Deve criar ProdutoModel usando Builder com valores parciais")
    void deveCriarProdutoModelComBuilderParcial() {
        // Arrange & Act
        ProdutoModel produto = new ProdutoModel.Builder()
                .setNmProduto("Coca-Cola")
                .setVlPreco(5.50)
                .build();

        // Assert
        assertNotNull(produto);
        assertNull(produto.getCdProduto());
        assertEquals("Coca-Cola", produto.getNmProduto());
        assertNull(produto.getDsDescricao());
        assertEquals(5.50, produto.getVlPreco());
        assertFalse(produto.getAtivo());
        assertNull(produto.getTpCategoria());
    }

    @Test
    @DisplayName("Deve retornar instância do Builder ao chamar setCdProduto")
    void deveRetornarBuilderAoChamarSetCdProduto() {
        // Arrange
        ProdutoModel.Builder builder = new ProdutoModel.Builder();

        // Act
        ProdutoModel.Builder resultado = builder.setCdProduto("123");

        // Assert
        assertNotNull(resultado);
        assertSame(builder, resultado);
    }

    @Test
    @DisplayName("Deve retornar instância do Builder ao chamar setNmProduto")
    void deveRetornarBuilderAoChamarSetNmProduto() {
        // Arrange
        ProdutoModel.Builder builder = new ProdutoModel.Builder();

        // Act
        ProdutoModel.Builder resultado = builder.setNmProduto("Produto Teste");

        // Assert
        assertNotNull(resultado);
        assertSame(builder, resultado);
    }

    @Test
    @DisplayName("Deve retornar instância do Builder ao chamar setDsDescricao")
    void deveRetornarBuilderAoChamarSetDsDescricao() {
        // Arrange
        ProdutoModel.Builder builder = new ProdutoModel.Builder();

        // Act
        ProdutoModel.Builder resultado = builder.setDsDescricao("Descrição teste");

        // Assert
        assertNotNull(resultado);
        assertSame(builder, resultado);
    }

    @Test
    @DisplayName("Deve retornar instância do Builder ao chamar setVlPreco")
    void deveRetornarBuilderAoChamarSetVlPreco() {
        // Arrange
        ProdutoModel.Builder builder = new ProdutoModel.Builder();

        // Act
        ProdutoModel.Builder resultado = builder.setVlPreco(10.50);

        // Assert
        assertNotNull(resultado);
        assertSame(builder, resultado);
    }

    @Test
    @DisplayName("Deve retornar instância do Builder ao chamar setSnActive")
    void deveRetornarBuilderAoChamarSetSnActive() {
        // Arrange
        ProdutoModel.Builder builder = new ProdutoModel.Builder();

        // Act
        ProdutoModel.Builder resultado = builder.setSnActive(true);

        // Assert
        assertNotNull(resultado);
        assertSame(builder, resultado);
    }

    @Test
    @DisplayName("Deve retornar instância do Builder ao chamar setTpCategoria")
    void deveRetornarBuilderAoChamarSetTpCategoria() {
        // Arrange
        ProdutoModel.Builder builder = new ProdutoModel.Builder();

        // Act
        ProdutoModel.Builder resultado = builder.setTpCategoria(ProdutoEnum.BEBIDA);

        // Assert
        assertNotNull(resultado);
        assertSame(builder, resultado);
    }

    @Test
    @DisplayName("Deve criar ProdutoModel com Builder em cadeia")
    void deveCriarProdutoModelComBuilderEmCadeia() {
        // Arrange & Act
        ProdutoModel produto = new ProdutoModel.Builder()
                .setCdProduto("999")
                .setNmProduto("Sorvete")
                .setDsDescricao("Sorvete de chocolate")
                .setVlPreco(8.00)
                .setSnActive(true)
                .setTpCategoria(ProdutoEnum.SOBREMESA)
                .build();

        // Assert
        assertNotNull(produto);
        assertEquals("999", produto.getCdProduto());
        assertEquals("Sorvete", produto.getNmProduto());
        assertEquals("Sorvete de chocolate", produto.getDsDescricao());
        assertEquals(8.00, produto.getVlPreco());
        assertTrue(produto.getAtivo());
        assertEquals(ProdutoEnum.SOBREMESA, produto.getTpCategoria());
    }

    @Test
    @DisplayName("Deve permitir sobrescrever valores no Builder")
    void devePermitirSobrescreverValoresNoBuilder() {
        // Arrange & Act
        ProdutoModel produto = new ProdutoModel.Builder()
                .setNmProduto("Nome Inicial")
                .setNmProduto("Nome Final")
                .setVlPreco(10.00)
                .setVlPreco(15.00)
                .build();

        // Assert
        assertNotNull(produto);
        assertEquals("Nome Final", produto.getNmProduto());
        assertEquals(15.00, produto.getVlPreco());
    }

    @Test
    @DisplayName("Deve criar ProdutoModel inativo usando Builder")
    void deveCriarProdutoModelInativoComBuilder() {
        // Arrange & Act
        ProdutoModel produto = new ProdutoModel.Builder()
                .setNmProduto("Produto Inativo")
                .setVlPreco(20.00)
                .setSnActive(false)
                .setTpCategoria(ProdutoEnum.ACOMPANHAMENTO)
                .build();

        // Assert
        assertNotNull(produto);
        assertEquals("Produto Inativo", produto.getNmProduto());
        assertFalse(produto.getAtivo());
        assertEquals(ProdutoEnum.ACOMPANHAMENTO, produto.getTpCategoria());
    }

    @Test
    @DisplayName("Deve criar múltiplos produtos com mesmo Builder")
    void deveCriarMultiplosProdutosComMesmoBuilder() {
        // Arrange
        ProdutoModel.Builder builder = new ProdutoModel.Builder();

        // Act
        ProdutoModel produto1 = builder
                .setNmProduto("Produto 1")
                .setVlPreco(10.00)
                .build();

        ProdutoModel produto2 = builder
                .setNmProduto("Produto 2")
                .setVlPreco(20.00)
                .build();

        // Assert
        assertNotNull(produto1);
        assertNotNull(produto2);
        // Produto2 sobrescreve os valores do produto1 no builder
        assertEquals("Produto 2", produto2.getNmProduto());
        assertEquals(20.00, produto2.getVlPreco());
    }
}
