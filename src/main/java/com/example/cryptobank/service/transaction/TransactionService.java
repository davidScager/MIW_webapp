package com.example.cryptobank.service.transaction;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.maildata.AssetMailData;
import com.example.cryptobank.domain.maildata.MailData;
import com.example.cryptobank.domain.transaction.*;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.GenerateMailContent;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.mailSender.mailsenderfacade.MailSenderFacade;
import com.example.cryptobank.service.security.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
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
    private final MailSenderFacade mailSenderFacade;

    private final String START_DATE = "2000-01-01 00:16:26";
    private final String BUY = "Aankoop";
    private final String SELL = "Verkoop";

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RootRepository rootRepository, TokenService tokenService, @Qualifier("transactionTriggerMailFacade") MailSenderFacade mailSenderFacade) {
        super();
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        this.mailSenderFacade = mailSenderFacade;
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
        List<TransactionHistory> finalTransactionHistoryList = new ArrayList<>();
        boolean buy;
        for (Transaction transaction: tempTransactionList) {
            if(!transaction.getAssetBought().equals("USD")) {
                buy = true;
                finalTransactionHistoryList.add(makeTransactionHistoryObject(transaction, buy));
            }
            if(!transaction.getAssetSold().equals("USD")) {
                buy = false;
                finalTransactionHistoryList.add(makeTransactionHistoryObject(transaction, buy));
            }
        }

        return finalTransactionHistoryList;
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
            System.out.println(assetName);
            System.out.println(transaction.getAssetBought());
            System.out.println(transaction.getAssetSold());
            if(transaction.getAssetBought().equals(assetName) || transaction.getAssetSold().equals(assetName)) {
                System.out.println("Hooray!!");
                if(tempTradeDate.compareTo(lastTrade) > 1) {
                    System.out.println("Hoorah!");
                    lastTrade = tempTradeDate;
                    tempMostRecentTransaction = transaction;
                }
            }
        }
        return tempMostRecentTransaction;

//        Was nodig om data te kunnen vergelijken, misschien weer nodig als systeem transacties genereerd.
//        LocalDateTime.parse(tempTradeDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).isAfter(LocalDateTime.parse(lastTrade, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
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
                Duration.ofSeconds(60).toMillis()
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

    private void executeTransaction(TransactionData transactionData) throws IOException, MessagingException {
        createNewTransaction(transactionData);
        MailData assetMailData = new AssetMailData();
        assetMailData.setReceiver(transactionData.getUsername());
        assetMailData.setMailSubject("Succesvolle Transactie");
        if (transactionData.getBuyer() == 1) {
            assetMailData.setMailText(String.format("Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
                            "Wij kunnen u mededelen dat de transactie is geslaagd. Ga naar de website om uw transactie te bekijken via onderstaande link."
                    , transactionData.getUsername(), transactionData.getAssetSold(), transactionData.getTriggerValue()));
        } else {
            assetMailData.setMailText(String.format("Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
                            "Wij kunnen u mededelen dat de transactie is geslaagd."
                    , transactionData.getUsername(), transactionData.getAssetBought(), transactionData.getTriggerValue()));
        }
        mailSenderFacade.sendMail(assetMailData);
    }

    private void executeTransactionInDollars(TransactionData transactionData) throws IOException, MessagingException {
        transactionData.setAssetSold("USD");
        createNewTransaction(transactionData);
        MailData assetMailData = new AssetMailData();
        assetMailData.setReceiver(transactionData.getUsername());
        assetMailData.setMailSubject("Succesvolle transactie in dollars");
        assetMailData.setMailText(String.format("Geachte %s,\nU heeft een order geplaatst voor de verkoop van %s bij het bereiken van de waarde %.2f." +
                        "Helaas hebben wij op het moment van transactie niet voldoende van de gewenste assets. Wij betalen u uit in dollars" +
                        "Ga naar de website om uw transactie te bekijken via onderstaande link."
                , transactionData.getUsername(), transactionData.getAssetSold(), transactionData.getTriggerValue()));
        mailSenderFacade.sendMail(assetMailData);
    }

    private void sendMailInsufficentAmount(TransactionData transactionData) throws MalformedURLException, MessagingException, FileNotFoundException {
        MailData assetMailData = new AssetMailData();
        assetMailData.setReceiver(transactionData.getUsername());
        assetMailData.setMailSubject("Succesvolle transactie in dollars");
        assetMailData.setMailText(String.format("Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
                        "Helaas heeft u niet de voldoende hoeveelheid assets om uw order te voltooien. Wij zullen deze daarom annuleren. Ga naar de website voor het maken " +
                        "van een nieuwe transactie via onderstaande link."
                , transactionData.getUsername(), transactionData.getAssetBought(), transactionData.getTriggerValue()));
        mailSenderFacade.sendMail(assetMailData);
    }

    private void sendMailWithExcuse(TransactionData transactionData) throws MalformedURLException, MessagingException, FileNotFoundException {
        MailData assetMailData = new AssetMailData();
        assetMailData.setReceiver(transactionData.getUsername());
        assetMailData.setMailSubject("Transactie geannuleerd");
        assetMailData.setMailText(String.format("Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
                        "Helaas heeft de bank niet de voldoende hoeveelheid assets om uw order te voltooien. Onze welgemeende excuses. Ga naar de website voor het maken " +
                        "van een nieuwe transactie via onderstaande link."
                , transactionData.getUsername(), transactionData.getAssetBought(), transactionData.getTriggerValue()));
        mailSenderFacade.sendMail(assetMailData);
    }


    private List sufficientTransactionValue(double numberOfAssetsClient, double numberOfAssetsBank, Map<String, Map> bankAndClient, String assetClient, String assetBank, String username){
        Map<Asset, Double> clientAssets = bankAndClient.get(username);
        Map<Asset, Double> bankAssets = bankAndClient.get("bank");
        Optional<Double> numberOfOwnedAssetsClient = clientAssets.entrySet().stream().filter(e -> e.getKey().getAbbreviation().equals(assetClient)).map(Map.Entry::getValue).findFirst();
        Optional<Double> numberOfOwnedDollarsClient = clientAssets.entrySet().stream().filter(e -> e.getKey().getAbbreviation().equals("USD")).map(Map.Entry::getValue).findFirst();
        Optional<Double> numberOfOwnedAssetsBank = bankAssets.entrySet().stream().filter(e -> e.getKey().getAbbreviation().equals(assetBank)).map(Map.Entry::getValue).findFirst();
        List<Boolean> isSufficient = new ArrayList<>();
        double transactionCost = calculateTransactionCost(numberOfAssetsClient, assetClient);
        isSufficient.add(numberOfOwnedAssetsClient.get() >= numberOfAssetsClient);
        isSufficient.add(numberOfOwnedDollarsClient.get() >= transactionCost);
        isSufficient.add(numberOfOwnedAssetsBank.get() >= numberOfAssetsBank);
        return isSufficient;
    }
}
