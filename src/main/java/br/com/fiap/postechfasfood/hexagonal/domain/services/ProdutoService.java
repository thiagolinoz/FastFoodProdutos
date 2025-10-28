package br.com.fiap.postechfasfood.hexagonal.domain.services;

import br.com.fiap.postechfasfood.hexagonal.adapters.in.web.requests.ProdutoControllerRequest;
import br.com.fiap.postechfasfood.hexagonal.domain.models.Produto;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.in.ProdutoServicePort;

public class ProdutoService implements ProdutoServicePort {
    @Override
    public Produto cadastrar(ProdutoControllerRequest produtoControllerRequest) {
        return null;
    }
}
