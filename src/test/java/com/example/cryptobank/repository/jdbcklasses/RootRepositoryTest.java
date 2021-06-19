package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.login.LoginAccount;
import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.domain.user.*;
import com.example.cryptobank.repository.daointerfaces.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RootRepositoryTest {
    private final JdbcTemplate jdbcTemplate;
    private final RootRepository rootRepositoryTest;
    private final UserDao userTestDao;
    private final LoginDao testLoginDao;
    private final PortfolioDao portfolioTestDao;
    private static User userExpected;
    private static Portfolio portFolioExpected;
    private static Actor actorExpected;
    private final ActorDao testActorDao;
    private static LoginAccount loginAccountExpected;
    private final AssetPortfolioDao testAssetPortfolioDao;

    private static final long USER_ID = 7;
    private static final long NEW_USER_ID = 8;
    private static final int PORTFOLIO_ID = 107;
    private static final int NEW_PORTFOLIO_ID = 108;

    private static User newUserExpected;
    private static UserLoginAccount newUserLoginAccountExpected;
    private static Actor newActorExpected;
    private static LoginAccount newLoginAccountExpected;
    private static Portfolio newPortfolioExpected;


    @Autowired
    public RootRepositoryTest(RootRepository rootRepositoryTest, UserDao userTestDao, PortfolioDao portfolioTestDao, JdbcTemplate jdbcTemplate, ActorDao actorDao, LoginDao loginDao, AssetPortfolioDao assetPortfolioDao) {
        this.rootRepositoryTest = rootRepositoryTest;
        this.userTestDao = userTestDao;
        this.testLoginDao = loginDao;
        this.portfolioTestDao = portfolioTestDao;
        this.jdbcTemplate = jdbcTemplate;
        this.testActorDao = actorDao;
        this.testAssetPortfolioDao = assetPortfolioDao;
    }

    @BeforeAll
    public static void setup() {
        userExpected = new User(123456, new FullName("Huib", "van", "Straten"),
                "1982-01-29", new UserAddress("2252BX", 8,  "b", "van Lierdreef", "Voorschoten"),
                "huib@huib.com");
        userExpected.setId(USER_ID);
        actorExpected = new Actor();
        actorExpected.setUserId(USER_ID);
        actorExpected.setRole(Role.CLIENT);
        actorExpected.setCheckingAccount("32187456");
        portFolioExpected = new Portfolio(actorExpected);
        portFolioExpected.setPortfolioId(PORTFOLIO_ID);
        registerUserSetup();
        loginAccountExpected = new LoginAccount("test_testman@hotmail.com", "123", "321");
    }

    public static void registerUserSetup(){
        newUserLoginAccountExpected = new UserLoginAccount(234561,
                "Dingetje", "van", "Dinges",
                "2000-01-01",
                "2345AB", 20, "b", "Dorpstraat", "Lutjebroek",
                "not.an@email.com", "password");
        newUserExpected = new User(234561,
                new FullName("Dingetje", "van", "Dinges"),
                "2000-01-01",
                new UserAddress("2345AB", 20,  "b", "Dorpstraat", "Lutjebroek"),
                "not.an@email.com");
        newLoginAccountExpected = new LoginAccount(newUserLoginAccountExpected.getUser().getEmail(), newUserLoginAccountExpected.getPassword(), null);
        newActorExpected = new Actor(Role.CLIENT);
        newActorExpected.setUserId(NEW_USER_ID);
        newPortfolioExpected = new Portfolio(newActorExpected);
        newPortfolioExpected.setPortfolioId(NEW_PORTFOLIO_ID);
    }

    @Test @Order(1)
    void rootRepositoryTest_is_NotNull() {
        assertThat(rootRepositoryTest).isNotNull();
    }

    @Test @Order(2)
    void userDAO_is_NotNull() {
        assertThat(userTestDao).isNotNull();
    }

    @Test @Order(3)
    void loginDAO_is_NotNull() {
        assertThat(testLoginDao).isNotNull();
    }

    @Test @Order(4)
    void actorDAO_is_NotNull() {
        assertThat(testActorDao).isNotNull();
    }

    @Test @Order(5)
    void PortfolioTestDao_is_NotNull() {
        assertThat(portfolioTestDao).isNotNull();
    }

    @Test @Order(6)
    void assetPortfolioDao_is_NotNull(){assertThat(testAssetPortfolioDao).isNotNull();}

    //start tests David
    @Test @Order(7)
    void user_not_alreadyRegistered(){
        assertThat(rootRepositoryTest.alreadyRegistered(newUserLoginAccountExpected)).isFalse();
    }

    @Test @Order(8)
    void loginAccount_registered_exists(){
        rootRepositoryTest.registerLogin(newUserExpected, newUserLoginAccountExpected.getPassword());
        assertThat(testLoginDao.loginExists(newUserExpected.getEmail())).isTrue();
    }

    @Test @Order(9)
    void loginAccount_registered_right_values(){
        LoginAccount loginAccountActual = testLoginDao.get(newUserLoginAccountExpected.getUser().getEmail());
        assertThat(loginAccountActual).isEqualTo(newLoginAccountExpected);
    }

    @Test @Order(10)
    void user_not_alreadyRegistered_with_LoginAccount_registered(){
        assertThat(rootRepositoryTest.alreadyRegistered(newUserLoginAccountExpected)).isFalse();
    }

    @Test @Order(11)
    void registerUser_user_alreadyRegistered() {
        rootRepositoryTest.registerUser(newUserExpected, Role.CLIENT);
        assertThat(rootRepositoryTest.alreadyRegistered(newUserLoginAccountExpected)).isTrue();
    }

    @Test @Order(12)
    void registerUser_actor_exists(){
        String sql = "select exists(select * from actor where userId= '" + NEW_USER_ID + "')";
        try {
            boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class);
            assertThat(exists).isTrue();
        } catch (EmptyResultDataAccessException error) {
            fail("No return from SQL statement");
        }
    }

    @Test @Order(13)
    void registerUser_actor_right_values(){
        testActorDao.get(NEW_USER_ID).ifPresent(actorActual -> {
            assertThat(actorActual.getUserId()).isEqualTo(NEW_USER_ID);
            assertThat(actorActual.getRole()).isEqualTo(Role.CLIENT);
        });
    }

    @Test @Order(14)
    void registerUser_user_exists(){
        assertThat(userTestDao.userExists(newUserExpected.getEmail(), newUserExpected.getBSN())).isTrue();
    }

    @Test @Order(15)
    void registerUser_user_right_values(){
        User userActual = userTestDao.get(newUserLoginAccountExpected.getUser().getEmail());
        assertThat(userActual).isEqualTo(newUserExpected);
    }

    @Test @Order(16)
    void registerUser_portfolio_exists(){
        String sql = "select exists(select * from portfolio where actor= '" + NEW_USER_ID + "')";
        try {
            boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class);
            assertThat(exists).isTrue();
        } catch (EmptyResultDataAccessException error) {
            fail("No return from SQL statement");
        }
    }

    @Test @Order(17)
    void registerUser_portfolio_right_value(){
        portfolioTestDao.get(NEW_PORTFOLIO_ID).ifPresent(portfolioActual -> {
            assertThat(portfolioActual.getActor().getUserId()).isEqualTo(NEW_USER_ID);
        });
    }
    //eind tests David

    //Start tests Huib?
    @Test
    void userDAO_returns_id() {
        long id = userTestDao.get("huib@huib.com").getId();
        assertThat(id).isEqualTo(7);
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
    //eind tests Huib?

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