package br.com.fiap.postechfasfood.adapters.web.responses;

import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoResponseTest {

    @Test
    @DisplayName("Deve criar ProdutoResponse a partir de campos individuais")
    void deveCriarProdutoResponseAPartirDeCamposIndividuais() {
        // Arrange & Act
        ProdutoResponse response = new ProdutoResponse(
                "123e4567-e89b-12d3-a456-426614174000",
                "X-Burger",
                "Hambúrguer com queijo e bacon",
                25.90,
                true,
                "LANCHE"
        );

        // Assert
        assertNotNull(response);
        assertEquals("123e4567-e89b-12d3-a456-426614174000", response.cdProduto());
        assertEquals("X-Burger", response.nmProduto());
        assertEquals("Hambúrguer com queijo e bacon", response.dsDescricao());
        assertEquals(25.90, response.vlPreco());
        assertTrue(response.snAtivo());
        assertEquals("LANCHE", response.tpCategoria());
    }

    @Test
    @DisplayName("Deve criar ProdutoResponse a partir de ProdutoModel")
    void deveCriarProdutoResponseAPartirDeProdutoModel() {
        // Arrange
        ProdutoModel model = new ProdutoModel();
        model.setCdProduto("123e4567-e89b-12d3-a456-426614174000");
        model.setNmProduto("X-Burger");
        model.setDsDescricao("Hambúrguer com queijo");
        model.setVlPreco(25.90);
        model.setAtivo(true);
        model.setTpCategoria(ProdutoEnum.LANCHE);

        // Act
        ProdutoResponse response = new ProdutoResponse(model);

        // Assert
        assertNotNull(response);
        assertEquals("123e4567-e89b-12d3-a456-426614174000", response.cdProduto());
        assertEquals("X-Burger", response.nmProduto());
        assertEquals("Hambúrguer com queijo", response.dsDescricao());
        assertEquals(25.90, response.vlPreco());
        assertTrue(response.snAtivo());
        assertEquals("LANCHE", response.tpCategoria());
    }

    @Test
    @DisplayName("Deve converter categoria BEBIDA corretamente")
    void deveConverterCategoriaBebidaCorretamente() {
        // Arrange
        ProdutoModel model = new ProdutoModel();
        model.setCdProduto("456");
        model.setNmProduto("Coca-Cola");
        model.setDsDescricao("Refrigerante 350ml");
        model.setVlPreco(5.50);
        model.setAtivo(true);
        model.setTpCategoria(ProdutoEnum.BEBIDA);

        // Act
        ProdutoResponse response = new ProdutoResponse(model);

        // Assert
        assertEquals("BEBIDA", response.tpCategoria());
        assertEquals("Coca-Cola", response.nmProduto());
    }

    @Test
    @DisplayName("Deve converter categoria ACOMPANHAMENTO corretamente")
    void deveConverterCategoriaAcompanhamentoCorretamente() {
        // Arrange
        ProdutoModel model = new ProdutoModel();
        model.setCdProduto("789");
        model.setNmProduto("Batata Frita");
        model.setDsDescricao("Batata frita crocante");
        model.setVlPreco(8.90);
        model.setAtivo(true);
        model.setTpCategoria(ProdutoEnum.ACOMPANHAMENTO);

        // Act
        ProdutoResponse response = new ProdutoResponse(model);

        // Assert
        assertEquals("ACOMPANHAMENTO", response.tpCategoria());
        assertEquals("Batata Frita", response.nmProduto());
    }

    @Test
    @DisplayName("Deve converter categoria SOBREMESA corretamente")
    void deveConverterCategoriaSobremesaCorretamente() {
        // Arrange
        ProdutoModel model = new ProdutoModel();
        model.setCdProduto("101");
        model.setNmProduto("Sorvete");
        model.setDsDescricao("Sorvete de chocolate");
        model.setVlPreco(7.50);
        model.setAtivo(true);
        model.setTpCategoria(ProdutoEnum.SOBREMESA);

        // Act
        ProdutoResponse response = new ProdutoResponse(model);

        // Assert
        assertEquals("SOBREMESA", response.tpCategoria());
        assertEquals("Sorvete", response.nmProduto());
    }

    @Test
    @DisplayName("Deve criar response para produto inativo")
    void deveCriarResponseParaProdutoInativo() {
        // Arrange
        ProdutoModel model = new ProdutoModel();
        model.setCdProduto("202");
        model.setNmProduto("Produto Descontinuado");
        model.setDsDescricao("Produto não mais disponível");
        model.setVlPreco(10.00);
        model.setAtivo(false);
        model.setTpCategoria(ProdutoEnum.LANCHE);

        // Act
        ProdutoResponse response = new ProdutoResponse(model);

        // Assert
        assertFalse(response.snAtivo());
        assertEquals("Produto Descontinuado", response.nmProduto());
    }

    @Test
    @DisplayName("Deve criar response com preço zero")
    void deveCriarResponseComPrecoZero() {
        // Arrange
        ProdutoModel model = new ProdutoModel();
        model.setCdProduto("303");
        model.setNmProduto("Produto Grátis");
        model.setDsDescricao("Promoção");
        model.setVlPreco(0.0);
        model.setAtivo(true);
        model.setTpCategoria(ProdutoEnum.LANCHE);

        // Act
        ProdutoResponse response = new ProdutoResponse(model);

        // Assert
        assertEquals(0.0, response.vlPreco());
    }

    @Test
    @DisplayName("Deve criar response com preço alto")
    void deveCriarResponseComPrecoAlto() {
        // Arrange
        ProdutoModel model = new ProdutoModel();
        model.setCdProduto("404");
        model.setNmProduto("Combo Premium");
        model.setDsDescricao("Combo completo premium");
        model.setVlPreco(99.90);
        model.setAtivo(true);
        model.setTpCategoria(ProdutoEnum.LANCHE);

        // Act
        ProdutoResponse response = new ProdutoResponse(model);

        // Assert
        assertEquals(99.90, response.vlPreco());
    }

    @Test
    @DisplayName("Deve garantir imutabilidade do record")
    void deveGarantirImutabilidadeDoRecord() {
        // Arrange
        ProdutoResponse response1 = new ProdutoResponse(
                "123",
                "X-Burger",
                "Hambúrguer",
                25.90,
                true,
                "LANCHE"
        );

        ProdutoResponse response2 = new ProdutoResponse(
                "123",
                "X-Burger",
                "Hambúrguer",
                25.90,
                true,
                "LANCHE"
        );

        // Assert
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertEquals(response1.toString(), response2.toString());
    }

    @Test
    @DisplayName("Deve comparar corretamente dois ProdutoResponse diferentes")
    void deveCompararCorretamenteDoisProdutoResponseDiferentes() {
        // Arrange
        ProdutoResponse response1 = new ProdutoResponse(
                "123",
                "X-Burger",
                "Hambúrguer",
                25.90,
                true,
                "LANCHE"
        );

        ProdutoResponse response2 = new ProdutoResponse(
                "456",
                "Coca-Cola",
                "Refrigerante",
                5.50,
                true,
                "BEBIDA"
        );

        // Assert
        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    @DisplayName("Deve converter todas as categorias de enum para String")
    void deveConverterTodasAsCategoriasDeEnumParaString() {
        // Test LANCHE
        ProdutoModel lanche = new ProdutoModel();
        lanche.setTpCategoria(ProdutoEnum.LANCHE);
        lanche.setCdProduto("1");
        lanche.setNmProduto("Lanche");
        lanche.setDsDescricao("Desc");
        lanche.setVlPreco(10.0);
        lanche.setAtivo(true);
        assertEquals("LANCHE", new ProdutoResponse(lanche).tpCategoria());

        // Test ACOMPANHAMENTO
        ProdutoModel acomp = new ProdutoModel();
        acomp.setTpCategoria(ProdutoEnum.ACOMPANHAMENTO);
        acomp.setCdProduto("2");
        acomp.setNmProduto("Acomp");
        acomp.setDsDescricao("Desc");
        acomp.setVlPreco(10.0);
        acomp.setAtivo(true);
        assertEquals("ACOMPANHAMENTO", new ProdutoResponse(acomp).tpCategoria());

        // Test BEBIDA
        ProdutoModel bebida = new ProdutoModel();
        bebida.setTpCategoria(ProdutoEnum.BEBIDA);
        bebida.setCdProduto("3");
        bebida.setNmProduto("Bebida");
        bebida.setDsDescricao("Desc");
        bebida.setVlPreco(10.0);
        bebida.setAtivo(true);
        assertEquals("BEBIDA", new ProdutoResponse(bebida).tpCategoria());

        // Test SOBREMESA
        ProdutoModel sobremesa = new ProdutoModel();
        sobremesa.setTpCategoria(ProdutoEnum.SOBREMESA);
        sobremesa.setCdProduto("4");
        sobremesa.setNmProduto("Sobremesa");
        sobremesa.setDsDescricao("Desc");
        sobremesa.setVlPreco(10.0);
        sobremesa.setAtivo(true);
        assertEquals("SOBREMESA", new ProdutoResponse(sobremesa).tpCategoria());
    }

    @Test
    @DisplayName("Deve preservar todos os campos ao converter de Model para Response")
    void devePreservarTodosCamposAoConverterDeModelParaResponse() {
        // Arrange
        ProdutoModel model = new ProdutoModel(
                "999",
                "Produto Completo",
                "Descrição Completa",
                45.50,
                true,
                ProdutoEnum.LANCHE
        );

        // Act
        ProdutoResponse response = new ProdutoResponse(model);

        // Assert
        assertEquals(model.getCdProduto(), response.cdProduto());
        assertEquals(model.getNmProduto(), response.nmProduto());
        assertEquals(model.getDsDescricao(), response.dsDescricao());
        assertEquals(model.getVlPreco(), response.vlPreco());
        assertEquals(model.getAtivo(), response.snAtivo());
        assertEquals(model.getTpCategoria().name(), response.tpCategoria());
    }

    @Test
    @DisplayName("Deve criar response com valores de borda")
    void deveCriarResponseComValoresDeBorda() {
        // Arrange - Valores extremos
        ProdutoResponse response = new ProdutoResponse(
                "",
                "",
                "",
                Double.MAX_VALUE,
                false,
                "LANCHE"
        );

        // Assert
        assertEquals("", response.cdProduto());
        assertEquals("", response.nmProduto());
        assertEquals("", response.dsDescricao());
        assertEquals(Double.MAX_VALUE, response.vlPreco());
        assertFalse(response.snAtivo());
    }
}
