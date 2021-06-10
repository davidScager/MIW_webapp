package com.example.cryptobank.domain;

import com.example.cryptobank.service.security.HashService;
import com.example.cryptobank.service.security.PepperService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David Scager
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserLoginAccountTest {
    private static HashService hashService;
    private static UserLoginAccount userLoginAccount;
    private static final String TEST_PASSWORD = "password";


    @BeforeAll
    static void setupAll(){
        hashService = new HashService(new PepperService());
        userLoginAccount = new UserLoginAccount(0, "", "", "", "", "", 0, "", "", "", "",
                        TEST_PASSWORD);
    }

    @Test @Order(1)
    void passwordNotNullTest() {
        assertNotNull(userLoginAccount.getPassword());
    }

    @Test @Order(2)
    void passwordHashedTest() {
        assertNotEquals(TEST_PASSWORD, userLoginAccount.getPassword());
    }

    @Test @Order(3)
    void passwordRegExTest(){
        int length = 97;
        String argon2IdParams = "$argon2id$v=19$m=" + hashService.getMemSize() + ",t=" + hashService.getIterations() + ",p=" + hashService.getParallelDeg() + "$";
        assertEquals(userLoginAccount.getPassword().length(), length);
        assertEquals(userLoginAccount.getPassword().substring(0, 31), argon2IdParams);
    }

    @Test @Order(4)
    void resetPasswordTest(){
        String passwordHash = userLoginAccount.getPassword();
        userLoginAccount.setPassword(TEST_PASSWORD);
        assertNotEquals(passwordHash, userLoginAccount.getPassword());
    }

    @Test @Order(5)
    void verifyPasswordTest(){
        assertTrue(hashService.argon2idVerify(userLoginAccount.getPassword(), TEST_PASSWORD));
    }


}