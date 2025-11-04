package br.com.fiap.postechfasfood.hexagonal.domain.ports.out;

import br.com.fiap.postechfasfood.hexagonal.domain.models.ProductModel;

public interface ProductRepositoryPort {
    ProductModel create(ProductModel productModel);
}
