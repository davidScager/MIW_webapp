package com.example.cryptobank.service.transaction;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.transaction.*;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.GenerateMailContent;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.security.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import java.io.IOException;

@Service
public class TransactionService {

    private final RootRepository rootRepository;
    private final TokenService tokenService;
    private final MailSenderService mailSenderService;
    private final GenerateMailContent generateMailContent;

    private final String START_DATE = "2000-01-01 00:16:26";
    private final String BUY = "Aankoop";
    private final String SELL = "Verkoop";

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository, TokenService tokenService, MailSenderService mailSenderService, GenerateMailContent generateMailContent) {
        super();
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        this.mailSenderService = mailSenderService;
        this.generateMailContent = generateMailContent;
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

    public Map<Asset, Double> getAssetOverviewWithAmount(int portfolioId) {
        return rootRepository.getAssetOverviewWithAmount(portfolioId);
    }

    public ArrayList<TransactionHTMLBank> bankArrayList(){
        return rootRepository.bankListForTransactionHTML();
    }

    public Transaction createNewTransaction(TransactionData transactionData) {
        TransactionLog tempTransactionLog = rootRepository.createNewTransactionLog(transactionData.getAssetSold(), transactionData.getAssetBought(), transactionData.getNumberOfAssets() , transactionData.getTransactionCost());
        Transaction newTransaction = new Transaction(transactionData.getSeller(), transactionData.getBuyer(), transactionData.getAssetSold(), transactionData.getAssetBought(), tempTransactionLog);
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

    public List<TransactionHistory> getTransactionHistory(int userId) throws JsonProcessingException {

        List<Transaction> tempTransactionList = rootRepository.getTradesForUser(userId);
        List<TransactionHistory> tempTransactionHistoryList = new ArrayList<>();
        boolean buy;
        for (Transaction transaction: tempTransactionList) {
            if(transaction.getAssetBought()!="USD") {
                buy = true;
                tempTransactionHistoryList.add(makeTransactionHistoryObject(transaction, buy));
            }
            if(transaction.getAssetSold()!="USD") {
                buy = false;
                tempTransactionHistoryList.add(makeTransactionHistoryObject(transaction, buy));
            }
        }

        return tempTransactionHistoryList;
    }

    public TransactionHistory makeTransactionHistoryObject(Transaction transaction, boolean buy) {
        if(buy) {
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

    public void setTransaction(TransactionData transactionData) {
        if (transactionData.getTriggerValue() == 0) {
            createNewTransaction(transactionData);
        } else {
           controlValueAsset(transactionData);
        }
    }

    public void controlValueAsset(TransactionData transactionData) {
        final Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if (transactionData.getBuyer() == 1) {
                                Asset asset = rootRepository.getAsset(transactionData.getAssetSold());
                                if (asset.getValueInUsd() >= transactionData.getTriggerValue()) {
                                    controlResourcesAndExecute(transactionData);
                                    timer.cancel();
                                    timer.purge();
                                }
                            } else {
                                Asset asset = rootRepository.getAsset(transactionData.getAssetBought());
                                if (asset.getValueInUsd() <= transactionData.getTriggerValue()) {
                                    controlResourcesAndExecute(transactionData);
                                    timer.cancel();
                                    timer.purge();
                                }
                            }
                        } catch (IOException | MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                Date.from(Instant.now()),
                Duration.ofSeconds(60).toMillis() //The timer. You can also choose onHours, onDays etc.
        );
    }


    public void controlResourcesAndExecute(TransactionData transactionData) throws IOException, MessagingException {
        Map<String, Map> bankAndClient = rootRepository.getAssetPortfolioByUsername(transactionData.getUsername());
        double amountBoughtAssets = rootRepository.getAsset(transactionData.getAssetBought()).getValueInUsd() / rootRepository.getAsset(transactionData.getAssetSold()).getValueInUsd();
        List<Boolean> sufficientAmount;
        if (transactionData.getBuyer() == 1) {
            sufficientAmount = sufficientTransactionValue(transactionData.getNumberOfAssets(), amountBoughtAssets, bankAndClient, transactionData.getAssetSold(), transactionData.getAssetBought(), transactionData.getUsername());
        } else {
            sufficientAmount = sufficientTransactionValue(amountBoughtAssets, transactionData.getNumberOfAssets(), bankAndClient, transactionData.getAssetBought(), transactionData.getAssetSold(), transactionData.getUsername());
        }
        if (sufficientAmount.get(0) == false || sufficientAmount.get(1) == false) {
            sendMailInsufficentAmount(transactionData);
        } else if (transactionData.getBuyer() == 1 && !sufficientAmount.get(2)) {
            executeTransactionInDollars(transactionData);
        } else if (!sufficientAmount.get(2)) {
            sendMailWithExcuse(transactionData);
        } else {
            executeTransaction(transactionData);
        }
    }

    //alles klopt
    private void executeTransaction(TransactionData transactionData) throws IOException, MessagingException {
        createNewTransaction(transactionData);
        if (transactionData.getBuyer() ==1){
            //mailSenderService.sendMail(username, generateMailContext.transactionOrderConfirmed(username, assetSold, value), "Succesvole transactie:)");
        } else {
            //mailSenderService.sendMail(username, generateMailContext.transactionOrderConfirmed(username, assetBought, value), "Succesvole transactie:)");
        }
    }
    //bank heeft niet genoeg assets al koper
    private void executeTransactionInDollars(TransactionData transactionData) throws IOException, MessagingException {
        transactionData.setAssetSold("USD");
        createNewTransaction(transactionData);
        //mailSenderService.sendMail(username, generateMailContext.transactionOrderInDollars(username, assetBought, value), "Succesvole transactie in dollars:)");
    }

    //klant heeft niet genoeg
    private void sendMailInsufficentAmount(TransactionData transactionData) throws MalformedURLException, MessagingException {
            //mailSenderService.sendMail(username, generateMailContext.transactionOrderCancelledDueToClient(username, assetBought, value), "Transactie geannuleerd");
    }

    private void sendMailWithExcuse(TransactionData transactionData) throws MalformedURLException, MessagingException {
        //mailSenderService.sendMail(username, generateMailContext.transactionOrderCancelledDueToBank(username, assetBought, value), "Transactie geannuleerd");

    }


    private List sufficientTransactionValue(double numberOfAssetsCient, double numberOfAssetsBank, Map<String, Map> bankAndClient, String assetCient, String assetBank, String username){
        Map<Asset, Double> clientAssets = bankAndClient.get(username);
        Map<Asset, Double> bankAssets = bankAndClient.get("bank");
        Optional<Double> numberOfOwnedassetsCient = clientAssets.entrySet().stream().filter(e -> e.getKey().getAbbreviation().equals(assetCient)).map(Map.Entry::getValue).findFirst();
        Optional<Double> numberOfOwnedDolarsCient = clientAssets.entrySet().stream().filter(e -> e.getKey().getAbbreviation().equals("USD")).map(Map.Entry::getValue).findFirst();
        Optional<Double> numberOfOwnedassetsBank = bankAssets.entrySet().stream().filter(e -> e.getKey().getAbbreviation().equals(assetBank)).map(Map.Entry::getValue).findFirst();
        List<Boolean> isSufficient = new ArrayList<>();
        double trasactioncost = calculateTransactionCost(numberOfAssetsCient, assetCient);
        isSufficient.add(numberOfOwnedassetsCient.get() >= numberOfAssetsCient);
        isSufficient.add(numberOfOwnedDolarsCient.get() >= trasactioncost);
        isSufficient.add(numberOfOwnedassetsBank.get() >= numberOfAssetsBank);
        return isSufficient;
    }
}
