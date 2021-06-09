package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionHistory {

    private int transactionId;
    private Asset asset;
    private double assetTransactionRate;
    private double assetAdjustmentFactor;
    private double numberOfAssetsTraded;
    private String timestamp;
    private String aankoopVerkoop;

    private final Logger logger = LoggerFactory.getLogger(TransactionHistory.class);

    public TransactionHistory(int transactionId, Asset asset, double assetTransactionRate, double assetAdjustmentFactor, double numberOfAssetsTraded, String timestamp, String aankoopVerkoop) {
        this.transactionId = transactionId;
        this.asset = asset;
        this.assetTransactionRate = assetTransactionRate;
        this.assetAdjustmentFactor = assetAdjustmentFactor;
        this.numberOfAssetsTraded = numberOfAssetsTraded;
        this.timestamp = timestamp;
        this.aankoopVerkoop = aankoopVerkoop;
        logger.info("New TransactionHistory created");
    }

    public TransactionHistory() {
        super();
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public double getAssetTransactionRate() {
        return assetTransactionRate;
    }

    public void setAssetTransactionRate(double assetTransactionRate) {
        this.assetTransactionRate = assetTransactionRate;
    }

    public double getAssetAdjustmentFactor() {
        return assetAdjustmentFactor;
    }

    public void setAssetAdjustmentFactor(double assetAdjustmentFactor) {
        this.assetAdjustmentFactor = assetAdjustmentFactor;
    }

    public double getNumberOfAssetsTraded() {
        return numberOfAssetsTraded;
    }

    public void setNumberOfAssetsTraded(double numberOfAssetsTraded) {
        this.numberOfAssetsTraded = numberOfAssetsTraded;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAankoopVerkoop() {
        return aankoopVerkoop;
    }

    public void setAankoopVerkoop(String aankoopVerkoop) {
        this.aankoopVerkoop = aankoopVerkoop;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "transactionId=" + transactionId +
                ", timestamp=" + timestamp +
                ", asset=" + asset.getAbbreviation() +
                ", rate=" + assetTransactionRate +
                ", amount='" + numberOfAssetsTraded + '\'' +
                ", buy or sell='" + aankoopVerkoop +
                '}';
    }
}
