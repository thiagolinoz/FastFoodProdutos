package br.com.fiap.postechfasfood.adapters.repositories;

import br.com.fiap.postechfasfood.adapters.mappers.ProdutoMapper;
import br.com.fiap.postechfasfood.domain.exceptions.ProdutoNotFoundException;
import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.infra.db.entities.ProdutoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private ProdutoMapper mapper;

    @Mock
    private TypedQuery<ProdutoEntity> query;

    private ProdutoRepository produtoRepository;

    private ProdutoModel produtoModel;
    private ProdutoEntity produtoEntity;

    @BeforeEach
    void setUp() {
        produtoRepository = new ProdutoRepository(mapper);
        ReflectionTestUtils.setField(produtoRepository, "em", entityManager);
        
        produtoModel = new ProdutoModel();
        produtoModel.setNmProduto("X-Burger");
        produtoModel.setDsDescricao("Hambúrguer com queijo");
        produtoModel.setVlPreco(25.90);
        produtoModel.setAtivo(true);
        produtoModel.setTpCategoria(ProdutoEnum.LANCHE);

        produtoEntity = new ProdutoEntity();
        produtoEntity.setCdProduto("123e4567-e89b-12d3-a456-426614174000");
        produtoEntity.setNmProduto("X-Burger");
        produtoEntity.setDsDescricao("Hambúrguer com queijo");
        produtoEntity.setVlPreco(25.90);
        produtoEntity.setSnAtivo(true);
        produtoEntity.setTpCategoria(ProdutoEnum.LANCHE);
    }

    @Test
    @DisplayName("Deve cadastrar um produto com sucesso")
    void deveCadastrarProdutoComSucesso() {
        // Arrange
        when(mapper.toEntity(produtoModel)).thenReturn(produtoEntity);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        // Act
        ProdutoModel resultado = produtoRepository.cadastrar(produtoModel);

        // Assert
        assertNotNull(resultado);
        assertEquals("X-Burger", resultado.getNmProduto());
        verify(mapper).toEntity(produtoModel);
        verify(entityManager).merge(any(ProdutoEntity.class));
        verify(mapper).toModel(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve atualizar um produto existente com sucesso")
    void deveAtualizarProdutoExistenteComSucesso() {
        // Arrange
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(produtoEntity);
        when(mapper.toEntityWithCdProduto(produtoModel, cdProduto)).thenReturn(produtoEntity);
        when(mapper.toModel(produtoEntity)).thenReturn(produtoModel);

        // Act
        ProdutoModel resultado = produtoRepository.atualizar(cdProduto, produtoModel);

        // Assert
        assertNotNull(resultado);
        assertEquals("X-Burger", resultado.getNmProduto());
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(mapper).toEntityWithCdProduto(produtoModel, cdProduto);
        verify(entityManager).merge(produtoEntity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar produto inexistente")
    void deveLancarExcecaoAoAtualizarProdutoInexistente() {
        // Arrange
        String cdProduto = "inexistente";
        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(null);

        // Act & Assert
        ProdutoNotFoundException exception = assertThrows(ProdutoNotFoundException.class, () -> {
            produtoRepository.atualizar(cdProduto, produtoModel);
        });

        assertEquals("Produto não encontrado: inexistente", exception.getMessage());
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager, never()).merge(any());
    }

    @Test
    @DisplayName("Deve ativar um produto com sucesso")
    void deveAtivarProdutoComSucesso() {
        // Arrange
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        produtoEntity.setSnAtivo(false);
        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(produtoEntity);
        when(mapper.toModel(produtoEntity)).thenReturn(produtoModel);

        // Act
        ProdutoModel resultado = produtoRepository.ativar(cdProduto);

        // Assert
        assertNotNull(resultado);
        assertTrue(produtoEntity.isSnAtivo());
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager).merge(produtoEntity);
        verify(mapper).toModel(produtoEntity);
    }

    @Test
    @DisplayName("Deve retornar null ao tentar ativar produto inexistente")
    void deveRetornarNullAoAtivarProdutoInexistente() {
        // Arrange
        String cdProduto = "inexistente";
        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(null);

        // Act
        ProdutoModel resultado = produtoRepository.ativar(cdProduto);

        // Assert
        assertNull(resultado);
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager, never()).merge(any());
    }

    @Test
    @DisplayName("Deve desativar um produto com sucesso")
    void deveDesativarProdutoComSucesso() {
        // Arrange
        String cdProduto = "123e4567-e89b-12d3-a456-426614174000";
        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(produtoEntity);
        when(mapper.toModel(produtoEntity)).thenReturn(produtoModel);

        // Act
        ProdutoModel resultado = produtoRepository.desativar(cdProduto);

        // Assert
        assertNotNull(resultado);
        assertFalse(produtoEntity.isSnAtivo());
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager).merge(produtoEntity);
        verify(mapper).toModel(produtoEntity);
    }

    @Test
    @DisplayName("Deve retornar null ao tentar desativar produto inexistente")
    void deveRetornarNullAoDesativarProdutoInexistente() {
        // Arrange
        String cdProduto = "inexistente";
        when(entityManager.find(ProdutoEntity.class, cdProduto)).thenReturn(null);

        // Act
        ProdutoModel resultado = produtoRepository.desativar(cdProduto);

        // Assert
        assertNull(resultado);
        verify(entityManager).find(ProdutoEntity.class, cdProduto);
        verify(entityManager, never()).merge(any());
    }

    @Test
    @DisplayName("Deve listar produtos por categoria com sucesso")
    void deveListarProdutosPorCategoriaComSucesso() {
        // Arrange
        ProdutoEntity produto2 = new ProdutoEntity();
        produto2.setCdProduto("456");
        produto2.setNmProduto("Big Burger");
        produto2.setDsDescricao("Hambúrguer grande");
        produto2.setVlPreco(35.90);
        produto2.setSnAtivo(true);
        produto2.setTpCategoria(ProdutoEnum.LANCHE);

        List<ProdutoEntity> entidades = Arrays.asList(produtoEntity, produto2);

        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(entidades);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        // Act
        List<ProdutoModel> resultado = produtoRepository.listarProdutosPorCategoria(ProdutoEnum.LANCHE);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(entityManager).createQuery(anyString(), eq(ProdutoEntity.class));
        verify(query).setParameter("tpCategoria", ProdutoEnum.LANCHE);
        verify(query).getResultList();
        verify(mapper, times(2)).toModel(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver produtos da categoria")
    void deveRetornarListaVaziaQuandoNaoHouverProdutosDaCategoria() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<ProdutoModel> resultado = produtoRepository.listarProdutosPorCategoria(ProdutoEnum.SOBREMESA);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(entityManager).createQuery(anyString(), eq(ProdutoEntity.class));
        verify(query).setParameter("tpCategoria", ProdutoEnum.SOBREMESA);
    }

    @Test
    @DisplayName("Deve listar todos os produtos ativos com sucesso")
    void deveListarTodosProdutosAtivosComSucesso() {
        // Arrange
        ProdutoEntity produto2 = new ProdutoEntity();
        produto2.setCdProduto("456");
        produto2.setNmProduto("Coca-Cola");
        produto2.setDsDescricao("Refrigerante");
        produto2.setVlPreco(5.50);
        produto2.setSnAtivo(true);
        produto2.setTpCategoria(ProdutoEnum.BEBIDA);

        List<ProdutoEntity> entidades = Arrays.asList(produtoEntity, produto2);

        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(entidades);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        // Act
        List<ProdutoModel> resultado = produtoRepository.listarProdutos();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(entityManager).createQuery(anyString(), eq(ProdutoEntity.class));
        verify(query).getResultList();
        verify(mapper, times(2)).toModel(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver produtos ativos")
    void deveRetornarListaVaziaQuandoNaoHouverProdutosAtivos() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<ProdutoModel> resultado = produtoRepository.listarProdutos();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(entityManager).createQuery(anyString(), eq(ProdutoEntity.class));
        verify(query).getResultList();
        verify(mapper, never()).toModel(any(ProdutoEntity.class));
    }

    @Test
    @DisplayName("Deve listar apenas produtos LANCHE ativos")
    void deveListarApenasProdutosLancheAtivos() {
        // Arrange
        when(entityManager.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(produtoEntity));
        when(mapper.toModel(produtoEntity)).thenReturn(produtoModel);

        // Act
        List<ProdutoModel> resultado = produtoRepository.listarProdutosPorCategoria(ProdutoEnum.LANCHE);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(query).setParameter("tpCategoria", ProdutoEnum.LANCHE);
    }

    @Test
    @DisplayName("Deve cadastrar produto e gerar UUID automaticamente")
    void deveCadastrarProdutoEGerarUUIDAutomaticamente() {
        // Arrange
        ProdutoEntity entitySemId = new ProdutoEntity();
        entitySemId.setNmProduto("X-Burger");
        
        when(mapper.toEntity(produtoModel)).thenReturn(entitySemId);
        when(mapper.toModel(any(ProdutoEntity.class))).thenReturn(produtoModel);

        // Act
        ProdutoModel resultado = produtoRepository.cadastrar(produtoModel);

        // Assert
        assertNotNull(resultado);
        assertNotNull(entitySemId.getCdProduto());
        verify(entityManager).merge(entitySemId);
    }
}
