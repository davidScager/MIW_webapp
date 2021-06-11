package com.example.cryptobank.domain.portfolio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortfolioPosition {

    private String assetName;
    private String assetAbbreviation;
    private double amount;
    private double valueInUSD;
    private String timeStamp;
    private Boolean buySell;
    private double transactionRate;

    private final Logger logger = LoggerFactory.getLogger(PortfolioPosition.class);

    public PortfolioPosition(String assetName, String assetAbbreviation, double amount, double valueInUSD, String timeStamp, Boolean buySell, double transactionRate) {
        this.assetName = assetName;
        this.assetAbbreviation = assetAbbreviation;
        this.amount = amount;
        this.valueInUSD = valueInUSD;
        this.timeStamp = timeStamp;
        this.buySell = buySell;
        this.transactionRate = transactionRate;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetAbbreviation() {
        return assetAbbreviation;
    }

    public void setAssetAbbreviation(String assetAbbreviation) {
        this.assetAbbreviation = assetAbbreviation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getValueInUSD() {
        return valueInUSD;
    }

    public void setValueInUSD(double valueInUSD) {
        this.valueInUSD = valueInUSD;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Boolean getBuySell() {
        return buySell;
    }

    public void setBuySell(Boolean buySell) {
        this.buySell = buySell;
    }

    public double getTransactionRate() {
        return transactionRate;
    }

    public void setTransactionRate(double transactionRate) {
        this.transactionRate = transactionRate;
    }

    @Override
    public String toString() {
        return "PortfolioPosition{" +
                "Asset =" + assetName +
                ", Abbreviation =" + assetAbbreviation +
                ", amount =" + amount +
                ", Current Rate =" + valueInUSD +
                ", Most recent Transaction ='" + timeStamp + '\'' +
                ", Buy/Sell @ rate ='" + buySell + '\'' + ", " +
                transactionRate +
                '}';
    }
}
