package com.example.cryptobank.controller;

import com.example.cryptobank.domain.transaction.*;
import com.example.cryptobank.domain.urls.UrlAdresses;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import com.example.cryptobank.service.login.UserService;
import com.example.cryptobank.service.security.TokenService;
//import com.example.cryptobank.service.transaction.TransactionHelper;
import com.example.cryptobank.service.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.*;

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
//    private final TransactionHelper transactionHelper;
    private UrlAdresses urlAdresses = new UrlAdresses();

    @Autowired
    public TransactionController(TransactionService transactionService, TokenService tokenService, UserService userService, PortfolioService portfolioService) {
        super();
        this.transactionService = transactionService;
        this.tokenService = tokenService;
        this.userService = userService;
        this.portfolioService = portfolioService;
//        this.transactionHelper = transactionHelper;
        logger.info("New TransactionController");
    }

    @GetMapping
    public RedirectView showResetPage() {
        return new RedirectView(urlAdresses.getTransactionPage());
    }

    @GetMapping("/historypage")
    public RedirectView transactionHistoryHtmlHandler() {
        return new RedirectView(urlAdresses.getTransactionHistoryPageUrl());
    }

    @PostMapping("/myassets")
    public ResponseEntity<ArrayList<TransactionHTMLClient>> authorizeAndGetAssets(@RequestHeader("Authorization") String token) {
        ArrayList<TransactionHTMLClient> transactionHTMLClients = transactionService.authorizeAndGetAssets(token);
        if (transactionHTMLClients == null) {
            URI uri = URI.create(urlAdresses.getLoginPage());
            ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).location(uri).build();
            logger.info(responseEntity.toString());
            return responseEntity;
        }
        transactionHTMLClients.sort(Comparator.comparing(TransactionHTMLClient::getAssetName));
        return ResponseEntity.ok().body(transactionHTMLClients);
    }

    @GetMapping("/bankassets")
    public ArrayList<TransactionHTMLBank> getAssetOverviewWithAmount() {
        ArrayList<TransactionHTMLBank> bankArrayList = transactionService.bankArrayList();
        bankArrayList.sort(Comparator.comparing(TransactionHTMLBank::getAssetName));
        return bankArrayList;
    }

    /*@GetMapping("/myavailableassetstosell")
    public Map<Asset, Double> getAssetOverviewOfUser(@RequestHeader(value = "Authorization") String token) {
        User user = userService.getUserFromToken(token);
        int portfolioId = portfolioService.getPortfolioIdByUserId(user.getId());
        return transactionService.getAssetOverviewWithAmount(portfolioId);
    }*/

    @GetMapping("/transactioncost")
    public double transactionCost(@RequestParam double numberOfAssets, @RequestParam String assetBought) throws IOException {
        return transactionService.calculateTransactionCost(numberOfAssets, assetBought);
    }

//    dit kan weg - MB
//    @GetMapping("/mostrecenttransaction")
//    public Transaction mostRecentTransactionHandler(@RequestParam int userId, @RequestParam String assetName) throws IOException {
//        return transactionService.getMostRecentBuyOrSell(userId, assetName);
//    }

    @GetMapping("/history")
    public List<TransactionHistory> transactionHistoryHandler(@RequestHeader(value = "Authorization") String token) throws IOException {
        User user = userService.getUserFromToken(token);
        return transactionService.getTransactionHistory((int) user.getId());
    }

    @PostMapping("/createtransaction")
    public void createTransaction(@RequestHeader(value = "Authorization") String token, @RequestBody TransactionData transactionData) throws IOException {
        transactionData.setUsername(tokenService.parseToken(token, "session"));
        logger.debug(transactionData.toString());
        transactionData.setTransactionCost(transactionService.calculateTransactionCost(transactionData.getNumberOfAssets(), transactionData.getAssetBought()));
        transactionService.setTransaction(transactionData);
    }

    @GetMapping("/userid")
    public ResponseEntity<?> getUserIdByUsername(@RequestHeader(value = "Authorization") String token) {
        Map<String, Long> map = new HashMap<>();
        map.put("UserId" ,userService.getUserFromToken(token).getId());
        return ResponseEntity.ok().body(map);
    }
}