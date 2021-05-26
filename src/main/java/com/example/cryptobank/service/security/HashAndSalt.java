package com.example.cryptobank.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create object to allow both Hashed password and generated Salt to be
 * returned by methods of the HashService class
 * @author David_Scager
 */
public class HashAndSalt {
    private Logger logger = LoggerFactory.getLogger(SaltMaker.class);
    private String hash;
    private String salt;

    public HashAndSalt(String hash, String salt){
        super();
        logger.info("New HashAndSalt");
        this.hash = hash;
        this.salt = salt;
    }

    @Override
    public String toString() {
        return salt + "$" + hash;
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
}
