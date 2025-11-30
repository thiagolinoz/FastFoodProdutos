package br.com.fiap.postechfasfood.infra.entities;

import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.infra.db.entities.ProdutoEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProdutoEntity - Testes da Entidade JPA")
class ProdutoEntityTest {

    private ProdutoEntity produtoEntity;

    @BeforeEach
    void setUp() {
        produtoEntity = new ProdutoEntity();
    }

    @Test
    @DisplayName("Deve criar ProdutoEntity com construtor padrão")
    void deveCriarProdutoEntityComConstrutorPadrao() {
        ProdutoEntity entity = new ProdutoEntity();

        assertNotNull(entity);
        assertNull(entity.getCdProduto());
        assertNull(entity.getNmProduto());
        assertNull(entity.getDsDescricao());
        assertEquals(0.0, entity.getVlPreco());
        assertFalse(entity.isSnAtivo());
        assertNull(entity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve criar ProdutoEntity com construtor completo")
    void deveCriarProdutoEntityComConstrutorCompleto() {
        ProdutoEntity entity = new ProdutoEntity(
                "123e4567-e89b-12d3-a456-426614174000",
                "X-Burger Especial",
                "Hambúrguer artesanal com queijo",
                28.90,
                true,
                ProdutoEnum.LANCHE
        );

        assertNotNull(entity);
        assertEquals("123e4567-e89b-12d3-a456-426614174000", entity.getCdProduto());
        assertEquals("X-Burger Especial", entity.getNmProduto());
        assertEquals("Hambúrguer artesanal com queijo", entity.getDsDescricao());
        assertEquals(28.90, entity.getVlPreco());
        assertTrue(entity.isSnAtivo());
        assertEquals(ProdutoEnum.LANCHE, entity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve setar e obter cdProduto")
    void deveSetarEObterCdProduto() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        produtoEntity.setCdProduto(cdProduto);

        assertEquals(cdProduto, produtoEntity.getCdProduto());
    }

    @Test
    @DisplayName("Deve setar e obter nmProduto")
    void deveSetarEObterNmProduto() {
        String nmProduto = "X-Burger Especial";
        produtoEntity.setNmProduto(nmProduto);

        assertEquals(nmProduto, produtoEntity.getNmProduto());
    }

    @Test
    @DisplayName("Deve setar e obter dsDescricao")
    void deveSetarEObterDsDescricao() {
        String dsDescricao = "Hambúrguer artesanal com queijo";
        produtoEntity.setDsDescricao(dsDescricao);

        assertEquals(dsDescricao, produtoEntity.getDsDescricao());
    }

    @Test
    @DisplayName("Deve setar e obter vlPreco")
    void deveSetarEObterVlPreco() {
        double vlPreco = 28.90;
        produtoEntity.setVlPreco(vlPreco);

        assertEquals(vlPreco, produtoEntity.getVlPreco());
    }

    @Test
    @DisplayName("Deve setar e obter snAtivo como true")
    void deveSetarEObterSnAtivoComoTrue() {
        produtoEntity.setSnAtivo(true);

        assertTrue(produtoEntity.isSnAtivo());
    }

    @Test
    @DisplayName("Deve setar e obter snAtivo como false")
    void deveSetarEObterSnAtivoComoFalse() {
        produtoEntity.setSnAtivo(false);

        assertFalse(produtoEntity.isSnAtivo());
    }

    @Test
    @DisplayName("Deve setar e obter tpCategoria LANCHE")
    void deveSetarEObterTpCategoriaLanche() {
        produtoEntity.setTpCategoria(ProdutoEnum.LANCHE);

        assertEquals(ProdutoEnum.LANCHE, produtoEntity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve setar e obter tpCategoria BEBIDA")
    void deveSetarEObterTpCategoriaBebida() {
        produtoEntity.setTpCategoria(ProdutoEnum.BEBIDA);

        assertEquals(ProdutoEnum.BEBIDA, produtoEntity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve setar e obter tpCategoria ACOMPANHAMENTO")
    void deveSetarEObterTpCategoriaAcompanhamento() {
        produtoEntity.setTpCategoria(ProdutoEnum.ACOMPANHAMENTO);

        assertEquals(ProdutoEnum.ACOMPANHAMENTO, produtoEntity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve setar e obter tpCategoria SOBREMESA")
    void deveSetarEObterTpCategoriaSobremesa() {
        produtoEntity.setTpCategoria(ProdutoEnum.SOBREMESA);

        assertEquals(ProdutoEnum.SOBREMESA, produtoEntity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve criar toString com todos os campos")
    void deveCriarToStringComTodosCampos() {
        produtoEntity.setCdProduto("123");
        produtoEntity.setNmProduto("X-Burger");
        produtoEntity.setDsDescricao("Lanche");
        produtoEntity.setVlPreco(25.90);
        produtoEntity.setSnAtivo(true);
        produtoEntity.setTpCategoria(ProdutoEnum.LANCHE);

        String toString = produtoEntity.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("cdProduto=123"));
        assertTrue(toString.contains("nmProduto='X-Burger'"));
        assertTrue(toString.contains("dsDescricao='Lanche'"));
        assertTrue(toString.contains("vlPreco=25.9"));
        assertTrue(toString.contains("snAtivo=true"));
        assertTrue(toString.contains("tpCategoria=LANCHE"));
    }

    @Test
    @DisplayName("Deve aceitar valores null em campos opcionais")
    void deveAceitarValoresNullEmCamposOpcionais() {
        produtoEntity.setCdProduto(null);
        produtoEntity.setNmProduto(null);
        produtoEntity.setDsDescricao(null);
        produtoEntity.setTpCategoria(null);

        assertNull(produtoEntity.getCdProduto());
        assertNull(produtoEntity.getNmProduto());
        assertNull(produtoEntity.getDsDescricao());
        assertNull(produtoEntity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve aceitar valores extremos para vlPreco")
    void deveAceitarValoresExtremosParaVlPreco() {
        produtoEntity.setVlPreco(0.01);
        assertEquals(0.01, produtoEntity.getVlPreco(), 0.001);

        produtoEntity.setVlPreco(9999.99);
        assertEquals(9999.99, produtoEntity.getVlPreco(), 0.001);

        produtoEntity.setVlPreco(0.0);
        assertEquals(0.0, produtoEntity.getVlPreco(), 0.001);
    }

    @Test
    @DisplayName("Deve aceitar strings vazias em campos de texto")
    void deveAceitarStringsVaziasEmCamposDeTexto() {
        produtoEntity.setCdProduto("");
        produtoEntity.setNmProduto("");
        produtoEntity.setDsDescricao("");

        assertEquals("", produtoEntity.getCdProduto());
        assertEquals("", produtoEntity.getNmProduto());
        assertEquals("", produtoEntity.getDsDescricao());
    }

    @Test
    @DisplayName("Deve aceitar strings longas em campos de texto")
    void deveAceitarStringsLongasEmCamposDeTexto() {
        String textoLongo = "A".repeat(500);
        
        produtoEntity.setNmProduto(textoLongo);
        produtoEntity.setDsDescricao(textoLongo);

        assertEquals(textoLongo, produtoEntity.getNmProduto());
        assertEquals(textoLongo, produtoEntity.getDsDescricao());
    }

    @Test
    @DisplayName("Deve manter independência entre instâncias")
    void deveManterIndependenciaEntreInstancias() {
        ProdutoEntity entity1 = new ProdutoEntity();
        ProdutoEntity entity2 = new ProdutoEntity();

        entity1.setCdProduto("123");
        entity1.setNmProduto("Produto 1");
        entity1.setVlPreco(10.0);
        entity1.setSnAtivo(true);

        entity2.setCdProduto("456");
        entity2.setNmProduto("Produto 2");
        entity2.setVlPreco(20.0);
        entity2.setSnAtivo(false);

        assertNotEquals(entity1.getCdProduto(), entity2.getCdProduto());
        assertNotEquals(entity1.getNmProduto(), entity2.getNmProduto());
        assertNotEquals(entity1.getVlPreco(), entity2.getVlPreco());
        assertNotEquals(entity1.isSnAtivo(), entity2.isSnAtivo());
    }

    @Test
    @DisplayName("Deve permitir modificar valores após criação")
    void devePermitirModificarValoresAposCriacao() {
        produtoEntity.setCdProduto("123");
        produtoEntity.setNmProduto("Nome Original");
        produtoEntity.setVlPreco(10.0);
        produtoEntity.setSnAtivo(true);
        produtoEntity.setTpCategoria(ProdutoEnum.LANCHE);

        // Modificar valores
        produtoEntity.setCdProduto("456");
        produtoEntity.setNmProduto("Nome Modificado");
        produtoEntity.setVlPreco(20.0);
        produtoEntity.setSnAtivo(false);
        produtoEntity.setTpCategoria(ProdutoEnum.BEBIDA);

        assertEquals("456", produtoEntity.getCdProduto());
        assertEquals("Nome Modificado", produtoEntity.getNmProduto());
        assertEquals(20.0, produtoEntity.getVlPreco());
        assertFalse(produtoEntity.isSnAtivo());
        assertEquals(ProdutoEnum.BEBIDA, produtoEntity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve criar entidade completa para LANCHE")
    void deveCriarEntidadeCompletaParaLanche() {
        ProdutoEntity entity = new ProdutoEntity(
                "1",
                "X-Burger",
                "Hambúrguer tradicional",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        assertEquals("1", entity.getCdProduto());
        assertEquals("X-Burger", entity.getNmProduto());
        assertEquals("Hambúrguer tradicional", entity.getDsDescricao());
        assertEquals(25.90, entity.getVlPreco());
        assertTrue(entity.isSnAtivo());
        assertEquals(ProdutoEnum.LANCHE, entity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve criar entidade completa para BEBIDA")
    void deveCriarEntidadeCompletaParaBebida() {
        ProdutoEntity entity = new ProdutoEntity(
                "2",
                "Coca-Cola",
                "Refrigerante 350ml",
                5.50,
                true,
                ProdutoEnum.BEBIDA
        );

        assertEquals("2", entity.getCdProduto());
        assertEquals("Coca-Cola", entity.getNmProduto());
        assertEquals("Refrigerante 350ml", entity.getDsDescricao());
        assertEquals(5.50, entity.getVlPreco());
        assertTrue(entity.isSnAtivo());
        assertEquals(ProdutoEnum.BEBIDA, entity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve criar entidade completa para ACOMPANHAMENTO")
    void deveCriarEntidadeCompletaParaAcompanhamento() {
        ProdutoEntity entity = new ProdutoEntity(
                "3",
                "Batata Frita",
                "Porção grande",
                12.90,
                true,
                ProdutoEnum.ACOMPANHAMENTO
        );

        assertEquals("3", entity.getCdProduto());
        assertEquals("Batata Frita", entity.getNmProduto());
        assertEquals("Porção grande", entity.getDsDescricao());
        assertEquals(12.90, entity.getVlPreco());
        assertTrue(entity.isSnAtivo());
        assertEquals(ProdutoEnum.ACOMPANHAMENTO, entity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve criar entidade completa para SOBREMESA")
    void deveCriarEntidadeCompletaParaSobremesa() {
        ProdutoEntity entity = new ProdutoEntity(
                "4",
                "Sorvete",
                "Sorvete de chocolate",
                8.90,
                true,
                ProdutoEnum.SOBREMESA
        );

        assertEquals("4", entity.getCdProduto());
        assertEquals("Sorvete", entity.getNmProduto());
        assertEquals("Sorvete de chocolate", entity.getDsDescricao());
        assertEquals(8.90, entity.getVlPreco());
        assertTrue(entity.isSnAtivo());
        assertEquals(ProdutoEnum.SOBREMESA, entity.getTpCategoria());
    }

    @Test
    @DisplayName("Deve criar entidade com produto inativo")
    void deveCriarEntidadeComProdutoInativo() {
        ProdutoEntity entity = new ProdutoEntity(
                "5",
                "Produto Descontinuado",
                "Produto fora de linha",
                10.0,
                false,
                ProdutoEnum.LANCHE
        );

        assertFalse(entity.isSnAtivo());
    }

    @Test
    @DisplayName("Deve aceitar valores decimais precisos para preço")
    void deveAceitarValoresDecimaisPrecisosParaPreco() {
        produtoEntity.setVlPreco(25.99);
        assertEquals(25.99, produtoEntity.getVlPreco(), 0.001);

        produtoEntity.setVlPreco(10.50);
        assertEquals(10.50, produtoEntity.getVlPreco(), 0.001);

        produtoEntity.setVlPreco(0.99);
        assertEquals(0.99, produtoEntity.getVlPreco(), 0.001);
    }

    @Test
    @DisplayName("Deve verificar comportamento de isSnAtivo")
    void deveVerificarComportamentoDeIsSnAtivo() {
        // Padrão é false
        assertFalse(produtoEntity.isSnAtivo());

        // Setar como true
        produtoEntity.setSnAtivo(true);
        assertTrue(produtoEntity.isSnAtivo());

        // Voltar para false
        produtoEntity.setSnAtivo(false);
        assertFalse(produtoEntity.isSnAtivo());
    }

    @Test
    @DisplayName("Deve criar toString formatado corretamente")
    void deveCriarToStringFormatadoCorretamente() {
        produtoEntity.setCdProduto("abc-123");
        produtoEntity.setNmProduto("Teste");
        produtoEntity.setDsDescricao("Descrição Teste");
        produtoEntity.setVlPreco(15.50);
        produtoEntity.setSnAtivo(true);
        produtoEntity.setTpCategoria(ProdutoEnum.BEBIDA);

        String resultado = produtoEntity.toString();

        assertTrue(resultado.startsWith("ProdutoEntity{"));
        assertTrue(resultado.endsWith("}"));
        assertTrue(resultado.contains("cdProduto=abc-123"));
        assertTrue(resultado.contains("nmProduto='Teste'"));
        assertTrue(resultado.contains("dsDescricao='Descrição Teste'"));
        assertTrue(resultado.contains("vlPreco=15.5"));
        assertTrue(resultado.contains("snAtivo=true"));
        assertTrue(resultado.contains("tpCategoria=BEBIDA"));
    }

    @Test
    @DisplayName("Deve aceitar caracteres especiais em campos de texto")
    void deveAceitarCaracteresEspeciaisEmCamposDeTexto() {
        String textoComEspeciais = "X-Burger Ñ@#$%&*()[]{}";
        produtoEntity.setNmProduto(textoComEspeciais);
        produtoEntity.setDsDescricao("Descrição com àçéñtõs");

        assertEquals(textoComEspeciais, produtoEntity.getNmProduto());
        assertEquals("Descrição com àçéñtõs", produtoEntity.getDsDescricao());
    }

    @Test
    @DisplayName("Deve manter valores após múltiplas modificações")
    void deveManterValoresAposMultiplasModificacoes() {
        // Primeira modificação
        produtoEntity.setCdProduto("1");
        produtoEntity.setVlPreco(10.0);
        
        // Segunda modificação
        produtoEntity.setNmProduto("Nome");
        produtoEntity.setSnAtivo(true);
        
        // Terceira modificação
        produtoEntity.setDsDescricao("Descrição");
        produtoEntity.setTpCategoria(ProdutoEnum.LANCHE);

        // Verificar todos os valores
        assertEquals("1", produtoEntity.getCdProduto());
        assertEquals("Nome", produtoEntity.getNmProduto());
        assertEquals("Descrição", produtoEntity.getDsDescricao());
        assertEquals(10.0, produtoEntity.getVlPreco());
        assertTrue(produtoEntity.isSnAtivo());
        assertEquals(ProdutoEnum.LANCHE, produtoEntity.getTpCategoria());
    }
}
