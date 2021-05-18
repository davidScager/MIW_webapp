package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAssetDao implements AssetDao {

    private final JdbcTemplate jdbcTemplate;

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
//        int newKey = keyHolder.getKey().intValue();
//        asset.setAssetId(newKey);
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

    @Override
    public List<Asset> getAssetOverview() {

        String query = "SELECT * FROM asset";
        List<Asset> tempAssetList = jdbcTemplate.query(query, new AssetRowMapper());
//        Asset tempAsset = jdbcTemplate.queryForObject( query, new Object[] { id }, new AssetRowMapper());

        return tempAssetList;
    }

    @Override
    public Asset getOneByName(String name) {
        String query = "SELECT * FROM asset WHERE abbreviation = ?";
        Asset tempAsset = jdbcTemplate.queryForObject( query, new Object[] { name }, new AssetRowMapper());
        return tempAsset;
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

            return asset;
        }
    }

    @Override
    public void update(Asset asset, int id) {
    }

    @Override
    public void delete(int id) {

    }
}
