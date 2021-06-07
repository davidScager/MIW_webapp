package com.example.cryptobank.repository;

import com.example.cryptobank.domain.AssetPortfolio;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
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
    private final AssetPortfolioDao assetPortfolioDao;

    @Autowired
    JdbcAssetPortfolioDaoTest(AssetPortfolioDao assetPortfolioDao) {
        this.assetPortfolioDao = assetPortfolioDao;
    }


    @Test
    void create() {
        AssetPortfolio assetPortfolio = new AssetPortfolio("BTC", 1, 2, 1);
        assetPortfolioDao.create(assetPortfolio);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }


    @Test
    void getAssetOverview() {
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




    @Test
    void getOverviewWithAmount() {
    }
}