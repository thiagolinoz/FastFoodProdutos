package br.com.fiap.postechfasfood.domain.services;

import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.domain.ports.in.ProdutoServicePort;
import br.com.fiap.postechfasfood.domain.ports.out.ProdutoRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public ProdutoModel atualizarProduto(String cdProduto, ProdutoModel produtoModel) {
        return produtoRepositoryPort.atualizar(cdProduto, produtoModel);
    }

    @Override
    public ProdutoModel ativar(String cdProduto) {
        return produtoRepositoryPort.ativar(cdProduto);
    }

    @Override
    public ProdutoModel desativar(String cdProduto) {
        return produtoRepositoryPort.desativar(cdProduto);
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
