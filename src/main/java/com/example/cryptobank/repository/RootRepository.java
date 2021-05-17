package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Conversion;
import com.example.cryptobank.domain.Actor;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.security.HashAndSalt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

/**
 * Root for all repository functionalities
 */
@Repository
public class RootRepository {
    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);
    private final UserDao userDao;
    private final PortfolioDao portfolioDao;
    private final ConversionDao conversionDao;
    private final AssetDao assetDao;
    private final LoginDao loginDAO;
    private final ActorDao actorDao;
    private final AssetPortfolioDao assetPortfolioDao;
    private final TransactionDao transactionDao;
    private final LogDao logDao;

    public RootRepository(UserDao userDao, PortfolioDao portfolioDao, ConversionDao conversionDao, AssetDao assetDao, ActorDao actorDao, LoginDao loginDAO, AssetPortfolioDao assetPortfolioDao, TransactionDao transactionDao, LogDao logDao) {
        logger.info("New RootRepository");
        this.userDao = userDao;
        this.portfolioDao = portfolioDao;
        this.conversionDao = conversionDao;
        this.assetDao = assetDao;
        this.loginDAO = loginDAO;
        this.actorDao = actorDao;
        this.assetPortfolioDao = assetPortfolioDao;
        this.transactionDao = transactionDao;
        this.logDao = logDao;
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
    public boolean registerUser(User user, Role role){
        logger.debug("RootRepository.registerUser aangeroepen voor user " + user.getBSN());
        if (!loginDAO.isRegistered(user) && userDao.get(user.getBSN()).isEmpty()){
            Actor newActor = new Actor(role);
            actorDao.create(newActor);
            savePortfolio(new Portfolio(newActor));
            user.setId(newActor.getUserId());
            userDao.create(user);
            //still need to add starting amount in EUR to portfolio
            return true;
        }
        return false;
    }

    /**
     * Register new login account
     * @param user (User)
     * @param hashAndSalt (HashAndSalt)
     *
     * @author David_Scager
     */
    public void registerLogin(User user, HashAndSalt hashAndSalt){
        loginDAO.create(user, hashAndSalt);
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

    public void savePortfolio(Portfolio portfolio){//te gebruiken in actor check staat in portfolio dao
        portfolioDao.create(portfolio);
    }

    public void saveConversion(Conversion conversion){
        conversionDao.create(conversion);
    }

    public void saveAsset(Asset asset) { assetDao.create(asset); }

    public List<Asset> showAssetOverview() { return assetDao.getAssetOverview();}

    public int getPortfolioIdByUserId(int userId) { return portfolioDao.getPortfolioIdByUserId(userId);}

    public List<String> showPortfolioOverview(int portfolioId) {
        List<Asset> tempPortfolioOverview = assetPortfolioDao.getAssetOverview(portfolioId);
        List<String> tempAssetOverview = new ArrayList();
        tempAssetOverview.add("Portefeuille-overzicht voor portfolio " + portfolioId + ": ");
        for (Asset asset : tempPortfolioOverview ) {
            double tempAmount = assetPortfolioDao.getAmountByAssetName(asset.getName(), portfolioId);
            int tempTransactionId = transactionDao.getTransactionIdMostRecentTrade(asset.getName());
            double tradedRate = logDao.getTradedRateByTransactionId(tempTransactionId);
            double stijgingDaling = Math.round( (asset.getValueInUsd() / tradedRate - 1 ) * 100);
            tempAssetOverview.add("Asset: /" + asset.getName() + ", huidige koers (in USD): " + asset.getValueInUsd() + ", aantal in portefeuille: " + tempAmount +
                    ". De waarde van deze positie is: " + Math.round(asset.getValueInUsd() * tempAmount * 100) / 100 +
                    " USD. Sinds uw laatste aankoop is deze positie met " + stijgingDaling + " % gestegen.");
        }
        return tempAssetOverview; }

    public String showPortfolioValue(int portfolioId) {
        List<Asset> tempPortfolioValue = assetPortfolioDao.getAssetOverview(portfolioId);
        List<String> tempAssetOverview = new ArrayList();
        double tempTotalPortfolioValue = 0;
        tempAssetOverview.add("Portefeuille-overzicht voor portfolio " + portfolioId + ": ");
        for (Asset asset : tempPortfolioValue ) {
            double tempAmount = assetPortfolioDao.getAmountByAssetName(asset.getName(), portfolioId);
            tempTotalPortfolioValue = tempTotalPortfolioValue + Math.round(asset.getValueInUsd() * tempAmount);
        }
        String portfolioValueOutput = "De waarde van uw portefeuille is momenteel " + tempTotalPortfolioValue + " dollar.";
        return portfolioValueOutput; }
}
