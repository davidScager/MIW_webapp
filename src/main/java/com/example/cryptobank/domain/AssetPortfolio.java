package com.example.cryptobank.domain;

public class AssetPortfolio {

    private String assetName;
    private int portfolioId;
    private double amount;

    public AssetPortfolio(String symbol, int portfolioId, double amount) {
        this.assetName = symbol;
        this.portfolioId = portfolioId;
        this.amount = amount;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String symbol) {
        this.assetName = symbol;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortofolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
