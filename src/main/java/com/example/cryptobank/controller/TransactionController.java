package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.service.TokenService;
import com.example.cryptobank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

import java.io.IOException;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;
    private final TokenService tokenService;

    @Autowired
    public TransactionController(TransactionService transactionService, TokenService tokenService) {
        super();
        this.transactionService = transactionService;
        this.tokenService = tokenService;
        logger.info("New TransactionController");
    }

    @PostMapping("/transactionorder")
    public ResponseEntity<Map> authorizeAndGetAssets(@RequestParam("Authorization") String token) {
        Map <String, Map> bankAndClientAssets = transactionService.authorizeAndGetAssets(token);
        if (bankAndClientAssets == null) {
            URI uri = URI.create("http://localhost:8080/login");
            return ResponseEntity.notFound().location(uri).header("Authorization").build(); //responseEntity werkt niet
        }
        return ResponseEntity.ok().header("Authorization").body(bankAndClientAssets);
    }



    @GetMapping("/assetoverviewfrombank")
    public Map<Asset, Double> getAssetOverVieuwWithAmount() {
        return transactionService.getAssetOverVieuwWithAmount(106);//dit is de portfolioId van de bank
    }

    @GetMapping("/myavalableassetstosell")
    public Map<Asset, Double> getAssetOverVieuwfoUser(@RequestParam int portfolioId) {
        return transactionService.getAssetOverVieuwWithAmount(portfolioId);
    }

    @GetMapping("/transactioncost")
    public double transactionCost(@RequestParam double numberOfAssets, @RequestParam String assetBought) throws IOException {
        return transactionService.calculateTransactionCost(numberOfAssets, assetBought);
    }

    @PostMapping("/createtransaction")
    public Transaction createTransactionHandler(@RequestParam int seller,
                                                @RequestParam int buyer, @RequestParam double numberOfAssets,
                                                @RequestParam String assetSold,
                                                @RequestParam String assetBought) throws IOException {
        double transactionCost = transactionService.calculateTransactionCost(numberOfAssets, assetBought);
        Transaction newTransaction = transactionService.createNewTransaction(seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought);
        //TODO update adjesment factor for both assets
        return newTransaction;
    }



}
