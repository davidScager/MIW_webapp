package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Login account type object
 * @author David_Scager
 */
public class LoginAccount {
    private final Logger logger = LoggerFactory.getLogger(LoginAccount.class);
    private String username;
    private String hash;
    private String salt;

    public LoginAccount(String username, String hash, String salt) {
        super();
        logger.info("New LoginAccount");
        this.username = username;
        this.hash = hash;
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "LoginAccount{" +
                "username='" + username + '\'' +
                ", hash='" + hash + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
}
