package br.com.fiap.postechfasfood.hexagonal.domain.models;


import br.com.fiap.postechfasfood.hexagonal.domain.models.enuns.ProductEnum;

public class ProductModel {

    private String cdProduct;
    private String nmProduct;
    private String dsDescription;
    private double vlPrice;
    private boolean isActive;
    private ProductEnum tpCategory;
    
    public ProductModel() {
    }

    public ProductModel(String cdProduct, String nmProduct, String dsDescription, double vlPrice, boolean isActive,
                        ProductEnum tpCategory) {
        this.cdProduct = cdProduct;
        this.nmProduct = nmProduct;
        this.dsDescription = dsDescription;
        this.vlPrice = vlPrice;
        this.isActive = isActive;
        this.tpCategory = tpCategory;
    }

    public String getCdProduct() {
        return cdProduct;
    }

    public void setCdProduct(String cdProduct) {
        this.cdProduct = cdProduct;
    }

    public String getNmProduct() {
        return nmProduct;
    }

    public void setNmProduct(String nmProduct) {
        this.nmProduct = nmProduct;
    }

    public String getDsDescription() {
        return dsDescription;
    }

    public void setDsDescription(String dsDescription) {
        this.dsDescription = dsDescription;
    }

    public double getVlPrice() {
        return vlPrice;
    }

    public void setVlPrice(double vlPrice) {
        this.vlPrice = vlPrice;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean getActive() {
        return isActive;
    }

    public ProductEnum getTpCategory() {
        return tpCategory;
    }

    public void setTpCategory(ProductEnum tpCategory) {
        this.tpCategory = tpCategory;
    }

    public static class Builder {
        private String cdProduct;
        private String nmProduct;
        private String dsDescription;
        private double vlPrice;
        private boolean snActive;
        private ProductEnum tpCategory;

        public Builder setCdProduct(String cdProduct) {
            this.cdProduct = cdProduct;
            return this;
        }

        public Builder setNmProduct(String nmProduct) {
            this.nmProduct = nmProduct;
            return this;
        }

        public Builder setDsDescription(String dsDescription) {
            this.dsDescription = dsDescription;
            return this;
        }

        public Builder setVlPrice(double vlPrice) {
            this.vlPrice = vlPrice;
            return this;
        }

        public Builder setSnActive(boolean snActive) {
            this.snActive = snActive;
            return this;
        }

        public Builder setTpCategory(ProductEnum tpCategory) {
            this.tpCategory = tpCategory;
            return this;
        }

        public ProductModel build() {
            return new ProductModel(cdProduct, nmProduct, dsDescription, vlPrice, snActive, tpCategory);
        }
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "cdProduct=" + cdProduct +
                ", nmProduct='" + nmProduct + '\'' +
                ", dsDescription='" + dsDescription + '\'' +
                ", vlPrice=" + vlPrice +
                ", isActive=" + isActive +
                ", tpCategory=" + tpCategory +
                '}';
    }
}
