package com.example.cryptobank.service.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David_Scager
 */
class HashAndSaltTest {
    private HashAndSalt testHashAndSalt;
    private String hash;
    private String salt;

    @BeforeEach
    void setup(){
        hash = "hash";
        salt = "salt";
        testHashAndSalt = new HashAndSalt(hash, salt);
    }

    @Test
    void testToString() {
        String expected = "salt$hash";
        assertEquals(expected, testHashAndSalt.toString());
    }

    @Test
    void getHash() {
        assertEquals(hash, testHashAndSalt.getHash());
    }

    @Test
    void getSalt() {
        assertEquals(salt, testHashAndSalt.getSalt());
    }
}