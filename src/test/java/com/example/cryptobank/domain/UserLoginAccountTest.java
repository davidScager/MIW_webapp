package com.example.cryptobank.domain;

import com.example.cryptobank.domain.login.UserLoginAccount;
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
    private static UserLoginAccount testUserLoginAccount;
    private static final String TEST_PASSWORD = "password";


    @BeforeAll
    static void setupAll(){
        hashService = new HashService(new PepperService());
        testUserLoginAccount = new UserLoginAccount(0, "", "", "", "", "", 0, "", "", "", "",
                        TEST_PASSWORD);
        testUserLoginAccount.addRequiredData();
    }

    @Test
    void checkRequiredData_only_password(){
        testUserLoginAccount.printData();
        assertFalse(testUserLoginAccount.allRequiredData());
    }

    @Test
    void all_requiredData_presented() {
        UserLoginAccount userLoginAccount = new UserLoginAccount(123456, "His", "", "Dudeness", "1950-01-01", "1234AB", 10, "", "Dudestreet", "Dudesville", "dude@duder.com",
                TEST_PASSWORD);
        userLoginAccount.addRequiredData();
        userLoginAccount.printData();
        assertTrue(userLoginAccount.allRequiredData());
    }

    @Test
    void some_requiredData_bsn_missing(){
        UserLoginAccount userLoginAccount = new UserLoginAccount(0, "His", "", "Dudeness", "1950-01-01", "1234AB", 10, "", "Dudestreet", "Dudesville", "dude@duder.com",
                TEST_PASSWORD);
        userLoginAccount.addRequiredData();
        userLoginAccount.printData();
        assertFalse(userLoginAccount.allRequiredData());
    }

    @Test
    void some_requiredData_String_missing(){
        UserLoginAccount userLoginAccount = new UserLoginAccount(123456, "His", "", "", "1950-01-01", "1234AB", 10, "", "Dudestreet", "Dudesville", "dude@duder.com",
                TEST_PASSWORD);
        userLoginAccount.addRequiredData();
        userLoginAccount.printData();
        assertFalse(userLoginAccount.allRequiredData());
    }

    @Test
    void some_requiredData_int_missing(){
        UserLoginAccount userLoginAccount = new UserLoginAccount(123456, "His", "", "Dudeness", "1950-01-01", "1234AB", 0, "", "Dudestreet", "Dudesville", "dude@duder.com",
                TEST_PASSWORD);
        userLoginAccount.addRequiredData();
        userLoginAccount.printData();
        assertFalse(userLoginAccount.allRequiredData());
    }

    @Test @Order(1)
    void passwordNotNullTest() {
        assertNotNull(testUserLoginAccount.getPassword());
    }

    @Test @Order(2)
    void passwordIsHashedTest() {
        assertNotEquals(TEST_PASSWORD, testUserLoginAccount.getPassword());
    }

    @Test @Order(3)
    void passwordRegExTest(){
        int length = 97;
        String argon2IdParams = "$argon2id$v=19$m=" + hashService.getMemSize() + ",t=" + hashService.getIterations() + ",p=" + hashService.getParallelDeg() + "$";
        assertEquals(testUserLoginAccount.getPassword().length(), length);
        assertEquals(argon2IdParams, testUserLoginAccount.getPassword().substring(0, 31));
    }

    @Test @Order(4)
    void resetPasswordTest(){
        String passwordHash = testUserLoginAccount.getPassword();
        testUserLoginAccount.setPassword(TEST_PASSWORD);
        assertNotEquals(passwordHash, testUserLoginAccount.getPassword());
    }

    @Test @Order(5)
    void verifyPasswordTest(){
        assertTrue(hashService.argon2idVerify(testUserLoginAccount.getPassword(), TEST_PASSWORD));
    }


}