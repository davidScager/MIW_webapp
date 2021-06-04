package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.service.assetenportfolio.AssetService;
import com.example.cryptobank.service.currency.CurrencyHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class AssetController {

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);
    private final CurrencyHistory currencyHistory;
    private final AssetService assetService;

    @Autowired
    public AssetController(CurrencyHistory currencyHistory, AssetService assetService) {
        super();
        this.currencyHistory = currencyHistory;
        this.assetService = assetService;
        logger.info("New AssetController");
    }

    @GetMapping("/createasset")
    @CrossOrigin
    public Asset createAssetHandler(@RequestParam String name, @RequestParam String apiName,@RequestParam String abbreviation, @RequestParam String description) throws IOException {
        return assetService.createNewAsset(name,apiName, abbreviation, description);
    }

    @GetMapping("/assetoverview")
    @CrossOrigin
    public List<Asset> assetOverviewHandler() {
        return assetService.showAssetList();
    }

    @GetMapping("/updateassetsbyapi")
    @CrossOrigin
    public List<Asset> updateAssetsByApi() {
        List<Asset> assetList = assetService.showAssetList();
        for (Asset asset : assetList){
            System.out.println("Get coin "+asset.getName());
            asset = assetService.updateAssetByApi(asset.getApiName());
        }
        return assetList;
    }

    @GetMapping("/updateassetbyapi")
    @CrossOrigin
    public Asset updateAssetByApi(@RequestParam String apiname) {
        Asset asset = assetService.updateAssetByApi(apiname);
        return asset;
    }

    @GetMapping("/getHistoryValue")
    @CrossOrigin
    public double getHistoryValue(@RequestParam String assetname, @RequestParam String date) throws IOException {
        return currencyHistory.historyValuefrom(date, assetService.getAsset(assetname).getName());
    }
}
