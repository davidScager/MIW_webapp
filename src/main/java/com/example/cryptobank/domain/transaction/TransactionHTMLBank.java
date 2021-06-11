package com.example.cryptobank.domain.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionHTMLBank {
    private String AssetName;
    private double assetUSDValue;
    private double getAssetUSDValueYesterday;
    private double avalable;
    private final Logger logger = LoggerFactory.getLogger(TransactionHTMLBank.class);

    public TransactionHTMLBank(String assetName, double assetUSDValue, double getAssetUSDValueYesterday, double avalable) {
        AssetName = assetName;
        this.assetUSDValue = assetUSDValue;
        this.getAssetUSDValueYesterday = getAssetUSDValueYesterday;
        this.avalable = avalable;
        logger.info("New TransactionHTMLBank");
    }

    public String getAssetName() {
        return AssetName;
    }

    public void setAssetName(String assetName) {
        AssetName = assetName;
    }

    public double getAssetUSDValue() {
        return assetUSDValue;
    }

    public void setAssetUSDValue(double assetUSDValue) {
        this.assetUSDValue = assetUSDValue;
    }

    public double getGetAssetUSDValueYesterday() {
        return getAssetUSDValueYesterday;
    }

    public void setGetAssetUSDValueYesterday(double getAssetUSDValueYesterday) {
        this.getAssetUSDValueYesterday = getAssetUSDValueYesterday;
    }

    public double getAvalable() {
        return avalable;
    }

    public void setAvalable(double avalable) {
        this.avalable = avalable;
    }
}
