package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAssetPortfolioDao implements AssetPortfolioDao {

    private final JdbcTemplate jdbcTemplate;
    private JdbcAssetDao jdbcAssetDao;

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

    public class AssetPortfolioRowMapper implements RowMapper<String> {
        @Override
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {

            String tempName = rs.getString(1);

            return tempName;
        }
    }

    public class AssetPortfolioAmountRowMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {

            int amount = rs.getInt("amount");

            return amount;
        }
    }

    @Override
    public double getAmountByAssetName(String name, int portfolioId) {
        String query = "SELECT amount FROM assetportfolio WHERE portfolioId = ? AND assetName = ?";
        double tempAmount = Double.parseDouble(jdbcTemplate.queryForObject(query, new Object[] { portfolioId, name }, new AssetPortfolioRowMapper()));
        return tempAmount;
    }

    @Override
    public void create(Object o) {

    }

    @Override
    public void update(Object o, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
