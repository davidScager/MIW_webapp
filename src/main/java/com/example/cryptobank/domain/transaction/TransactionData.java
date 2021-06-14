package com.example.cryptobank.domain.transaction;

public class TransactionData {
    private int seller;
    private int buyer;
    private double numberOfAssets;
    private String assetSold;
    private String assetBought;
    private double triggerValue;
    private String username;
    private double transactionCost;

    public TransactionData(int seller, int buyer, double numberOfAssets, String assetSold, String assetBought, double value, String username) {
        this.seller = seller;
        this.buyer = buyer;
        this.numberOfAssets = numberOfAssets;
        this.assetSold = assetSold;
        this.assetBought = assetBought;
        this.triggerValue = value;
        this.username = username;
    }

    @Override
    public String toString() {
        return "TransactionData{" +
                "seller=" + seller +
                ", buyer=" + buyer +
                ", numberOfAssets=" + numberOfAssets +
                ", assetSold='" + assetSold + '\'' +
                ", assetBought='" + assetBought + '\'' +
                ", triggerValue=" + triggerValue +
                ", username='" + username + '\'' +
                ", transactionCost=" + transactionCost +
                '}';
    }

    public TransactionData() {
    }

    public double getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(double numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public int getSeller() {
        return seller;
    }

    public void setSeller(int seller) {
        this.seller = seller;
    }

    public int getBuyer() {
        return buyer;
    }

    public void setBuyer(int buyer) {
        this.buyer = buyer;
    }

    public String getAssetSold() {
        return assetSold;
    }

    public void setAssetSold(String assetSold) {
        this.assetSold = assetSold;
    }

    public String getAssetBought() {
        return assetBought;
    }

    public void setAssetBought(String assetBought) {
        this.assetBought = assetBought;
    }

    public double getTriggerValue() {
        return triggerValue;
    }

    public void setTriggerValue(double value) {
        this.triggerValue = value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(double transactionCost) {
        this.transactionCost = transactionCost;
    }
}
