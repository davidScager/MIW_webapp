package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionHTMLClient {

    private String AssetName;
    private double avalable;
    private double assetUSDValue;
    private final Logger logger = LoggerFactory.getLogger(TransactionHTMLClient.class);

    public TransactionHTMLClient(String assetName, double avalable, double assetUSDValue) {
        AssetName = assetName;
        this.avalable = avalable;
        this.assetUSDValue = assetUSDValue;
    }

    public String getAssetName() {
        return AssetName;
    }

    public void setAssetName(String assetName) {
        AssetName = assetName;
    }

    public double getAvalable() {
        return avalable;
    }

    public void setAvalable(double avalable) {
        this.avalable = avalable;
    }

    public double getAssetUSDValue() {
        return assetUSDValue;
    }

    public void setAssetUSDValue(double assetUSDValue) {
        this.assetUSDValue = assetUSDValue;
    }
}
