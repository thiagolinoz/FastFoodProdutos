package br.com.fiap.postechfasfood.hexagonal.adapters.repositories;

import br.com.fiap.postechfasfood.hexagonal.adapters.commons.mappers.ProductMapper;
import br.com.fiap.postechfasfood.hexagonal.domain.models.ProductModel;
import br.com.fiap.postechfasfood.hexagonal.domain.ports.out.ProductRepositoryPort;
import br.com.fiap.postechfasfood.hexagonal.infra.db.entities.ProductEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ProductRepository implements ProductRepositoryPort {

    private final ProductMapper mapper;

    public ProductRepository(ProductMapper mapper) {
        this.mapper = mapper;
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public ProductModel create(ProductModel product) {
        ProductEntity productEntity = mapper.toEntity(product);
        productEntity.setCdProduct(String.valueOf(UUID.randomUUID()));
        em.merge(productEntity);
        return mapper.toModel(productEntity);
    }
}
