package com.example.cryptobank.domain.portfolio;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.transaction.TransactionHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortfolioReturnData {

    private Asset asset;
    private double amount;
    private double historicRate;
    private double lastTradeRate;
    private Boolean aankoopVerkoop;

    private final Logger logger = LoggerFactory.getLogger(TransactionHistory.class);

    public PortfolioReturnData(Asset asset, double amount, double historicRate, double lastTradeRate, Boolean aankoopVerkoop) {
        this.asset = asset;
        this.amount = amount;
        this.historicRate = historicRate;
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

    public double getHistoricRate() {
        return historicRate;
    }

    public void setHistoricRate(double historicRate) {
        this.historicRate = historicRate;
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
                ", historic rate=" + historicRate +
                ", lastTradeRate='" + lastTradeRate + '\'' +
                ", buy or sell='" + aankoopVerkoop +
                '}';
    }
}
