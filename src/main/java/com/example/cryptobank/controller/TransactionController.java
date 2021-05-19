package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/createtransaction")
    public Transaction createTransactionHandler(@RequestParam int seller,
                                                @RequestParam int buyer, @RequestParam double numberOfAssets,
                                                @RequestParam double transactionCost, @RequestParam String assetSold,
                                                @RequestParam String assetBought) throws IOException {
        Transaction newTransaction = transactionService.createNewTransaction(seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought);
        return newTransaction;
    }

}
