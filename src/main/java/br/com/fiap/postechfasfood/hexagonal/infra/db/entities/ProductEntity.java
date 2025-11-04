package br.com.fiap.postechfasfood.hexagonal.infra.db.entities;
import br.com.fiap.postechfasfood.hexagonal.domain.models.enuns.ProductEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_produtos")
public class ProductEntity {
    @Id
    @Column(name = "cd_produto", nullable = false, unique = true)
    private String cdProduct;
    @Column(name = "nm_produto", nullable = false)
    private String nmProduct;
    @Column(name = "ds_descricao", nullable = false)
    private String dsDescription;
    @Column(name = "vl_preco", nullable = false)
    private double vlPrice;
    @Column(name = "sn_ativo")
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_categoria", nullable = false)
    private ProductEnum tpCategory;

    @Override
    public String toString() {
        return "ProdutoEntity{" +
                "cdProduct=" + cdProduct +
                ", nmProduct='" + nmProduct + '\'' +
                ", dsDescription='" + dsDescription + '\'' +
                ", vlPrice=" + vlPrice +
                ", isActive=" + isActive +
                ", tpCategory=" + tpCategory +
                '}';
    }
}