package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Actor;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RootRepository {

    private final Logger logger = LoggerFactory.getLogger(RootRepository.class);

    private final UserDao userDao;
    private final PortfolioDao portfolioDao;
    private final ActorDao actorDao;

    public RootRepository(UserDao userDao, PortfolioDao portfolioDao, ActorDao actorDao) {
        this.userDao = userDao;
        this.portfolioDao = portfolioDao;
        this.actorDao = actorDao;
        logger.info("New RootRepository");
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
}
