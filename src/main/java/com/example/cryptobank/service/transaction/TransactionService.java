package com.example.cryptobank.service.transaction;

import com.example.cryptobank.domain.*;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.GenerateMailContext;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.security.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final GenerateMailContext generateMailContext;


    private final String START_DATE = "2000-01-01 00:16:26";

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository, TokenService tokenService, MailSenderService mailSenderService, GenerateMailContext generateMailContext) {
        super();
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        this.mailSenderService = mailSenderService;
        this.generateMailContext = generateMailContext;
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

    public Transaction createNewTransaction(int seller, int buyer, double numberOfAssets, double transactionCost, String assetSold, String assetBought) {
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

    public List<Transaction> getTransactionHistory(int userId) throws JsonProcessingException {

//        List<Transaction> tempTransactionList = ;
//        List<String> transactionHistoryAsJsonString = new ArrayList<>();
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString;
//        for (Transaction transaction :tempTransactionList) {
//            jsonString = mapper.writeValueAsString(transaction);
//            transactionHistoryAsJsonString.add(jsonString);
//        }
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

    public void setTransaction(Map transactionData, String username) { // raw map. problemen bij casten??
        int seller = (int) transactionData.get(0);
        int buyer = (int) transactionData.get(1);
        double numberOfAssets = (double)  transactionData.get(2);
        String assetSold = (String) transactionData.get(3);
        String assetBought = (String) transactionData.get(4);
        double value = (double) transactionData.get(5);
        ControlValueAsset(seller, buyer, numberOfAssets, assetSold, assetBought, value, username);
    }

    public void ControlValueAsset(int seller, int buyer, double numberOfAssets, String assetSold, String assetBought, double value, String username) {
        final Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if (buyer == 1) {
                                Asset asset = rootRepository.getAsset(assetSold);
                                System.out.println(asset.getName() + value);
                                if (asset.getValueInUsd() >= value) {
                                    ControlREcoursesAndExecute(seller, buyer, numberOfAssets, assetSold, assetBought, username, value);
                                    timer.cancel();
                                    timer.purge();
                                }
                            } else {
                                Asset asset = rootRepository.getAsset(assetBought);
                                System.out.println(asset.getName() + value);
                                if (asset.getValueInUsd() <= value) {
                                    ControlREcoursesAndExecute(seller, buyer, numberOfAssets, assetSold, assetBought, username, value);
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
                Duration.ofSeconds(21).toMillis() //The timer. You can also choose onHours, onDays etc.
        );
    }


    public void ControlREcoursesAndExecute(int seller, int buyer, double numberOfAssets, String assetSold, String assetBought, String username, double value) throws IOException, MessagingException {
        Map<String, Map> bankAndClient = rootRepository.getAssetPortfolioByUsername(username);
        double amountBoughtAssets = rootRepository.getAsset(assetBought).getValueInUsd() / rootRepository.getAsset(assetSold).getValueInUsd();
        List<Boolean> sufficientAmount;
        if (buyer ==1) {
            sufficientAmount = sufficientTransactionValue(numberOfAssets, amountBoughtAssets, bankAndClient, assetSold, assetBought, username);
        } else {
            sufficientAmount = sufficientTransactionValue(amountBoughtAssets, numberOfAssets, bankAndClient, assetBought, assetSold, username);
        }
        if (sufficientAmount.get(0) == false || sufficientAmount.get(1) == false) {
            sendMailInsufficentAmount(assetBought, username, value);
        } else if (buyer == 1 && !sufficientAmount.get(2)) {
            executeTransactionInDollars(seller, buyer, numberOfAssets, assetBought, username, value);
        } else if (!sufficientAmount.get(2)) {
            sendMailWithExcuse(assetBought, username, value);
        } else {
            executeTransaction(seller, buyer, numberOfAssets, assetSold, assetBought, username, value);
        }
    }
    //alles klopt
    private void executeTransaction(int seller, int buyer, double numberOfAssets, String assetSold, String assetBought, String username, double value) throws IOException, MessagingException {
        double tranasctioncost= calculateTransactionCost(numberOfAssets, assetBought);
        createNewTransaction(seller, buyer, numberOfAssets, tranasctioncost, assetSold, assetBought);
        if (buyer ==1){
            mailSenderService.sendMail(username, generateMailContext.transactionOrderConfirmed(username, assetSold, value), "Succesvole transactie:)");
        } else {
            mailSenderService.sendMail(username, generateMailContext.transactionOrderConfirmed(username, assetBought, value), "Succesvole transactie:)");
        }
    }
    //bank heeft niet genoeg assets al koper
    private void executeTransactionInDollars(int seller, int buyer, double numberOfAssets, String assetBought, String username, double value) throws IOException, MessagingException {
        double tranasctioncost= calculateTransactionCost(numberOfAssets, assetBought);
        createNewTransaction(seller, buyer, numberOfAssets, tranasctioncost, "USD", assetBought);
        mailSenderService.sendMail(username, generateMailContext.transactionOrderInDollars(username, assetBought, value), "Succesvole transactie in dollars:)");
    }

    //klant heeft niet genoeg
    private void sendMailInsufficentAmount(String assetBought, String username, double value) throws MalformedURLException, MessagingException {
            mailSenderService.sendMail(username, generateMailContext.transactionOrderCancelledDueToClient(username, assetBought, value), "Transactie geannuleerd");
    }

    private void sendMailWithExcuse(String assetBought, String username, double value) throws MalformedURLException, MessagingException {
        mailSenderService.sendMail(username, generateMailContext.transactionOrderCancelledDueToBank(username, assetBought, value), "Transactie geannuleerd");

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
