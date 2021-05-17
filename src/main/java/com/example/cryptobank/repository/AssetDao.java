package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetDao {

    public void create(Asset asset);

    public List<Asset> getAssetOverview();

    public Asset getOneByName(String name);

    public void update(Asset asset, int id);

    public void delete(int id);

}
