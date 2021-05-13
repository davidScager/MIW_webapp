package com.example.cryptobank.domain;

public class Portfolio {

    private int portfolioId;
    private Actor actor;

    //changed user to actor -David
    public Portfolio(Actor actor) {
        super();
        this.actor = actor;
        this.portfolioId = 0;
    }

    public Portfolio() {
        this.actor = null;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
