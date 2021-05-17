package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.service.AssetService;
import com.example.cryptobank.service.CurrencyHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Asset createAssetHandler(@RequestParam String name, @RequestParam String abbreviation, @RequestParam String description) throws IOException {
        Asset newAsset = assetService.createNewAsset(name, abbreviation, description);
        return newAsset;
    }

    @GetMapping("/assetoverview")
    public List<Asset> assetOverviewHandler() {
        List<Asset> assetList = assetService.showAssetList();
        return assetList;
    }


}
