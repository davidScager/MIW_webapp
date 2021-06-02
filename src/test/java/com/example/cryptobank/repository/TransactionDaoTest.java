package com.example.cryptobank.repository;

import com.example.cryptobank.repository.daointerfaces.TransactionDao;
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

    private final Logger logger = LoggerFactory.getLogger(TransactionDaoTest.class);
    private TransactionDao transactionDao;

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


}
