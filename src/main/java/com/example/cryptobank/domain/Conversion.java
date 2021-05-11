package com.example.cryptobank.domain;

public class Conversion {

    private String rate;
    private Asset asset1;
    private Asset asset2;
    private Double conversionValue;

    public Conversion(String rate, Asset asset1, Asset asset2, Double conversionValue) {
        this.rate = rate;
        this.asset1 = asset1;
        this.asset2 = asset2;
        this.conversionValue = conversionValue;
    }

    public Conversion() {
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Asset getAsset1() {
        return asset1;
    }

    public void setAsset1(Asset asset1) {
        this.asset1 = asset1;
    }

    public Asset getAsset2() {
        return asset2;
    }

    public void setAsset2(Asset asset2) {
        this.asset2 = asset2;
    }

    public Double getConversionValue() {
        return conversionValue;
    }

    public void setConversionValue(Double conversionValue) {
        this.conversionValue = conversionValue;
    }
}
