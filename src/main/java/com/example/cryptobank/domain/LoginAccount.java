package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Login account type object
 * @author David_Scager
 */
public class LoginAccount {
    private final Logger logger = LoggerFactory.getLogger(LoginAccount.class);
    private String username;
    private String hash;
    private String token;

    public LoginAccount(String username, String hash, String token) {
        super();
        logger.info("New LoginAccount");
        this.username = username;
        this.hash = hash;
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginAccount{" +
                "username='" + username + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginAccount that = (LoginAccount) o;
        return username.equals(that.username) && hash.equals(that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, hash);
    }

    public String getUsername() {
        return username;
    }

    public String getHash() {
        return hash;
    }

    public String getToken() { return token; }
}
