package com.example.cryptobank.service.transaction;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetPortfolio;
import com.example.cryptobank.domain.transaction.*;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.security.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private final RootRepository rootRepository;
    private final TokenService tokenService;
    private final TriggerService triggerService;

    private final String START_DATE = "2000-01-01 00:16:26";
    private final String BUY = "Aankoop";
    private final String SELL = "Verkoop";

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository, TokenService tokenService, TriggerService triggerService) {
        super();
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        this.triggerService = triggerService;
        logger.info("New TransactionService");
    }

    public ArrayList<TransactionHTMLClient> authorizeAndGetAssets(String token) {
        String username;
        try {
            username = tokenService.parseToken(token, "session");
            logger.info(username + "vanuit Token");
        } catch (Exception e) {
            logger.info("token is ongeldig");
            return null;
        }
        return rootRepository.clientListForTransactionHTML(username);
    }

    public ArrayList<TransactionHTMLBank> bankArrayList() {
        return rootRepository.bankListForTransactionHTML();
    }


    public Transaction getMostRecentBuyOrSell(int userId, String assetName) {

        return getMostRecentTrade(rootRepository.getTradesForUser(userId), assetName);
    }

    public List<TransactionHistory> getTransactionHistory(int userId) throws JsonProcessingException {
        List<Transaction> tempTransactionList = rootRepository.getTradesForUser(userId);
        List<TransactionHistory> finalTransactionHistoryList = new ArrayList<>();
        boolean buy;
        for (Transaction transaction : tempTransactionList) {
            if (!transaction.getAssetBought().equals("USD")) {
                buy = true;
                finalTransactionHistoryList.add(makeTransactionHistoryObject(transaction, buy));
            }
            if (!transaction.getAssetSold().equals("USD")) {
                buy = false;
                finalTransactionHistoryList.add(makeTransactionHistoryObject(transaction, buy));
            }
        }

        return finalTransactionHistoryList;
    }

    public TransactionHistory makeTransactionHistoryObject(Transaction transaction, boolean buy) {
        if (buy) {
            return new TransactionHistory(transaction.getTransactionId(), rootRepository.getAssetByAbbreviation(transaction.getAssetBought()),
                    transaction.getTransactionLog().getBoughtAssetTransactionRate(), transaction.getTransactionLog().getBoughtAssetAdjustmentFactor(),
                    transaction.getTransactionLog().getNumberOfAssetsBought(), transaction.getTimestamp(), BUY);
        } else {
            return new TransactionHistory(transaction.getTransactionId(), rootRepository.getAssetByAbbreviation(transaction.getAssetSold()),
                    transaction.getTransactionLog().getSoldAssetTransactionRate(), transaction.getTransactionLog().getSoldAssetAdjustmentFactor(),
                    transaction.getTransactionLog().getNumberOfAssetsSold(), transaction.getTimestamp(), SELL);
        }
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

        for (Transaction transaction : list) {
            tempTradeDate = transaction.getTimestamp();
            System.out.println(assetName);
            System.out.println(transaction.getAssetBought());
            System.out.println(transaction.getAssetSold());
            if (transaction.getAssetBought().equals(assetName) || transaction.getAssetSold().equals(assetName)) {
                System.out.println("Hooray!!");
                if (tempTradeDate.compareTo(lastTrade) > 1) {
                    System.out.println("Hoorah!");
                    lastTrade = tempTradeDate;
                    tempMostRecentTransaction = transaction;
                }
            }
        }
        return tempMostRecentTransaction;
    }

    public void setTransaction(TransactionData transactionData) {
        if (transactionData.getSeller() == 0) {
            List<AssetPortfolio> list = rootRepository.getAssetPortfolioByAbbrevation(transactionData.getAssetSold());
            list.forEach(assetPortfolio -> {
                if (assetPortfolio.getAmount() >= transactionData.getNumberOfAssets()) {
                    int id = rootRepository.getUserIdByPortfolioId(assetPortfolio.getPortfolioId());
                    transactionData.setSeller(id);
                }
            });
        }
        if (transactionData.getTriggerValue() == 0) {
            createNewTransaction(transactionData);
        } else {
            triggerService.controlValueAsset(transactionData);
        }
    }

    public Transaction createNewTransaction(TransactionData transactionData) {
        TransactionLog tempTransactionLog = rootRepository.createNewTransactionLog(transactionData.getAssetSold(), transactionData.getAssetBought(), transactionData.getNumberOfAssets(), transactionData.getTransactionCost());
        Transaction newTransaction = new Transaction(transactionData.getSeller(), transactionData.getBuyer(), transactionData.getAssetSold(), transactionData.getAssetBought(), tempTransactionLog);
        rootRepository.saveTransactionAndLog(newTransaction);
        rootRepository.updateAssetPortfolioForTransaction(newTransaction);
        return newTransaction;
    }

    public double calculateTransactionCost(double numberOfAssets, String asset) {
        return rootRepository.calculateTransactionCost(numberOfAssets, asset);
    }
}
