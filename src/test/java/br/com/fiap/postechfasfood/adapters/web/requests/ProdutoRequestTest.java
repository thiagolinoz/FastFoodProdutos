package br.com.fiap.postechfasfood.adapters.web.requests;

import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve criar um ProdutoRequest válido")
    void deveCriarProdutoRequestValido() {
        // Arrange & Act
        ProdutoRequest request = new ProdutoRequest(
                "X-Burger",
                "Hambúrguer com queijo e bacon",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        // Assert
        assertNotNull(request);
        assertEquals("X-Burger", request.nmProduto());
        assertEquals("Hambúrguer com queijo e bacon", request.dsDescricao());
        assertEquals(25.90, request.vlPreco());
        assertTrue(request.snAtivo());
        assertEquals(ProdutoEnum.LANCHE, request.tpCategoria());
    }

    @Test
    @DisplayName("Deve validar ProdutoRequest com todos os campos válidos")
    void deveValidarProdutoRequestComTodosCamposValidos() {
        // Arrange
        ProdutoRequest request = new ProdutoRequest(
                "X-Burger",
                "Hambúrguer delicioso",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        // Act
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Deve falhar validação quando nmProduto for null")
    void deveFalharValidacaoQuandoNmProdutoForNull() {
        // Arrange
        ProdutoRequest request = new ProdutoRequest(
                null,
                "Descrição",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        // Act
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nmProduto")));
    }

    @Test
    @DisplayName("Deve falhar validação quando dsDescricao for null")
    void deveFalharValidacaoQuandoDsDescricaoForNull() {
        // Arrange
        ProdutoRequest request = new ProdutoRequest(
                "X-Burger",
                null,
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        // Act
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("dsDescricao")));
    }

    @Test
    @DisplayName("Deve falhar validação quando tpCategoria for null")
    void deveFalharValidacaoQuandoTpCategoriaForNull() {
        // Arrange
        ProdutoRequest request = new ProdutoRequest(
                "X-Burger",
                "Descrição",
                25.90,
                true,
                null
        );

        // Act
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("tpCategoria")));
    }

    @Test
    @DisplayName("Deve criar produto com categoria BEBIDA")
    void deveCriarProdutoComCategoriaBebida() {
        // Arrange & Act
        ProdutoRequest request = new ProdutoRequest(
                "Coca-Cola",
                "Refrigerante 350ml",
                5.50,
                true,
                ProdutoEnum.BEBIDA
        );

        // Assert
        assertEquals(ProdutoEnum.BEBIDA, request.tpCategoria());
        assertEquals("Coca-Cola", request.nmProduto());
    }

    @Test
    @DisplayName("Deve criar produto com categoria ACOMPANHAMENTO")
    void deveCriarProdutoComCategoriaAcompanhamento() {
        // Arrange & Act
        ProdutoRequest request = new ProdutoRequest(
                "Batata Frita",
                "Batata frita crocante",
                8.90,
                true,
                ProdutoEnum.ACOMPANHAMENTO
        );

        // Assert
        assertEquals(ProdutoEnum.ACOMPANHAMENTO, request.tpCategoria());
        assertEquals("Batata Frita", request.nmProduto());
    }

    @Test
    @DisplayName("Deve criar produto com categoria SOBREMESA")
    void deveCriarProdutoComCategoriaSobremesa() {
        // Arrange & Act
        ProdutoRequest request = new ProdutoRequest(
                "Sorvete",
                "Sorvete de chocolate",
                7.50,
                true,
                ProdutoEnum.SOBREMESA
        );

        // Assert
        assertEquals(ProdutoEnum.SOBREMESA, request.tpCategoria());
        assertEquals("Sorvete", request.nmProduto());
    }

    @Test
    @DisplayName("Deve criar produto inativo")
    void deveCriarProdutoInativo() {
        // Arrange & Act
        ProdutoRequest request = new ProdutoRequest(
                "Produto Descontinuado",
                "Produto não mais disponível",
                10.00,
                false,
                ProdutoEnum.LANCHE
        );

        // Assert
        assertFalse(request.snAtivo());
    }

    @Test
    @DisplayName("Deve criar produto com preço zero")
    void deveCriarProdutoComPrecoZero() {
        // Arrange & Act
        ProdutoRequest request = new ProdutoRequest(
                "Produto Grátis",
                "Promoção",
                0.0,
                true,
                ProdutoEnum.LANCHE
        );

        // Assert
        assertEquals(0.0, request.vlPreco());
    }

    @Test
    @DisplayName("Deve criar produto com preço alto")
    void deveCriarProdutoComPrecoAlto() {
        // Arrange & Act
        ProdutoRequest request = new ProdutoRequest(
                "Combo Premium",
                "Combo completo premium",
                99.90,
                true,
                ProdutoEnum.LANCHE
        );

        // Assert
        assertEquals(99.90, request.vlPreco());
    }

    @Test
    @DisplayName("Deve garantir imutabilidade do record")
    void deveGarantirImutabilidadeDoRecord() {
        // Arrange
        ProdutoRequest request1 = new ProdutoRequest(
                "X-Burger",
                "Hambúrguer",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        ProdutoRequest request2 = new ProdutoRequest(
                "X-Burger",
                "Hambúrguer",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        // Assert
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertEquals(request1.toString(), request2.toString());
    }

    @Test
    @DisplayName("Deve comparar corretamente dois ProdutoRequest diferentes")
    void deveCompararCorretamenteDoisProdutoRequestDiferentes() {
        // Arrange
        ProdutoRequest request1 = new ProdutoRequest(
                "X-Burger",
                "Hambúrguer",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        ProdutoRequest request2 = new ProdutoRequest(
                "Coca-Cola",
                "Refrigerante",
                5.50,
                true,
                ProdutoEnum.BEBIDA
        );

        // Assert
        assertNotEquals(request1, request2);
        assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Deve criar requests para todas as categorias de produto")
    void deveCriarRequestsParaTodasAsCategoriasDeProduto() {
        // Test LANCHE
        ProdutoRequest lanche = new ProdutoRequest("Lanche", "Desc", 10.0, true, ProdutoEnum.LANCHE);
        assertEquals(ProdutoEnum.LANCHE, lanche.tpCategoria());

        // Test ACOMPANHAMENTO
        ProdutoRequest acomp = new ProdutoRequest("Acomp", "Desc", 10.0, true, ProdutoEnum.ACOMPANHAMENTO);
        assertEquals(ProdutoEnum.ACOMPANHAMENTO, acomp.tpCategoria());

        // Test BEBIDA
        ProdutoRequest bebida = new ProdutoRequest("Bebida", "Desc", 10.0, true, ProdutoEnum.BEBIDA);
        assertEquals(ProdutoEnum.BEBIDA, bebida.tpCategoria());

        // Test SOBREMESA
        ProdutoRequest sobremesa = new ProdutoRequest("Sobremesa", "Desc", 10.0, true, ProdutoEnum.SOBREMESA);
        assertEquals(ProdutoEnum.SOBREMESA, sobremesa.tpCategoria());
    }
}
