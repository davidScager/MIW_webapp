package com.example.cryptobank.service.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David_Scager
 */
class SaltMakerTest {

    @Test
    void generateSaltDefault() {
        SaltMaker saltMaker = new SaltMaker();
        int actual = saltMaker.generateSalt().length();
        int expected = 8;
        assertEquals(expected, actual);
    }

    @Test
    void generateSalt() {
        SaltMaker saltMaker = new SaltMaker(10);
        int actual =  saltMaker.generateSalt().length();
        int expected = 10;
        assertEquals(expected, actual);
    }

    @Test
    void setLength() {
        SaltMaker saltMaker = new SaltMaker();
        saltMaker.setLength(10);
        int actual = saltMaker.generateSalt().length();
        int expected = 10;
        assertEquals(expected, actual);
    }
}