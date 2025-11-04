package br.com.fiap.postechfasfood.hexagonal.adapters.web.requests;


public record ProductRequest(
        String nmProduct,
        String dsDescription,
        double vlPrice,
        boolean isActive,
        String tpCategory
) {
}