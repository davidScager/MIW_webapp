package com.example.cryptobank.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcTransactionDao implements TransactionDao{

    private final JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New TransactionDao");
    }

    @Override
    public int getTransactionIdMostRecentTrade(String assetName) {
        String query = "SELECT transactionId FROM transaction WHERE assetBought = ?";
        int transactionId = jdbcTemplate.queryForObject(query, new Object[] { assetName }, new TransactionIdRowMapper());
        return transactionId;
    }

    public class TransactionIdRowMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {

            int tempTransactionId = rs.getInt("transactionId");

            return tempTransactionId;
        }
    }
}
