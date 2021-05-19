package com.example.cryptobank.service;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.AssetPortfolio;
import com.example.cryptobank.repository.JdbcAssetPortfolioDao;
import com.example.cryptobank.repository.RootRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AssetPortfolioService {

    private final RootRepository rootReposistory;
    private final JdbcAssetPortfolioDao jdbcAssetPortfolioDao;

    //    private CurrencyHistory currencyHistory = new CurrencyHistory();
    private final Logger logger = LoggerFactory.getLogger(AssetPortfolioService.class);

    @Autowired
    public AssetPortfolioService(RootRepository rootReposistory, JdbcAssetPortfolioDao jdbcAssetPortfolioDao) {
        super();
        this.rootReposistory = rootReposistory;
        this.jdbcAssetPortfolioDao = jdbcAssetPortfolioDao;
        logger.info("New AssetPortofolioService");
    }

    public AssetPortfolio createNewAssetPortfolio(String symbol, double portfolioId, double amount) throws IOException {
        AssetPortfolio newAssetPortfolio = new AssetPortfolio(symbol, portfolioId, amount);
        jdbcAssetPortfolioDao.create(newAssetPortfolio);
        return newAssetPortfolio;
    }

    public List<Asset> showAssetPortfolioList() {
//        List<Asset> assetList = rootReposistory.showAssetPortfolioOverview();
//        return assetList;
        return null;
    }

    public AssetPortfolio update(String symbol,  double portfolioId, double amount) {
        AssetPortfolio newAssetPortfolio = new AssetPortfolio(symbol, portfolioId, amount);
        jdbcAssetPortfolioDao.update(newAssetPortfolio);
        return newAssetPortfolio;
    }
}
