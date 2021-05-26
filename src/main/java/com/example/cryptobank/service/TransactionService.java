package com.example.cryptobank.service;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.domain.TransactionLog;
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

    public Map<Asset, Double> getAssetOverviewWithAmount(int portfolioId) {
        return rootRepository.getAssetOverviewWithAmount(portfolioId);
    }

    public Transaction createNewTransaction(int seller, int buyer, double numberOfAssets, double transactionCost, String assetSold, String assetBought) throws IOException {
        TransactionLog tempTransactionLog = rootRepository.createNewTransactionLog(assetSold, assetBought, numberOfAssets, transactionCost);
        Transaction newTransaction = new Transaction(seller, buyer, assetSold, assetBought, tempTransactionLog);
        rootRepository.saveTransactionAndLog(newTransaction);
        rootRepository.updateAssetPortfolioForTransaction(newTransaction);
        return newTransaction;
    }

    public double calculateTransactionCost(double numberOfAssets, String asset) {
        return rootRepository.calculateTransactionCost(numberOfAssets, asset);
    }

    public void deleteTransaction(int id) {
        rootRepository.deleteTransaction(id);
    }

    public void updateAdjustmentFactor(String assetName, double numberOfAssets, int buyerId, int sellerId) {
        rootRepository.updateAdjustmentFactor(assetName, numberOfAssets, buyerId, sellerId);
    }

}
