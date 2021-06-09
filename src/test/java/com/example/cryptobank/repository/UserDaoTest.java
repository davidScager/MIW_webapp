package com.example.cryptobank.repository;

import com.example.cryptobank.domain.FullName;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserAddress;
import com.example.cryptobank.repository.daointerfaces.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @auth HvS
 */

@SpringBootTest
@ActiveProfiles("test")
class UserDaoTest {
    private UserDao userTestDao;
    private static User userExpected;

    @Autowired
    public UserDaoTest(UserDao userTestDao) {
        this.userTestDao = userTestDao;
    }

    @BeforeAll
    public static void setup() {
        userExpected = new User(123456, new FullName("Huib", "van", "Straten"),
                "1982-01-29", new UserAddress("van lierdreef", 8, "b", "2252BX", "Voorschoten"),
                "huib@huib.com");
        userExpected.setId(7);
    }

    @Test
    public void daoNotNull() {
        assertThat(userTestDao).isNotNull();
    }

    @Test
    public void check_list_not_null() {
         List<User> actualList = userTestDao.list();
         assertThat(actualList).isNotNull();
    }

    @Test
    public void list_size_is_correct() {
        List<User> actualList = userTestDao.list();
        assertThat(actualList.size()).isEqualTo(2);
    }

    @Test
    void user_is_added() {
        userExpected.setId(8);
        userTestDao.create(userExpected);
        List<User> actualList = userTestDao.list();
        assertThat(actualList.size()).isEqualTo(3);
    }

    @Test
    public void returns_a_User_notNull() {
        User userActual =  userTestDao.get("huib@huib.com");
        assertThat(userActual).isNotNull();
    }

    @Test
    public void returns_a_User_with_expected_values() {
        User userActual =  userTestDao.get("huib@huib.com");
        assertThat(userActual).isEqualTo(userExpected);
    }

    @Test
    public void update_changes_value() {
        userExpected.setId(7);
        userExpected.setDateOfBirth("1982-01-30");
        userTestDao.update(userExpected, 123456);
        User userActual = userTestDao.get("huib@huib.com");
        assertThat(userActual.getDateOfBirth()).isEqualTo(userExpected.getDateOfBirth());
    }

    @Test
    void delete() {
    }
}

