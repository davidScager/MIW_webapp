package com.example.cryptobank.service.assetenportfolio;

import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.assetenportfolio.AssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    private final RootRepository rootRepository;

    private final Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    public PortfolioService(RootRepository rootReposistory) {
        super();
        this.rootRepository = rootReposistory;
        logger.info("New PortfolioService");
    }

    public List<String> showAssetOverview(int userId) {
        Portfolio portfolio = rootRepository.getPortfolioIdByUserId(userId);
        List<String> overviewPortfolioList = rootRepository.showPortfolioOverview(portfolio.getPortfolioId());
        return overviewPortfolioList;
    }

    public String showValueOfPortfolio(int userId) {
        Portfolio portfolio = rootRepository.getPortfolioIdByUserId(userId);
        String portfolioValueString = rootRepository.showPortfolioValue(portfolio.getPortfolioId());
        return portfolioValueString;
    }
}
