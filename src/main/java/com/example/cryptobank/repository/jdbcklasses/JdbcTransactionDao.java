package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.repository.daointerfaces.TransactionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcTransactionDao implements TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    private final double TRANSACTION_COST_PERCENTAGE = 0.0025;

    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New TransactionDao");
    }

    @Override
    public List<Transaction> getTransactionsForUser(int userId) {
        String query = "SELECT * FROM transaction WHERE buyer = ? OR seller = ?";

        return jdbcTemplate.query(query, new Object[] { userId, userId }, new TransactionRowMapper());
    }

//    @Override
//    public List<Transaction> getSellsForAsset(String assetName) {
//        List<Transaction> tempTransactionList = new ArrayList<Integer>;
//        String query = "SELECT * FROM transaction WHERE assetSold = ?";
//
//        return tempTransactionList = jdbcTemplate.query(query, new Object[] { assetName }, new TransactionRowMapper());
//    }

    @Override
    public double calculateTransactionCost(double dollarAmount) {

        return dollarAmount * TRANSACTION_COST_PERCENTAGE;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        logger.debug("TransactionDao.saveTransaction aangeroepen voor " + transaction.getTransactionId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertTransactionStatement(transaction, connection), keyHolder);
        int newKey = keyHolder.getKey().intValue();
        transaction.setTransactionId(newKey);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM transaction WHERE transactionId = ?", id);
    }

    private PreparedStatement insertTransactionStatement(Transaction transaction, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into transaction (timestamp, seller, buyer, " +
                "assetSold, assetBought) values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, transaction.getTimestamp());
        preparedStatement.setInt(2, transaction.getSeller());
        preparedStatement.setInt(3, transaction.getBuyer());
        preparedStatement.setString(4, transaction.getAssetSold());
        preparedStatement.setString(5, transaction.getAssetBought());
        return preparedStatement;
    }

    public class TransactionIdRowMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {

            int tempTransactionId = rs.getInt("transactionId");

            return tempTransactionId;
        }
    }

    public class TransactionRowMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction transaction = new Transaction();
            transaction.setTransactionId(rs.getInt("transactionId"));
            transaction.setTimestamp(rs.getString("timestamp"));
            transaction.setSeller(rs.getInt("seller"));
            transaction.setBuyer(rs.getInt("buyer"));
            transaction.setAssetSold(rs.getString("assetSold"));
            transaction.setAssetBought(rs.getString("assetBought"));
            return transaction;
        }
    }


}
