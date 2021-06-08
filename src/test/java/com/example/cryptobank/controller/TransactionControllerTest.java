package com.example.cryptobank.controller;

import com.example.cryptobank.domain.FullName;
import com.example.cryptobank.domain.TransactionHTMLBank;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserAddress;
import com.example.cryptobank.service.login.UserService;
import com.example.cryptobank.service.security.TokenService;
import com.example.cryptobank.service.transaction.TransactionService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerTest {

    private Object TransactionHTMLBank;

    @Mock
    private static TokenService mockTokenService;

    @Mock
    private static TransactionService mockTransactionService;

    @Mock
    private static UserService mockUserService;



    @BeforeEach
    public void initRestAssuredMockMvcStandAlone() {
        mockTransactionService = Mockito.mock(TransactionService.class);
        mockTokenService = Mockito.mock(TokenService.class);
        mockUserService = Mockito.mock(UserService.class);
        RestAssuredMockMvc.standaloneSetup(new LoginController(mockUserService, mockTokenService));
        Mockito.when(mockUserService.verifyUser("niek@mol.com", "kaas")).thenReturn(
                new User(1, new FullName("Niek", null, "Mol"), "01-01-0000",
                        new UserAddress("Huis", 1, "a", "1234ab", "Jaruzalem"), "niek@mol.com"));
        Mockito.when(mockTokenService.generateJwtToken("niek@mol.com", "session", 60 )).thenReturn("ditiseentoken");
    }


    @Test
    void authorizeAndGetAssets() {

    }

    @Test
    void planTransaction() {
    }

    @Test
    void getAssetOverviewWithAmount() {
    }

    @Test
    void getAssetOverviewOfUser() {
    }

    @Test
    void transactionCost() {
    }

    @Test
    void mostRecentTransactionHandler() {
    }

    @Test
    void transactionHistoryHandler() {
    }

    @Test
    void createTransaction() {
    }
}