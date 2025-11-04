package br.com.fiap.postechfasfood.hexagonal.adapters.commons.mappers;

import br.com.fiap.postechfasfood.hexagonal.adapters.web.requests.ProductRequest;
import br.com.fiap.postechfasfood.hexagonal.domain.models.ProductModel;
import br.com.fiap.postechfasfood.hexagonal.domain.models.enuns.ProductEnum;
import br.com.fiap.postechfasfood.hexagonal.infra.db.entities.ProductEntity;

public class ProductMapper {
    public ProductModel toModel(ProductRequest request){
        ProductModel m = new ProductModel();
        m.setNmProduct(request.nmProduct());
        m.setDsDescription(request.dsDescription());
        m.setActive(request.isActive());
        m.setVlPrice(request.vlPrice());
        m.setTpCategory(ProductEnum.valueOf(request.tpCategory()));
        return m;
    }
    public ProductModel toModel(ProductEntity entity){
        ProductModel m = new ProductModel();
        m.setCdProduct(entity.getCdProduct());
        m.setNmProduct(entity.getNmProduct());
        m.setDsDescription(entity.getDsDescription());
        m.setActive(entity.isActive());
        m.setVlPrice(entity.getVlPrice());
        m.setTpCategory(entity.getTpCategory());
        return m;
    }
    public ProductEntity toEntity(ProductModel model){
        ProductEntity e = new ProductEntity();
        e.setNmProduct(model.getNmProduct());
        e.setDsDescription(model.getDsDescription());
        e.setActive(model.getActive());
        e.setVlPrice(model.getVlPrice());
        e.setTpCategory(model.getTpCategory());
        return e;
    }
}
