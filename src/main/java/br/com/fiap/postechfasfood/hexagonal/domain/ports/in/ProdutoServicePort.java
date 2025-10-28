package br.com.fiap.postechfasfood.hexagonal.domain.ports.in;

import br.com.fiap.postechfasfood.hexagonal.adapters.in.web.requests.ProdutoControllerRequest;
import br.com.fiap.postechfasfood.hexagonal.domain.models.Produto;

public interface ProdutoServicePort {
    Produto cadastrar(ProdutoControllerRequest produtoControllerRequest);
}
