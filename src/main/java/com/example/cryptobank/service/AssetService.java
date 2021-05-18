package com.example.cryptobank.service;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.repository.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AssetService {

    private final RootRepository rootReposistory;
    private CurrencyHistory currencyHistory = new CurrencyHistory();
    private final Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    public AssetService(RootRepository rootReposistory) {
        super();
        this.rootReposistory = rootReposistory;
        logger.info("New AssetService");
    }

    public Asset createNewAsset(String name, String abbreviation, String description) throws IOException {
        Asset newAsset = new Asset(name, abbreviation, description);
        double valueYesterday = currencyHistory.historyValuefrom(currencyHistory.dateYesterday(), newAsset.getName());
        newAsset.setValueYesterday(valueYesterday);
        double valueLastWeek = currencyHistory.historyValuefrom(currencyHistory.dateLasteWeek(), newAsset.getName());
        newAsset.setValueLastWeek(valueLastWeek);
        double valueLastMonth = currencyHistory.historyValuefrom(currencyHistory.dateLastMonth(), newAsset.getName());
        newAsset.setValueLastMonth(valueLastMonth);
        rootReposistory.saveAsset(newAsset);
        return newAsset;
    }

    public List<Asset> showAssetList() {
        List<Asset> assetList = rootReposistory.showAssetOverview();
        return assetList;
    }

    public void update (Asset asset){
        rootReposistory.updateAsset(asset);
    }
}
