package com.example.cryptobank.repository.jdbcklasses;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetPortfolio;
import com.example.cryptobank.domain.login.LoginAccount;
import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.domain.transaction.Transaction;
import com.example.cryptobank.domain.transaction.TransactionHTMLBank;
import com.example.cryptobank.domain.transaction.TransactionHTMLClient;
import com.example.cryptobank.domain.transaction.TransactionLog;
import com.example.cryptobank.domain.user.Actor;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.repository.daointerfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

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
    private final int STARTKAPITAAL = 2000;
    private final String TRADING_CURRENCY = "USD";
    private final int BANK_PORTFOLIO_ID = 101;

    public RootRepository(UserDao userDao, PortfolioDao portfolioDao, AssetDao assetDao, ActorDao actorDao, LoginDao loginDAO, AssetPortfolioDao assetPortfolioDao, TransactionDao transactionDao, LogDao logDao) {
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

    /**
     * Check in database whether user or login are already registered
     * @param userLoginAccount (UserLoginAccount)
     * @return (boolean)
     * @author David_Scager
     */
    public boolean alreadyRegistered(UserLoginAccount userLoginAccount){
        int bsn = userLoginAccount.getUser().getBSN();
        String username = userLoginAccount.getUser().getEmail();
        return userDao.userExists(username, bsn) && loginDAO.loginExists(username);
    }

    /**
     * Register new login account in database
     * @param user (User)
     * @param password (String)
     *
     * @author David_Scager
     */
    public void registerLogin(User user, String password){
        loginDAO.create(user.getEmail(), password);
        logger.info("LoginAccount registered");
    }

    /**
     * Register new user to database
     * by storing new actor, provided user, new portfolio
     * and adding first assets
     * @param user (User)
     * @param role (Role)
     *
     * @author David_Scager
     */
    public void registerUser(User user, Role role){
        logger.debug("RootRepository.registerUser aangeroepen voor user " + user.getBSN());
        Actor newActor = new Actor(role);
        actorDao.create(newActor);
        logger.info("Actor registered");
        user.setId(newActor.getUserId());
        userDao.create(user);
        Portfolio portfolio = new Portfolio(newActor);
        portfolioDao.create(portfolio);
        startAssetsNewUsers(portfolio.getPortfolioId());
        assetPortfolioDao.update(assetDao.getOneByName("USD"), portfolio, STARTKAPITAAL);
        logger.info("Portfolio registered");
        logger.info("User registered");
    }

    private void startAssetsNewUsers(int portfolioId){
        assetDao.getAssetOverview().forEach(asset ->
                assetPortfolioDao.create(new AssetPortfolio(asset.getAbbreviation(), portfolioId, 1, 0))
        );
    }

    public User getUserByUsername(String username) {
        return userDao.get(username);
    }

    public LoginAccount getLoginByUsername(String username) {
        return loginDAO.get(username);
    }

    public void storeResetToken(String username, String token) {
        LoginAccount loginAccount = loginDAO.get(username);
        loginDAO.update(loginAccount.getUsername(), loginAccount.getHash(), token);
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

    public void saveAsset(Asset asset) {
        assetDao.create(asset);
    }

    public List<Asset> showAssetOverview() {
        return assetDao.getAssetOverview();
    }

    public void updateAsset(Asset asset) {
        assetDao.update(asset);
    }

    public Asset getAssetByAbbreviation(String assetAbbreviation) {
        return assetDao.getOneBySymbol(assetAbbreviation);
    }

    public List<Asset> updateAssetsByApi() {
        assetDao.updateAssetsByApi();
        return assetDao.getAssetOverview();
    }

    public Asset updateAssetByApi(String name) {
        return assetDao.updateAssetByApi(name);
    }

    public void deleteTransaction(int id) {
        transactionDao.delete(id);
    }

    public Portfolio getPortfolioIdByUserId(int userId) {
        return portfolioDao.getPortfolioIdByUserId(userId);
    }

    public void saveTransactionAndLog(Transaction transaction) {
        transactionDao.saveTransaction(transaction);
        logDao.saveLog(transaction);
        updateAdjustmentFactor(transaction.getAssetBought(), transaction.getTransactionLog().getNumberOfAssetsBought(), transaction.getBuyer(), transaction.getSeller());
        updateAdjustmentFactor(transaction.getAssetSold(), transaction.getTransactionLog().getNumberOfAssetsSold(), transaction.getSeller(), transaction.getBuyer());
    }

    public Asset getAsset(String assetName){
        return assetDao.getOneByName(assetName);
    }

//    Is het beter de variablelen meteen in de update te stoppen?
    public void updateAssetPortfolioForTransaction(Transaction transaction) {
        Optional<Portfolio> tempPortfolioBuyer = portfolioDao.get(portfolioDao.getPortfolioIdByUserId(transaction.getBuyer()).getPortfolioId());
        Portfolio buyer = tempPortfolioBuyer.get();
        Optional<Portfolio> tempPortfolioSeller = portfolioDao.get(portfolioDao.getPortfolioIdByUserId(transaction.getSeller()).getPortfolioId());
        Portfolio seller = tempPortfolioSeller.get();
        double newAmountBuyerAssetBought = assetPortfolioDao.getAmountByAssetName(transaction.getAssetBought(), buyer.getPortfolioId()) + transaction.getTransactionLog().getNumberOfAssetsBought();
        double newAmountBuyerAssetSold = assetPortfolioDao.getAmountByAssetName(transaction.getAssetSold(), buyer.getPortfolioId()) - transaction.getTransactionLog().getNumberOfAssetsSold();
        double newAmountSellerAssetBought = assetPortfolioDao.getAmountByAssetName(transaction.getAssetBought(), seller.getPortfolioId()) - transaction.getTransactionLog().getNumberOfAssetsBought();
        double newAmountSellerAssetSold = assetPortfolioDao.getAmountByAssetName(transaction.getAssetSold(), seller.getPortfolioId()) + transaction.getTransactionLog().getNumberOfAssetsSold();
        assetPortfolioDao.update(assetDao.getOneByName(transaction.getAssetBought()), buyer, newAmountBuyerAssetBought);
        assetPortfolioDao.update(assetDao.getOneByName(transaction.getAssetSold()), buyer, newAmountBuyerAssetSold);
        assetPortfolioDao.update(assetDao.getOneByName(transaction.getAssetBought()), seller, newAmountSellerAssetBought);
        assetPortfolioDao.update(assetDao.getOneByName(transaction.getAssetSold()), seller, newAmountSellerAssetSold);
        handleTransactionCost(buyer, seller, transaction);
    }

    public void handleTransactionCost(Portfolio buyer, Portfolio seller, Transaction transaction) {
//        System.out.println("" + buyer.getActor().getRole());
        if(buyer.getActor().getRole().equals(Role.CLIENT) && seller.getActor().getRole().equals(Role.CLIENT)) {
            double tempTransactionCost = transaction.getTransactionLog().getTransactionCost() / 2;
            updateClientForTransactionCost(buyer, tempTransactionCost);
            updateClientForTransactionCost(seller, tempTransactionCost);
            updateBankForTransactionCost(transaction);
        } else if(buyer.getActor().getRole().equals(Role.BANK)) {
            updateClientForTransactionCost(seller, transaction.getTransactionLog().getTransactionCost());
            updateBankForTransactionCost(transaction);
        } else {
            updateClientForTransactionCost(buyer, transaction.getTransactionLog().getTransactionCost());
            updateBankForTransactionCost(transaction);
        }
    }

    public void updateBankForTransactionCost(Transaction transaction) {
        Optional<Portfolio> tempPortfolioBank = portfolioDao.get(BANK_PORTFOLIO_ID);
        Portfolio bank = tempPortfolioBank.get();
        double newUSDPositionBank = assetPortfolioDao.getAmountByAssetName(TRADING_CURRENCY, BANK_PORTFOLIO_ID) + transaction.getTransactionLog().getTransactionCost();
        assetPortfolioDao.update(assetDao.getOneByName(TRADING_CURRENCY), bank, newUSDPositionBank);
    }

    public void updateClientForTransactionCost(Portfolio client, double transactionCost) {
        double newUSDPositionClient = assetPortfolioDao.getAmountByAssetName(TRADING_CURRENCY, client.getPortfolioId()) - transactionCost;
        assetPortfolioDao.update(assetDao.getOneByName(TRADING_CURRENCY), client, newUSDPositionClient);
    }

    public void updateAdjustmentFactor(String assetName, double numberOfAssets, int buyerId, int sellerId) {
        Asset asset = assetDao.getOneByName(assetName);
        double dollarAmount = numberOfAssets * asset.getValueInUsd();
        Actor buyer = actorDao.get(buyerId).get();
        Actor seller = actorDao.get(sellerId).get();
        boolean buyFromBank = seller.getRole().equals(Role.BANK);
        System.out.println(" buyer "+ buyer.getUserId());
        boolean sellToBank = buyer.getRole().equals(Role.BANK);
        if (!asset.getAbbreviation().equals("USD")) {
            assetDao.updateAdjustmentFactor(asset, dollarAmount, buyFromBank, sellToBank);
        }
    }

    public TransactionLog createNewTransactionLog(String assetSold, String assetBought, double numberOfAssets, double transactionCost){
        Asset boughtAsset = assetDao.getOneByName(assetBought);
        Asset soldAsset = assetDao.getOneByName(assetSold);
        double soldAmount = (boughtAsset.getValueInUsd() * boughtAsset.getAdjustmentFactor() * numberOfAssets) / (soldAsset.getValueInUsd() * soldAsset.getAdjustmentFactor());

        return new TransactionLog(boughtAsset.getValueInUsd(), soldAsset.getValueInUsd(), boughtAsset.getAdjustmentFactor(), soldAsset.getAdjustmentFactor(), numberOfAssets, soldAmount, transactionCost);
    }

    public double calculateTransactionCost(double numberOfAssets, String asset) {
        Asset asset1 = assetDao.getOneByName(asset);
        double cost = numberOfAssets * asset1.getValueInUsd();
        return transactionDao.calculateTransactionCost(cost);
    }

    public List<Asset> showPortfolioOverview(int portfolioId) {
        return assetPortfolioDao.getAssetOverview(portfolioId);
    }

    public List<Transaction> getTradesForUser(int userID) {
        List<Transaction> tempTransactionList = transactionDao.getTransactionsForUser(userID);
        for (Transaction transaction:tempTransactionList ) {
            TransactionLog tempTransactionLog = logDao.getTransactionLogByTransactionId(transaction.getTransactionId());
            transaction.setTransactionLog(tempTransactionLog);
        }

        return tempTransactionList;
    }

//    Mag deze ertussen uit gehaald worden (rechtstreeks service naar dao)?
    public Map<Asset, Double> getAssetOverviewWithAmount(int portfolioId) {
        return assetPortfolioDao.getAssetOverviewWithAmount(portfolioId);
    }


//    waarschijnlijk niet meer nodig -MB
    public String showPortfolioValue(int portfolioId) {
        List<Asset> tempPortfolioValue = assetPortfolioDao.getAssetOverview(portfolioId);
        double tempTotalPortfolioValue = 0;
        double tempValueYesterday = 0;
        double tempValueLastWeek = 0;
        double tempValueLastMonth = 0;
        for (Asset asset : tempPortfolioValue) {
            double tempAmount = assetPortfolioDao.getAmountByAssetName(asset.getAbbreviation(), portfolioId);
            tempTotalPortfolioValue = tempTotalPortfolioValue + Math.round(asset.getValueInUsd() * tempAmount);
            tempValueYesterday = tempValueYesterday + Math.round(asset.getValueYesterday() * tempAmount * 100) / 100;
            tempValueLastWeek = tempValueLastWeek + Math.round(asset.getValueLastWeek() * tempAmount);
            tempValueLastMonth = tempValueLastMonth + Math.round(asset.getValueLastMonth() * tempAmount);
        }
        return buildString(tempTotalPortfolioValue, tempValueYesterday, tempValueLastWeek, tempValueLastMonth);
    }

//    zeer waarschijnlijk niet meer nodig - MB
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

    public void updateLoginAccount(String username, String hash, String token) {
        loginDAO.update(username, hash, token);
    }

    public LoginAccount getLoginAccount(String username) {
         return loginDAO.get(username);
    }


    public Map<String, Map> getAssetPortfolioByUsername(String username) {
        Map<String, Map> bankAndClientAssets = new HashMap<>();
        long userId = userDao.get(username).getId();
        int portfolioId = portfolioDao.getPortfolioIdByUserId((int)userId).getPortfolioId();
        bankAndClientAssets.put("bank", getAssetOverviewWithAmount(101));
        bankAndClientAssets.put(username, getAssetOverviewWithAmount(portfolioId));
        return bankAndClientAssets;
    }

    public ArrayList<TransactionHTMLClient> clientListForTransactionHTML(String username){
        ArrayList<TransactionHTMLClient> assetList = new ArrayList<>();
        long userId = userDao.get(username).getId();
        int portfolioId = portfolioDao.getPortfolioIdByUserId((int)userId).getPortfolioId();
        Map<Asset, Double> clientAssets = getAssetOverviewWithAmount(portfolioId);
        clientAssets.forEach((asset, aDouble) -> assetList.add(new TransactionHTMLClient(asset.getName(), aDouble, asset.getAbbreviation(), asset.getValueInUsd())));
        return assetList;
    }

    public ArrayList<TransactionHTMLBank> bankListForTransactionHTML(){
        ArrayList<TransactionHTMLBank> assetList = new ArrayList<>();
        Map<Asset, Double> clientAssets = getAssetOverviewWithAmount(101);//portfolioId from bank
        clientAssets.forEach((asset, aDouble) -> assetList.add(new TransactionHTMLBank(asset.getName(), asset.getValueInUsd(), asset.getAbbreviation(), asset.getValueYesterday(), aDouble)));
        return assetList;
    }
}
