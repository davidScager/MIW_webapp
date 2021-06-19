package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.login.LoginAccount;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.domain.user.*;
import com.example.cryptobank.repository.daointerfaces.ActorDao;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
import com.example.cryptobank.repository.daointerfaces.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class RootRepositoryTest {
    private final RootRepository rootRepositoryTest;
    private final UserDao userTestDao;
    private final PortfolioDao portfolioTestDao;
    private final ActorDao actorTestDao;
    private final JdbcTemplate jdbcTemplate;
    private static User userExpected;
    private static Portfolio portFolioExpected;
    private static Actor actorExpected;
    private static LoginAccount loginAccountExpected;



    @Autowired
    public RootRepositoryTest(RootRepository rootRepositoryTest, UserDao userTestDao, PortfolioDao portfolioTestDao, ActorDao actorDao, JdbcTemplate jdbcTemplate) {
        this.rootRepositoryTest = rootRepositoryTest;
        this.userTestDao = userTestDao;
        this.portfolioTestDao = portfolioTestDao;
        this.actorTestDao = actorDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeAll
    public static void setup() {
        userExpected = new User(123456, new FullName("Huib", "van", "Straten"),
                "1982-01-29", new UserAddress("2252BX", 8,  "b", "van Lierdreef", "Voorschoten"),
                "huib@huib.com");
        userExpected.setId(7);
        actorExpected = new Actor();
        actorExpected.setUserId(7);
        actorExpected.setRole(Role.CLIENT);
        actorExpected.setCheckingAccount("32187456");
        portFolioExpected = new Portfolio(actorExpected);
        portFolioExpected.setPortfolioId(107);
        loginAccountExpected = new LoginAccount("test_testman@hotmail.com", "123", "321");

    }

    @Test
    void rootRepositoryTest_is_NotNull() {
        assertThat(rootRepositoryTest).isNotNull();
    }
    @Test
    void userDAO_is_NotNull() {
        assertThat(userTestDao).isNotNull();
    }

    @Test
    void userDAO_returns_id() {
        long id = userTestDao.get("huib@huib.com").getId();
        assertThat(id).isEqualTo(7);
    }

    @Test
    void PortfolioTestDao_is_NotNull() {
        assertThat(portfolioTestDao).isNotNull();
    }

    @Test
    void portfolioDAO_returns_id() {
        int portfolioId = portfolioTestDao.getPortfolioIdByUserId(7).getPortfolioId();
        assertThat(portfolioId).isEqualTo(107);
    }

    @Test
    void Map_has_two_inputs() {
        Map<String, Map> actualList = rootRepositoryTest.getAssetPortfolioByUsername("huib@huib.com");
        assertThat(actualList.size()).isEqualTo(2);
    }

    @Test
    void one_input_is_from_bank() {
        Map<String, Map> actualList = rootRepositoryTest.getAssetPortfolioByUsername("huib@huib.com");
        assertThat(actualList).containsKey("bank");
    }

    @Test
    void Map_has_asset() {
        Map<String, Map> actualList = rootRepositoryTest.getAssetPortfolioByUsername("huib@huib.com");
        Map <String, Asset>actualAssetMap = actualList.get("bank");
        assertThat(actualAssetMap).isNotEmpty();
    }

    @Test
    void actorIsReturned(){
        Actor actor = rootRepositoryTest.getActor(1);
        assertThat(actor).isNotNull();
        assertThat(actor.getCheckingAccount()).isEqualTo("12345678");
    }

    @Test
    void actorIsUpdated(){
        Actor actor = rootRepositoryTest.getActor(1);
        assertThat(actor.getUserId()).isEqualTo(1);
        assertThat(actor.getCheckingAccount()).isNotEqualTo("54321");
        actor.setCheckingAccount("54321");
        rootRepositoryTest.updateActor(actor);
        Actor actorAfterTest = rootRepositoryTest.getActor(1);
        assertThat(actorAfterTest.getCheckingAccount()).isEqualTo("54321");
        assertThat(actorAfterTest.getUserId()).isEqualTo(1);
    }

    @Test
    void resetTokenIsStored(){
        String username = "niekmol1994@gmail.com";
        LoginAccount loginAccount = rootRepositoryTest.getLoginAccount(username);
        String token = loginAccount.getToken();
        assertThat(token).isNotEqualTo("12345");
        rootRepositoryTest.storeResetToken(username, "12345");
        LoginAccount loginAccountAfterTest = rootRepositoryTest.getLoginAccount(username);
        assertThat(loginAccountAfterTest.getUsername()).isEqualTo(username);
        String tokenAfterTest = loginAccountAfterTest.getToken();
        assertThat(tokenAfterTest).isEqualTo("12345");
        assertThat(tokenAfterTest).isNotEqualTo(token);
    }




}