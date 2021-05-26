package com.example.cryptobank.service;

import com.example.cryptobank.service.security.PepperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author David_Scager
 */
class PepperServiceTest {
    private PepperService pepperService;

    @BeforeEach
    void setup(){
        pepperService = new PepperService();
    }

    @Test
    void getPepper() {
        String expected =  "27d05704";
        assertEquals(expected, pepperService.getPepper());
    }
}