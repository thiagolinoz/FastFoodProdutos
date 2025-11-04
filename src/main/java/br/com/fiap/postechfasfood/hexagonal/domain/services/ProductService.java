package br.com.fiap.postechfasfood.hexagonal.domain.services;

import br.com.fiap.postechfasfood.hexagonal.domain.models.ProductModel;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.in.ProductServicePort;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.out.ProductRepositoryPort;

public class ProductService implements ProductServicePort {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public ProductModel create(ProductModel produto) {
        productRepositoryPort.create(produto);
        return null;
    }
}
