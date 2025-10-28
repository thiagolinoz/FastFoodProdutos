package br.com.fiap.postechfasfood.hexagonal.adapters.in.web.responses;

import br.com.fiap.postechfasfood.hexagonal.domain.models.Produto;

public record ProdutoControllerResponse(
        String cdProduto,
        String nmProduto,
        String dsDescricao,
        double vlPreco,
        boolean snAtivo,
        String tpCategoria
) {
    public ProdutoControllerResponse(Produto produto) {
        this(produto.getCdProduto(),
                produto.getNmProduto(),
                produto.getDsDescricao(),
                produto.getVlPreco(),
                produto.getSnAtivo(),
                produto.getTpCategoria().name());
    }
}
