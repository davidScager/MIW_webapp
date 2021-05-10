package com.example.cryptobank.repository;

import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class RootRepository {

    private final UserDao userDao;
    private final PortfolioDao portfolioDao;

    public RootRepository(UserDao userDao, PortfolioDao portfolioDao) {
        this.userDao = userDao;
        this.portfolioDao = portfolioDao;
    }

    public void saveUser(User user){
        userDao.create(user);
    }

    public void savePortfolio(Portfolio portfolio){//te gebruiken in actor check staat in portfolio dao
        portfolioDao.create(portfolio);
    }
}
