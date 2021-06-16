package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetPortfolio;
import com.example.cryptobank.domain.asset.AssetPortfolioView;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
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
        List<String> tempAssetNameList = jdbcTemplate.query(query, new Object[] { portfolioId }, new AssetPortfolioRowMapper());
        List<Asset> tempAssetList = new ArrayList<>();
        for (String string: tempAssetNameList) {
            tempAssetList.add(jdbcAssetDao.getOneByName(string));
        }
        return tempAssetList;
    }

    @Override
    public Map<Asset, Double> getAssetOverviewWithAmount(int portfolioId) {
        Map<Asset, Double> resultMap = new HashMap<>();
        String query = "SELECT * FROM assetportfolio WHERE portfolioId = ?";
        List<AssetPortfolio> tempList = jdbcTemplate.query(query, new AssetPortfolioAmountRowMapper(), portfolioId);
        tempList.forEach(assetPortfolio -> resultMap.put(jdbcAssetDao.getOneByName(assetPortfolio.getAssetName()), assetPortfolio.getAmount()));
        return resultMap;
    }


    public static class AssetPortfolioRowMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {

            return rs.getString(1);
        }
    }

    public static class AssetPortfolioAmountRowMapper implements RowMapper<AssetPortfolio> {
        @Override
        public AssetPortfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
//            System.out.println("dit is de rs: " + rs);
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int numberOfColumns = rsmd.getColumnCount();
//            System.out.println(numberOfColumns);
//            System.out.println(rs.getString(1));
//            System.out.println(rs.getInt(2));
//            System.out.println(rs.getDouble(3));
            String assetName = rs.getString("assetName");
            int portfolioId = rs.getInt("portfolioId");
            double amount = rs.getDouble("amount");
            double availableForSale = rs.getDouble("forSale");
            AssetPortfolio assetPortfolio = new AssetPortfolio(assetName, portfolioId, amount, availableForSale);
            return assetPortfolio;
        }
    }

    @Override
    public double getAmountByAssetName(String name, int portfolioId) {
        String query = "SELECT * FROM assetportfolio WHERE portfolioId = ? AND assetName = ?";
        return Objects.requireNonNull(jdbcTemplate.queryForObject(query, new Object[]{portfolioId, name}, new AssetPortfolioAmountRowMapper())).getAmount();
    }


    //Als dit weer wordt gebruikt, moet het worden aangepast aan het nieuwe ERD
    //tijdelijk uigecomment omdat er twijfel is of deze nog geburuikt kan worden
    /*@Override
    public void update(AssetPortfolio assetPortfolio) {
        String SQL = "update assetportfolio set assetName = ?,portfolioId = ?, amount = ? where assetName = ? and portfolioId = ? ";
        jdbcTemplate.update(SQL,assetPortfolio.getAssetName(),
                assetPortfolio.getPortfolioId(),
                assetPortfolio.getAmount(),
                assetPortfolio.getAssetName(),
                assetPortfolio.getPortfolioId());

        //System.out.println("Updated Record with name = " + asset.getName());
        return ;
    }*/

    @Override
    public void update(Asset asset, Portfolio portfolio, double amount) {
        String sql = "update assetportfolio set amount = ? where portfolioId = ? and assetName = ?";
        jdbcTemplate.update(sql, amount, portfolio.getPortfolioId(), asset.getAbbreviation());
    }

    @Override
    public void updateAssetsForSale(String Symbol, int portfolioId, double forSale) {
        String sql = "update assetportfolio set forSale = ? where portfolioId = ? and assetName = ?";
        jdbcTemplate.update(sql, forSale, portfolioId, Symbol);
    }

    @Override
    public List<AssetPortfolio> getAssetPortfolioByAbbrevation(String symbol) {
        // Order by portfolio DESC bank als laatste nodig transactie
        String sql = "select * from assetportfolio  where assetName = ? ORDER BY portfolioId DESC";
        List<AssetPortfolio> overView = jdbcTemplate.query(sql, new AssetPortfolioAmountRowMapper(), symbol);
        return overView;
    }


    public List<Map<String,Object>> getAssetsForSale(String symbol) {
        // Order by portfolio DESC bank als laatste nodig transactie
        String sql = "select portfolioId,forSale from assetportfolio  where assetName = ? ORDER BY portfolioId DESC";
        List<Map<String,Object>> overView = jdbcTemplate.query(sql, new AssetsForSaleRowMapper(), symbol);
        return overView;
    }

    public class AssetsForSaleRowMapper implements RowMapper<Map<String,Object>> {
        @Override
        public Map<String,Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String,Object> map =  new LinkedHashMap<>();
            map.put("portfolioId",rs.getInt("portfolioId"));
            map.put("forSale",rs.getDouble("forSale"));
            return map;
        }
    }

    //maak methode UpdateAvailableForSale

    @Override
    public void create(AssetPortfolio assetPortfolio) {
        jdbcTemplate.update(connection -> insertStatement(assetPortfolio, connection));
    }

    @Override
    public void delete(int id) {
        //TODO
    }

    @Override
    public List<AssetPortfolioView> getOverviewWithAmount(int portfolioId) {
        String query = "SELECT AP.assetName, portfolioId, amount, forSale, description, valueInUsd FROM AssetPortfolio AP LEFT JOIN Asset A on AP.assetName = A.abbreviation WHERE portfolioId = ?";
        List<AssetPortfolioView> overView = jdbcTemplate.query(query, new AssetPortfolioViewRowMapper(), portfolioId);
        return overView;
    }

    public class AssetPortfolioViewRowMapper implements RowMapper<AssetPortfolioView> {
        @Override
        public AssetPortfolioView mapRow(ResultSet rs, int rowNum) throws SQLException {
            String assetName = rs.getString("assetName");
            int portfolioId = rs.getInt("portfolioId");
            double amount = rs.getDouble("amount");
            double forSale = rs.getDouble("forSale");
            double amountUSD = rs.getDouble("amount");
            String assetDescription = rs.getString("description");
            AssetPortfolioView assetPortfolio = new AssetPortfolioView(assetName, portfolioId, amount, forSale, amountUSD, assetDescription);
            return assetPortfolio;
        }
    }


    private PreparedStatement insertStatement(AssetPortfolio assetPortfolio, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into assetportfolio (assetName, portfolioId, amount, forSale) values (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, assetPortfolio.getAssetName());
        ps.setDouble(2, assetPortfolio.getPortfolioId());
        ps.setDouble(3, assetPortfolio.getAmount());
        ps.setDouble(4, assetPortfolio.getAvailableForSale());
        return ps;
    }


}