package br.com.fiap.postechfasfood.hexagonal.adapters.commons.mappers;

import br.com.fiap.postechfasfood.hexagonal.adapters.web.requests.ProdutoRequest;
import br.com.fiap.postechfasfood.hexagonal.adapters.web.responses.ProdutoResponse;
import br.com.fiap.postechfasfood.hexagonal.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.hexagonal.domain.models.enuns.ProdutoEnum;
import br.com.fiap.postechfasfood.hexagonal.infra.db.entities.ProdutoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoMapper {
    public ProdutoModel toModel(ProdutoRequest request){
        ProdutoModel m = new ProdutoModel();
        m.setNmProduto(request.nmProduto());
        m.setDsDescricao(request.dsDescricao());
        m.setAtivo(request.snAtivo());
        m.setVlPreco(request.vlPreco());
        m.setTpCategoria(request.tpCategoria());
        return m;
    }
    public ProdutoModel toModel(ProdutoEntity entity){
        ProdutoModel m = new ProdutoModel();
        m.setCdProduto(entity.getCdProduto());
        m.setNmProduto(entity.getNmProduto());
        m.setDsDescricao(entity.getDsDescricao());
        m.setAtivo(entity.isSnAtivo());
        m.setVlPreco(entity.getVlPreco());
        m.setTpCategoria(entity.getTpCategoria());
        return m;
    }
    public List<ProdutoResponse> toResoponse(List<ProdutoModel> models){
        List<ProdutoResponse> r = new ArrayList<>();
        models.stream().forEach(produtoModel -> r.add(new ProdutoResponse(produtoModel)));
        return r;
    }
    public ProdutoEntity toEntity(ProdutoModel model){
        ProdutoEntity e = new ProdutoEntity();
        e.setNmProduto(model.getNmProduto());
        e.setDsDescricao(model.getDsDescricao());
        e.setSnAtivo(model.getAtivo());
        e.setVlPreco(model.getVlPreco());
        e.setTpCategoria(model.getTpCategoria());
        return e;
    }
}
