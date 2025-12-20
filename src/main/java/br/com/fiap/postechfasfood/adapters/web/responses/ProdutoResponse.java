package br.com.fiap.postechfasfood.adapters.web.responses;

import br.com.fiap.postechfasfood.domain.models.ProdutoModel;

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
//                produtoModel.getTpCategoria().name());
//    }
}
