package br.com.fiap.postechfasfood.hexagonal.domain.models;


import br.com.fiap.postechfasfood.hexagonal.domain.models.enuns.ProdutoEnum;

public class ProdutoModel {

    private String cdProduto;
    private String nmProduto;
    private String dsDescricao;
    private double vlPreco;
    private boolean snAtivo;
    private ProdutoEnum tpCategoria;
    
    public ProdutoModel() {
    }

    public ProdutoModel(String cdProduto, String nmProduto, String dsDescricao, double vlPreco, boolean snAtivo,
                        ProdutoEnum tpCategoria) {
        this.cdProduto = cdProduto;
        this.nmProduto = nmProduto;
        this.dsDescricao = dsDescricao;
        this.vlPreco = vlPreco;
        this.snAtivo = snAtivo;
        this.tpCategoria = tpCategoria;
    }

    public String getCdProduto() {
        return cdProduto;
    }

    public void setCdProduto(String cdProduto) {
        this.cdProduto = cdProduto;
    }

    public String getNmProduto() {
        return nmProduto;
    }

    public void setNmProduto(String nmProduto) {
        this.nmProduto = nmProduto;
    }

    public String getDsDescricao() {
        return dsDescricao;
    }

    public void setDsDescricao(String dsDescricao) {
        this.dsDescricao = dsDescricao;
    }

    public double getVlPreco() {
        return vlPreco;
    }

    public void setVlPreco(double vlPreco) {
        this.vlPreco = vlPreco;
    }

    public void setAtivo(boolean active) {
        this.snAtivo = active;
    }

    public boolean getAtivo() {
        return snAtivo;
    }

    public ProdutoEnum getTpCategoria() {
        return tpCategoria;
    }

    public void setTpCategoria(ProdutoEnum tpCategoria) {
        this.tpCategoria = tpCategoria;
    }

    public static class Builder {
        private String cdProduto;
        private String nmProduto;
        private String dsDescricao;
        private double vlPreco;
        private boolean snActive;
        private ProdutoEnum tpCategoria;

        public Builder setCdProduto(String cdProduto) {
            this.cdProduto = cdProduto;
            return this;
        }

        public Builder setNmProduto(String nmProduto) {
            this.nmProduto = nmProduto;
            return this;
        }

        public Builder setDsDescricao(String dsDescricao) {
            this.dsDescricao = dsDescricao;
            return this;
        }

        public Builder setVlPreco(double vlPreco) {
            this.vlPreco = vlPreco;
            return this;
        }

        public Builder setSnActive(boolean snActive) {
            this.snActive = snActive;
            return this;
        }

        public Builder setTpCategoria(ProdutoEnum tpCategoria) {
            this.tpCategoria = tpCategoria;
            return this;
        }

        public ProdutoModel build() {
            return new ProdutoModel(cdProduto, nmProduto, dsDescricao, vlPreco, snActive, tpCategoria);
        }
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "cdProduto=" + cdProduto +
                ", nmProduto='" + nmProduto + '\'' +
                ", dsDescricao='" + dsDescricao + '\'' +
                ", vlPreco=" + vlPreco +
                ", snAtivo=" + snAtivo +
                ", tpCategoria=" + tpCategoria +
                '}';
    }
}
