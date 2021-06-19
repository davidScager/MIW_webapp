package com.example.cryptobank.service.transaction;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.maildata.AssetMailData;
import com.example.cryptobank.domain.maildata.MailData;
import com.example.cryptobank.domain.transaction.TransactionData;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.mailsenderfacade.MailSenderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
public class TriggerService {
    private final String TRANSACTION_ORDER_CONFIRMATION = "Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
            "Wij kunnen u mededelen dat de transactie is geslaagd.";
    private final String GO_TO_WEBSITE = "Ga naar de website om uw transactie te bekijken via onderstaande link.";
    private final String SUCCESSFULL_TRANSACTION = "Succesvolle Transactie";
    private final String SUCCESSFULL_TRANSACTION_DOLLARS = "Succesvolle Transactie in dollars";
    private final String TRANSACTION_CANCELED = "Transactie geannuleerd";
    private final String INSUFFICIENT_ASSETS_PAYOUT = "Geachte %s,\nU heeft een order geplaatst voor de verkoop van %s bij het bereiken van de waarde %.2f." +
            "Helaas hebben wij op het moment van transactie niet voldoende van de gewenste assets. Wij betalen u uit in dollars" +
            "Ga naar de website om uw transactie te bekijken via onderstaande link.";
    private final String INSUFFICIENT_ASSETS_CLIENT = "Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
            "Helaas heeft u niet de voldoende hoeveelheid assets om uw order te voltooien. Wij zullen deze daarom annuleren. Ga naar de website voor het maken " +
            "van een nieuwe transactie via onderstaande link.";
    private final String INSUFFICIENT_ASSETS_APOLOGY = "Geachte %s,\nU heeft een order geplaatst voor de koop/verkoop van %s bij het bereiken van de waarde %.2f." +
            "Helaas heeft de bank niet de voldoende hoeveelheid assets om uw order te voltooien. Onze welgemeende excuses. Ga naar de website voor het maken " +
            "van een nieuwe transactie via onderstaande link.";

    private final MailSenderFacade mailSenderFacade;
    private final RootRepository rootRepository;
    private final TransactionService transactionService;

    @Autowired
    public TriggerService(@Qualifier("transactionTriggerMailFacade") MailSenderFacade mailSenderFacade, RootRepository rootRepository, @Lazy TransactionService transactionService) {
        this.mailSenderFacade = mailSenderFacade;
        this.rootRepository = rootRepository;
        this.transactionService = transactionService;
    }

    public void checkBuyerOrSeller(TransactionData transactionData, Timer timer) throws MessagingException, IOException {
        if (transactionData.getBuyer() == 1) {
            Asset asset = rootRepository.getAsset(transactionData.getAssetSold());
            if (asset.getValueInUsd() >= transactionData.getTriggerValue()) {
                handleTimer(timer, transactionData);
            }
        } else {
            Asset asset = rootRepository.getAsset(transactionData.getAssetBought());
            if (asset.getValueInUsd() <= transactionData.getTriggerValue()) {
                handleTimer(timer, transactionData);
            }
        }
    }

    public void handleTimer(Timer timer, TransactionData transactionData) throws MessagingException, IOException {
        timer.cancel();
        timer.purge();
        controlResourcesAndExecute(transactionData);
    }

    public void controlResourcesAndExecute(TransactionData transactionData) throws IOException, MessagingException {
        Map<String, Map> bankAndClient = rootRepository.getAssetPortfolioByUsername(transactionData.getUsername());
        double amountBoughtAssets = rootRepository.getAsset(transactionData.getAssetBought()).getValueInUsd() / rootRepository.getAsset(transactionData.getAssetSold()).getValueInUsd();
        List<Boolean> sufficientAmount = getSufficientAmount(transactionData, amountBoughtAssets, bankAndClient);

        if (!sufficientAmount.get(0) || !sufficientAmount.get(1)) {
            sendMailInsufficentAmount(transactionData);
        } else if (transactionData.getBuyer() == 1 && !sufficientAmount.get(2)) {
            executeTransactionInDollars(transactionData);
        } else if (!sufficientAmount.get(2)) {
            sendMailWithExcuse(transactionData);
        } else {
            executeTransaction(transactionData);
        }
    }

    public List<Boolean> getSufficientAmount(TransactionData transactionData, double amountBoughtAssets, Map<String, Map> bankAndClient){
        List<Boolean> sufficientAmount;
        if (transactionData.getBuyer() == 1) {
            sufficientAmount = sufficientTransactionValue(transactionData.getNumberOfAssets(), amountBoughtAssets, bankAndClient, transactionData.getAssetSold(), transactionData.getAssetBought(), transactionData.getUsername());
        } else {
            sufficientAmount = sufficientTransactionValue(amountBoughtAssets, transactionData.getNumberOfAssets(), bankAndClient, transactionData.getAssetBought(), transactionData.getAssetSold(), transactionData.getUsername());
        }
        return sufficientAmount;
    }

    private void executeTransaction(TransactionData transactionData) throws IOException, MessagingException {
        transactionService.createNewTransaction(transactionData);
        MailData assetMailData = new AssetMailData();
        assetMailData.setReceiver(transactionData.getUsername());
        assetMailData.setMailSubject(SUCCESSFULL_TRANSACTION);
        if (transactionData.getBuyer() == 1) {
            assetMailData.setMailText(String.format(TRANSACTION_ORDER_CONFIRMATION + " " + GO_TO_WEBSITE
                    , transactionData.getUsername(), transactionData.getAssetSold(), transactionData.getTriggerValue()));
        } else {
            assetMailData.setMailText(String.format(TRANSACTION_ORDER_CONFIRMATION
                    , transactionData.getUsername(), transactionData.getAssetBought(), transactionData.getTriggerValue()));
        }
        mailSenderFacade.sendMail(assetMailData);
    }

    private void executeTransactionInDollars(TransactionData transactionData) throws IOException, MessagingException {
        transactionData.setAssetSold("USD");
        transactionService.createNewTransaction(transactionData);
        MailData assetMailData = new AssetMailData();
        assetMailData.setReceiver(transactionData.getUsername());
        assetMailData.setMailSubject(SUCCESSFULL_TRANSACTION_DOLLARS);
        assetMailData.setMailText(String.format(INSUFFICIENT_ASSETS_PAYOUT
                , transactionData.getUsername(), transactionData.getAssetSold(), transactionData.getTriggerValue()));
        mailSenderFacade.sendMail(assetMailData);
    }

    private void sendMailInsufficentAmount(TransactionData transactionData) throws MalformedURLException, MessagingException, FileNotFoundException {
        MailData assetMailData = new AssetMailData();
        assetMailData.setReceiver(transactionData.getUsername());
        assetMailData.setMailSubject(SUCCESSFULL_TRANSACTION_DOLLARS);
        assetMailData.setMailText(String.format(INSUFFICIENT_ASSETS_CLIENT
                , transactionData.getUsername(), transactionData.getAssetBought(), transactionData.getTriggerValue()));
        mailSenderFacade.sendMail(assetMailData);
    }

    private void sendMailWithExcuse(TransactionData transactionData) throws MalformedURLException, MessagingException, FileNotFoundException {
        MailData assetMailData = new AssetMailData();
        assetMailData.setReceiver(transactionData.getUsername());
        assetMailData.setMailSubject(TRANSACTION_CANCELED);
        assetMailData.setMailText(String.format(INSUFFICIENT_ASSETS_APOLOGY
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
        double transactionCost = transactionService.calculateTransactionCost(numberOfAssetsClient, assetClient);
        isSufficient.add(numberOfOwnedAssetsClient.get() >= numberOfAssetsClient);
        isSufficient.add(numberOfOwnedDollarsClient.get() >= transactionCost);
        isSufficient.add(numberOfOwnedAssetsBank.get() >= numberOfAssetsBank);
        return isSufficient;
    }

    public void controlValueAsset(TransactionData transactionData) {
        final Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            checkBuyerOrSeller(transactionData, timer);
                        } catch (IOException | MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                Date.from(Instant.now()),
                Duration.ofSeconds(60).toMillis()
        );
    }
}
