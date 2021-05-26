package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.repository.daointerfaces.LogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class JdbcLogDao implements LogDao {

    private final JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(JdbcAssetDao.class);

    public JdbcLogDao(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New LogDao");
    }

    private PreparedStatement insertLogStatement(Transaction transaction, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into log (transactionId, soldAssetTransactionRate, boughtAssetTransactionRate, " +
                "soldAssetAdjustmentFactor, boughtAssetAdjustmentFactor, amount) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, transaction.getTransactionId());
        preparedStatement.setDouble(2, transaction.getTransactionLog().getSoldAssetTransactionRate());
        preparedStatement.setDouble(3, transaction.getTransactionLog().getBoughtAssetTransactionRate());
        preparedStatement.setDouble(4, transaction.getTransactionLog().getSoldAssetAdjustmentFactor());
        preparedStatement.setDouble(5, transaction.getTransactionLog().getBoughtAssetAdjustmentFactor());
        preparedStatement.setDouble(6, transaction.getTransactionLog().getNumberOfAssetsSold());
        return preparedStatement;
    }

    @Override
    public void saveLog(Transaction transaction) {
//        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> insertLogStatement(transaction, connection));
//        int newKey = keyHolder.getKey().intValue();
//        transaction.setTransactionId(newKey);
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
