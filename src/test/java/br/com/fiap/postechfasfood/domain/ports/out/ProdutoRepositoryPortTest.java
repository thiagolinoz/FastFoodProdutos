package br.com.fiap.postechfasfood.domain.ports.out;

import br.com.fiap.postechfasfood.adapters.mappers.ProdutoMapper;
import br.com.fiap.postechfasfood.domain.exceptions.ProdutoNotFoundException;
import br.com.fiap.postechfasfood.adapters.repositories.ProdutoRepository;
import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.infra.db.entities.ProdutoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProdutoRepositoryPort - Testes do Contrato de Repositório")
class ProdutoRepositoryPortTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private ProdutoMapper mapper;

    @Mock
    private TypedQuery<ProdutoEntity> typedQuery;

    @InjectMocks
    private ProdutoRepository produtoRepository;

    private ProdutoModel produtoModel;
    private ProdutoEntity produtoEntity;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(produtoRepository, "em", entityManager);

        produtoModel = new ProdutoModel(
                null,
                "X-Burger",
                "Delicioso hambúrguer",
                25.90,
                true,
                ProdutoEnum.LANCHE
        );

        produtoEntity = new ProdutoEntity();
        produtoEntity.setCdProduto("123e4567-e89b-12d3-a456-426614174000");
        produtoEntity.setNmProduto("X-Burger");
        produtoEntity.setDsDescricao("Delicioso hambúrguer");
        produtoEntity.setVlPreco(25.90);
        produtoEntity.setSnAtivo(true);
        produtoEntity.setTpCategoria(ProdutoEnum.LANCHE);
    }

    @Test
    @DisplayName("Deve cadastrar produto e gerar UUID automaticamente")
    void deveCadastrarProdutoComUUID() {
        when(mapper.toEntity(any(ProdutoModel.class))).thenReturn(produtoEntity);
        when(entityManager.merge(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        ProdutoModel resultado = produtoRepository.cadastrar(produtoModel);

        assertNotNull(resultado);
        verify(mapper).toEntity(produtoModel);
        verify(entityManager).merge(any(ProdutoEntity.class));
        verify(mapper).toModel(produtoEntity);
    }

    @Test
    @DisplayName("Deve atualizar produto existente")
    void deveAtualizarProdutoExistente() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        ProdutoModel produtoAtualizado = new ProdutoModel(
                cdProduto,
                "X-Burger Premium",
                "Hambúrguer premium",
                29.90,
                true,
                ProdutoEnum.LANCHE
        );

        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(produtoEntity);
        when(mapper.toEntityWithCdProduto(any(ProdutoModel.class), eq(cdProduto))).thenReturn(produtoEntity);
        when(entityManager.merge(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoAtualizado);

        ProdutoModel resultado = produtoRepository.atualizar(cdProduto, produtoAtualizado);

        assertNotNull(resultado);
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(mapper).toEntityWithCdProduto(produtoAtualizado, cdProduto);
        verify(entityManager).merge(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar produto inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        String cdProduto = "inexistente";
        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(null);

        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoRepository.atualizar(cdProduto, produtoModel);
        });

        assertEquals("Produto não encontrado: inexistente", exception.getMessage());
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager, never()).merge(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve ativar produto com sucesso")
    void deveAtivarProdutoComSucesso() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        produtoEntity.setSnAtivo(false);

        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(produtoEntity);
        when(entityManager.merge(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        ProdutoModel resultado = produtoRepository.ativar(cdProduto);

        assertNotNull(resultado);
        assertTrue(produtoEntity.isSnAtivo());
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager).merge(produtoEntity);
        verify(mapper).toModel(produtoEntity);
    }

    @Test
    @DisplayName("Deve retornar null ao ativar produto inexistente")
    void deveRetornarNullAoAtivarProdutoInexistente() {
        String cdProduto = "inexistente";
        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(null);

        ProdutoModel resultado = produtoRepository.ativar(cdProduto);

        assertNull(resultado);
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager, never()).merge(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve desativar produto com sucesso")
    void deveDesativarProdutoComSucesso() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        produtoEntity.setSnAtivo(true);

        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(produtoEntity);
        when(entityManager.merge(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        ProdutoModel resultado = produtoRepository.desativar(cdProduto);

        assertNotNull(resultado);
        assertFalse(produtoEntity.isSnAtivo());
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager).merge(produtoEntity);
        verify(mapper).toModel(produtoEntity);
    }

    @Test
    @DisplayName("Deve retornar null ao desativar produto inexistente")
    void deveRetornarNullAoDesativarProdutoInexistente() {
        String cdProduto = "inexistente";
        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(null);

        ProdutoModel resultado = produtoRepository.desativar(cdProduto);

        assertNull(resultado);
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager, never()).merge(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve listar produtos por categoria LANCHE")
    void deveListarProdutosPorCategoriaLanche() {
        ProdutoEntity lanche1 = new ProdutoEntity();
        lanche1.setCdProduto("1");
        lanche1.setNmProduto("X-Burger");
        lanche1.setTpCategoria(ProdutoEnum.LANCHE);
        lanche1.setSnAtivo(true);

        ProdutoEntity lanche2 = new ProdutoEntity();
        lanche2.setCdProduto("2");
        lanche2.setNmProduto("X-Salada");
        lanche2.setTpCategoria(ProdutoEnum.LANCHE);
        lanche2.setSnAtivo(true);

        List<ProdutoEntity> entities = Arrays.asList(lanche1, lanche2);

        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("tpCategoria", ProdutoEnum.LANCHE)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(entities);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        List<ProdutoModel> resultado = produtoRepository.listarProdutosPorCategoria(ProdutoEnum.LANCHE);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(entityManager).createQuery(anyString(), eq(ProdutoEntity.class));
        verify(typedQuery).setParameter("tpCategoria", ProdutoEnum.LANCHE);
        verify(mapper, times(2)).toModel(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve listar produtos por categoria BEBIDA")
    void deveListarProdutosPorCategoriaBebida() {
        ProdutoEntity bebida = new ProdutoEntity();
        bebida.setCdProduto("3");
        bebida.setNmProduto("Coca-Cola");
        bebida.setTpCategoria(ProdutoEnum.BEBIDA);
        bebida.setSnAtivo(true);

        List<ProdutoEntity> entities = List.of(bebida);

        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("tpCategoria", ProdutoEnum.BEBIDA)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(entities);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        List<ProdutoModel> resultado = produtoRepository.listarProdutosPorCategoria(ProdutoEnum.BEBIDA);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(typedQuery).setParameter("tpCategoria", ProdutoEnum.BEBIDA);
    }

    @Test
    @DisplayName("Deve listar produtos por categoria ACOMPANHAMENTO")
    void deveListarProdutosPorCategoriaAcompanhamento() {
        ProdutoEntity acompanhamento = new ProdutoEntity();
        acompanhamento.setCdProduto("4");
        acompanhamento.setNmProduto("Batata Frita");
        acompanhamento.setTpCategoria(ProdutoEnum.ACOMPANHAMENTO);
        acompanhamento.setSnAtivo(true);

        List<ProdutoEntity> entities = List.of(acompanhamento);

        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("tpCategoria", ProdutoEnum.ACOMPANHAMENTO)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(entities);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        List<ProdutoModel> resultado = produtoRepository.listarProdutosPorCategoria(ProdutoEnum.ACOMPANHAMENTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(typedQuery).setParameter("tpCategoria", ProdutoEnum.ACOMPANHAMENTO);
    }

    @Test
    @DisplayName("Deve listar produtos por categoria SOBREMESA")
    void deveListarProdutosPorCategoriaSobremesa() {
        ProdutoEntity sobremesa = new ProdutoEntity();
        sobremesa.setCdProduto("5");
        sobremesa.setNmProduto("Sorvete");
        sobremesa.setTpCategoria(ProdutoEnum.SOBREMESA);
        sobremesa.setSnAtivo(true);

        List<ProdutoEntity> entities = List.of(sobremesa);

        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("tpCategoria", ProdutoEnum.SOBREMESA)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(entities);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        List<ProdutoModel> resultado = produtoRepository.listarProdutosPorCategoria(ProdutoEnum.SOBREMESA);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(typedQuery).setParameter("tpCategoria", ProdutoEnum.SOBREMESA);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há produtos da categoria")
    void deveRetornarListaVaziaQuandoNaoHaProdutosDaCategoria() {
        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("tpCategoria"), any(ProdutoEnum.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of());

        List<ProdutoModel> resultado = produtoRepository.listarProdutosPorCategoria(ProdutoEnum.LANCHE);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve listar todos os produtos ativos")
    void deveListarTodosProdutosAtivos() {
        ProdutoEntity produto1 = new ProdutoEntity();
        produto1.setCdProduto("1");
        produto1.setNmProduto("X-Burger");
        produto1.setSnAtivo(true);

        ProdutoEntity produto2 = new ProdutoEntity();
        produto2.setCdProduto("2");
        produto2.setNmProduto("Coca-Cola");
        produto2.setSnAtivo(true);

        List<ProdutoEntity> entities = Arrays.asList(produto1, produto2);

        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(entities);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        List<ProdutoModel> resultado = produtoRepository.listarProdutos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(entityManager).createQuery(anyString(), eq(ProdutoEntity.class));
        verify(mapper, times(2)).toModel(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há produtos")
    void deveRetornarListaVaziaQuandoNaoHaProdutos() {
        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of());

        List<ProdutoModel> resultado = produtoRepository.listarProdutos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(mapper, never()).toModel(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve verificar que apenas produtos ativos são listados")
    void deveVerificarQueApenasProdutosAtividosSaoListados() {
        String jpqlEsperado = "FROM ProdutoEntity WHERE snAtivo = true";

        when(entityManager.createQuery(jpqlEsperado, ProdutoEntity.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of());

        produtoRepository.listarProdutos();

        verify(entityManager).createQuery(jpqlEsperado, ProdutoEntity.class);
    }

    @Test
    @DisplayName("Deve verificar JPQL correto para listar por categoria")
    void deveVerificarJPQLCorretoParaListarPorCategoria() {
        String jpqlEsperado = "FROM ProdutoEntity WHERE tpCategoria = :tpCategoria AND snAtivo = true";

        when(entityManager.createQuery(jpqlEsperado, ProdutoEntity.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("tpCategoria", ProdutoEnum.LANCHE)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of());

        produtoRepository.listarProdutosPorCategoria(ProdutoEnum.LANCHE);

        verify(entityManager).createQuery(jpqlEsperado, ProdutoEntity.class);
        verify(typedQuery).setParameter("tpCategoria", ProdutoEnum.LANCHE);
    }

    @Test
    @DisplayName("Deve converter todas as entidades para modelo ao listar")
    void deveConverterTodasEntidadesParaModeloAoListar() {
        ProdutoEntity entity1 = new ProdutoEntity();
        ProdutoEntity entity2 = new ProdutoEntity();
        ProdutoEntity entity3 = new ProdutoEntity();
        List<ProdutoEntity> entities = Arrays.asList(entity1, entity2, entity3);

        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(entities);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        List<ProdutoModel> resultado = produtoRepository.listarProdutos();

        assertEquals(3, resultado.size());
        verify(mapper, times(3)).toModel(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve usar merge ao cadastrar produto")
    void deveUsarMergeAoCadastrarProduto() {
        when(mapper.toEntity(any(ProdutoModel.class))).thenReturn(produtoEntity);
        when(entityManager.merge(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        produtoRepository.cadastrar(produtoModel);

        verify(entityManager).merge(any(ProdutoEntity.class));
        verify(entityManager, never()).persist(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve usar merge ao atualizar produto")
    void deveUsarMergeAoAtualizarProduto() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";

        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(produtoEntity);
        when(mapper.toEntityWithCdProduto(any(ProdutoModel.class), eq(cdProduto))).thenReturn(produtoEntity);
        when(entityManager.merge(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        produtoRepository.atualizar(cdProduto, produtoModel);

        verify(entityManager).merge(produtoEntity);
    }

    @Test
    @DisplayName("Deve modificar flag snAtivo ao ativar")
    void deveModificarFlagSnAtivoAoAtivar() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        produtoEntity.setSnAtivo(false);

        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(produtoEntity);
        when(entityManager.merge(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        produtoRepository.ativar(cdProduto);

        assertTrue(produtoEntity.isSnAtivo());
    }

    @Test
    @DisplayName("Deve modificar flag snAtivo ao desativar")
    void deveModificarFlagSnAtivoAoDesativar() {
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        produtoEntity.setSnAtivo(true);

        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(produtoEntity);
        when(entityManager.merge(any(ProdutoEntity.class))).thenReturn(produtoEntity);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        produtoRepository.desativar(cdProduto);

        assertFalse(produtoEntity.isSnAtivo());
    }
}
