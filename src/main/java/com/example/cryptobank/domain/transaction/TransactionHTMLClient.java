package com.example.cryptobank.domain.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionHTMLClient {

    private String AssetName;
    private double avalable;
    private String abbreviation;
    private double assetUSDValue;
    private final Logger logger = LoggerFactory.getLogger(TransactionHTMLClient.class);

    public TransactionHTMLClient(String assetName, double avalable, String abbreviation, double assetUSDValue) {
        AssetName = assetName;
        this.avalable = avalable;
        this.abbreviation = abbreviation;
        this.assetUSDValue = assetUSDValue;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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
