package br.com.fiap.postechfasfood.externals;

import br.com.fiap.postechfasfood.entities.ProdutoVO;
import br.com.fiap.postechfasfood.externals.mappers.ProdutoRowMapper;
import br.com.fiap.postechfasfood.interfaces.ProdutoRepositoryInterface;
import br.com.fiap.postechfasfood.types.TipoCategoriaProdutoEnum;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProdutoRepository implements ProdutoRepositoryInterface {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String SELECT_TB_PRODUTOS = "SELECT cd_produto, nm_produto, ds_descricao, vl_preco, sn_ativo, tp_categoria FROM tb_produtos";

    public ProdutoRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void cadastrar(ProdutoVO produto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cdProduct", produto.getCdProduto());
        params.addValue("nmProduct", produto.getNmProduto());
        params.addValue("dsDescription", produto.getDsDescricao());
        params.addValue("vlPrice", produto.getVlPreco());
        params.addValue("isActive", produto.getSnAtivo());
        params.addValue("tpCategory", produto.getTpCategoria().name());

        String sql = "INSERT INTO tb_produtos (cd_produto, nm_produto, ds_descricao, vl_preco, sn_ativo, tp_categoria) " +
                "VALUES (:cdProduct, :nmProduct, :dsDescription, :vlPrice, :isActive, :tpCategory)";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void atualizar(String cdProduto, ProdutoVO produto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cdProduct", cdProduto);
        params.addValue("nmProduct", produto.getNmProduto());
        params.addValue("dsDescription", produto.getDsDescricao());
        params.addValue("vlPrice", produto.getVlPreco());
        params.addValue("isActive", produto.getSnAtivo());
        params.addValue("tpCategory", produto.getTpCategoria().name());

        String sql = "UPDATE tb_produtos SET " +
                "nm_produto = :cdProduct, " +
                "ds_descricao = :dsDescription, " +
                "vl_preco = :vlPrice, " +
                "sn_ativo = :isActive, " +
                "tp_categoria = :tpCategory " +
                "WHERE cd_produto = :cdProduct";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void desativar(String cdProduto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cdProduct", cdProduto);
        params.addValue("isActive", false);

        String sql = "UPDATE tb_produtos SET " +
                "sn_ativo = :isActive " +
                "WHERE cd_produto = :cdProduct";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public void ativar(String cdProduto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cdProduct", cdProduto);
        params.addValue("isActive", true);

        String sql = "UPDATE tb_produtos SET " +
                "sn_ativo = :isActive " +
                "WHERE cd_produto = :cdProduct";
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<ProdutoVO> listar() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("isActive", true);

        String sql = SELECT_TB_PRODUTOS + " WHERE sn_ativo = :isActive";
        return namedParameterJdbcTemplate.query(sql, params, new ProdutoRowMapper());
    }

    @Override
    public List<ProdutoVO> listar(TipoCategoriaProdutoEnum tpCategoria) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("isActive", true);
        params.addValue("tpCategory", tpCategoria.name());

        String sql = SELECT_TB_PRODUTOS + " WHERE sn_ativo = :isActive AND tp_categoria = :tpCategory";
        return namedParameterJdbcTemplate.query(sql, params, new ProdutoRowMapper());
    }

    @Override
    public ProdutoVO buscarPorCdProduto(String cdProduto) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cdProduct", cdProduto);

        String sql = SELECT_TB_PRODUTOS + " WHERE cd_produto = :cdProduct";
        return namedParameterJdbcTemplate.queryForObject(sql, params, new ProdutoRowMapper());
    }
}
