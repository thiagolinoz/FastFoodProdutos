package br.com.fiap.postechfasfood.hexagonal.domain.ports.out;

import br.com.fiap.postechfasfood.hexagonal.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.hexagonal.domain.models.enuns.ProdutoEnum;

import java.util.List;


public interface ProdutoRepositoryPort {
    ProdutoModel cadastrar(ProdutoModel produtoModel);
    ProdutoModel atualizar(String cdProduto, ProdutoModel produtoModel) throws Exception;
    ProdutoModel ativar(String cdProduto);
    ProdutoModel desativar(String cdProduto);
    List<ProdutoModel> listarProdutosPorCategoria(ProdutoEnum tpCategoria);
}
