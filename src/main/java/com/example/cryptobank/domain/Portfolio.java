package com.example.cryptobank.domain;

public class Portfolio {

    private int portfolioId;
    private final User user;

    public Portfolio(User user) {
        super();
        this.user = user;
        this.portfolioId = 0;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public User getUser() {
        return user;
    }
}
