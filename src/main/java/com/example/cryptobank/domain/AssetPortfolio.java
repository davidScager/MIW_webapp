package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetPortfolio {

    String assetname;
    int portfolioId;
    Double amount;

    private final Logger logger = LoggerFactory.getLogger(AssetPortfolio.class);

    public AssetPortfolio(String assetname, int portfolioId, Double amount) {
        this.assetname = assetname;
        this.portfolioId = portfolioId;
        this.amount = amount;
        logger.info("New AssetPortfolio");
    }

    public String getAssetname() {
        return assetname;
    }

    public void setAssetname(String assetname) {
        this.assetname = assetname;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
