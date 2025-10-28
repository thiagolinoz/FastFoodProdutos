package br.com.fiap.postechfasfood.hexagonal.domain.ports.out;

import br.com.fiap.postechfasfood.hexagonal.domain.models.Produto;

public interface ProdutoRepositoryPort {
    Produto salvar(Produto produto);
}
