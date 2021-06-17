package com.example.cryptobank.controller;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetPortfolio;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.domain.transaction.TransactionData;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.repository.daointerfaces.AssetDao;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
import com.example.cryptobank.repository.jdbcklasses.JdbcAssetDao;
import com.example.cryptobank.repository.jdbcklasses.JdbcAssetPortfolioDao;
import com.example.cryptobank.service.assetenportfolio.AssetPortfolioService;
import com.example.cryptobank.service.assetenportfolio.AssetService;
import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import com.example.cryptobank.service.login.UserService;
import com.example.cryptobank.service.transaction.TransactionService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class AssetPortfolioController {

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);
    private final AssetService assetService;
    private final AssetDao assetDao;
    private final PortfolioService portfolioService;
    private final AssetPortfolioService assetPortfolioService;
    private final PortfolioDao portfolioDao;
    private final AssetPortfolioDao assetPortfolioDao;
    private final UserService userService;
    private final JdbcAssetPortfolioDao jdbcAssetPortfolioDao;
    private final TransactionService transactionService;

    @Autowired
    public AssetPortfolioController(AssetService assetService, JdbcAssetDao jdbcAssetDao, AssetDao assetDao, PortfolioService portfolioService, AssetPortfolioService assetPortfolioService, PortfolioDao portfolioDao, AssetPortfolioDao assetPortfolioDao, UserService userService, JdbcAssetPortfolioDao jdbcAssetPortfolioDao, TransactionService transactionService) {
        super();
        this.assetDao = assetDao;
        this.portfolioService = portfolioService;
        this.assetService = assetService;
        this.assetPortfolioService = assetPortfolioService;
        this.portfolioDao = portfolioDao;
        this.assetPortfolioDao = assetPortfolioDao;
        this.userService = userService;
        this.jdbcAssetPortfolioDao = jdbcAssetPortfolioDao;
        this.transactionService = transactionService;
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
    public ResponseEntity<String> updateassetforsale(@RequestHeader(value = "Authorization") String token,@RequestBody Map<String, String> requestParams) {
        JSONObject jsonObject = new JSONObject();

        String symbol = requestParams.get("symbol");
        Double amount = Double.parseDouble(requestParams.get("amount"));
        System.out.println("symbol " + symbol + " amountForsale " + amount);
        //TODO get token
//        System.out.println("fake token " + token);
//        int userId = 2;
        User user = userService.getUserFromToken(token);
        long userId = user.getId();
        System.out.println("Token "+token);
        System.out.println("userId "+userId);
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

    @PostMapping("/updatebuyasset")
    public ResponseEntity<Asset> updatebuyasset(@RequestHeader(value = "Authorization") String token,@RequestBody Map<String, String> requestParams) {
        //JSONObject jsonObject = new JSONObject();

        String symbol = requestParams.get("symbol");
        Double amountUsd = Double.parseDouble(requestParams.get("amountUsd"));
        String payWith = requestParams.get("payWith");
        System.out.println("symbol " + symbol + " amountUsd " + amountUsd+  " payWith " + payWith);


        User user = userService.getUserFromToken(token);
        long userId = user.getId();
        System.out.println("Token "+token);
        System.out.println("userId "+userId);
        int portfolioIdBuyer = portfolioDao.getPortfolioIdByUserId((int) userId).getPortfolioId();


        Asset assetBuy = assetDao.getOneBySymbol(symbol);
        double amount = amountUsd / assetBuy.getValueInUsd();
        System.out.println(" amountUsd "+ amountUsd+ " / asset.getValueInUsd() "+ assetBuy.getValueInUsd() + " = "+amount);

        /* Niet nodig
        if (!payWith.equals("USD")){
            // Als betalen met anders is dan USD Eerst verkopen aan bank
            // VB. Betaal met y_Coin usd Value 100 USD
            // We moeten verkopen 1000 / 100 = 10
            Asset assetPayWith = assetDao.getOneBySymbol(payWith);
            double amountPayWith = amountUsd / assetPayWith.getValueInUsd();
            // Check of over voldoende is
            TransactionData transactionData = new TransactionData();
            transactionData.setBuyer(1); // Bank
            transactionData.setSeller((int) userId);  // De kopen is nu eerste de verkoper naar de bank
            transactionData.setNumberOfAssets(amountPayWith);
            // Krijg USD betaald
            transactionData.setAssetBought("USD");
            transactionData.setAssetSold(payWith);
            transactionData.setUsername(user.getFullName().toString());
            transactionData.setTriggerValue(0); //?????
            transactionData.setTransactionCost(0); //?????
            transactionService.createNewTransaction(transactionData);
            // USD Asset niet updated

            //double amount = amountUsd / asset.getValueInUsd();

        }
        */
        // List het volgorde DESC dus bank is als laatste
        // Niet meer nodig lijkt te werken
        List<Map<String,Object>> forSaleList = jdbcAssetPortfolioDao.getAssetsForSale(symbol);
        double otherForSale = 0;
        for (Map<String,Object> map : forSaleList){
            int portfolioIdSeller = (Integer) map.get("portfolioId");

            Double forSale = (Double) map.get("forSale");
            double transactieAmount = 0;
            if (Double.compare(forSale, 0) >0  ||  portfolioIdSeller == 101) {

                // We hebben een verkoper
                // We willen 100 maar hij heeft er maar 50
                if (forSale > amount) {
                    transactieAmount = amount;
                } else {
                    transactieAmount = forSale;
                    // Restant in volgende transactie
                    amount -= forSale;
                }

                TransactionData transactionData = new TransactionData();
                transactionData.setBuyer((int) userId);
                // Get seller id
                int sellerId = (int) portfolioDao.get(portfolioIdSeller).orElse(new Portfolio()).getActor().getUserId();
                transactionData.setSeller(sellerId);
                transactionData.setNumberOfAssets(transactieAmount);
                transactionData.setAssetBought(symbol);
                // betaal met USD
                Asset assetPayWith = assetDao.getOneBySymbol(payWith);
                transactionData.setAssetSold(assetPayWith.getAbbreviation());
                transactionData.setUsername(user.getFullName().toString());
                transactionData.setTriggerValue(0); //?????
                transactionData.setTransactionCost(0); //???
                Asset asset = assetDao.getOneBySymbol(symbol);
                // Check of asset bestaat anders aan maken
                assetPortfolioDao.checkExistElseCreate(asset,portfolioDao.getPortfolioIdByUserId((int) userId));
                transactionService.createNewTransaction(transactionData);
                // USD Asset niet updated
                //assetPortfolioService.update(symbol,portfolioDao.getPortfolioIdByUserId((int) userId).getPortfolioId(),
                //xs       transactieAmount);
            }
        }

        // TransactionData

        // Transactie???
        System.out.println("Transactie update assets");
        return ResponseEntity.ok().body(assetBuy);
        //return new ResponseEntity<String>("You are f....", HttpStatus.OK);
        //return "You are f....";
    }

}
