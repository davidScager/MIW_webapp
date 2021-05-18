package com.example.cryptobank.repository;

import com.example.cryptobank.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * @auth HvS
 */

@SpringBootTest
@ActiveProfiles("test")
class JdbcUserDaoTest {
    // todo equals en hashcode in User

    private JdbcUserDao userTestDao;
    private RootRepository rootRepository;
    private static User user;

    @Autowired
    public JdbcUserDaoTest(JdbcUserDao userTestDao, RootRepository rootRepository) {
        super();
        this.userTestDao = userTestDao;
        this.rootRepository = rootRepository;
    }

    @BeforeAll
    static void setUp() {
        user = new User(12345, "Huib", "van", "Straten", "29-01-1982"
                , "pietstraat", "huib@huib.com", "huib");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void daoNotNull() {
        assertThat(userTestDao).isNotNull();
    }

    @Test
    void list() {
    }

    @Test
    void addUserTest() {
        userTestDao.create(user);
        assertThat(user).isEqualTo(user);
    }

    @Test
    void get() {
        Optional<User> user2 = userTestDao.get(12345);
        assertThat(user).isEqualTo(user2);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}

