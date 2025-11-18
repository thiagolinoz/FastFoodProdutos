package br.com.fiap.postechfasfood.infra.db.entities;
import br.com.fiap.postechfasfood.domain.models.enuns.ProdutoEnum;
import jakarta.persistence.*;
import lombok.*;

//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_produtos")
public class ProdutoEntity {
    @Id
    @Column(name = "cd_produto", nullable = false, unique = true)
    private String cdProduto;
    @Column(name = "nm_produto", nullable = false)
    private String nmProduto;
    @Column(name = "ds_descricao", nullable = false)
    private String dsDescricao;
    @Column(name = "vl_preco", nullable = false)
    private double vlPreco;
    @Column(name = "sn_ativo")
    private boolean snAtivo;
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_categoria", nullable = false)
    private ProdutoEnum tpCategoria;

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

    public ProdutoEnum getTpCategoria() {
        return tpCategoria;
    }

    public void setTpCategoria(ProdutoEnum tpCategoria) {
        this.tpCategoria = tpCategoria;
    }

    public boolean isSnAtivo() {
        return snAtivo;
    }

    public void setSnAtivo(boolean snAtivo) {
        this.snAtivo = snAtivo;
    }

    @Override
    public String toString() {
        return "ProdutoEntity{" +
                "cdProduto=" + cdProduto +
                ", nmProduto='" + nmProduto + '\'' +
                ", dsDescricao='" + dsDescricao + '\'' +
                ", vlPreco=" + vlPreco +
                ", snAtivo=" + snAtivo +
                ", tpCategoria=" + tpCategoria +
                '}';
    }
}