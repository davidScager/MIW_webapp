package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.service.AssetService;
import com.example.cryptobank.service.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PortfolioController {

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);

    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        super();
        this.portfolioService = portfolioService;
        logger.info("New PortfolioController");
    }

    @GetMapping("/portfoliooverview")
    public List<String> portfolioOverviewHandler(@RequestParam int userId) {
        return portfolioService.showAssetOverview(userId);
    }

    @GetMapping("/portfoliovalue")
    public String portfolioValueHandler(@RequestParam int userId) {
        return portfolioService.showValueOfPortfolio(userId);
    }

}
