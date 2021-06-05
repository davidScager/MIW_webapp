package com.example.cryptobank.service.assetenportfolio;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.domain.Transaction;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.assetenportfolio.AssetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PortfolioService {

    private final RootRepository rootRepository;
    private final PortfolioDao portfolioDao;

    private final Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    public PortfolioService(RootRepository rootReposistory, PortfolioDao portfolioDao) {
        super();
        this.rootRepository = rootReposistory;
        this.portfolioDao = portfolioDao;
        logger.info("New PortfolioService");
    }

    public List<String> showAssetOverview(int userId) throws JsonProcessingException {
        Portfolio portfolio = rootRepository.getPortfolioIdByUserId(userId);
        List<Asset> overviewPortfolioList = rootRepository.showPortfolioOverview(portfolio.getPortfolioId());
        List<String> portfolioPositionsAsJsonString = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        for (Asset asset :overviewPortfolioList) {
            jsonString = mapper.writeValueAsString(asset);
            portfolioPositionsAsJsonString.add(jsonString);
        }
        return portfolioPositionsAsJsonString;
    }

    public String showValueOfPortfolio(int userId) {
        Portfolio portfolio = rootRepository.getPortfolioIdByUserId(userId);
        String portfolioValueString = rootRepository.showPortfolioValue(portfolio.getPortfolioId());
        return portfolioValueString;
    }

    public Portfolio getByActor(int actor) {
        return portfolioDao.getPortfolioIdByUserId(actor);
    }
}
