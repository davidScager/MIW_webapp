package com.example.cryptobank.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Generates SecureRandom byte array of either provided length or default length
 * Salt length can only be even, not odd.
 * @author David_Scager
 */
public class SaltMaker {
    private Logger logger = LoggerFactory.getLogger(SaltMaker.class);
    private static final int DEFAULT_SALT_LENGTH = 8;
    private final SecureRandom secureRandom;
    private int saltLength = 0;

    /**
     * Set desired length for salt
     * Note: odd values will result in generated salt length - 1
     * @param saltLength (int)
     */
    public SaltMaker(int saltLength){
        super();
        logger.info("New SaltMaker");
        this.secureRandom = new SecureRandom();
        setLength(saltLength);
    }

    public SaltMaker(){
        this(DEFAULT_SALT_LENGTH);
    }

    /**
     * Generate salt of default length if attribute saltLength == 0
     * @return (String) secure randomly generated salt
     */
    public String generateSalt(){
        byte[] bytes = new byte[this.saltLength / 2];
        secureRandom.nextBytes(bytes);
        return ByteArrayToHexHelper.encodeHexString(bytes);
    }

    public void setLength(int length){
        this.saltLength = length;
    }
}
