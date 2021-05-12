package com.example.cryptobank.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom utility for encoding byte arrays to hexadecimal Strings
 * @author Remi de Boer
 */
public class ByteArrayToHexHelper {

    /**
     * Encode any byte array to hexadecimal String
     * - uses byteToHex() (see below)
     * @param byteArray (byte[])
     * @return (String) hexadecimal
     */
    public static String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (byte b : byteArray) {
            hexStringBuffer.append(byteToHex(b));
        }
        return hexStringBuffer.toString();
    }

    /**
     * Encode any byte to 2 hexadecimal characters
     * @param num (byte)
     * @return (String) 2 hexadecimal characters representing 1 byte
     */
    private static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }
}
