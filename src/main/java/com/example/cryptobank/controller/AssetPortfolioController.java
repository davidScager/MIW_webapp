package com.example.cryptobank.controller;

import com.example.cryptobank.domain.AssetPortfolio;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
import com.example.cryptobank.service.assetenportfolio.AssetPortfolioService;
import com.example.cryptobank.service.assetenportfolio.AssetService;
import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import com.example.cryptobank.service.login.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AssetPortfolioController {

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);
    private final AssetService assetService;
    private final PortfolioService portfolioService;
    private final AssetPortfolioService assetPortfolioService;
    private final PortfolioDao portfolioDao;
    private final AssetPortfolioDao assetPortfolioDao;
    private final UserService userService;

    @Autowired
    public AssetPortfolioController(AssetService assetService, PortfolioService portfolioService, AssetPortfolioService assetPortfolioService, PortfolioDao portfolioDao, AssetPortfolioDao assetPortfolioDao, UserService userService) {
        super();
        this.portfolioService = portfolioService;
        this.assetService = assetService;
        this.assetPortfolioService = assetPortfolioService;
        this.portfolioDao = portfolioDao;
        this.assetPortfolioDao = assetPortfolioDao;
        this.userService = userService;
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

    @PostMapping("/updateassetforsale")
    @CrossOrigin
    public ResponseEntity<String> updateassetforsale(@RequestHeader(value = "Authorization") String token, @RequestParam String symbol, @RequestParam double amount) {
        JSONObject jsonObject = new JSONObject();
        System.out.println("symbol " + symbol + " amountForsale " + amount);
        //TODO get token
//        System.out.println("fake token " + token);
//        int userId = 2;
        User user = userService.getUserFromToken(token);
        long userId = user.getId();
        int portfolioId = portfolioDao.getPortfolioIdByUserId((int) userId).getPortfolioId();
        double amountAvailable = assetPortfolioDao.getAmountByAssetName(symbol, portfolioId);
        System.out.println(amountAvailable);
        //Portfolio portfolio = portfolioService.getByActor(userId);
        System.out.println("portfolio " + portfolioId);
        if (amountAvailable < amount) {
            return new ResponseEntity<String>("Niet voldoende assets ter beschikking.", HttpStatus.OK);
        }
        assetPortfolioDao.updateAssetsForSale(symbol,portfolioId,amount);
        return new ResponseEntity<String>("Je hebt ze beschikbaar gezet.", HttpStatus.OK);

        //return new ResponseEntity<JSONObject>(jsonObject, HttpStatus.OK);
    }

}
