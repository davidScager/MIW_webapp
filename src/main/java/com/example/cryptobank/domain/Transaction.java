package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Transaction {

    private int transactionId;
    private String timestamp;
    private int seller;
    private int buyer;
//    private double numberOfAssets;
//    private double transactionCost;
    private String assetSold;
    private String assetBought;
    TransactionLog transactionLog;

    private final Logger logger = LoggerFactory.getLogger(Transaction.class);

    public Transaction(int transactionId, String timestamp, int seller, int buyer, String assetSold, String assetBought, TransactionLog transactionLog) {
        this.transactionId = transactionId;
        this.timestamp = timestamp;
        this.seller = seller;
        this.buyer = buyer;
        this.assetSold = assetSold;
        this.assetBought = assetBought;
        this.transactionLog = transactionLog;
    }

    public Transaction(int seller, int buyer, String assetSold, String assetBought, TransactionLog transactionLog) {
        this(0, LocalDateTime.now().toString(), seller, buyer, assetSold, assetBought, transactionLog);
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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

    public TransactionLog getTransactionLog() {
        return transactionLog;
    }

    public void setTransactionLog(TransactionLog transactionLog) {
        this.transactionLog = transactionLog;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", timestamp=" + timestamp +
                ", seller=" + seller +
                ", buyer=" + buyer +
                ", assetSold='" + assetSold + '\'' +
                ", assetBought='" + assetBought + '\'' + ", " +
                transactionLog +
                '}';
    }
}
