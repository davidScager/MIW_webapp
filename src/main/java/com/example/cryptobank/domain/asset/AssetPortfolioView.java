package com.example.cryptobank.domain.asset;

public class AssetPortfolioView {

    private String assetName;
    private String assetDescription;
    private int portfolioId;
    private double amount;
    private double forSale;
    private double amountUSD;


    public AssetPortfolioView(String assetName, int portfolioId, double amount, double forSale, double amountUSD, String assetDescription) {
        this.assetName = assetName;
        this.assetDescription = assetDescription;
        this.portfolioId = portfolioId;
        this.amount = amount;
        this.amountUSD = amountUSD;
        this.forSale = forSale;
    }
    public double getForSale() {
        return forSale;
    }

    public void setForSale(double forSale) {
        this.forSale = forSale;
    }


    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetDescription() {
        return assetDescription;
    }

    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(double amountUSD) {
        this.amountUSD = amountUSD;
    }
}