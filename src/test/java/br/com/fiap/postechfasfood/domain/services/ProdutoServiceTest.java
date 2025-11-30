package br.com.fiap.postechfasfood.domain.services;

import br.com.fiap.postechfasfood.domain.exceptions.ProdutoNotFoundException;
import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.domain.ports.out.ProdutoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProdutoService - Testes do Serviço de Domínio")
class ProdutoServiceTest {

    @Mock
    private ProdutoRepositoryPort produtoRepositoryPort;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoModel produtoModel;

    @BeforeEach
    void setUp() {
        produtoModel = new ProdutoModel(
                "123e4567-e89b-12d3-a456-426614174000",
                "X-Burger Especial",
                "Hambúrguer artesanal com queijo",
                28.90,
                true,
                ProdutoEnum.LANCHE
        );
    }

    @Test
    @DisplayName("Deve cadastrar produto com sucesso delegando ao repositório")
    void deveCadastrarProdutoComSucesso() {
        when(produtoRepositoryPort.cadastrar(any(ProdutoModel.class))).thenReturn(produtoModel);

        ProdutoModel resultado = produtoService.cadastrar(produtoModel);

        assertNotNull(resultado);
        assertEquals("123e4567-e89b-12d3-a456-426614174000", resultado.getCdProduto());
        assertEquals("X-Burger Especial", resultado.getNmProduto());
        assertEquals("Hambúrguer artesanal com queijo", resultado.getDsDescricao());
        assertEquals(28.90, resultado.getVlPreco());
        assertTrue(resultado.getAtivo());
        assertEquals(ProdutoEnum.LANCHE, resultado.getTpCategoria());
        verify(produtoRepositoryPort, times(1)).cadastrar(produtoModel);
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void deveAtualizarProdutoComSucesso() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        ProdutoModel produtoAtualizado = new ProdutoModel(
                cdProduto,
                "X-Burger Premium",
                "Hambúrguer premium gourmet",
                35.90,
                true,
                ProdutoEnum.LANCHE
        );

        when(produtoRepositoryPort.atualizar(eq(cdProduto), any(ProdutoModel.class)))
                .thenReturn(produtoAtualizado);

        ProdutoModel resultado = produtoService.atualizarProduto(cdProduto, produtoAtualizado);

        assertNotNull(resultado);
        assertEquals("X-Burger Premium", resultado.getNmProduto());
        assertEquals(35.90, resultado.getVlPreco());
        verify(produtoRepositoryPort, times(1)).atualizar(cdProduto, produtoAtualizado);
    }

    @Test
    @DisplayName("Deve lançar ProdutoNotFoundException ao atualizar produto inexistente")
    void deveLancarRuntimeExceptionAoAtualizarProdutoInexistente() {
        String cdProduto = "inexistente";
        when(produtoRepositoryPort.atualizar(eq(cdProduto), any(ProdutoModel.class)))
                .thenThrow(new ProdutoNotFoundException("Produto não encontrado: inexistente"));

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.atualizarProduto(cdProduto, produtoModel);
        });

        assertEquals("Produto não encontrado: inexistente", exception.getMessage());
        verify(produtoRepositoryPort, times(1)).atualizar(cdProduto, produtoModel);
    }

    @Test
    @DisplayName("Deve ativar produto com sucesso")
    void deveAtivarProdutoComSucesso() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        produtoModel.setAtivo(true);

        when(produtoRepositoryPort.ativar(cdProduto)).thenReturn(produtoModel);

        ProdutoModel resultado = produtoService.ativar(cdProduto);

        assertNotNull(resultado);
        assertTrue(resultado.getAtivo());
        verify(produtoRepositoryPort, times(1)).ativar(cdProduto);
    }

    @Test
    @DisplayName("Deve desativar produto com sucesso")
    void deveDesativarProdutoComSucesso() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        produtoModel.setAtivo(false);

        when(produtoRepositoryPort.desativar(cdProduto)).thenReturn(produtoModel);

        ProdutoModel resultado = produtoService.desativar(cdProduto);

        assertNotNull(resultado);
        assertFalse(resultado.getAtivo());
        verify(produtoRepositoryPort, times(1)).desativar(cdProduto);
    }

    @Test
    @DisplayName("Deve listar todos os produtos ativos")
    void deveListarTodosProdutosAtivos() {
        ProdutoModel produto1 = new ProdutoModel("1", "X-Burger", "Lanche", 25.90, true, ProdutoEnum.LANCHE);
        ProdutoModel produto2 = new ProdutoModel("2", "Coca-Cola", "Bebida", 5.50, true, ProdutoEnum.BEBIDA);
        ProdutoModel produto3 = new ProdutoModel("3", "Batata Frita", "Acompanhamento", 12.90, true, ProdutoEnum.ACOMPANHAMENTO);

        List<ProdutoModel> produtos = Arrays.asList(produto1, produto2, produto3);
        when(produtoRepositoryPort.listarProdutos()).thenReturn(produtos);

        List<ProdutoModel> resultado = produtoService.listarProdutos();

        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals("X-Burger", resultado.get(0).getNmProduto());
        assertEquals("Coca-Cola", resultado.get(1).getNmProduto());
        assertEquals("Batata Frita", resultado.get(2).getNmProduto());
        verify(produtoRepositoryPort, times(1)).listarProdutos();
    }

    @Test
    @DisplayName("Deve listar produtos por categoria LANCHE")
    void deveListarProdutosPorCategoriaLanche() {
        ProdutoModel lanche1 = new ProdutoModel("1", "X-Burger", "Lanche básico", 25.90, true, ProdutoEnum.LANCHE);
        ProdutoModel lanche2 = new ProdutoModel("2", "X-Salada", "Lanche saudável", 22.90, true, ProdutoEnum.LANCHE);

        List<ProdutoModel> lanches = Arrays.asList(lanche1, lanche2);
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
        ProdutoModel bebida1 = new ProdutoModel("3", "Coca-Cola", "Refrigerante", 5.50, true, ProdutoEnum.BEBIDA);
        ProdutoModel bebida2 = new ProdutoModel("4", "Suco Natural", "Suco de laranja", 7.50, true, ProdutoEnum.BEBIDA);

        List<ProdutoModel> bebidas = Arrays.asList(bebida1, bebida2);
        when(produtoRepositoryPort.listarProdutosPorCategoria(ProdutoEnum.BEBIDA)).thenReturn(bebidas);

        List<ProdutoModel> resultado = produtoService.listarProdutosPorCategoria(ProdutoEnum.BEBIDA);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(p -> p.getTpCategoria() == ProdutoEnum.BEBIDA));
        verify(produtoRepositoryPort, times(1)).listarProdutosPorCategoria(ProdutoEnum.BEBIDA);
    }

    @Test
    @DisplayName("Deve listar produtos por categoria ACOMPANHAMENTO")
    void deveListarProdutosPorCategoriaAcompanhamento() {
        ProdutoModel acompanhamento = new ProdutoModel("5", "Batata Frita", "Porção grande", 12.90, true, ProdutoEnum.ACOMPANHAMENTO);

        List<ProdutoModel> acompanhamentos = Collections.singletonList(acompanhamento);
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
        ProdutoModel sobremesa = new ProdutoModel("6", "Sorvete", "Sorvete de chocolate", 8.90, true, ProdutoEnum.SOBREMESA);

        List<ProdutoModel> sobremesas = Collections.singletonList(sobremesa);
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
        when(produtoRepositoryPort.listarProdutos()).thenReturn(Collections.emptyList());

        List<ProdutoModel> resultado = produtoService.listarProdutos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(produtoRepositoryPort, times(1)).listarProdutos();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há produtos da categoria")
    void deveRetornarListaVaziaQuandoNaoHaProdutosDaCategoria() {
        when(produtoRepositoryPort.listarProdutosPorCategoria(ProdutoEnum.SOBREMESA))
                .thenReturn(Collections.emptyList());

        List<ProdutoModel> resultado = produtoService.listarProdutosPorCategoria(ProdutoEnum.SOBREMESA);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(produtoRepositoryPort, times(1)).listarProdutosPorCategoria(ProdutoEnum.SOBREMESA);
    }

    @Test
    @DisplayName("Deve verificar que o serviço delega todas as operações ao repositório")
    void deveVerificarDelegacaoAoRepositorio() {
        when(produtoRepositoryPort.cadastrar(any(ProdutoModel.class))).thenReturn(produtoModel);

        produtoService.cadastrar(produtoModel);

        verify(produtoRepositoryPort).cadastrar(produtoModel);
        verifyNoMoreInteractions(produtoRepositoryPort);
    }

    @Test
    @DisplayName("Deve propagar exceção do repositório ao cadastrar")
    void devePropagarExcecaoDoRepositorioAoCadastrar() {
        when(produtoRepositoryPort.cadastrar(any(ProdutoModel.class)))
                .thenThrow(new RuntimeException("Erro ao cadastrar"));

        assertThrows(RuntimeException.class, () -> {
            produtoService.cadastrar(produtoModel);
        });

        verify(produtoRepositoryPort, times(1)).cadastrar(produtoModel);
    }

    @Test
    @DisplayName("Deve retornar null ao ativar produto inexistente")
    void deveRetornarNullAoAtivarProdutoInexistente() {
        String cdProduto = "inexistente";
        when(produtoRepositoryPort.ativar(cdProduto)).thenReturn(null);

        ProdutoModel resultado = produtoService.ativar(cdProduto);

        assertNull(resultado);
        verify(produtoRepositoryPort, times(1)).ativar(cdProduto);
    }

    @Test
    @DisplayName("Deve retornar null ao desativar produto inexistente")
    void deveRetornarNullAoDesativarProdutoInexistente() {
        String cdProduto = "inexistente";
        when(produtoRepositoryPort.desativar(cdProduto)).thenReturn(null);

        ProdutoModel resultado = produtoService.desativar(cdProduto);

        assertNull(resultado);
        verify(produtoRepositoryPort, times(1)).desativar(cdProduto);
    }



    @Test
    @DisplayName("Deve cadastrar múltiplos produtos sequencialmente")
    void deveCadastrarMultiplosProdutosSequencialmente() {
        ProdutoModel produto1 = new ProdutoModel(null, "Produto 1", "Desc 1", 10.0, true, ProdutoEnum.LANCHE);
        ProdutoModel produto2 = new ProdutoModel(null, "Produto 2", "Desc 2", 20.0, true, ProdutoEnum.BEBIDA);

        when(produtoRepositoryPort.cadastrar(produto1)).thenReturn(produto1);
        when(produtoRepositoryPort.cadastrar(produto2)).thenReturn(produto2);

        ProdutoModel resultado1 = produtoService.cadastrar(produto1);
        ProdutoModel resultado2 = produtoService.cadastrar(produto2);

        assertNotNull(resultado1);
        assertNotNull(resultado2);
        verify(produtoRepositoryPort, times(1)).cadastrar(produto1);
        verify(produtoRepositoryPort, times(1)).cadastrar(produto2);
    }

    @Test
    @DisplayName("Deve listar produtos de todas as categorias")
    void deveListarProdutosDeTodasCategorias() {
        for (ProdutoEnum categoria : ProdutoEnum.values()) {
            ProdutoModel produto = new ProdutoModel("id", "Nome", "Desc", 10.0, true, categoria);
            when(produtoRepositoryPort.listarProdutosPorCategoria(categoria))
                    .thenReturn(Collections.singletonList(produto));

            List<ProdutoModel> resultado = produtoService.listarProdutosPorCategoria(categoria);

            assertNotNull(resultado);
            assertFalse(resultado.isEmpty());
            assertEquals(categoria, resultado.get(0).getTpCategoria());
        }

        verify(produtoRepositoryPort, times(4)).listarProdutosPorCategoria(any(ProdutoEnum.class));
    }

    @Test
    @DisplayName("Deve manter estado consistente após múltiplas operações")
    void deveManterEstadoConsistenteAposMultiplasOperacoes() {
        String cdProduto = "123";
        
        // Cadastrar
        when(produtoRepositoryPort.cadastrar(any(ProdutoModel.class))).thenReturn(produtoModel);
        produtoService.cadastrar(produtoModel);
        
        // Atualizar
        when(produtoRepositoryPort.atualizar(eq(cdProduto), any(ProdutoModel.class))).thenReturn(produtoModel);
        produtoService.atualizarProduto(cdProduto, produtoModel);
        
        // Desativar
        when(produtoRepositoryPort.desativar(cdProduto)).thenReturn(produtoModel);
        produtoService.desativar(cdProduto);

        verify(produtoRepositoryPort, times(1)).cadastrar(any(ProdutoModel.class));
        verify(produtoRepositoryPort, times(1)).atualizar(eq(cdProduto), any(ProdutoModel.class));
        verify(produtoRepositoryPort, times(1)).desativar(cdProduto);
    }
}
