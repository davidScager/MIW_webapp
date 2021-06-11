package com.example.cryptobank.domain.portfolio;

import com.example.cryptobank.domain.user.Actor;

public class Portfolio {

    private int portfolioId;
    private Actor actor;

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

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioID ='" + portfolioId +
                ", actor ='" + actor +
                '}';
    }
}
