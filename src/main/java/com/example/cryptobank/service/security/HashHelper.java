package com.example.cryptobank.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for Hashing passwords using SHA-256
 * @author David_Scager, Reyndert_Mehrer, Huib_van_Straten
 */
public class HashHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaltMaker.class);
    private static final String SHA_256 = "SHA-256";
    private static final String NO_SUCH_ALGORITHM = "No such algorithm";

    /**
     * Hash a password using default algorithm
     * Used for rehashing by HashService.class
     * @param password (String)
     * @return (String) hashed password
     */
    public static String hash(String password) {
        byte[] bytes = new byte[64];
        try {
            MessageDigest mDig = MessageDigest.getInstance(SHA_256);
            mDig.update(password.getBytes(StandardCharsets.UTF_8));
            bytes = mDig.digest();
        } catch (NoSuchAlgorithmException nsa){
            LOGGER.info(NO_SUCH_ALGORITHM + "\n" + nsa.getLocalizedMessage());
        }
        return ByteArrayToHexHelper.encodeHexString(bytes);
    }

    /**
     * Hash a password using default algorithm
     * Add pepper first and generated salt last
     *
     * @param pepper (String)
     * @param password (String)
     * @param salt (String) generated with SaltMaker
     * @return (String)
     */
    public static String hash(String pepper, String password, String salt) {
        byte[] bytes = new byte[64];
        try {
            MessageDigest mDig = MessageDigest.getInstance(SHA_256);
            mDig.update(pepper.getBytes(StandardCharsets.UTF_8));
            mDig.update(password.getBytes(StandardCharsets.UTF_8));
            mDig.update(salt.getBytes(StandardCharsets.UTF_8));
            bytes = mDig.digest();
        } catch (NoSuchAlgorithmException nsa){
            LOGGER.info(NO_SUCH_ALGORITHM + "\n" + nsa.getLocalizedMessage());
        }
        return ByteArrayToHexHelper.encodeHexString(bytes);
    }

}
