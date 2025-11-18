package br.com.fiap.postechfasfood.adapters.web.requests;


import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequest(
        @Valid @NotNull String nmProduto,
        @Valid @NotNull String dsDescricao,
        @Valid @NotNull double vlPreco,
        @Valid @NotNull boolean snAtivo,
        @Valid @NotNull ProdutoEnum tpCategoria
) {
}