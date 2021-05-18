package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Transaction {

    private int transactionId;
    private LocalDateTime timestamp;
    private int seller;
    private int buyer;
    private double numberOfAssets;
    private double transactionCost;
    private String assetSold;
    private String assetBought;

    private final Logger logger = LoggerFactory.getLogger(Transaction.class);

    public Transaction(int transactionId, LocalDateTime timestamp, int seller, int buyer, double numberOfAssets, double transactionCost, String assetSold, String assetBought) {
        this.transactionId = transactionId;
        this.timestamp = timestamp;
        this.seller = seller;
        this.buyer = buyer;
        this.numberOfAssets = numberOfAssets;
        this.transactionCost = transactionCost;
        this.assetSold = assetSold;
        this.assetBought = assetBought;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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

    public double getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(double numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(double transactionCost) {
        this.transactionCost = transactionCost;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", timestamp=" + timestamp +
                ", seller=" + seller +
                ", buyer=" + buyer +
                ", numberOfAssets=" + numberOfAssets +
                ", transactionCost=" + transactionCost +
                ", assetSold='" + assetSold + '\'' +
                ", assetBought='" + assetBought + '\'' +
                '}';
    }
}
