package br.com.fiap.postechfasfood.hexagonal.domain.services;

import br.com.fiap.postechfasfood.hexagonal.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.hexagonal.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.in.ProdutoServicePort;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.out.ProdutoRepositoryPort;

import java.util.List;

public class ProdutoService implements ProdutoServicePort {

    private final ProdutoRepositoryPort produtoRepositoryPort;

    public ProdutoService(ProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }

    @Override
    public ProdutoModel cadastrar(ProdutoModel produto) {
        return produtoRepositoryPort.cadastrar(produto);
    }

    @Override
    public ProdutoModel atualizarProduto(String cdProduto, ProdutoModel produtoModel) throws RuntimeException {
        try {
            return produtoRepositoryPort.atualizar(cdProduto, produtoModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProdutoModel ativar(String cdProduto) {
        produtoRepositoryPort.ativar(cdProduto);
        return null;
    }

    @Override
    public ProdutoModel desativar(String cdProduto) {
        produtoRepositoryPort.desativar(cdProduto);
        return null;
    }

    @Override
    public List<ProdutoModel> listarProdutosPorCategoria(ProdutoEnum tpCategoria) {
        return produtoRepositoryPort.listarProdutosPorCategoria(tpCategoria);
    }

    @Override
    public List<ProdutoModel> listarProdutos() {
        return produtoRepositoryPort.listarProdutos();
    }
}
