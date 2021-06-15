package com.example.cryptobank.service.assetenportfolio;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.currency.CurrencyHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AssetService {

    private final RootRepository rootRepository;
    private CurrencyHistory currencyHistory = new CurrencyHistory();
    private final Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    public AssetService(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("New AssetService");
    }

    public Asset createNewAsset(String name,String apiName, String abbreviation, String description) throws IOException {
        Asset newAsset = new Asset(name, apiName,abbreviation, description);
        updateAssetByApi(name); //TODO check of dit werkt in nieuwe versie
        double valueYesterday = currencyHistory.historyValuefrom(currencyHistory.dateYesterday(), newAsset.getName());
        newAsset.setValueYesterday(valueYesterday);
        double valueLastWeek = currencyHistory.historyValuefrom(currencyHistory.dateLasteWeek(), newAsset.getName());
        newAsset.setValueLastWeek(valueLastWeek);
        double valueLastMonth = currencyHistory.historyValuefrom(currencyHistory.dateLastMonth(), newAsset.getName());
        newAsset.setValueLastMonth(valueLastMonth);
        rootRepository.saveAsset(newAsset);
        return newAsset;
    }

    public List<Asset> showAssetList() {
        List<Asset> assetList = rootRepository.showAssetOverview();
        updateAssetsByApi();
        return assetList;
    }

    public void update (Asset asset){
        rootRepository.updateAsset(asset);
    }

    public List<Asset> updateAssetsByApi() {
        List<Asset> assetList = rootRepository.updateAssetsByApi();
        return assetList;
    }

    public Asset updateAssetByApi(String apiName) {
        Asset asset = rootRepository.updateAssetByApi(apiName);
        return asset;
    }

    public Asset getAsset(String abbriviation){
       return rootRepository.getAsset(abbriviation);
    }

}
