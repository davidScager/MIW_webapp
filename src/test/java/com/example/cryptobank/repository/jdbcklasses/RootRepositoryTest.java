package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.domain.user.*;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
import com.example.cryptobank.repository.daointerfaces.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class RootRepositoryTest {
    private final RootRepository rootRepositoryTest;
    private final UserDao userTestDao;
    private final PortfolioDao portfolioTestDao;
    private final JdbcTemplate jdbcTemplate;
    private static User userExpected;
    private static Portfolio portFolioExpected;
    private static Actor actorExpected;
    private final AssetPortfolioDao assetPortfolioDao;


    @Autowired
    public RootRepositoryTest(RootRepository rootRepositoryTest, UserDao userTestDao, PortfolioDao portfolioTestDao, JdbcTemplate jdbcTemplate, AssetPortfolioDao assetPortfolioDao) {
        this.rootRepositoryTest = rootRepositoryTest;
        this.userTestDao = userTestDao;
        this.portfolioTestDao = portfolioTestDao;
        this.jdbcTemplate = jdbcTemplate;
        this.assetPortfolioDao = assetPortfolioDao;
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
    void getUserIdByPortfolioId(){
        int actual = portfolioTestDao.getUserIdByPortfolioId(101);
        assertThat(actual).isEqualTo(1);
    }




}