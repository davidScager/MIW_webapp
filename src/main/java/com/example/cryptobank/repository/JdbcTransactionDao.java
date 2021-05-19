package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class JdbcTransactionDao implements TransactionDao{

    private final JdbcTemplate jdbcTemplate;

    private final double TRANSACTION_COST_PERCENTAGE = 0.0025;

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

    @Override
    public double calculateTransactionCost(double dollarAmount) {

        return dollarAmount * TRANSACTION_COST_PERCENTAGE;
    }

    private PreparedStatement insertTransactionStatement(Transaction transaction, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into transaction (timestamp, seller, buyer, " +
                "numberOfAssets, transactionCost, assetSold, assetBought) values (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, transaction.getTimestamp());
        preparedStatement.setInt(2, transaction.getSeller());
        preparedStatement.setInt(3, transaction.getBuyer());
        preparedStatement.setDouble(4, transaction.getNumberOfAssets());
        preparedStatement.setDouble(5, transaction.getTransactionCost());
        preparedStatement.setString(6, transaction.getAssetSold());
        preparedStatement.setString(7, transaction.getAssetBought());
        return preparedStatement;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        logger.debug("TransactionDao.saveTransaction aangeroepen voor " + transaction.getTransactionId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertTransactionStatement(transaction, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        transaction.setTransactionId(newKey);
    }


}
