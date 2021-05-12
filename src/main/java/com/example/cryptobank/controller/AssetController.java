package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.service.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AssetController {

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        super();
        this.assetService = assetService;
    }

    @GetMapping("/createasset")
    public Asset createAssetHandler(@RequestParam String name, @RequestParam String abbreviation, @RequestParam String description){
        Asset newAsset = assetService.createNewAsset(name, abbreviation, description);
        return newAsset;
    }

    @GetMapping("/assetoverview")
    public List<Asset> assetOverviewHandler() {
        List<Asset> assetList = assetService.showAssetList();
        return assetList;
    }


}
