package com.example.cryptobank.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating random bank account numbers
 * @author David Scager
 */
@Service
public class AccountNrService {
    private static final Logger logger = LoggerFactory.getLogger(AccountNrService.class);
    private static final String ACCOUNT_PREFIX = "NL99 BITB ";
    private static final int ACCOUNT_NR_LENGTH = 10;

    /**
     * Generate SecureRandom numbers
     * append to account prefix
     * @return (String) account number
     */
    public static String randomAccountNr(){
        SecureRandom secureRandom = new SecureRandom();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < ACCOUNT_NR_LENGTH; i++) {
            numbers.add(secureRandom.nextInt(9));
        }
        StringBuilder stringBuilder = new StringBuilder(ACCOUNT_PREFIX);
        numbers.forEach(stringBuilder::append);
        logger.info("New AccountNr generated");
        return stringBuilder.toString();
    }
}
