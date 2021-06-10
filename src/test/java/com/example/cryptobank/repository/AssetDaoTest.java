package com.example.cryptobank.repository;


import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.FullName;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserAddress;
import com.example.cryptobank.repository.daointerfaces.AssetDao;
import com.example.cryptobank.repository.daointerfaces.UserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class AssetDaoTest {
    private AssetDao assetTestDao;
    private static Asset assetExpected;


    @Autowired
    public void AssetTestDao(AssetDao assetTestDao) {
        this.assetTestDao = assetTestDao;
    }


    @BeforeAll
    public static void setupAsset() {
        assetExpected = new Asset("BTC", "Bitcoin", "BTC", "mooiemunt",4000, 1,1,1,1);

    }
    @Test
    public void assetDaoNotNull() {
        assertThat(assetTestDao).isNotNull();
    }

    @Test
    public void checkAssetOvervieuw() {
        List<Asset> actualAssetList = assetTestDao.getAssetOverview();
        assertThat(actualAssetList).isNotNull();
    }
    @Test
    public void checkAssetGetOnByName() {
        List<Asset> actualAssetList = assetTestDao.getAssetOverview();
        assertThat(actualAssetList).isNotNull();
    }
}
