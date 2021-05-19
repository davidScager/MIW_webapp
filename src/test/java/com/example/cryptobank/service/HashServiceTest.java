package com.example.cryptobank.service;

import com.example.cryptobank.security.HashHelper;
import com.example.cryptobank.security.HashAndSalt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David_Scager
 */
class HashServiceTest {
    private static PepperService pepperService;
    private static HashService hashService;
    private static String password;
    private static String salt;

    @BeforeAll
    static void setupAll(){
        pepperService = new PepperService();
        hashService = new HashService(pepperService);
        password = "password";
        salt = "salt";
    }

    @Test
    void hash1() {
        String expected = "ff8765915011fe0cc11c6b0088aa94092cc7ac2037357d47e80bebe1d9e635bd";
        hashService.setRounds(0);
        String actual = hash(password, salt);
        assertEquals(expected, actual);
        hashService.setRounds(10);
    }

    @Test
    void hash2() {
        String expected = "80918df4f44ec133e196df5352729d7566bf12ecfa2ef9bef0cf34f531cbb979";
        String actual = hash(password, salt);
        assertEquals(expected, actual);
    }

    @Test
    void hash3() {
        HashAndSalt actual = hashService.hash(password);
        String actualHash = actual.getHash();
        String expectedHash = hash(password, actual.getSalt());
        assertEquals(expectedHash, actualHash);
    }

    @Test
    void argonHash(){
        String actual = hashService.argon2idHash(password).getHash();
        assertTrue(hashService.argon2idVerify(actual, password));
    }

    /**
     * Local test method to allow bypassing random salt generation
     * @param password (String)
     * @param salt (String)
     * @return (String) peppered, salted and key stretched password hash
     */
    public String hash(String password, String salt){
        String hash = HashHelper.hash(pepperService.getPepper(), password, salt);
        for (int i = 0; i < hashService.getRounds(); i++) {
            hash = HashHelper.hash(hash);
        }
        return hash;
    }

}