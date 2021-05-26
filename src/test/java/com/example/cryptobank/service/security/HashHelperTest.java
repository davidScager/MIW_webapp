package com.example.cryptobank.service.security;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David_Scager
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HashHelperTest {
    private static String pepper;
    private static String password;
    private static String salt;

    @BeforeAll
    static void setupAll() {
        pepper = "pepper";
        password = "password";
        salt = "salt";
    }

    @Test @Order(1)
    void hashTest1() {
        String expected = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
        String actual = HashHelper.hash(password);
        assertEquals(expected, actual);
    }

    @Test @Order(2)
    void hashTest2() {
        String expected = "62ea391b32fa4254f4613e8c2c4f3d1fde06da4791ec160702ee8805c65cea1d";
        String actual = HashHelper.hash(pepper, password, salt);
        assertEquals(expected, actual);
    }
}