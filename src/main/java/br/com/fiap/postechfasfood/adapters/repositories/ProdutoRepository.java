package br.com.fiap.postechfasfood.adapters.repositories;

import br.com.fiap.postechfasfood.adapters.mappers.ProdutoMapper;
import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.domain.ports.out.ProdutoRepositoryPort;
import br.com.fiap.postechfasfood.infra.db.entities.ProdutoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProdutoRepository implements ProdutoRepositoryPort {

    private final ProdutoMapper mapper;

    public ProdutoRepository(ProdutoMapper mapper) {
        this.mapper = mapper;
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public ProdutoModel cadastrar(ProdutoModel product) {
        ProdutoEntity produtoEntity = mapper.toEntity(product);
        produtoEntity.setCdProduto(String.valueOf(UUID.randomUUID()));
        em.merge(produtoEntity);
        return mapper.toModel(produtoEntity);
    }

    @Override
    @Transactional
    public ProdutoModel atualizar(String cdProduto, ProdutoModel produto) throws Exception {
        var produtoAntigo = buscar(cdProduto);
        if (produtoAntigo == null){
            throw new Exception("NotFound - produto inexiste");
        }
        ProdutoEntity produtoEntity = mapper.toEntityWithCdProduto(produto, cdProduto);
        em.merge(produtoEntity);
        return mapper.toModel(produtoEntity);
    }

    @Override
    @Transactional
    public ProdutoModel ativar(String cdProduto){
        var produto = buscar(cdProduto);
        if (produto != null){
            produto.setSnAtivo(true);
            em.merge(produto);
            return mapper.toModel(produto);
        }else {
            return null;
        }
    }

    @Override
    @Transactional
    public ProdutoModel desativar(String cdProduto){
        var produto = buscar(cdProduto);
        if (produto != null){
            produto.setSnAtivo(false);
            em.merge(produto);
            var r = mapper.toModel(produto);
            return r;
        }else {
            return null;
        }
    }

    @Override
    @Transactional
    public List<ProdutoModel> listarProdutosPorCategoria(ProdutoEnum tpCategoria) {
        var jpql = "FROM ProdutoEntity WHERE tpCategoria = :tpCategoria AND snAtivo = true";
        List<ProdutoEntity> produtosEntity = em.createQuery(jpql, ProdutoEntity.class)
                .setParameter("tpCategoria", tpCategoria)
                .getResultList();

        return produtosEntity.stream().map(mapper::toModel).toList();
    }

    @Override
    @Transactional
    public List<ProdutoModel> listarProdutos(){
        var jpql = "FROM ProdutoEntity WHERE snAtivo = true";
        List<ProdutoEntity> produtosEntity = em.createQuery(jpql, ProdutoEntity.class)
                .getResultList();
        return produtosEntity.stream().map(mapper::toModel).toList();
    }

    private ProdutoEntity buscar(String cdProduto) {
        var produto = Optional.ofNullable(em.find(ProdutoEntity.class, cdProduto));
        if(produto.isPresent()){
            ProdutoEntity produtoEntity = produto.get();
            return produtoEntity;
        }else {
            return null;
        }
    }
}
