package com.example.cryptobank.repository;

import com.example.cryptobank.domain.*;
import com.example.cryptobank.repository.daointerfaces.ActorDao;
import com.example.cryptobank.repository.daointerfaces.AssetDao;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcAssetPortfolioDaoTest {
    private static AssetPortfolio assetPortfolio;
    private static Actor actor;
    private static ActorDao actorDao;
    private final AssetPortfolioDao assetPortfolioDao;
    private static AssetDao assetDao;





    @Autowired
    JdbcAssetPortfolioDaoTest(AssetPortfolioDao assetPortfolioDao, ActorDao actorDao, AssetDao assetDao) {
        this.assetPortfolioDao = assetPortfolioDao;
        this.actorDao = actorDao;
        this.assetDao = assetDao;
    }
    @BeforeAll
    static void setup(){
        Asset asset = new Asset("Test", "test", "tt", "bla", 1, 1, 1, 1, 1);
        assetDao.create(asset);
        assetPortfolio = new AssetPortfolio("Test", 1, 2, 1);
        actor = actorDao.get(1).get();
    }


    @Test
    void create() {
        assetPortfolioDao.create(assetPortfolio);
    }

    @Test
    void getAssetOverview() {
        assetPortfolioDao.getAssetOverview(1);
    }

    @Test
    void update() {
        assetPortfolio.setAssetName("Update");
        assetPortfolioDao.update(new Asset("Test", "test"), new Portfolio(new Actor(Role.CLIENT)), 1);

    }

    @Test
    void delete() {
    }




    @Test
    void getAssetOverviewWithAmount() {
    }

    @Test
    void getAmountByAssetName() {
    }



    @Test
    void updateAssetsForSale() {
    }

}