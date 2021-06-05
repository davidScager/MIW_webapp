package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Actor;
import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.AssetPortfolioView;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
import com.example.cryptobank.repository.jdbcklasses.JdbcActorDao;
import com.example.cryptobank.service.assetenportfolio.ActorService;
import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import com.example.cryptobank.service.transaction.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class PortfolioController {

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);

    private final PortfolioService portfolioService;
    private final PortfolioDao portfolioDao;
    private final AssetPortfolioDao assetPortfolioDao;

    @Autowired
    public PortfolioController(PortfolioService portfolioService, PortfolioDao portfolioDao,
                               ActorService actorService, AssetPortfolioDao assetPortfolioDao) {
        super();
        this.portfolioService = portfolioService;
        this.portfolioDao = portfolioDao;
        this.assetPortfolioDao = assetPortfolioDao;
        logger.info("New PortfolioController");
    }

    @GetMapping("/portfoliooverview")
    public List<String> portfolioOverviewHandler(@RequestParam int userId) throws JsonProcessingException {
        return portfolioService.showAssetOverview(userId);
    }

    @GetMapping("/portfoliovalue")
    public String portfolioValueHandler(@RequestParam int userId) {
        return portfolioService.showValueOfPortfolio(userId);
    }

    @PostMapping("/listportfolio")
    @CrossOrigin
    public ResponseEntity<List<AssetPortfolioView>> listPortFolio(@RequestParam("token") String token) {
        //TODO get token
        System.out.println("fake token "+token);
        int userId = 1;
        int portfolioId = portfolioDao.getPortfolioIdByUserId((int)userId).getPortfolioId();
        Portfolio portfolio = portfolioService.getByActor(userId);
        System.out.println("portfolio "+ portfolio);
        List<AssetPortfolioView> assetPortfolioView = assetPortfolioDao.getOverviewWithAmount(userId);
        return new ResponseEntity<>(assetPortfolioView, HttpStatus.OK);
    }

}
