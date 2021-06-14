package com.example.cryptobank.controller;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.transaction.Transaction;
import com.example.cryptobank.domain.transaction.TransactionHTMLBank;
import com.example.cryptobank.domain.transaction.TransactionHTMLClient;
import com.example.cryptobank.domain.transaction.TransactionHistory;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import com.example.cryptobank.service.login.UserService;
import com.example.cryptobank.service.security.TokenService;
import com.example.cryptobank.service.transaction.TransactionService;
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
    private final UserService userService;
    private final PortfolioService portfolioService;

    @Autowired
    public TransactionController(TransactionService transactionService, TokenService tokenService, UserService userService, PortfolioService portfolioService) {
        super();
        this.transactionService = transactionService;
        this.tokenService = tokenService;
        this.userService = userService;
        this.portfolioService = portfolioService;
        logger.info("New TransactionController");
    }

    @PostMapping("/transactionorder")
    public ResponseEntity<ArrayList<TransactionHTMLClient>> authorizeAndGetAssets(@RequestHeader("Authorization") String token) {
        ArrayList<TransactionHTMLClient> transactionHTMLClients = transactionService.authorizeAndGetAssets(token);
        if (transactionHTMLClients == null) {
            URI uri = URI.create("http://localhost:8080/login");
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).location(uri).build();
            logger.info(responseEntity.toString());
            return responseEntity;
        }
        return ResponseEntity.ok().body(transactionHTMLClients);
    }

    @PostMapping("/plantransaction")
    public void planTransaction(@RequestParam("Authorization") String token, @RequestBody Map<String, String> transActionData) throws InterruptedException, IOException {
        String username = tokenService.parseToken(token, "session");
        transactionService.setTransaction(transActionData, username);
    }

    @GetMapping("/assetoverviewfrombank")
    public ArrayList<TransactionHTMLBank> getAssetOverviewWithAmount() {
        return transactionService.bankArrayList();
    }

    @GetMapping("/myavailableassetstosell")
    public Map<Asset, Double> getAssetOverviewOfUser(@RequestHeader(value = "Authorization") String token) {
        User user = userService.getUserFromToken(token);
        int portfolioId = portfolioService.getPortfolioIdByUserId(user.getId());
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
    public Transaction createTransaction(@RequestBody Map transactionData) throws IOException {
        int seller = (int) transactionData.get(0);
        int buyer = (int) transactionData.get(1);
        double numberOfAssets = (double)  transactionData.get(2);
        String assetSold = (String) transactionData.get(3);
        String assetBought = (String) transactionData.get(4);
        double transactionCost = transactionService.calculateTransactionCost(numberOfAssets, assetBought);
        Transaction newTransaction = transactionService.createNewTransaction(seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought);
        return newTransaction;
    }
}
