package com.example.cryptobank.domain;

public class AssetPortfolio {

    private String assetName;
    private int portfolioId;
    private double amount;
    private double availableForSale;

    public AssetPortfolio(String assetName, int portfolioId, double amount, double availableForSale) {
        this.assetName = assetName;
        this.portfolioId = portfolioId;
        this.amount = amount;
        this.availableForSale = availableForSale;
    }

    public AssetPortfolio(String symbol, int portfolioId, double amount) {
        super();
        this.assetName = symbol;
        this.portfolioId = portfolioId;
        this.amount = amount;
    }

    public String getAssetName() {
        return assetName;
    }

//    MB: Is deze setter wel nodig? wordt vlgs mij niet gebruikt. En zou ook niet moeten worden gebruikt in deze klasse.
    public void setAssetName(String symbol) {
        this.assetName = symbol;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAvailableForSale() {
        return availableForSale;
    }

    public void setAvailableForSale(double availableForSale) {
        this.availableForSale = availableForSale;
    }
}
