package com.example.cryptobank.service;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.AssetPortfolio;
import com.example.cryptobank.domain.Portfolio;
import com.example.cryptobank.repository.AssetDao;
import com.example.cryptobank.repository.JdbcAssetPortfolioDao;
import com.example.cryptobank.repository.PortfolioDao;
import com.example.cryptobank.repository.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AssetPortfolioService {

    private final RootRepository rootReposistory;
    private final JdbcAssetPortfolioDao jdbcAssetPortfolioDao;
    private AssetDao assetDao;
    private PortfolioDao portfolioDao;

    //    private CurrencyHistory currencyHistory = new CurrencyHistory();
    private final Logger logger = LoggerFactory.getLogger(AssetPortfolioService.class);

    @Autowired
    public AssetPortfolioService(RootRepository rootReposistory, JdbcAssetPortfolioDao jdbcAssetPortfolioDao) {
        super();
        this.rootReposistory = rootReposistory;
        this.jdbcAssetPortfolioDao = jdbcAssetPortfolioDao;
        logger.info("New AssetPortofolioService");
    }

    public AssetPortfolio createNewAssetPortfolio(String symbol, int portfolioId, double amount) throws IOException {
        AssetPortfolio newAssetPortfolio = new AssetPortfolio(symbol, portfolioId, amount);
        jdbcAssetPortfolioDao.create(newAssetPortfolio);
        return newAssetPortfolio;
    }

    public List<Asset> showAssetPortfolioList() {
//        List<Asset> assetList = rootReposistory.showAssetPortfolioOverview();
//        return assetList;
        return null;
    }

    public AssetPortfolio update(String symbol,  int portfolioId, double amount) {
        Asset asset = assetDao.getOneByName(symbol);
        Optional<Portfolio> portfoliotemp = portfolioDao.get(portfolioId);
        Portfolio portfolio = portfoliotemp.get();
        jdbcAssetPortfolioDao.update(asset, portfolio, amount);
        AssetPortfolio newAssetPortfolio = new AssetPortfolio(symbol, portfolioId, amount);
        return newAssetPortfolio;
    }
}
