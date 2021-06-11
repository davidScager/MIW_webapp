package com.example.cryptobank.controller;

import com.example.cryptobank.domain.user.FullName;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.user.UserAddress;
import com.example.cryptobank.service.login.UserService;
import com.example.cryptobank.service.security.TokenService;
import com.example.cryptobank.service.transaction.TransactionService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    private Object TransactionHTMLBank;

    private MockMvc mockMvc;

    @Mock
    private static TokenService mockTokenService;

    @Mock
    private static TransactionService mockTransactionService;

    @Mock
    private static UserService mockUserService;

    @Autowired
    public TransactionControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public void initRestAssuredMockMvcStandAlone() {
        RestAssuredMockMvc.standaloneSetup(new LoginController(mockUserService, mockTokenService));
        Mockito.when(mockUserService.verifyUser("niek@mol.com", "kaas")).thenReturn(
                new User(1, new FullName("Niek", null, "Mol"), "01-01-0000",
                        new UserAddress("Huis", 1, "a", "1234ab", "Jaruzalem"), "niek@mol.com"));
        Mockito.when(mockTokenService.generateJwtToken("niek@mol.com", "session", 60 )).thenReturn("ditiseentoken");
    }


    @Test
    void authorizeAndGetAssets() {
        Mockito.when(mockTransactionService.authorizeAndGetAssets("niek")).thenReturn(new ArrayList<>());
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