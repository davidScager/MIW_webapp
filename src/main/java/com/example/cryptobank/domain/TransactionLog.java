package com.example.cryptobank.domain;

import com.example.cryptobank.repository.daointerfaces.AssetDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class TransactionLog {

    private double boughtAssetTransactionRate;
    private double soldAssetTransactionRate;
    private double boughtAssetAdjustmentFactor;
    private double soldAssetAdjustmentFactor;
    private double numberOfAssetsBought;
    private double numberOfAssetsSold;
    private double transactionCost;

    private final Logger logger = LoggerFactory.getLogger(TransactionLog.class);

    public TransactionLog(double boughtAssetTransactionRate, double soldAssetTransactionRate, double boughtAssetAdjustmentFactor, double soldAssetAdjustmentFactor, double numberOfAssetsBought, double numberOfAssetsSold, double transactionCost) {
        this.boughtAssetTransactionRate = boughtAssetTransactionRate;
        this.soldAssetTransactionRate = soldAssetTransactionRate;
        this.boughtAssetAdjustmentFactor = boughtAssetAdjustmentFactor;
        this.soldAssetAdjustmentFactor = soldAssetAdjustmentFactor;
        this.numberOfAssetsBought = numberOfAssetsBought;
        this.numberOfAssetsSold = numberOfAssetsSold;
        this.transactionCost = transactionCost;
        logger.info("New TransactionLog created");
    }

    public TransactionLog(){
        super();
    }

    //    MB: Veel setters worden niet gebruikt. Deze mogen weg als ze niet nodig zijn.
    public double getBoughtAssetTransactionRate() {
        return boughtAssetTransactionRate;
    }

    public void setBoughtAssetTransactionRate(double boughtAssetTransactionRate) {
        this.boughtAssetTransactionRate = boughtAssetTransactionRate;
    }

    public double getSoldAssetTransactionRate() {
        return soldAssetTransactionRate;
    }

    public void setSoldAssetTransactionRate(double soldAssetTransactionRate) {
        this.soldAssetTransactionRate = soldAssetTransactionRate;
    }

    public double getBoughtAssetAdjustmentFactor() {
        return boughtAssetAdjustmentFactor;
    }

    public void setBoughtAssetAdjustmentFactor(double boughtAssetAdjustmentFactor) {
        this.boughtAssetAdjustmentFactor = boughtAssetAdjustmentFactor;
    }

    public double getSoldAssetAdjustmentFactor() {
        return soldAssetAdjustmentFactor;
    }

    public void setSoldAssetAdjustmentFactor(double soldAssetAdjustmentFactor) {
        this.soldAssetAdjustmentFactor = soldAssetAdjustmentFactor;
    }

    public double getNumberOfAssetsBought() {
        return numberOfAssetsBought;
    }

    public void setNumberOfAssetsBought(double numberOfAssetsBought) {
        this.numberOfAssetsBought = numberOfAssetsBought;
    }

    public double getNumberOfAssetsSold() {
        return numberOfAssetsSold;
    }

    public void setNumberOfAssetsSold(double numberOfAssetsSold) {
        this.numberOfAssetsSold = numberOfAssetsSold;
    }

    public double getTransactionCost() {
        return transactionCost;
    }

    public void setTransactionCost(double transactionCost) {
        this.transactionCost = transactionCost;
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
                "Bought at rate " + boughtAssetTransactionRate +
                ", Sold at rate " + soldAssetTransactionRate +
                ", amount bought" + numberOfAssetsBought +
                ", amount sold" + numberOfAssetsSold +
                ", transaction cost " + transactionCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionLog)) return false;
        TransactionLog that = (TransactionLog) o;
        return Double.compare(that.getBoughtAssetTransactionRate(), getBoughtAssetTransactionRate()) == 0 && Double.compare(that.getSoldAssetTransactionRate(), getSoldAssetTransactionRate()) == 0 && Double.compare(that.getBoughtAssetAdjustmentFactor(), getBoughtAssetAdjustmentFactor()) == 0 && Double.compare(that.getSoldAssetAdjustmentFactor(), getSoldAssetAdjustmentFactor()) == 0 && Double.compare(that.getNumberOfAssetsBought(), getNumberOfAssetsBought()) == 0 && Double.compare(that.getNumberOfAssetsSold(), getNumberOfAssetsSold()) == 0 && Double.compare(that.getTransactionCost(), getTransactionCost()) == 0 && Objects.equals(logger, that.logger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBoughtAssetTransactionRate(), getSoldAssetTransactionRate(), getBoughtAssetAdjustmentFactor(), getSoldAssetAdjustmentFactor(), getNumberOfAssetsBought(), getNumberOfAssetsSold(), getTransactionCost(), logger);
    }
}
