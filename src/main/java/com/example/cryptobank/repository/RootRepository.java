package com.example.cryptobank.repository;

import com.example.cryptobank.domain.*;
import com.example.cryptobank.security.HashAndSalt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.Optional;

/**
 * Root for all repository functionalities
 */
@Repository
public class RootRepository {
    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);
    private final UserDao userDao;
    private final PortfolioDao portfolioDao;
    private final AssetDao assetDao;
    private final LoginDao loginDAO;
    private final ActorDao actorDao;
    private final AssetPortfolioDao assetPortfolioDao;
    private final TransactionDao transactionDao;
    private final LogDao logDao;
    private final JdbcAssetPortfolioDao jdbcAssetPortfolioDao;
    private final JdbcAssetDao jdbcAssetDao;
    private final int STARTKAPITAAL = 200;

    public RootRepository(UserDao userDao, PortfolioDao portfolioDao, AssetDao assetDao, ActorDao actorDao, LoginDao loginDAO, AssetPortfolioDao assetPortfolioDao, TransactionDao transactionDao, LogDao logDao, JdbcAssetPortfolioDao jdbcAssetPortfolioDao, JdbcAssetDao jdbcAssetDao) {
        this.jdbcAssetPortfolioDao = jdbcAssetPortfolioDao;
        this.jdbcAssetDao = jdbcAssetDao;
        logger.info("New RootRepository");
        this.userDao = userDao;
        this.portfolioDao = portfolioDao;
        this.assetDao = assetDao;
        this.loginDAO = loginDAO;
        this.actorDao = actorDao;
        this.assetPortfolioDao = assetPortfolioDao;
        this.transactionDao = transactionDao;
        this.logDao = logDao;
    }

    public void saveUser(User user){
        userDao.create(user);
    }


    /**
     * Register new user if that user is not yet registered and does not
     * already have a login account.
     * @param user (User)
     * @param role (Role)
     * @return (boolean) true if registration succesfull, false if already registered
     *
     * @author David_Scager
     */
    public void registerUser(User user, Role role){
        logger.debug("RootRepository.registerUser aangeroepen voor user " + user.getBSN());
        Actor newActor = new Actor(role);
        actorDao.create(newActor);
        user.setId(newActor.getUserId());
        userDao.create(user);
        Portfolio portfolio = new Portfolio(newActor);
        savePortfolio(portfolio);
        //jdbcAssetPortfolioDao.update(jdbcAssetDao.getOneByName("EUR"), portfolio, STARTKAPITAAL);
    }

    public User getUserByUsername(String username) {
        return userDao.get(username);
    }

    public User getUserByBsn(int bsn){
        return userDao.list().stream().filter(u -> u.getBSN() == bsn).findFirst().orElse(null);
    }

    public LoginAccount getLoginByUsername(String username) {
        return loginDAO.get(username).orElse(null);
    }

    public void storeResetToken(String username, String token) {
        Optional<LoginAccount> loginAccount = loginDAO.get(username);
        LoginAccount loginAccount1 = loginAccount.orElse(null);
        loginDAO.update(loginAccount1.getUsername(), new HashAndSalt(loginAccount1.getHash(), loginAccount1.getSalt()), token);
    }

    /**
     * Register new login account
     * @param user (User)
     * @param hashAndSalt (HashAndSalt)
     *
     * @author David_Scager
     */
    public void registerLogin(User user, HashAndSalt hashAndSalt){
        loginDAO.create(user.getEmail(), hashAndSalt);
    }

    public void saveActor(Actor actor){
        actorDao.create(actor);
    }

    public Actor getActor(long userId){
        logger.debug("Rootrepository.getActor aangeroepen voor actor " + userId);
        Optional<Actor> optionalActor = actorDao.get(userId);
        Actor actor = optionalActor.orElseThrow(RuntimeException::new);
        return actor;
    }

    public Actor updateActor(Actor actor){
        logger.debug("RootRepository.updateActor aangeroepen voor actor " + actor.getUserId());
        actorDao.update(actor, actor.getUserId());
        return getActor(actor.getUserId());
    }

    public void savePortfolio(Portfolio portfolio) {//te gebruiken in actor check staat in portfolio dao
        portfolioDao.create(portfolio);
    }

    public void saveAsset(Asset asset) {
        assetDao.create(asset);
    }

    public List<Asset> showAssetOverview() {
        return assetDao.getAssetOverview();
    }

    public void updateAsset(Asset asset) {
        assetDao.update(asset);
    }

    public List<Asset> updateAssetsByApi() { return assetDao.getAssetOverview(); }

    public Asset updateAssetByApi(String name) {
        return assetDao.updateAssetByApi(name);
    }

    public int getPortfolioIdByUserId(int userId) {
        return portfolioDao.getPortfolioIdByUserId(userId);
    }

    public void saveTransactionAndLog(Transaction transaction) {
        transactionDao.saveTransaction(transaction);
        logDao.saveLog(transaction);
        updateAdjustmentFactor(transaction.getAssetBought(), transaction.getTransactionLog().getNumberOfAssetsBought(), transaction.getBuyer(), transaction.getSeller());
        updateAdjustmentFactor(transaction.getAssetSold(), transaction.getTransactionLog().getNumberOfAssetsSold(), transaction.getBuyer(), transaction.getSeller());
    }

    public void updateAdjustmentFactor(String assetName, double numberOfAssets, int buyerId, int sellerId) {
        Asset asset = assetDao.getOneByName(assetName);
        if (asset.getAbbreviation().equals("USD")) {
            return;
        }
        double dollarAmount = numberOfAssets * asset.getValueInUsd();
        Optional<Actor> tempBuyer = actorDao.get(buyerId);
        Actor buyer = tempBuyer.get();
        Optional<Actor> tempSeller = actorDao.get(sellerId);
        Actor seller = tempSeller.get();
        boolean buyFromBank = buyer.getRole().equals("BANK") ? true : false;
        boolean sellToBank = seller.getRole().equals("BANK") ? true : false;
        assetDao.updateAdjustmentFactor(asset, dollarAmount, buyFromBank, sellToBank);
    }

    public TransactionLog createNewTransactionLog(String assetSold, String assetBought, double numberOfAssets, double transactionCost){
        Asset boughtAsset = assetDao.getOneByName(assetBought);
        Asset soldAsset = assetDao.getOneByName(assetSold);
        double soldAmount = (boughtAsset.getValueInUsd() * boughtAsset.getAdjustmentFactor() * numberOfAssets) / (soldAsset.getValueInUsd() * soldAsset.getAdjustmentFactor());

//        TransactionLog tempTransactionLog = new TransactionLog();
//        tempTransactionLog.setBoughtAssetTransactionRate(boughtAsset.getValueInUsd());
//        tempTransactionLog.setSoldAssetTransactionRate(soldAsset.getValueInUsd());
//        tempTransactionLog.setBoughtAssetAdjustmentFactor(boughtAsset.getAdjustmentFactor());
//        tempTransactionLog.setSoldAssetAdjustmentFactor(soldAsset.getAdjustmentFactor());
//        tempTransactionLog.setNumberOfAssetsBought(numberOfAssets);
//        tempTransactionLog.setNumberOfAssetsSold(soldAmount);
//        tempTransactionLog.setTransactionCost(transactionCost);
        return new TransactionLog(boughtAsset.getValueInUsd(), soldAsset.getValueInUsd(), boughtAsset.getAdjustmentFactor(), soldAsset.getAdjustmentFactor(), numberOfAssets, soldAmount, transactionCost);
    }

    public double calculateTransactionCost(double numberOfAssets, String asset) {
        Asset asset1 = assetDao.getOneByName(asset);
        double cost = numberOfAssets * asset1.getValueInUsd();
        return transactionDao.calculateTransactionCost(cost);
    }

    public List<String> showPortfolioOverview(int portfolioId) {
        List<Asset> tempPortfolioOverview = assetPortfolioDao.getAssetOverview(portfolioId);
        List<String> tempAssetOverview = new ArrayList();
        tempAssetOverview.add("Portefeuille-overzicht voor portfolio " + portfolioId + ": ");
        for (Asset asset : tempPortfolioOverview) {
            double tempAmount = assetPortfolioDao.getAmountByAssetName(asset.getAbbreviation(), portfolioId);
            int tempTransactionId = transactionDao.getTransactionIdMostRecentTrade(asset.getAbbreviation());
            double tradedRate = logDao.getTradedRateByTransactionId(tempTransactionId);
            double stijgingDaling = Math.round((asset.getValueInUsd() / tradedRate - 1) * 100);
            tempAssetOverview.add("Asset: /" + asset.getName() + ", huidige koers (in USD): " + asset.getValueInUsd() + ", aantal in portefeuille: " + tempAmount +
                    ". De waarde van deze positie is: " + Math.round(asset.getValueInUsd() * tempAmount * 100) / 100 +
                    " USD. Sinds uw laatste aankoop is deze positie met " + stijgingDaling + " % gestegen.");
        }
        return tempAssetOverview;
    }

    public Map<Asset, Double> getAssetOverVieuwWithAmount(int portfolioId){
        return assetPortfolioDao.getAssetOvervieuwWithAmmount(portfolioId);
    }

    public String showPortfolioValue(int portfolioId) {
        List<Asset> tempPortfolioValue = assetPortfolioDao.getAssetOverview(portfolioId);
        double tempTotalPortfolioValue = 0;
        double tempValueYesterday = 0;
        double tempValueLastWeek = 0;
        double tempValueLastMonth = 0;
        for (Asset asset : tempPortfolioValue ) {
            double tempAmount = assetPortfolioDao.getAmountByAssetName(asset.getAbbreviation(), portfolioId);
            tempTotalPortfolioValue = tempTotalPortfolioValue + Math.round(asset.getValueInUsd() * tempAmount);
            tempValueYesterday = tempValueYesterday + Math.round(asset.getValueYesterday() * tempAmount * 100) / 100;
            tempValueLastWeek = tempValueLastWeek + Math.round(asset.getValueLastWeek() * tempAmount);
            tempValueLastMonth = tempValueLastMonth + Math.round(asset.getValueLastMonth() * tempAmount);
        }
        return buildString(tempTotalPortfolioValue, tempValueYesterday, tempValueLastWeek, tempValueLastMonth);
    }

    private String buildString(double now, double yesterday, double week, double month) {
        StringBuilder portfolioValueOutput = new StringBuilder();
        portfolioValueOutput.append("De waarde van uw portefeuille is momenteel " + now + " dollar. \n" +
                "\tGisteren was de waarde " + yesterday +
                " dollar (" + Math.round( (now / yesterday - 1 ) * 100) + "% gestegen).\n" +
                "\tVorige week was de waarde " + week +
                " dollar (" + Math.round( (now / week - 1 ) * 100) + "% gestegen).\n" +
                "\tVorige maand was de waarde " + month +
                " dollar (" + Math.round( (now / month - 1 ) * 100) + "% gestegen).");
        return portfolioValueOutput.toString();
    }


}
