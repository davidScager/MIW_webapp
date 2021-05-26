package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.service.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import java.io.IOException;

@RestController
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        super();
        this.transactionService = transactionService;
        logger.info("New TransactionController");
    }

    @GetMapping("/assetoverviewfrombank")
    public Map<Asset, Double> getAssetOverviewWithAmount() {
        return transactionService.getAssetOverviewWithAmount(101);//dit is de portfolioId van de bank
    }

    @GetMapping("/myavalableassetstosell") //available
    public Map<Asset, Double> getAssetOverviewOfUser(@RequestParam int portfolioId) {
        return transactionService.getAssetOverviewWithAmount(portfolioId);
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
        return newTransaction;
    }



}
