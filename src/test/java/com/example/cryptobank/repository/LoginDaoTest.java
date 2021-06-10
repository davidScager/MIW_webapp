package com.example.cryptobank.repository;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.repository.daointerfaces.LoginDao;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David
 */

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginDaoTest {
    private final Logger logger = LoggerFactory.getLogger(LoginDaoTest.class);
    private final LoginDao testLoginDao;
    private final String USERNAME = "Somebody";
    private final String HASH = "hash";

    @Autowired
    public LoginDaoTest(LoginDao loginDao) {
        testLoginDao = loginDao;
        logger.info("New LoginDaoTest Started");
    }

    @Test
    @Order(1)
    void daoNotNull() {
        assertThat(testLoginDao).isNotNull();
    }
}