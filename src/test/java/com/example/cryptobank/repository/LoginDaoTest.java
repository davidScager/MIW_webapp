package com.example.cryptobank.repository;

import com.example.cryptobank.domain.login.LoginAccount;
import com.example.cryptobank.repository.daointerfaces.LoginDao;
import org.junit.jupiter.api.*;
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
    private final LoginDao testLoginDao;
    private final JdbcTemplate jdbcTemplate;
    private final String USERNAME = "Somebody";
    private final String HASH = "hash";

    @Autowired
    public LoginDaoTest(LoginDao loginDao, JdbcTemplate jdbcTemplate) {
        super();
        testLoginDao = loginDao;
        this.jdbcTemplate = jdbcTemplate;
        LoggerFactory.getLogger(LoginDaoTest.class).info("New LoginDaoTest Started");
    }

    @Test
    @Order(1)
    void dao_not_null() {
        assertThat(testLoginDao).isNotNull();
    }

    @Test
    @Order(2)
    void created_loginAccount_exists() {
        testLoginDao.create(USERNAME, HASH);
        String sql = "select exists(select * from loginaccount where username= 'Somebody')";
        try {
            boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class);
            assertThat(exists).isTrue();
        } catch (EmptyResultDataAccessException error) {
            fail("No return from SQL statement");
        }
    }

    @Test
    @Order(3)
    void get_loginAccount_not_null() {
        LoginAccount loginAccount = testLoginDao.get(USERNAME);
        assertThat(loginAccount).isNotNull();
        assertThat(loginAccount.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    @Order(4)
    void get_loginAccount_right_values(){
        LoginAccount loginAccount = testLoginDao.get(USERNAME);
        assertThat(loginAccount.getHash()).isEqualTo(HASH);
        assertThat(loginAccount.getToken()).isNull();
    }

    @Test
    @Order(5)
    void loginAccount_updated(){
        String actualHash = "notAHash";
        String actualToken = "notAToken";
        testLoginDao.update(USERNAME, actualHash, actualToken);
        LoginAccount loginAccount = testLoginDao.get(USERNAME);
        assertThat(loginAccount.getHash()).isEqualTo(actualHash);
        assertThat(loginAccount.getToken()).isEqualTo(actualToken);
    }

    @Test
    @Order(6)
    void loginAccount_exists() {
        assertThat(testLoginDao.loginExists(USERNAME)).isTrue();
    }

    @Test
    @Order(7)
    void loginAccount_deleted(){
        testLoginDao.delete(USERNAME);
        String sql = "select exists(select * from loginaccount where username= 'Somebody')";
        try {
            boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class);
            assertThat(exists).isFalse();
        } catch (EmptyResultDataAccessException error) {
            fail("No return from SQL statement");
        }
    }

    @Test
    @Order(8)
    void loginAccount_not_exists(){
        assertThat(testLoginDao.loginExists(USERNAME)).isFalse();
    }
}