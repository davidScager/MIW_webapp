package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Conversion;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.security.HashAndSalt;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RootRepository {

    private final UserDao userDao;
    private final PortfolioDao portfolioDao;
    private final ConversionDao conversionDao;
    private final AssetDao assetDao;
    private final LoginDAO loginDAO;

    public RootRepository(UserDao userDao, PortfolioDao portfolioDao, ConversionDao conversionDao, AssetDao assetDao, LoginDAO loginDAO) {
        this.userDao = userDao;
        this.portfolioDao = portfolioDao;
        this.conversionDao = conversionDao;
        this.assetDao = assetDao;
        this.loginDAO = loginDAO;
    }

    public boolean saveLogin(User user, HashAndSalt hashAndSalt){
        if (!loginDAO.isRegistered(user)) {
            loginDAO.register(user, hashAndSalt);
            return true;
        }
        return false;
    }

    public void saveActor(User user){
    }

    public void saveUser(User user){
        userDao.create(user);
    }

    public void savePortfolio(Portfolio portfolio){//te gebruiken in actor check staat in portfolio dao
        portfolioDao.create(portfolio);
    }

    public void saveConversion(Conversion conversion){
        conversionDao.create(conversion);
    }

    public void saveAsset(Asset asset) { assetDao.create(asset); }

    public List<Asset> showAssetOverview() { return assetDao.getAssetOverview();}
}
