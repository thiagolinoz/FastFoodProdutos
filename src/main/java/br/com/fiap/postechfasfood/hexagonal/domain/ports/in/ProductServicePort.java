package br.com.fiap.postechfasfood.hexagonal.domain.ports.in;

import br.com.fiap.postechfasfood.hexagonal.domain.models.ProductModel;

public interface ProductServicePort {
    ProductModel create(ProductModel productModel);
}
