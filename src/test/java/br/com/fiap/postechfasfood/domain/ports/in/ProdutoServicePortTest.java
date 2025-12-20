package br.com.fiap.postechfasfood.domain.ports.in;

import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.domain.ports.out.ProdutoRepositoryPort;
import br.com.fiap.postechfasfood.domain.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProdutoService - Testes do Serviço de Produto")
class ProdutoServicePortTest {

    @Mock
    private ProdutoRepositoryPort produtoRepositoryPort;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoModel produtoModel;

    @BeforeEach
    void setUp() {
        produtoModel = new ProdutoModel(
                "1",
                "X-Burger",
                "Delicioso hambúrguer",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );
    }

    @Test
    @DisplayName("Deve cadastrar produto com sucesso")
    void deveCadastrarProdutoComSucesso() {
        when(produtoRepositoryPort.cadastrar(any(ProdutoModel.class))).thenReturn(produtoModel);

        ProdutoModel resultado = produtoService.cadastrar(produtoModel);

        assertNotNull(resultado);
        assertEquals("1", resultado.getCdProduto());
        assertEquals("X-Burger", resultado.getNmProduto());
        assertEquals("Delicioso hambúrguer", resultado.getDsDescricao());
        assertEquals(25.90, resultado.getVlPreco());
        assertTrue(resultado.getAtivo());
        assertEquals(ProdutoEnum.LANCHE, resultado.getTpCategoria());
        verify(produtoRepositoryPort, times(1)).cadastrar(produtoModel);
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void deveAtualizarProdutoComSucesso() {
        ProdutoModel produtoAtualizado = new ProdutoModel(
                "1",
                "X-Burger Premium",
                "Hambúrguer premium",
                29.90,
                true,
                ProdutoEnum.LANCHE
        );

        when(produtoRepositoryPort.atualizar(eq("1"), any(ProdutoModel.class))).thenReturn(produtoAtualizado);

        ProdutoModel resultado = produtoService.atualizarProduto("1", produtoAtualizado);

        assertNotNull(resultado);
        assertEquals("X-Burger Premium", resultado.getNmProduto());
        assertEquals(29.90, resultado.getVlPreco());
        verify(produtoRepositoryPort, times(1)).atualizar("1", produtoAtualizado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        when(produtoRepositoryPort.atualizar(eq("999"), any(ProdutoModel.class)))
                .thenThrow(new RuntimeException("Produto não encontrado"));

        assertThrows(RuntimeException.class, () -> {
            produtoService.atualizarProduto("999", produtoModel);
        });

        verify(produtoRepositoryPort, times(1)).atualizar("999", produtoModel);
    }

    @Test
    @DisplayName("Deve ativar produto com sucesso")
    void deveAtivarProdutoComSucesso() {
        produtoModel.setAtivo(false);
        ProdutoModel produtoAtivado = new ProdutoModel(
                "1",
                "X-Burger",
                "Delicioso hambúrguer",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        when(produtoRepositoryPort.ativar("1")).thenReturn(produtoAtivado);

        ProdutoModel resultado = produtoService.ativar("1");

        assertNotNull(resultado);
        assertTrue(resultado.getAtivo());
        verify(produtoRepositoryPort, times(1)).ativar("1");
    }

    @Test
    @DisplayName("Deve desativar produto com sucesso")
    void deveDesativarProdutoComSucesso() {
        ProdutoModel produtoDesativado = new ProdutoModel(
                "1",
                "X-Burger",
                "Delicioso hambúrguer",
                25.90,
                false,
                ProdutoEnum.LANCHE
        );

        when(produtoRepositoryPort.desativar("1")).thenReturn(produtoDesativado);

        ProdutoModel resultado = produtoService.desativar("1");

        assertNotNull(resultado);
        assertFalse(resultado.getAtivo());
        verify(produtoRepositoryPort, times(1)).desativar("1");
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosProdutos() {
        ProdutoModel produto2 = new ProdutoModel(
                "2",
                "Coca-Cola",
                "Refrigerante 350ml",
                5.50,
                true,
                ProdutoEnum.BEBIDA
        );

        List<ProdutoModel> produtos = Arrays.asList(produtoModel, produto2);
        when(produtoRepositoryPort.listarProdutos()).thenReturn(produtos);

        List<ProdutoModel> resultado = produtoService.listarProdutos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("X-Burger", resultado.get(0).getNmProduto());
        assertEquals("Coca-Cola", resultado.get(1).getNmProduto());
        verify(produtoRepositoryPort, times(1)).listarProdutos();
    }

    @Test
    @DisplayName("Deve listar produtos por categoria LANCHE")
    void deveListarProdutosPorCategoriaLanche() {
        ProdutoModel lanche2 = new ProdutoModel(
                "2",
                "X-Salada",
                "Hambúrguer com salada",
                22.90,
                true,
                ProdutoEnum.LANCHE
        );

        List<ProdutoModel> lanches = Arrays.asList(produtoModel, lanche2);
        when(produtoRepositoryPort.listarProdutosPorCategoria(ProdutoEnum.LANCHE)).thenReturn(lanches);

        List<ProdutoModel> resultado = produtoService.listarProdutosPorCategoria(ProdutoEnum.LANCHE);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getTpCategoria() == ProdutoEnum.LANCHE));
        verify(produtoRepositoryPort, times(1)).listarProdutosPorCategoria(ProdutoEnum.LANCHE);
    }

    @Test
    @DisplayName("Deve listar produtos por categoria BEBIDA")
    void deveListarProdutosPorCategoriaBebida() {
        ProdutoModel bebida = new ProdutoModel(
                "3",
                "Suco de Laranja",
                "Suco natural 300ml",
                7.50,
                true,
                ProdutoEnum.BEBIDA
        );

        List<ProdutoModel> bebidas = List.of(bebida);
        when(produtoRepositoryPort.listarProdutosPorCategoria(ProdutoEnum.BEBIDA)).thenReturn(bebidas);

        List<ProdutoModel> resultado = produtoService.listarProdutosPorCategoria(ProdutoEnum.BEBIDA);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(ProdutoEnum.BEBIDA, resultado.get(0).getTpCategoria());
        verify(produtoRepositoryPort, times(1)).listarProdutosPorCategoria(ProdutoEnum.BEBIDA);
    }

    @Test
    @DisplayName("Deve listar produtos por categoria ACOMPANHAMENTO")
    void deveListarProdutosPorCategoriaAcompanhamento() {
        ProdutoModel acompanhamento = new ProdutoModel(
                "4",
                "Batata Frita",
                "Porção grande de batata",
                12.90,
                true,
                ProdutoEnum.ACOMPANHAMENTO
        );

        List<ProdutoModel> acompanhamentos = List.of(acompanhamento);
        when(produtoRepositoryPort.listarProdutosPorCategoria(ProdutoEnum.ACOMPANHAMENTO))
                .thenReturn(acompanhamentos);

        List<ProdutoModel> resultado = produtoService.listarProdutosPorCategoria(ProdutoEnum.ACOMPANHAMENTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(ProdutoEnum.ACOMPANHAMENTO, resultado.get(0).getTpCategoria());
        verify(produtoRepositoryPort, times(1)).listarProdutosPorCategoria(ProdutoEnum.ACOMPANHAMENTO);
    }

    @Test
    @DisplayName("Deve listar produtos por categoria SOBREMESA")
    void deveListarProdutosPorCategoriaSobremesa() {
        ProdutoModel sobremesa = new ProdutoModel(
                "5",
                "Sorvete",
                "Sorvete de chocolate",
                8.90,
                true,
                ProdutoEnum.SOBREMESA
        );

        List<ProdutoModel> sobremesas = List.of(sobremesa);
        when(produtoRepositoryPort.listarProdutosPorCategoria(ProdutoEnum.SOBREMESA))
                .thenReturn(sobremesas);

        List<ProdutoModel> resultado = produtoService.listarProdutosPorCategoria(ProdutoEnum.SOBREMESA);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(ProdutoEnum.SOBREMESA, resultado.get(0).getTpCategoria());
        verify(produtoRepositoryPort, times(1)).listarProdutosPorCategoria(ProdutoEnum.SOBREMESA);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há produtos")
    void deveRetornarListaVaziaQuandoNaoHaProdutos() {
        when(produtoRepositoryPort.listarProdutos()).thenReturn(List.of());

        List<ProdutoModel> resultado = produtoService.listarProdutos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(produtoRepositoryPort, times(1)).listarProdutos();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há produtos da categoria")
    void deveRetornarListaVaziaQuandoNaoHaProdutosDaCategoria() {
        when(produtoRepositoryPort.listarProdutosPorCategoria(ProdutoEnum.SOBREMESA))
                .thenReturn(List.of());

        List<ProdutoModel> resultado = produtoService.listarProdutosPorCategoria(ProdutoEnum.SOBREMESA);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(produtoRepositoryPort, times(1)).listarProdutosPorCategoria(ProdutoEnum.SOBREMESA);
    }

    @Test
    @DisplayName("Deve verificar interação com repository ao cadastrar")
    void deveVerificarInteracaoComRepositoryAoCadastrar() {
        when(produtoRepositoryPort.cadastrar(any(ProdutoModel.class))).thenReturn(produtoModel);

        produtoService.cadastrar(produtoModel);

        verify(produtoRepositoryPort).cadastrar(produtoModel);
        verifyNoMoreInteractions(produtoRepositoryPort);
    }

    @Test
    @DisplayName("Deve propagar exceção do repository no cadastro")
    void devePropagarExcecaoDoRepositoryNoCadastro() {
        when(produtoRepositoryPort.cadastrar(any(ProdutoModel.class)))
                .thenThrow(new RuntimeException("Erro ao cadastrar"));

        assertThrows(RuntimeException.class, () -> {
            produtoService.cadastrar(produtoModel);
        });

        verify(produtoRepositoryPort, times(1)).cadastrar(produtoModel);
    }

    @Test
    @DisplayName("Deve manter dados originais ao ativar produto")
    void deveManterDadosOriginaisAoAtivarProduto() {
        when(produtoRepositoryPort.ativar("1")).thenReturn(produtoModel);

        ProdutoModel resultado = produtoService.ativar("1");

        assertEquals(produtoModel.getCdProduto(), resultado.getCdProduto());
        assertEquals(produtoModel.getNmProduto(), resultado.getNmProduto());
        assertEquals(produtoModel.getVlPreco(), resultado.getVlPreco());
        verify(produtoRepositoryPort, times(1)).ativar("1");
    }

    @Test
    @DisplayName("Deve manter dados originais ao desativar produto")
    void deveManterDadosOriginaisAoDesativarProduto() {
        produtoModel.setAtivo(false);
        when(produtoRepositoryPort.desativar("1")).thenReturn(produtoModel);

        ProdutoModel resultado = produtoService.desativar("1");

        assertEquals(produtoModel.getCdProduto(), resultado.getCdProduto());
        assertEquals(produtoModel.getNmProduto(), resultado.getNmProduto());
        assertEquals(produtoModel.getVlPreco(), resultado.getVlPreco());
        assertFalse(resultado.getAtivo());
        verify(produtoRepositoryPort, times(1)).desativar("1");
    }
}
