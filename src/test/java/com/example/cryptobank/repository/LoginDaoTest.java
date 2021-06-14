package com.example.cryptobank.repository;

import com.example.cryptobank.domain.login.LoginAccount;
import com.example.cryptobank.repository.daointerfaces.LoginDao;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

/**
 * @author David
 */

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginDaoTest {
    private final Logger logger = LoggerFactory.getLogger(LoginDaoTest.class);
    private final LoginDao testLoginDao;
    private final JdbcTemplate jdbcTemplate;
    private final String USERNAME = "Somebody";
    private final String HASH = "hash";

    @Autowired
    public LoginDaoTest(LoginDao loginDao, JdbcTemplate jdbcTemplate) {
        super();
        testLoginDao = loginDao;
        this.jdbcTemplate = jdbcTemplate;
        logger.info("New LoginDaoTest Started");
    }

    @Test
    @Order(1)
    void daoNotNull() {
        assertThat(testLoginDao).isNotNull();
    }

    @Test
    @Order(2)
    void createdLoginAccountExists() {
        testLoginDao.create(USERNAME, HASH);
        String sql = "select exists(select * from loginaccount where username= 'Somebody')";
        try {
            boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class);
            assertThat(exists).isTrue();
        } catch (EmptyResultDataAccessException error) {
            failBecauseExceptionWasNotThrown(EmptyResultDataAccessException.class);
        }
    }

    @Test
    @Order(3)
    void getLoginAccount() {
        LoginAccount loginAccount = testLoginDao.get(USERNAME);
        assertThat(loginAccount).isNotNull();
        assertThat(loginAccount.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    @Order(4)
    void loginAccountUpdated(){
        String actualHash = "notAHash";
        String actualToken = "notAToken";
        testLoginDao.update(USERNAME, actualHash, actualToken);
        LoginAccount loginAccount = testLoginDao.get(USERNAME);
        assertThat(loginAccount.getHash()).isEqualTo(actualHash);
        assertThat(loginAccount.getToken()).isEqualTo(actualToken);
    }
}