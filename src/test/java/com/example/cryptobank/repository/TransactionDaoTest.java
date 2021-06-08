package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.domain.TransactionLog;
import com.example.cryptobank.repository.daointerfaces.TransactionDao;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootTest
@ActiveProfiles("test")
public class TransactionDaoTest {

    private RootRepository rootRepository;
    private TransactionDao transactionDao;
    private final Logger logger = LoggerFactory.getLogger(TransactionDaoTest.class);

    @Autowired
    public TransactionDaoTest(TransactionDao transactionDao) {
        super();
        this.transactionDao = transactionDao;
        logger.info("New instance of TransactionDaoTest created.");
    }

    @Test
    public void transaction_dao_is_available() {
        assertThat(transactionDao).isNotNull();
    }

    @Test
    public void saveTransactionTest() {
        Transaction transaction = new Transaction(3, 1, "USD", "BTC", new TransactionLog());
        transactionDao.saveTransaction(transaction);
        assertThat(transaction).isEqualTo(transaction);
    }

    @Test
    public void getTransactionsForUserTest() {

    }

    @Test
    public void calculateTransactionCostTest() {

    }

    @Test
    public void deleteTest() {

    }

}

