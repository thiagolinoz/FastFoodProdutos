package br.com.fiap.postechfasfood.hexagonal.adapters.out.db.repositories;

import br.com.fiap.postechfasfood.hexagonal.domain.models.Produto;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.out.ProdutoRepositoryPort;

public class ProdutoRepository implements ProdutoRepositoryPort {
    @Override
    public Produto salvar(Produto produto) {
        return null;
    }
}
