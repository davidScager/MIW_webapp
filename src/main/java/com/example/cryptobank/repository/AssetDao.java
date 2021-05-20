package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetDao {

    void create(Asset asset);

   List<Asset> getAssetOverview();

    Asset getOneByName(String name);

    void delete(int id);

    Asset updateAssetByApi(String name);

    Asset getOneBySymbol(String symbol);

    void update(Asset asset);

    List<Asset> updateAssetsByApi();

    void updateAdjustmentFactor(Asset asset, double dollarAmount, boolean buyFromBank, boolean sellToBank);

}
