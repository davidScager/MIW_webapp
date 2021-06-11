package com.example.cryptobank.service.assetenportfolio;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetPortfolio;
import com.example.cryptobank.domain.portfolio.Portfolio;
import com.example.cryptobank.repository.daointerfaces.AssetDao;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AssetPortfolioService {

    private final RootRepository rootReposistory;
    private AssetPortfolioDao assetPortfolioDao;
    private AssetDao assetDao;
    private PortfolioDao portfolioDao;

    private final Logger logger = LoggerFactory.getLogger(AssetPortfolioService.class);

    @Autowired
    public AssetPortfolioService(RootRepository rootReposistory, AssetDao assetDao,AssetPortfolioDao assetPortfolioDao, PortfolioDao portfolioDao) {
        super();
        this.rootReposistory = rootReposistory;
        this.assetPortfolioDao = assetPortfolioDao;
        this.assetDao = assetDao;
        this.portfolioDao = portfolioDao;
        logger.info("New AssetPortofolioService");
    }

    public AssetPortfolio createNewAssetPortfolio(String symbol, int portfolioId, double amount) throws IOException {
        AssetPortfolio newAssetPortfolio = new AssetPortfolio(symbol, portfolioId, amount);
        assetPortfolioDao.create(newAssetPortfolio);
        return newAssetPortfolio;
    }

    public AssetPortfolio update(String symbol,  int portfolioId, double amount) {
        Asset asset = assetDao.getOneByName(symbol);
        Optional<Portfolio> portfoliotemp = portfolioDao.get(portfolioId);
        Portfolio portfolio = portfoliotemp.get();
        assetPortfolioDao.update(asset, portfolio, amount);
        AssetPortfolio newAssetPortfolio = new AssetPortfolio(symbol, portfolioId, amount);
        return newAssetPortfolio;
    }
}
