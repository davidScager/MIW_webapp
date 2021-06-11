package com.example.cryptobank.domain.user;

import com.example.cryptobank.service.security.AccountNrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Actor {
    private final Logger logger = LoggerFactory.getLogger(Actor.class);
    private long userId;
    private String checkingAccount;
    private Role role;

    private Actor(int userId, String checkingAccount, Role role) {
        super();
        this.userId = userId;
        this.checkingAccount = checkingAccount;
        this.role = role;
        logger.info("New Actor");
    }

    //uses SecureRNG to generate 10 numbers. Prefix set in service/security/AccountNrService.java -David
    public Actor(Role role) {
        this(0, AccountNrService.randomAccountNr(), role);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "userId=" + userId +
                ", checkingaccount='" + checkingAccount + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return userId == actor.userId && checkingAccount.equals(actor.checkingAccount) && role == actor.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, checkingAccount, role);
    }

    public Actor(){}

    public long getUserId() {
        return userId;
    }

    public String getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(String checkingAccount) {
        this.checkingAccount = checkingAccount;
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
