package com.example.cryptobank.repository;

import com.example.cryptobank.domain.user.FullName;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.user.UserAddress;
import com.example.cryptobank.repository.daointerfaces.UserDao;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoTest {
    private final UserDao userTestDao;
    private final JdbcTemplate jdbcTemplate;
    private static User userExpected;

    @Autowired
    public UserDaoTest(UserDao userTestDao, JdbcTemplate jdbcTemplate) {
        this.userTestDao = userTestDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeAll
    public static void setup() {
        userExpected = new User(123456, new FullName("Huib", "van", "Straten"),
                "1982-01-29", new UserAddress("2252BX", 8,  "b", "van Lierdreef", "Voorschoten"),
                "huib@huib.com");
        userExpected.setId(7);
    }

    @Test
    @Order(1)
    public void daoNotNull() {
        assertThat(userTestDao).isNotNull();
    }

    @Test
    @Order(2)
    public void check_list_not_null() {
         List<User> actualList = userTestDao.list();
         assertThat(actualList).isNotNull();
    }

    @Test
    @Order(3)
    public void list_size_is_correct() {
        List<User> actualList = userTestDao.list();
        assertThat(actualList.size()).isEqualTo(2);
    }

    @Test
    @Order(4)
    public void returns_a_User_notNull() {
        User userActual =  userTestDao.get("huib@huib.com");
        assertThat(userActual).isNotNull();
    }

    @Test
    @Order(5)
    public void returns_a_User_with_expected_values() {
        User userActual =  userTestDao.get("huib@huib.com");
        assertThat(userActual).isEqualTo(userExpected);
    }

    @Test
    @Order(6)
    public void update_changes_value() {
        userExpected.setId(7);
        userExpected.setDateOfBirth("1982-01-30");
        userTestDao.update(userExpected, 123456);
        User userActual = userTestDao.get("huib@huib.com");
        assertThat(userActual.getDateOfBirth()).isEqualTo(userExpected.getDateOfBirth());
    }

    @Test
    @Order(7)
    void user_is_deleted() {
        userTestDao.delete(123456);
        String sql = "select exists(select * from user where firstName= 'huib')";
        try {
            boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class);
            assertThat(exists).isFalse();
        } catch (EmptyResultDataAccessException error) {
            failBecauseExceptionWasNotThrown(EmptyResultDataAccessException.class);
        }
    }

    @Test
    @Order(8)
    void user_is_added() {
        userExpected.setId(8);
        userTestDao.create(userExpected);
        String sql = "select exists(select * from user where firstName= 'huib')";
        try {
            boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class);
            assertThat(exists).isTrue();
        } catch (EmptyResultDataAccessException error) {
            failBecauseExceptionWasNotThrown(EmptyResultDataAccessException.class);
        }
    }

    @Test
    @Order(9)
    void userAlreadyExists(){
        userTestDao.create(userExpected);
        assertThat(userTestDao.userExists("huib@huib.com", 123456)).isTrue();
    }

    @Test
    @Order(10)
    void userWithSameEmailAlreadyExists(){
        assertThat(userTestDao.userExists("huib@huib.com", 123457)).isTrue();
    }

    @Test
    @Order(11)
    void userWithSameBsnAlreadyExists(){
        assertThat(userTestDao.userExists("dennis@huib.com", 123456)).isTrue();
    }

    @Test
    @Order(12)
    void userNotExists() {
        userTestDao.delete(123456);
        assertThat(userTestDao.userExists("huib@huib.com", 123457)).isFalse();
    }
}

