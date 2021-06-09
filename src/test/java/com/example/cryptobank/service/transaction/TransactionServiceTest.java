package com.example.cryptobank.service.transaction;

import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.login.UserService;
import com.example.cryptobank.service.mailSender.GenerateMailContext;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    @Mock
    private RootRepository mockRootRepository;
    @Mock
    private TokenService mockTokenService;
    @Mock
    private MailSenderService mockMailSenderService;
    @Mock
    private GenerateMailContext mockGenerateMailContext;

    @Autowired
    public TransactionServiceTest(RootRepository rootRepository, TokenService tokenService, MailSenderService mailSenderService, GenerateMailContext generateMailContext) {
        this.mockRootRepository = rootRepository;
        this.mockTokenService = tokenService;
        this.mockMailSenderService = mailSenderService;
        this.mockGenerateMailContext = generateMailContext;
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void returns_null_if_token_is_invalid() {

    }

    @Test
    void getAssetOverviewWithAmount() {
    }

    @Test
    void createNewTransaction() {
    }

    @Test
    void calculateTransactionCost() {
    }

    @Test
    void getMostRecentBuyOrSell() {
    }

    @Test
    void getTransactionHistory() {
    }

    @Test
    void determineBuyOrSell() {
    }

    @Test
    void getMostRecentTrade() {
    }

    @Test
    void deleteTransaction() {
    }

    @Test
    void updateAdjustmentFactor() {
    }

    @Test
    void setTransaction() {
    }

    @Test
    void controlValueAsset() {
    }

    @Test
    void controlREcoursesAndExecute() {
    }
}