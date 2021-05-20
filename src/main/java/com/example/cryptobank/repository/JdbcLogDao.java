package com.example.cryptobank.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcLogDao implements LogDao {

    private final JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);

    public JdbcLogDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New LogDao");
    }

    @Override
    public double getTradedRateByTransactionId(int transactionId) {
        String query = "SELECT boughtAssetTransactionRate FROM log WHERE transactionId = ?";
        double tradedRate = jdbcTemplate.queryForObject(query, new Object[] { transactionId }, new TradedRateRowMapper());
        return tradedRate;
    }

    public class TradedRateRowMapper implements RowMapper<Double> {
        @Override
        public Double mapRow(ResultSet rs, int rowNum) throws SQLException {

            double tempTradedRate = rs.getInt("boughtAssetTransactionRate");

            return tempTradedRate;
        }
    }
}
