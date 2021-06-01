package com.example.cryptobank.service.transaction;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.domain.TransactionLog;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.GenerateMailContext;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.io.IOException;

@Service
public class TransactionService {

    private final RootRepository rootRepository;
    private final TokenService tokenService;
    private final MailSenderService mailSenderService;
    private final GenerateMailContext generateMailContext;


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

    public void deleteTransaction(int id) {
        rootRepository.deleteTransaction(id);
    }

    public void updateAdjustmentFactor(String assetName, double numberOfAssets, int buyerId, int sellerId) {
        rootRepository.updateAdjustmentFactor(assetName, numberOfAssets, buyerId, sellerId);
    }


    public void setTransaction(int seller, int buyer, double numberOfAssets, String assetSold, String assetBought, boolean setinFuture, double value, String username) throws InterruptedException, IOException {
            /*boolean valueReached = false;
            boolean bankIsBuyer = buyer == 1;
            while (valueReached = false) {
                if (bankIsBuyer) {
                    valueReached = checkValueAsset(value, assetSold, buyer);
                } else {
                    valueReached = checkValueAsset(value, assetBought, buyer);
                }
                Thread.sleep(60000); //TODO hier moet timer ipv sleep daarna alles hieronder in een apparte methode
            }*/
        ControlValueAsset(seller,buyer, numberOfAssets, assetSold, assetBought, value, username);
            /*Map<String, Map> bankAndClient = rootRepository.getAssetPortfolioByUsername(username);
            double amountBoughtAssets = rootRepository.getAsset(assetBought).getValueInUsd() / rootRepository.getAsset(assetSold).getValueInUsd();
            List<Boolean> sufficientAmount;
            if (bankIsBuyer){
                sufficientAmount = sufficientTransactionValue(numberOfAssets, amountBoughtAssets, bankAndClient, assetSold, assetBought, username);
            } else {
                sufficientAmount = sufficientTransactionValue(amountBoughtAssets, numberOfAssets, bankAndClient, assetBought, assetSold, username);
            }
            if (sufficientAmount.get(0) == false || sufficientAmount.get(1) == false){
                sendMailInsufficentAmount(username);
            } else if (bankIsBuyer && sufficientAmount.get(2) == false){
                executeTransactionInDollars(sufficientAmount, seller, buyer,numberOfAssets,assetBought);
            } else if(sufficientAmount.get(2) == false){

            }
            else {
                executeTransaction(sufficientAmount, seller, buyer, numberOfAssets, assetSold, assetBought);
            }*/
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

                                }
                            } else {
                                Asset asset = rootRepository.getAsset(assetBought);
                                System.out.println(asset.getName() + value);
                                if (asset.getValueInUsd() <= value) {
                                    ControlREcoursesAndExecute(seller, buyer, numberOfAssets, assetSold, assetBought, username, value);
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                Date.from(Instant.now()),
                Duration.ofSeconds(21).toMillis() //The timer. You can also choose onHours, onDays etc.
        );
    }


    public void ControlREcoursesAndExecute(int seller, int buyer, double numberOfAssets, String assetSold, String assetBought, String username, double value) throws IOException {
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
        } else if (buyer == 1 && sufficientAmount.get(2) == false) {
            executeTransactionInDollars(seller, buyer, numberOfAssets, assetBought, username, value);
        } else if (sufficientAmount.get(2) == false) {
            sendMailWithExcuse(assetBought, username, value);
        } else {
            executeTransaction(seller, buyer, numberOfAssets, assetSold, assetBought, username, value);
        }

    }
    //alles klopt
    private void executeTransaction(int seller, int buyer, double numberOfAssets, String assetSold, String assetBought, String username, double value) throws IOException {
        double tranasctioncost= calculateTransactionCost(numberOfAssets, assetBought);
        createNewTransaction(seller, buyer, numberOfAssets, tranasctioncost, assetSold, assetBought);
        if (buyer ==1){
            mailSenderService.sendMail(username, generateMailContext.transactionOrderConfirmed(username, assetSold, value), "Succesvole transactie:)");
        } else {
            mailSenderService.sendMail(username, generateMailContext.transactionOrderConfirmed(username, assetBought, value), "Succesvole transactie:)");
        }

    }
    //bank heeft niet genoeg assets al koper
    private void executeTransactionInDollars(int seller, int buyer, double numberOfAssets, String assetBought, String username, double value) throws IOException {
        double tranasctioncost= calculateTransactionCost(numberOfAssets, assetBought);
        createNewTransaction(seller, buyer, numberOfAssets, tranasctioncost, "USD", assetBought);
        mailSenderService.sendMail(username, generateMailContext.transactionOrderInDollars(username, assetBought, value), "Succesvole transactie in dollars:)");
    }

    //klant heeft niet genoeg
    private void sendMailInsufficentAmount(String assetBought, String username, double value) throws MalformedURLException {
            mailSenderService.sendMail(username, generateMailContext.transactionOrderCancelledDueToClient(username, assetBought, value), "Transactie geannuleerd");
    }

    private void sendMailWithExcuse(String assetBought, String username, double value) throws MalformedURLException {
        mailSenderService.sendMail(username, generateMailContext.transactionOrderCancelledDueToBank(username, assetBought, value), "Transactie geannuleerd");

    }


    private List sufficientTransactionValue(double numberOfAssetsCient, double numberOfAssetsBank, Map<String, Map> bankAndClient, String assetCient, String assetBank, String username){
        Map<Asset, Double> clientAssets = bankAndClient.get(username);
        Map<Asset, Double> bankAssets = bankAndClient.get("bank");
        Optional<Double> numberOfOwnedassetsCient = clientAssets.entrySet().stream().filter(e -> e.getKey().getName().equals(assetCient)).map(Map.Entry::getValue).findFirst();
        Optional<Double> numberOfOwnedDolarsCient = clientAssets.entrySet().stream().filter(e -> e.getKey().getName().equals("USD")).map(Map.Entry::getValue).findFirst();
        Optional<Double> numberOfOwnedassetsBank = bankAssets.entrySet().stream().filter(e -> e.getKey().getName().equals(assetBank)).map(Map.Entry::getValue).findFirst();
        List<Boolean> isSufficient = new ArrayList<>();
        double trasactioncost = calculateTransactionCost(numberOfAssetsCient, assetCient);
        isSufficient.add(numberOfOwnedassetsCient.get() >= numberOfAssetsCient);
        isSufficient.add(numberOfOwnedDolarsCient.get() >= trasactioncost);
        isSufficient.add(numberOfOwnedassetsBank.get() >= numberOfAssetsBank);
        return isSufficient;
    }

    /*@Scheduled(fixedDelay = 60000)*/
    private boolean checkValueAsset(double value, String assetName, int buyer) {
        Asset asset = rootRepository.getAsset(assetName);
        if (buyer == 1) {
            if (asset.getValueInUsd() >= value) {
                return true;
                //stop
            }
        } else {
            if (asset.getValueInUsd() <= value) {
                return true;
                //stop
            }
        }
        return false;
    }

}
