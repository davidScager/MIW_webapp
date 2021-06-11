package com.example.cryptobank.repository;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetPortfolio;
import com.example.cryptobank.domain.asset.AssetPortfolioView;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.domain.user.Actor;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.repository.daointerfaces.ActorDao;
import com.example.cryptobank.repository.daointerfaces.AssetDao;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AssetPortfolioDaoTest {
    private static AssetPortfolio assetPortfolio;
    private  static AssetPortfolioDao assetPortfolioDao;
    private static Actor actor;
    private static ActorDao actorDao;
    private static Asset asset;
    private static AssetDao assetDao;
    private static Portfolio portfolio;
    private static PortfolioDao portfolioDao;

    @Autowired
    AssetPortfolioDaoTest(AssetPortfolioDao assetPortfolioDao, ActorDao actorDao, AssetDao assetDao, PortfolioDao portfolioDao) {
        this.assetPortfolioDao = assetPortfolioDao;
        this.actorDao = actorDao;
        this.assetDao = assetDao;
        this.portfolioDao = portfolioDao;
        asset = new Asset("Test", "Test", "tt", "bla", 1, 1, 1, 1, 1);
        assetDao.create(asset);
        Actor dbActor = new Actor(Role.CLIENT);
        actorDao.create(dbActor);
        actor = actorDao.get(8).get();
        portfolio = new Portfolio(actor);
        portfolioDao.create(portfolio);
        assetPortfolio = new AssetPortfolio("Test", 1, 2, 1);
        assetPortfolioDao.create(assetPortfolio);
    }

    @Test
    @Order(1)
    void getAssetOverview() {
        List<Asset> assetList =  assetPortfolioDao.getAssetOverview(1);
        Asset result = assetList.get(0);
        assertThat(asset.getName()).isEqualTo(result.getName());
    }

    @Test
    @Order(2)
    void getAssetOverviewWithAmount() {
        Map<Asset, Double> resultMap = assetPortfolioDao.getAssetOverviewWithAmount(1);
        assertThat(resultMap.keySet().contains(asset));
        assertThat(resultMap.containsValue(2));
    }
    @Test
    @Order(3)
    void getOverviewWithAmount() {
        List<AssetPortfolioView> list = assetPortfolioDao.getOverviewWithAmount(1);
        assertThat(list.get(0).getAssetName()).isEqualTo("Test");
    }

    @Test
    @Order(4)
    void updateAssetsForSale() {
        assetPortfolioDao.updateAssetsForSale("Test", 1, 10);
        List<AssetPortfolioView> list = assetPortfolioDao.getOverviewWithAmount(1);
        assertThat(list.get(0).getForSale()).isEqualTo(10);
    }
}