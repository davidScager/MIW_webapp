package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Actor {
    private final Logger logger = LoggerFactory.getLogger(Actor.class);
    private long userId;
    private String checkingaccount;
    private Role role;

    private Actor(int userId, String checkingaccount, Role role) {
        super();
        this.userId = userId;
        this.checkingaccount = checkingaccount;
        this.role = role;
        logger.info("New Actor");
    }

    //provided simple account number. Eventually to be SecureRNG. -David
    public Actor(Role role) {
        this(0, "NL88 BITB 1234 1234 12", role);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "userId=" + userId +
                ", checkingaccount='" + checkingaccount + '\'' +
                ", role=" + role +
                '}';
    }

    public Actor(){}

    public long getUserId() {
        return userId;
    }

    public String getCheckingaccount() {
        return checkingaccount;
    }

    public void setCheckingaccount(String checkingaccount) {
        this.checkingaccount = checkingaccount;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
