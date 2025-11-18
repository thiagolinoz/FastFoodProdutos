package br.com.fiap.postechfasfood.domain.ports.in;

import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;

import java.util.List;

public interface ProdutoServicePort {
    ProdutoModel cadastrar(ProdutoModel produtoModel);
    ProdutoModel atualizarProduto(String cdProduto, ProdutoModel produtoModel) throws RuntimeException;
    ProdutoModel ativar(String cdProduto);
    ProdutoModel desativar(String cdProduto);
    List<ProdutoModel> listarProdutos();
    List<ProdutoModel> listarProdutosPorCategoria(ProdutoEnum tpCategoria);
}
