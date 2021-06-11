package com.example.cryptobank.repository;

import com.example.cryptobank.domain.transaction.Transaction;
import com.example.cryptobank.domain.transaction.TransactionLog;
import com.example.cryptobank.repository.daointerfaces.TransactionDao;
import org.junit.jupiter.api.BeforeAll;
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

    private TransactionDao transactionDao;
    private static Transaction transaction;
    private final Logger logger = LoggerFactory.getLogger(TransactionDaoTest.class);

    @Autowired
    public TransactionDaoTest(TransactionDao transactionDao) {
        super();
        this.transactionDao = transactionDao;
        logger.info("New instance of TransactionDaoTest created.");
    }

    @BeforeAll
    public static void setup() {
        transaction = new Transaction(3, 1, "USD", "BTC", new TransactionLog());
        transaction.setTransactionId(2013);
    }

    @Test
    public void transaction_dao_is_available() {
        assertThat(transactionDao).isNotNull();
    }

    @Test
    public void saveTransactionTest() {
        transaction.setTransactionId(2013);
        transactionDao.saveTransaction(transaction);
    }

    @Test
    public void getTransactionsForUserTest() {
//        Transaction transactionActual = transactionDao.getTransactionsForUser(3);
//        assertThat(transactionActual).isEqualTo(transaction);
    }

    @Test
    public void calculateTransactionCostTest() {
        transaction.setTransactionId(2013);
        transactionDao.calculateTransactionCost(30.0);
    }

    @Test
    public void deleteTest() {
        transaction.setTransactionId(2013);
        transactionDao.delete(2013);
    }
}
