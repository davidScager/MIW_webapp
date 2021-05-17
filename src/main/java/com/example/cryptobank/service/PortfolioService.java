package com.example.cryptobank.service;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.repository.RootRepository;
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
        int portfolioId = rootRepository.getPortfolioIdByUserId(userId);
        List<String> overviewPortfolioList = rootRepository.showPortfolioOverview(portfolioId);
        return overviewPortfolioList;
    }

    public String showValueOfPortfolio(int userId) {
        int portfolioId = rootRepository.getPortfolioIdByUserId(userId);
        String portfolioValueString = rootRepository.showPortfolioValue(portfolioId);
        return portfolioValueString;
    }
}
