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
    private Logger logger = LoggerFactory.getLogger(LoginDaoTest.class);
    private LoginDao loginDao;
    private final String USERNAME = "Somebody";
    private final String HASH = "hash";
    private String testHash;

    @Autowired
    public LoginDaoTest(LoginDao loginDao){
        this.loginDao = loginDao;
        logger.info("New LoginDaoTest Started");
        testHash = HASH;
    }

    @Test @Order(1)
    void daoNotNull(){
        assertThat(loginDao).isNotNull();
    }

    @Test @Order(2)
    void createTest1() {
        loginDao.create(USERNAME, testHash);
    }

    @Test @Order(3)
    void get() {
        LoginAccount expectedLoginAccount = new LoginAccount(USERNAME, HASH, null);
        LoginAccount actualLoginAccount = loginDao.get(USERNAME).orElse(null);
        assertEquals(expectedLoginAccount, actualLoginAccount);
    }

    @Test @Order(4)
    void update1() {
        String expectedUsername = "Somebody";
        loginDao.update(expectedUsername, testHash, null);
        String actualUsername = loginDao.get(expectedUsername).get().getUsername();
        assertEquals(expectedUsername, actualUsername);
    }

    @Test @Order(5)
    void delete() {
        loginDao.delete("Somebody");
        assert loginDao.get("Somebody").orElse(null) == null;
    }

    @Test @Order(6)
    void update2(){
        assert loginDao.get(USERNAME).orElse(null) == null;
    }
}