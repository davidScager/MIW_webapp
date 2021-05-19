package com.example.cryptobank.service;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.repository.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import java.io.IOException;

@Service
public class TransactionService {

    private final RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository) {
        super();
        this.rootRepository = rootRepository;
        logger.info("New TransactionService");
    }

    public Map<Asset, Double> getAssetOverVieuwWithAmount(int portfolioId){
        return rootRepository.getAssetOverVieuwWithAmount(portfolioId);
    }

    public Transaction createNewTransaction(int seller, int buyer, double numberOfAssets, double transactionCost, String assetSold, String assetBought) throws IOException {
        Transaction newTransaction = new Transaction(seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought);
        rootRepository.saveTransaction(newTransaction);
        return newTransaction;
    }

}
