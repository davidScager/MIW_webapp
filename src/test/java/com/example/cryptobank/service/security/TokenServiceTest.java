package com.example.cryptobank.service.security;

import io.jsonwebtoken.ExpiredJwtException;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David Scager
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TokenServiceTest {
    private static TokenService tokenService;
    private static final String EMAIL = "dude@duder.com";
    private static final String SUBJECT = "Session";
    private static final int TIME_UNITS_VALID = 2000;
    private static String token;

    @BeforeAll
    static void setupAll(){
        tokenService = new TokenService();
        tokenService.setChronoUnit(ChronoUnit.MILLIS);
        tokenService.setClockSkewSec(0);
        token = tokenService.generateJwtToken(EMAIL, SUBJECT, TIME_UNITS_VALID);
    }

    @Test @Order(1)
    void tokenRegExTest() {
        System.out.println(token);
        assert(token.matches("([a-zA-Z0-9]{20}).([a-zA-Z0-9]+).([\\p{P}|a-zA-Z0-9]{43})"));
    }

    @Test @Order(2)
    void tokenValidTest(){
        try {
            assertEquals(EMAIL, tokenService.parseToken(token, SUBJECT));
        } catch (ExpiredJwtException jwtException){
            System.out.println(jwtException.getMessage());
            fail();
        }
    }

    @Test @Order(3)
    void bearerTokenValidTest(){
        try {
            String bearerToken = "Bearer:" + token;
            assertEquals(EMAIL, tokenService.parseToken(bearerToken, SUBJECT));
        } catch (ExpiredJwtException jwtException){
            fail();
        }
    }

    @Test @Order(4)
    void tokenExpiredTest() throws InterruptedException {
        Thread.sleep(2500);
        try {
            tokenService.parseToken(token, SUBJECT);
            fail();
        } catch (ExpiredJwtException jwtException){
            assertNotNull(jwtException);
        }
    }

}