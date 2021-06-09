package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortfolioReturnData {

    private Asset asset;
    private double amount;
    private double lastTradeRate;
    private Boolean aankoopVerkoop;

    private final Logger logger = LoggerFactory.getLogger(TransactionHistory.class);

    public PortfolioReturnData(Asset asset, double amount, double lastTradeRate, Boolean aankoopVerkoop) {
        this.asset = asset;
        this.amount = amount;
        this.lastTradeRate = lastTradeRate;
        this.aankoopVerkoop = aankoopVerkoop;
        logger.info("New PortfolioReturnData created");
    }

    public PortfolioReturnData() {
        super();
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getLastTrade() {
        return lastTradeRate;
    }

    public void setLastTrade(double lastTradeRate) {
        this.lastTradeRate = lastTradeRate;
    }

    public Boolean getAankoopVerkoop() {
        return aankoopVerkoop;
    }

    public void setAankoopVerkoop(Boolean aankoopVerkoop) {
        this.aankoopVerkoop = aankoopVerkoop;
    }

    @Override
    public String toString() {
        return "PortfolioReturnData{" +
                "Asset=" + asset.getAbbreviation() +
                ", amount=" + amount +
                ", lastTradeRate='" + lastTradeRate + '\'' +
                ", buy or sell='" + aankoopVerkoop +
                '}';
    }
}
