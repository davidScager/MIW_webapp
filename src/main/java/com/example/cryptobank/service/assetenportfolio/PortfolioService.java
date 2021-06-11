package com.example.cryptobank.service.assetenportfolio;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.domain.portfolio.PortfolioReturnData;
import com.example.cryptobank.domain.transaction.Transaction;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioService {

    private final RootRepository rootRepository;
    private final PortfolioDao portfolioDao;
    private final TransactionService transactionService;

    private final Logger logger = LoggerFactory.getLogger(AssetService.class);

    @Autowired
    public PortfolioService(RootRepository rootReposistory, PortfolioDao portfolioDao, TransactionService transactionService) {
        super();
        this.rootRepository = rootReposistory;
        this.portfolioDao = portfolioDao;
        this.transactionService = transactionService;
        logger.info("New PortfolioService");
    }

    public List<Asset> showAssetOverview(int userId) {
        Portfolio portfolio = rootRepository.getPortfolioIdByUserId(userId);
        return rootRepository.showPortfolioOverview(portfolio.getPortfolioId());
    }

    public String showValueOfPortfolio(int userId) {
        Portfolio portfolio = rootRepository.getPortfolioIdByUserId(userId);

        return rootRepository.showPortfolioValue(portfolio.getPortfolioId());
    }

    public List<PortfolioReturnData> showListOfAssets(int userId) {
        Portfolio portfolio = rootRepository.getPortfolioIdByUserId(userId);
        List<PortfolioReturnData> tempList = new ArrayList<>();
        Map<Asset, Double> assetMap = rootRepository.getAssetOverviewWithAmount(portfolio.getPortfolioId());
        double transactionRate = 0;
        for (Map.Entry<Asset, Double> entry: assetMap.entrySet() ) {
            Transaction tempTransaction = transactionService.getMostRecentBuyOrSell(userId, entry.getKey().getAbbreviation());
            Boolean koopVerkoop = transactionService.determineBuyOrSell(tempTransaction, entry.getKey().getAbbreviation());
            if(koopVerkoop){ transactionRate = tempTransaction.getTransactionLog().getBoughtAssetTransactionRate();}
            if(koopVerkoop=false) {transactionRate = tempTransaction.getTransactionLog().getSoldAssetTransactionRate();}
            PortfolioReturnData tempPortfolioReturnData = new PortfolioReturnData(entry.getKey(), entry.getValue(), transactionRate, koopVerkoop);
            tempList.add(tempPortfolioReturnData);
        }

        return tempList;
    }



    public Portfolio getByActor(int actor) {
        return portfolioDao.getPortfolioIdByUserId(actor);
    }
}
