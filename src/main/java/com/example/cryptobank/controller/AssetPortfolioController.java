package com.example.cryptobank.controller;

import com.example.cryptobank.domain.AssetPortfolio;
import com.example.cryptobank.service.assetenportfolio.AssetPortfolioService;
import com.example.cryptobank.service.assetenportfolio.AssetService;
import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AssetPortfolioController {

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);
    private final AssetService assetService;
    private final PortfolioService portfolioService;
    private final AssetPortfolioService assetPortfolioService;

    @Autowired
    public AssetPortfolioController(AssetService assetService, PortfolioService portfolioService, AssetPortfolioService assetPortfolioService) {
        super();
        this.portfolioService = portfolioService;
        this.assetService = assetService;
        this.assetPortfolioService = assetPortfolioService;
        logger.info("New AssetPortofolioController");
    }

    @GetMapping("/createassetportfolio")
    public AssetPortfolio createAssetPortfolioHandler(@RequestParam String assetName, @RequestParam int portfolioId, double amount) throws IOException {
        AssetPortfolio assetPortfolio = assetPortfolioService.createNewAssetPortfolio(assetName, portfolioId, amount);
        return assetPortfolio;
    }

    @GetMapping("/updateportfolioasset")
    public AssetPortfolio updateAssetHandler(@RequestParam String assetName, @RequestParam int portfolioId, @RequestParam double amount) throws IOException {
        AssetPortfolio assetPortfolio = assetPortfolioService.update(assetName, portfolioId, amount);
        return assetPortfolio;
    }

}
