package br.com.fiap.postechfasfood.adapters.mappers;

import br.com.fiap.postechfasfood.adapters.web.requests.ProdutoRequest;
import br.com.fiap.postechfasfood.adapters.web.responses.ProdutoResponse;
import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.infra.db.entities.ProdutoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoMapperTest {

    private ProdutoMapper produtoMapper;

    @BeforeEach
    void setUp() {
        produtoMapper = new ProdutoMapper();
    }

    @Test
    @DisplayName("Deve converter ProdutoRequest para ProdutoModel corretamente")
    void deveConverterProdutoRequestParaProdutoModel() {
        // Arrange
        ProdutoRequest request = new ProdutoRequest(
                "X-Burger",
                "Hambúrguer com queijo e bacon",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        // Act
        ProdutoModel resultado = produtoMapper.toModel(request);

        // Assert
        assertNotNull(resultado);
        assertEquals("X-Burger", resultado.getNmProduto());
        assertEquals("Hambúrguer com queijo e bacon", resultado.getDsDescricao());
        assertEquals(25.90, resultado.getVlPreco());
        assertTrue(resultado.getAtivo());
        assertEquals(ProdutoEnum.LANCHE, resultado.getTpCategoria());
        assertNull(resultado.getCdProduto());
    }

    @Test
    @DisplayName("Deve converter ProdutoEntity para ProdutoModel corretamente")
    void deveConverterProdutoEntityParaProdutoModel() {
        // Arrange
        ProdutoEntity entity = new ProdutoEntity();
        entity.setCdProduto("123");
        entity.setNmProduto("Coca-Cola");
        entity.setDsDescricao("Refrigerante 350ml");
        entity.setVlPreco(5.50);
        entity.setSnAtivo(true);
        entity.setTpCategoria(ProdutoEnum.BEBIDA);

        // Act
        ProdutoModel resultado = produtoMapper.toModel(entity);

        // Assert
        assertNotNull(resultado);
        assertEquals("123", resultado.getCdProduto());
        assertEquals("Coca-Cola", resultado.getNmProduto());
        assertEquals("Refrigerante 350ml", resultado.getDsDescricao());
        assertEquals(5.50, resultado.getVlPreco());
        assertTrue(resultado.getAtivo());
        assertEquals(ProdutoEnum.BEBIDA, resultado.getTpCategoria());
    }

    @Test
    @DisplayName("Deve converter ProdutoModel para ProdutoEntity sem código")
    void deveConverterProdutoModelParaProdutoEntitySemCodigo() {
        // Arrange
        ProdutoModel model = new ProdutoModel();
        model.setNmProduto("Batata Frita");
        model.setDsDescricao("Batata frita crocante");
        model.setVlPreco(8.90);
        model.setAtivo(true);
        model.setTpCategoria(ProdutoEnum.ACOMPANHAMENTO);

        // Act
        ProdutoEntity resultado = produtoMapper.toEntity(model);

        // Assert
        assertNotNull(resultado);
        assertNull(resultado.getCdProduto());
        assertEquals("Batata Frita", resultado.getNmProduto());
        assertEquals("Batata frita crocante", resultado.getDsDescricao());
        assertEquals(8.90, resultado.getVlPreco());
        assertTrue(resultado.isSnAtivo());
        assertEquals(ProdutoEnum.ACOMPANHAMENTO, resultado.getTpCategoria());
    }

    @Test
    @DisplayName("Deve converter ProdutoModel para ProdutoEntity com código")
    void deveConverterProdutoModelParaProdutoEntityComCodigo() {
        // Arrange
        ProdutoModel model = new ProdutoModel();
        model.setNmProduto("Sorvete");
        model.setDsDescricao("Sorvete de chocolate");
        model.setVlPreco(7.50);
        model.setAtivo(false);
        model.setTpCategoria(ProdutoEnum.SOBREMESA);
        String cdProduto = "456";

        // Act
        ProdutoEntity resultado = produtoMapper.toEntityWithCdProduto(model, cdProduto);

        // Assert
        assertNotNull(resultado);
        assertEquals("456", resultado.getCdProduto());
        assertEquals("Sorvete", resultado.getNmProduto());
        assertEquals("Sorvete de chocolate", resultado.getDsDescricao());
        assertEquals(7.50, resultado.getVlPreco());
        assertFalse(resultado.isSnAtivo());
        assertEquals(ProdutoEnum.SOBREMESA, resultado.getTpCategoria());
    }

    @Test
    @DisplayName("Deve converter lista de ProdutoModel para lista de ProdutoResponse")
    void deveConverterListaProdutoModelParaListaProdutoResponse() {
        // Arrange
        ProdutoModel model1 = new ProdutoModel(
                "1",
                "X-Burger",
                "Hambúrguer com queijo",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        ProdutoModel model2 = new ProdutoModel(
                "2",
                "Coca-Cola",
                "Refrigerante 350ml",
                5.50,
                true,
                ProdutoEnum.BEBIDA
        );

        List<ProdutoModel> models = Arrays.asList(model1, model2);

        // Act
        List<ProdutoResponse> resultado = produtoMapper.toResoponse(models);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        
        ProdutoResponse response1 = resultado.get(0);
        assertEquals("1", response1.cdProduto());
        assertEquals("X-Burger", response1.nmProduto());
        assertEquals("Hambúrguer com queijo", response1.dsDescricao());
        assertEquals(25.90, response1.vlPreco());
        assertTrue(response1.snAtivo());
        assertEquals("LANCHE", response1.tpCategoria());

        ProdutoResponse response2 = resultado.get(1);
        assertEquals("2", response2.cdProduto());
        assertEquals("Coca-Cola", response2.nmProduto());
        assertEquals("Refrigerante 350ml", response2.dsDescricao());
        assertEquals(5.50, response2.vlPreco());
        assertTrue(response2.snAtivo());
        assertEquals("BEBIDA", response2.tpCategoria());
    }

    @Test
    @DisplayName("Deve converter lista vazia de ProdutoModel para lista vazia de ProdutoResponse")
    void deveConverterListaVaziaProdutoModelParaListaVaziaProdutoResponse() {
        // Arrange
        List<ProdutoModel> models = Arrays.asList();

        // Act
        List<ProdutoResponse> resultado = produtoMapper.toResoponse(models);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve manter valores null ao converter ProdutoRequest com valores null")
    void deveManterValoresNullAoConverterProdutoRequest() {
        // Arrange
        ProdutoRequest request = new ProdutoRequest(
                null,
                null,
                0.0,
                false,
                null
        );

        // Act
        ProdutoModel resultado = produtoMapper.toModel(request);

        // Assert
        assertNotNull(resultado);
        assertNull(resultado.getNmProduto());
        assertNull(resultado.getDsDescricao());
        assertEquals(0.0, resultado.getVlPreco());
        assertFalse(resultado.getAtivo());
        assertNull(resultado.getTpCategoria());
    }

    @Test
    @DisplayName("Deve converter produto inativo de Entity para Model")
    void deveConverterProdutoInativoDeEntityParaModel() {
        // Arrange
        ProdutoEntity entity = new ProdutoEntity();
        entity.setCdProduto("789");
        entity.setNmProduto("Produto Descontinuado");
        entity.setDsDescricao("Produto não mais disponível");
        entity.setVlPreco(10.00);
        entity.setSnAtivo(false);
        entity.setTpCategoria(ProdutoEnum.LANCHE);

        // Act
        ProdutoModel resultado = produtoMapper.toModel(entity);

        // Assert
        assertNotNull(resultado);
        assertEquals("789", resultado.getCdProduto());
        assertFalse(resultado.getAtivo());
    }

    @Test
    @DisplayName("Deve converter todos os tipos de categoria de produto")
    void deveConverterTodosOsTiposDeCategoriasDeProduto() {
        // Test LANCHE
        ProdutoRequest requestLanche = new ProdutoRequest("Lanche", "Desc", 10.0, true, ProdutoEnum.LANCHE);
        assertEquals(ProdutoEnum.LANCHE, produtoMapper.toModel(requestLanche).getTpCategoria());

        // Test ACOMPANHAMENTO
        ProdutoRequest requestAcomp = new ProdutoRequest("Acomp", "Desc", 10.0, true, ProdutoEnum.ACOMPANHAMENTO);
        assertEquals(ProdutoEnum.ACOMPANHAMENTO, produtoMapper.toModel(requestAcomp).getTpCategoria());

        // Test BEBIDA
        ProdutoRequest requestBebida = new ProdutoRequest("Bebida", "Desc", 10.0, true, ProdutoEnum.BEBIDA);
        assertEquals(ProdutoEnum.BEBIDA, produtoMapper.toModel(requestBebida).getTpCategoria());

        // Test SOBREMESA
        ProdutoRequest requestSobremesa = new ProdutoRequest("Sobremesa", "Desc", 10.0, true, ProdutoEnum.SOBREMESA);
        assertEquals(ProdutoEnum.SOBREMESA, produtoMapper.toModel(requestSobremesa).getTpCategoria());
    }
}
