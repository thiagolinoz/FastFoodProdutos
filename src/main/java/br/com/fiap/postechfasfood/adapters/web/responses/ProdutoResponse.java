package br.com.fiap.postechfasfood.hexagonal.adapters.web.responses;

import br.com.fiap.postechfasfood.hexagonal.domain.models.ProdutoModel;

import java.util.List;

public record ProdutoResponse(
        String cdProduto,
        String nmProduto,
        String dsDescricao,
        double vlPreco,
        boolean snAtivo,
        String tpCategoria
) {
    public ProdutoResponse(ProdutoModel produtoModel) {
        this(produtoModel.getCdProduto(),
                produtoModel.getNmProduto(),
                produtoModel.getDsDescricao(),
                produtoModel.getVlPreco(),
                produtoModel.getAtivo(),
                produtoModel.getTpCategoria().name());
    }
//TODO: implementar mapper para devolver uma lista de Produto response
//    public ProdutoResponse(List<ProdutoModel> produtoModelList) {
//        produtoModelList.stream().map(mapper::toModel).toList();
//        this(produtoModel.getCdProduto(),
//                produtoModel.getNmProduto(),
//                produtoModel.getDsDescricao(),
//                produtoModel.getVlPreco(),
//                produtoModel.getAtivo(),
//                produtoModel.getTpCategoria().name());
//    }
}
