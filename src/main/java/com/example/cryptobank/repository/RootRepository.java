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

import java.util.List;

import java.util.Optional;

@Repository
public class RootRepository {

    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    private final UserDao userDao;
    private final PortfolioDao portfolioDao;
    private final ConversionDao conversionDao;
    private final AssetDao assetDao;
    private final LoginDAO loginDAO;
    private final ActorDao actorDao;

    public RootRepository(UserDao userDao, PortfolioDao portfolioDao, ConversionDao conversionDao, AssetDao assetDao, ActorDao actorDao, LoginDAO loginDAO) {
        logger.info("New RootRepository");
        this.userDao = userDao;
        this.portfolioDao = portfolioDao;
        this.conversionDao = conversionDao;
        this.assetDao = assetDao;
        this.loginDAO = loginDAO;
        this.actorDao = actorDao;
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

    public void saveUser(User user, Role role){
        logger.debug("RootRepository.save aangeroepen voor user " + user.getBSN());
        Actor actor = new Actor(role);
        actorDao.create(actor);
        user.setId(actor.getUserId());
        userDao.create(user);
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
}
