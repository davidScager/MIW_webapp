package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.domain.TransactionHistory;
import com.example.cryptobank.service.security.TokenService;
import com.example.cryptobank.service.transaction.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).location(uri).build();
            logger.info(responseEntity.toString());
            return responseEntity;
        }
        return ResponseEntity.ok().body(bankAndClientAssets);
    }

    @PostMapping("/plantransaction")
    public void planTransaction(@RequestParam("Authorization") String token, @RequestBody Map<String, String> transActionData) throws InterruptedException, IOException {
        String username = tokenService.parseToken(token, "session");
        transactionService.setTransaction(transActionData, username);
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

    @GetMapping("/mostrecenttransaction")
    public Transaction mostRecentTransactionHandler(@RequestParam int userId, @RequestParam String assetName) throws IOException {
        return transactionService.getMostRecentBuyOrSell(userId, assetName);
    }

    @GetMapping("/transactionhistory")
    public List<TransactionHistory> transactionHistoryHandler(@RequestParam int userId) throws IOException {
        return transactionService.getTransactionHistory(userId);
    }

    @PostMapping("/createtransaction")
    public Transaction createTransaction(@RequestParam int seller,
                                                @RequestParam int buyer, @RequestParam double numberOfAssets,
                                                @RequestParam String assetSold,
                                                @RequestParam String assetBought) throws IOException {
        double transactionCost = transactionService.calculateTransactionCost(numberOfAssets, assetBought);
        Transaction newTransaction = transactionService.createNewTransaction(seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought);
        return newTransaction;
    }
}
