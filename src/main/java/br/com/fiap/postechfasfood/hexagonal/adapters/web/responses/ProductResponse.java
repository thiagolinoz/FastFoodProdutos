package br.com.fiap.postechfasfood.hexagonal.adapters.web.responses;

import br.com.fiap.postechfasfood.hexagonal.domain.models.ProductModel;

public record ProductResponse(
        String cdProduct,
        String nmProduct,
        String dsDescription,
        double vlPrice,
        boolean isActive,
        String tpCategory
) {
    public ProductResponse(ProductModel productModel) {
        this(productModel.getCdProduct(),
                productModel.getNmProduct(),
                productModel.getDsDescription(),
                productModel.getVlPrice(),
                productModel.getActive(),
                productModel.getTpCategory().name());
    }
}
