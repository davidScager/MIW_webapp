package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.service.CurrencyCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAssetDao implements AssetDao {

    private final JdbcTemplate jdbcTemplate;

    private final double ADJUSTMENT_UNIT = 10000;
    private final double ADJUSTMENT_PERCENTAGE = 0.01;

    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);

    @Autowired
    public JdbcAssetDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New AssetDao");
    }

    @Override
    public void create(Asset asset) {
        logger.debug("AssetDao.save aangeroepen voor " + asset.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> insertMemberStatement(asset, connection), keyHolder);
    }

    @Override
    public List<Asset> getAssetOverview() {
        String query = "SELECT * FROM asset";
        return jdbcTemplate.query(query, new AssetRowMapper());
    }

    @Override
    public Asset getOneByName(String name) {
        String query = "SELECT * FROM asset WHERE abbreviation = ?";

        return jdbcTemplate.queryForObject( query, new Object[] { name }, new AssetRowMapper());
    }

    @Override
    public Asset getOneBySymbol(String symbol) {
        String query = "SELECT * FROM asset WHERE abbreviation = ?";
        Asset tempAsset = jdbcTemplate.queryForObject( query, new Object[] { symbol }, new AssetRowMapper());

        return tempAsset;
    }

    @Override
    public void update(Asset asset) {
        logger.debug("JdbcAssetDao.update aan geroepen voor " + asset.getName());
        String sql = "UPDATE asset set name = ?, abbreviation = ?, description = ?, valueInUsd = ?, adjustmentFactor = ?, " +
                "valueYesterday = ?, valueLastWeek = ?, valueLastMonth = ? where name = ?";
        jdbcTemplate.update(sql, asset.getName(), asset.getAbbreviation(),
                asset.getDescription(), asset.getValueInUsd(), asset.getAdjustmentFactor(), asset.getValueYesterday(),
                asset.getValueLastWeek(), asset.getValueLastMonth(), asset.getName());
    }

    @Override
    public Asset updateAssetByApi(String symbol)  {
        Asset asset = getOneBySymbol(symbol);
        CurrencyCollector currencyCollector = new CurrencyCollector();
        try {
            currencyCollector.makeRequestPerAsset(jdbcTemplate, asset );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return asset;
    }

    @Override
    public List<Asset> updateAssetsByApi()  {
        for (Asset asset : getAssetOverview()){
            System.out.println("asset.getAbbreviation() "+asset.getAbbreviation());
            updateAssetByApi(asset.getAbbreviation());
        }
        return getAssetOverview();
    }

    @Override
    public void updateAdjustmentFactor(Asset asset, double dollarAmount, boolean buyFromBank, boolean sellToBank) {
        double tempAdjustmentFactor = asset.getAdjustmentFactor();
        if(buyFromBank) {
            tempAdjustmentFactor = tempAdjustmentFactor + (dollarAmount / ADJUSTMENT_UNIT) * ADJUSTMENT_PERCENTAGE;
        } else if (sellToBank){
            tempAdjustmentFactor = tempAdjustmentFactor - (dollarAmount / ADJUSTMENT_UNIT) * ADJUSTMENT_PERCENTAGE;
        }
        asset.setAdjustmentFactor(tempAdjustmentFactor);
        update(asset);
    }

    @Override
    public void delete(int id) {

    }

    private PreparedStatement insertMemberStatement(Asset asset, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "insert into asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, ValueLastMonth) values (?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, asset.getName());
        ps.setString(2, asset.getAbbreviation());
        ps.setString(3, asset.getDescription());
        ps.setDouble(4, asset.getValueInUsd());
        ps.setDouble(5, asset.getAdjustmentFactor());
        ps.setDouble(6, asset.getValueYesterday());
        ps.setDouble(7, asset.getValueLastWeek());
        ps.setDouble(8, asset.getValueLastMonth());
        return ps;
    }

    public class AssetRowMapper implements RowMapper<Asset> {
        @Override
        public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
            Asset asset = new Asset();

            asset.setName(rs.getString("name"));
            asset.setAbbreviation(rs.getString("abbreviation"));
            asset.setDescription(rs.getString("description"));
            asset.setValueInUsd(rs.getDouble("valueInUsd"));
            asset.setAdjustmentFactor(rs.getDouble("adjustmentFactor"));
            asset.setValueYesterday(rs.getDouble("valueYesterday"));
            asset.setValueLastWeek(rs.getDouble("valueLastWeek"));
            asset.setValueLastMonth(rs.getDouble("valueLastMonth"));
            return asset;
        }
    }


}
