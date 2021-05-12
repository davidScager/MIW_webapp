package com.example.cryptobank.security;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David_Scager
 */
class ByteArrayToHexHelperTest {
    private static byte[] byteArray;

    @BeforeAll
    static void setupAll(){
        byteArray = new byte[1];
        byteArray[0] = 127;
    }

    @Test
    void encodeHexString() {
        String expected = "7f";
        String actual = ByteArrayToHexHelper.encodeHexString(byteArray);
        assertEquals(expected, actual);
    }

}