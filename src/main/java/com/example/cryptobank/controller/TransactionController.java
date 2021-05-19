package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Map<Asset, Double> getAssetOverVieuwWithAmount(){
        return transactionService.getAssetOverVieuwWithAmount(106);//dit is de portfolioId van de bank
    }

    @GetMapping("/myavalableassetstosell")
    public Map<Asset, Double> getAssetOverVieuwfoUser(@RequestParam int portfolioId){
        return transactionService.getAssetOverVieuwWithAmount(portfolioId);
    }
}
