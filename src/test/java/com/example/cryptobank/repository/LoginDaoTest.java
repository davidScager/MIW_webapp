package com.example.cryptobank.repository;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.security.HashAndSalt;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("testdb")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginDaoTest {
    private Logger logger = LoggerFactory.getLogger(LoginDaoTest.class);
    private LoginDao loginDao;
    private final String USERNAME = "Somebody";
    private final String HASH = "hash";
    private final String SALT = "salt";
    private HashAndSalt testHashAndSalt;

    @Autowired
    public LoginDaoTest(LoginDao loginDao){
        this.loginDao = loginDao;
        logger.info("New LoginDaoTest Started");
        testHashAndSalt = new HashAndSalt(HASH, SALT);
    }

    @Test @Order(1)
    void daoNotNull(){
        assertThat(loginDao).isNotNull();
    }

    @Test @Order(2)
    void createTest1() {
        loginDao.create(USERNAME, testHashAndSalt);
    }

    @Test @Order(3)
    void get() {
        LoginAccount expectedLoginAccount = new LoginAccount(USERNAME, HASH, SALT, null);
        LoginAccount actualLoginAccount = loginDao.get(USERNAME).orElse(null);
        assertEquals(expectedLoginAccount, actualLoginAccount);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}