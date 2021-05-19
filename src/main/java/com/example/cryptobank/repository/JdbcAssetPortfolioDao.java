package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.AssetPortfolio;
import com.example.cryptobank.domain.Portfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class JdbcAssetPortfolioDao implements AssetPortfolioDao {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcAssetDao jdbcAssetDao;

    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);

    public JdbcAssetPortfolioDao(JdbcTemplate jdbcTemplate, JdbcAssetDao jdbcAssetDao) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcAssetDao = jdbcAssetDao;
        logger.info("New AssetPortfolioDao");
    }

    @Override
    public List<Asset> getAssetOverview(int portfolioId) {

        String query = "SELECT * FROM assetportfolio WHERE portfolioId = ?";
        List<String> tempAssetNameList = jdbcTemplate.query(query, new Object[] { portfolioId },new AssetPortfolioRowMapper());
        List<Asset> tempAssetList = new ArrayList<>();
        for (String string: tempAssetNameList) {
            Asset asset = jdbcAssetDao.getOneByName(string);
            tempAssetList.add(asset);
        }

        return tempAssetList;
    }

    @Override
    public List<AssetPortfolio> getAssetPortfolioOverview(int portfolioId) {
        return null;
    }

    @Override
    public Map<Asset, Double> getAssetOvervieuwWithAmmount(int portfolioId){
        Map<Asset, Double> resultMap = new HashMap<>();
        String query = "SELECT * FROM assetportfolio WHERE portfolioId = ?";
        List<AssetPortfolio> tempList = jdbcTemplate.query(query, new AssetPortfolioAmountRowMapper(), portfolioId);
        tempList.forEach(assetPortfolio -> resultMap.put(jdbcAssetDao.getOneByName(assetPortfolio.getAssetName()), assetPortfolio.getAmount()));
        return resultMap;
    }

    public class AssetPortfolioRowMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {

            return rs.getString(1);
        }
    }

    public class AssetPortfolioAmountRowMapper implements RowMapper<AssetPortfolio> {
        @Override
        public AssetPortfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
            String assetName = rs.getString("assetName");
            int portfolioId = rs.getInt("portfolioId");
            double amount = rs.getInt("amount");
            AssetPortfolio assetPortfolio = new AssetPortfolio(assetName, (double) portfolioId, amount);
            return assetPortfolio;
        }
    }

    @Override
    public double getAmountByAssetName(String name, int portfolioId) {
        String query = "SELECT amount FROM assetportfolio WHERE portfolioId = ? AND assetName = ?";

        return Double.parseDouble(jdbcTemplate.queryForObject(query, new Object[] { portfolioId, name }, new AssetPortfolioRowMapper()));
    }

    @Override
    public void update(AssetPortfolio assetPortfolio) {
        String SQL = "update assetportfolio set assetName = ?,portfolioId = ?, amount = ? where assetName = ? and portfolioId = ? ";
        jdbcTemplate.update(SQL,assetPortfolio.getAssetName(),
                assetPortfolio.getPortfolioId(),
                assetPortfolio.getAmount(),
                assetPortfolio.getAssetName(),
                assetPortfolio.getPortfolioId());

        //System.out.println("Updated Record with name = " + asset.getName());
        return ;
    }

    @Override
    public void update(Asset asset, Portfolio portfolio, double amount) {
        String sql = "insert into assetportfolio(assetname, portfolioid, amount) values (?,?,?)";
        jdbcTemplate.update(sql, asset.getAbbreviation(), portfolio.getPortfolioId(), amount);
    }

    @Override
    public void create(Object o) {

    }

    @Override
    public void create(AssetPortfolio assetPortfolio) {
        jdbcTemplate.update(connection -> insertStatement(assetPortfolio, connection));
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(AssetPortfolio assetPortfolio) {

    }

    private PreparedStatement insertStatement(AssetPortfolio assetPortfolio, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into assetportfolio (assetName, portfolioId, amount) values (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, assetPortfolio.getAssetName());
        ps.setDouble(2, assetPortfolio.getPortfolioId());
        ps.setDouble(3, assetPortfolio.getAmount());
        return ps;
    }


}
