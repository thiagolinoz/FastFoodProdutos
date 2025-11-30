package br.com.fiap.postechfasfood.domain.models.enuns;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProdutoEnum - Testes do Enum de Categorias de Produto")
class ProdutoEnumTest {

    @Test
    @DisplayName("Deve verificar que o enum possui 4 valores")
    void deveVerificarQuantidadeDeValores() {
        ProdutoEnum[] values = ProdutoEnum.values();
        assertEquals(4, values.length);
    }

    @Test
    @DisplayName("Deve verificar que LANCHE existe no enum")
    void deveVerificarExistenciaLanche() {
        ProdutoEnum lanche = ProdutoEnum.valueOf("LANCHE");
        assertNotNull(lanche);
        assertEquals(ProdutoEnum.LANCHE, lanche);
    }

    @Test
    @DisplayName("Deve verificar que ACOMPANHAMENTO existe no enum")
    void deveVerificarExistenciaAcompanhamento() {
        ProdutoEnum acompanhamento = ProdutoEnum.valueOf("ACOMPANHAMENTO");
        assertNotNull(acompanhamento);
        assertEquals(ProdutoEnum.ACOMPANHAMENTO, acompanhamento);
    }

    @Test
    @DisplayName("Deve verificar que BEBIDA existe no enum")
    void deveVerificarExistenciaBebida() {
        ProdutoEnum bebida = ProdutoEnum.valueOf("BEBIDA");
        assertNotNull(bebida);
        assertEquals(ProdutoEnum.BEBIDA, bebida);
    }

    @Test
    @DisplayName("Deve verificar que SOBREMESA existe no enum")
    void deveVerificarExistenciaSobremesa() {
        ProdutoEnum sobremesa = ProdutoEnum.valueOf("SOBREMESA");
        assertNotNull(sobremesa);
        assertEquals(ProdutoEnum.SOBREMESA, sobremesa);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar valor inexistente")
    void deveLancarExcecaoParaValorInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProdutoEnum.valueOf("INVALIDO");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao passar null para valueOf")
    void deveLancarExcecaoParaNull() {
        assertThrows(NullPointerException.class, () -> {
            ProdutoEnum.valueOf(null);
        });
    }

    @Test
    @DisplayName("Deve verificar o nome do enum LANCHE")
    void deveVerificarNomeLanche() {
        assertEquals("LANCHE", ProdutoEnum.LANCHE.name());
    }

    @Test
    @DisplayName("Deve verificar o nome do enum ACOMPANHAMENTO")
    void deveVerificarNomeAcompanhamento() {
        assertEquals("ACOMPANHAMENTO", ProdutoEnum.ACOMPANHAMENTO.name());
    }

    @Test
    @DisplayName("Deve verificar o nome do enum BEBIDA")
    void deveVerificarNomeBebida() {
        assertEquals("BEBIDA", ProdutoEnum.BEBIDA.name());
    }

    @Test
    @DisplayName("Deve verificar o nome do enum SOBREMESA")
    void deveVerificarNomeSobremesa() {
        assertEquals("SOBREMESA", ProdutoEnum.SOBREMESA.name());
    }

    @Test
    @DisplayName("Deve verificar a ordem dos valores do enum")
    void deveVerificarOrdemDosValores() {
        ProdutoEnum[] values = ProdutoEnum.values();
        assertEquals(ProdutoEnum.LANCHE, values[0]);
        assertEquals(ProdutoEnum.ACOMPANHAMENTO, values[1]);
        assertEquals(ProdutoEnum.BEBIDA, values[2]);
        assertEquals(ProdutoEnum.SOBREMESA, values[3]);
    }

    @Test
    @DisplayName("Deve verificar ordinal de LANCHE")
    void deveVerificarOrdinalLanche() {
        assertEquals(0, ProdutoEnum.LANCHE.ordinal());
    }

    @Test
    @DisplayName("Deve verificar ordinal de ACOMPANHAMENTO")
    void deveVerificarOrdinalAcompanhamento() {
        assertEquals(1, ProdutoEnum.ACOMPANHAMENTO.ordinal());
    }

    @Test
    @DisplayName("Deve verificar ordinal de BEBIDA")
    void deveVerificarOrdinalBebida() {
        assertEquals(2, ProdutoEnum.BEBIDA.ordinal());
    }

    @Test
    @DisplayName("Deve verificar ordinal de SOBREMESA")
    void deveVerificarOrdinalSobremesa() {
        assertEquals(3, ProdutoEnum.SOBREMESA.ordinal());
    }

    @Test
    @DisplayName("Deve verificar igualdade entre enums")
    void deveVerificarIgualdadeEntreEnums() {
        ProdutoEnum lanche1 = ProdutoEnum.LANCHE;
        ProdutoEnum lanche2 = ProdutoEnum.valueOf("LANCHE");
        assertSame(lanche1, lanche2);
        assertEquals(lanche1, lanche2);
    }

    @Test
    @DisplayName("Deve verificar toString de cada valor")
    void deveVerificarToString() {
        assertEquals("LANCHE", ProdutoEnum.LANCHE.toString());
        assertEquals("ACOMPANHAMENTO", ProdutoEnum.ACOMPANHAMENTO.toString());
        assertEquals("BEBIDA", ProdutoEnum.BEBIDA.toString());
        assertEquals("SOBREMESA", ProdutoEnum.SOBREMESA.toString());
    }

    @Test
    @DisplayName("Deve verificar que valores diferentes não são iguais")
    void deveVerificarDesigualdade() {
        assertNotEquals(ProdutoEnum.LANCHE, ProdutoEnum.BEBIDA);
        assertNotEquals(ProdutoEnum.ACOMPANHAMENTO, ProdutoEnum.SOBREMESA);
    }

    @Test
    @DisplayName("Deve verificar compatibilidade com switch statement")
    void deveVerificarCompatibilidadeComSwitch() {
        String resultado = switch (ProdutoEnum.LANCHE) {
            case LANCHE -> "É um lanche";
            case ACOMPANHAMENTO -> "É um acompanhamento";
            case BEBIDA -> "É uma bebida";
            case SOBREMESA -> "É uma sobremesa";
        };
        assertEquals("É um lanche", resultado);
    }
}
