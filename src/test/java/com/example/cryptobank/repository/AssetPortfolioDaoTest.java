package com.example.cryptobank.repository;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetPortfolio;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.domain.user.Actor;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.repository.daointerfaces.ActorDao;
import com.example.cryptobank.repository.daointerfaces.AssetDao;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
import org.junit.jupiter.api.BeforeAll;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
class AssetPortfolioDaoTest {
    private static AssetPortfolio assetPortfolio;
    private AssetPortfolioDao assetPortfolioDao;
    private static Actor actor;
    private ActorDao actorDao;
    private static Asset asset;
    private AssetDao assetDao;

    @Autowired
    AssetPortfolioDaoTest(AssetPortfolioDao assetPortfolioDao, ActorDao actorDao, AssetDao assetDao) {
        this.assetPortfolioDao = assetPortfolioDao;
        this.actorDao = actorDao;
        this.assetDao = assetDao;
    }
    @BeforeAll
    static void setup(){
        /*asset = new Asset("Test", "test", "tt", "bla", 1, 1, 1, 1, 1);
        assetDao.create(asset);
        assetPortfolio = new AssetPortfolio("Test", 1, 2, 1);
        actor = actorDao.get(1).get();*/
    }


    @Test
    void create() {
        assetPortfolioDao.create(assetPortfolio);
    }

    @Test
    void getAssetOverview() {
        List<Asset> assetList =  assetPortfolioDao.getAssetOverview(1);
        Asset result = assetList.get(0);
        assertThat(asset.getName()).isEqualTo(result.getName());
    }

    @Test
    void update() {
        assetPortfolio.setAssetName("Update");
        assetPortfolioDao.update(asset, new Portfolio(new Actor(Role.CLIENT)), 1);

    }

    @Test
    void getAssetOverviewWithAmount() {
        Map<Asset, Double> resultMap = assetPortfolioDao.getAssetOverviewWithAmount(1);
        assertThat(resultMap.keySet().contains(asset));
        assertThat(resultMap.get(asset)).isEqualTo(1);
    }

    @Test
    void getAmountByAssetName() {
    }



    @Test
    void updateAssetsForSale() {
    }

}