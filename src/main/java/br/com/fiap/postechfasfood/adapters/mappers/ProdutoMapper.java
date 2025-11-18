package br.com.fiap.postechfasfood.adapters.mappers;

import br.com.fiap.postechfasfood.adapters.web.requests.ProdutoRequest;
import br.com.fiap.postechfasfood.adapters.web.responses.ProdutoResponse;
import br.com.fiap.postechfasfood.domain.models.ProdutoModel;
import br.com.fiap.postechfasfood.infra.db.entities.ProdutoEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
    public ProdutoEntity toEntityWithCdProduto(ProdutoModel model, String cdProduto){
        ProdutoEntity e = new ProdutoEntity();
        e.setCdProduto(cdProduto);
        e.setNmProduto(model.getNmProduto());
        e.setDsDescricao(model.getDsDescricao());
        e.setSnAtivo(model.getAtivo());
        e.setVlPreco(model.getVlPreco());
        e.setTpCategoria(model.getTpCategoria());
        return e;
    }
}
