package com.example.cryptobank.service;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.repository.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private final RootRepository rootReposistory;

    private final Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    public AssetService(RootRepository rootReposistory) {
        super();
        this.rootReposistory = rootReposistory;
        logger.info("New AssetService");
    }

    public Asset createNewAsset(String name, String abbreviation, String description) {
        Asset newAsset = new Asset(name, abbreviation, description);
        rootReposistory.saveAsset(newAsset);
        return newAsset;
    }

    public List<Asset> showAssetList() {
        List<Asset> assetList = rootReposistory.showAssetOverview();
        return assetList;
    }
}
