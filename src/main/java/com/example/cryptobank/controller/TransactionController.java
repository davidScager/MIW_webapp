package com.example.cryptobank.controller;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.transaction.*;
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
    public void createTransaction(@RequestHeader(value = "Authorization") String token, @RequestBody TransactionData transactionData) throws IOException {
        transactionData.setUsername(tokenService.parseToken(token, "session"));
        transactionData.setTransactionCost(transactionService.calculateTransactionCost(transactionData.getNumberOfAssets(), transactionData.getAssetBought()));
        transactionService.setTransaction(transactionData);
    }
}
