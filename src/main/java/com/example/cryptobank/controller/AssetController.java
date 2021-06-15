package com.example.cryptobank.controller;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetViewForSale;
import com.example.cryptobank.repository.jdbcklasses.JdbcAssetPortfolioDao;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AssetController {

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);
    private final CurrencyHistory currencyHistory;
    private final AssetService assetService;
    private final JdbcAssetPortfolioDao jdbcAssetPortfolioDao;
    private final RootRepository rootReposistory;

    @Autowired
    public AssetController(CurrencyHistory currencyHistory, AssetService assetService, JdbcAssetPortfolioDao jdbcAssetPortfolioDao, RootRepository rootReposistory) {
        super();
        this.currencyHistory = currencyHistory;
        this.assetService = assetService;
        this.jdbcAssetPortfolioDao = jdbcAssetPortfolioDao;
        this.rootReposistory = rootReposistory;
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

    @GetMapping("/updateassetsbyapiv2")
    @CrossOrigin
    public List<AssetViewForSale> updateAssetsByApiV2(@RequestParam String update) {
        List<Asset> assetList = rootReposistory.showAssetOverview();
        List<AssetViewForSale> assetViewForSaleList = new ArrayList<>();
        System.out.println("in updateassetsbyapiv2");
        for (Asset asset : assetList){
            // Update indien van toepassing
            if (update.equals("true")) {
                asset = assetService.updateAssetByApi(asset.getApiName());
            }

            AssetViewForSale assetViewForSale = new AssetViewForSale();
            assetViewForSale.setAbbreviation(asset.getAbbreviation());
            assetViewForSale.setDescription((asset.getDescription()));
            assetViewForSale.setName(asset.getName());
            assetViewForSale.setValueInUsd(asset.getValueInUsd());
            System.out.println("Get coin "+asset.getName());
            List<Map<String,Object>> forSale = jdbcAssetPortfolioDao.getAssetsForSale(asset.getAbbreviation());
            double otherForSale = 0;
            for (Map<String,Object> map : forSale){
                if ((Integer)map.get("portfolioId") == 101){
                    assetViewForSale.setAvailableBank((Double) map.get("forSale"));
                } else {
                    System.out.println("Portfolio id "+ (Integer) map.get("portfolioId")+ " For sale "+(Double) map.get("forSale"));
                    otherForSale += (Double) map.get("forSale");
                }
            }
            assetViewForSale.setAvailableOthers(otherForSale);
            assetViewForSaleList.add(assetViewForSale);
             //
        }
        return assetViewForSaleList;
    }


    @GetMapping("/updateassetbyapi")
    @CrossOrigin
    public Asset updateAssetByApi(@RequestParam String apiname) {
        System.out.println("in updateAssetByApi");
        Asset asset = assetService.updateAssetByApi(apiname);
        return asset;
    }

    @GetMapping("/getHistoryValue")
    @CrossOrigin
    public double getHistoryValue(@RequestParam String assetname, @RequestParam String date) throws IOException {
        return currencyHistory.historyValuefrom(date, assetService.getAsset(assetname).getName());
    }
}
