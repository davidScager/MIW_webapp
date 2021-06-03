package com.example.cryptobank.service.transaction;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.domain.TransactionLog;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import java.io.IOException;

@Service
public class TransactionService {

    private final RootRepository rootRepository;
    private final TokenService tokenService;

    private final String START_DATE = "2000-01-01 00:16:26";

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository, TokenService tokenService) {
        super();
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        logger.info("New TransactionService");
    }

    public Map authorizeAndGetAssets(String token) {
        String username;
        try {
            username = tokenService.parseToken(token, "session");
            logger.info(username + "vanuit Token");
        } catch (Exception e) {
            logger.info("token is ongeldig");
            return null;
        }
        return rootRepository.getAssetPortfolioByUsername(username);
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

    public Transaction getMostRecentBuyOrSell(int userId, String assetName) {

        return getMostRecentTrade(rootRepository.getTradesForUser(userId), assetName);
    }

    public List<Transaction> getTransactionHistory(int userId) {
        return rootRepository.getTradesForUser(userId);
    }

    public Boolean determineBuyOrSell(Transaction transaction, String assetName) {
        Boolean buy = null;
        if (transaction.getAssetSold().equals(assetName)) {
            buy = false;
        } else if (transaction.getAssetBought().equals(assetName)) {
            buy = true;
        }

        return buy;
    }

    public Transaction getMostRecentTrade(List<Transaction> list, String assetName) {
        String lastTrade = START_DATE;
        String tempTradeDate;
        Transaction tempMostRecentTransaction = null;

        for (Transaction transaction:list) {
            tempTradeDate = transaction.getTimestamp();
            if(transaction.getAssetBought().equals(assetName) || transaction.getAssetSold().equals(assetName)) {
                if(LocalDateTime.parse(tempTradeDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).isAfter(LocalDateTime.parse(lastTrade, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))) {
                    lastTrade = tempTradeDate;
                    tempMostRecentTransaction = transaction;
                }
            }
        }

        return tempMostRecentTransaction;
    }

    public void deleteTransaction(int id) {
        rootRepository.deleteTransaction(id);
    }

    public void updateAdjustmentFactor(String assetName, double numberOfAssets, int buyerId, int sellerId) {
        rootRepository.updateAdjustmentFactor(assetName, numberOfAssets, buyerId, sellerId);
    }


}
