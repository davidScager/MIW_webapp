package com.example.cryptobank.service.transaction;

import com.example.cryptobank.domain.transaction.TransactionHTMLClient;
import com.example.cryptobank.domain.transaction.TransactionLog;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.GenerateMailContent;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TransactionServiceTest {

    @Mock
    private RootRepository mockRootRepository;
    @Mock
    private TokenService mockTokenService;
    @Mock
    private MailSenderService mockMailSenderService;
    @Mock
    private GenerateMailContent mockGenerateMailContent;

    @BeforeEach
    public void setUp() {
        TransactionHTMLClient testTransActionClient = new TransactionHTMLClient("a", 12.0, 11.0);
        ArrayList<TransactionHTMLClient> htmlClients = new ArrayList<>();
        htmlClients.add(testTransActionClient);
        TransactionLog expectedLog = new TransactionLog(2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1);
        mockRootRepository = Mockito.mock(RootRepository.class);
        mockTokenService = Mockito.mock(TokenService.class);
        mockMailSenderService = Mockito.mock(MailSenderService.class);
        mockGenerateMailContent = Mockito.mock(GenerateMailContent.class);
        Mockito.when(mockTokenService.parseToken("123", "session")).thenReturn("huibEnNiek");
        Mockito.when(mockRootRepository.clientListForTransactionHTML("huibEnNiek")).thenReturn(htmlClients);
        Mockito.when(mockRootRepository.createNewTransactionLog("BTC", "ETH", 2, 0.3)).thenReturn(expectedLog);
    }

    public TransactionServiceTest() {
        super();
    }

    @Test
    void returns_null_if_token_is_invalid() {
        var transactionTest = new TransactionService(mockRootRepository, mockTokenService, mockMailSenderService, mockGenerateMailContent, mailSenderFacade);
        List<TransactionHTMLClient> actualList = transactionTest.authorizeAndGetAssets("456");
        assertThat(actualList).isNull();
    }

    @Test
    void mocked_tokenservice_returns_right_username() {
        String username = mockTokenService.parseToken("123", "session");
        assertThat(username).isEqualTo("huibEnNiek");
    }

    @Test
    void mocked_rootrepository_returns_filled_arraylist() {
        ArrayList<TransactionHTMLClient> actualList = mockRootRepository.clientListForTransactionHTML("huibEnNiek");
        assertThat(actualList.get(0).getAssetName()).isEqualTo("a");
    }

    @Test
    void returns_arraylist_with_values() {
        var transactionTest = new TransactionService(mockRootRepository, mockTokenService, mockMailSenderService, mockGenerateMailContent, mailSenderFacade);
        ArrayList<TransactionHTMLClient> actualList = transactionTest.authorizeAndGetAssets("123");
        assertThat(actualList).isNotNull();
    }

    @Test
    void createNewTransaction() {
        var transactionTest = new TransactionService(mockRootRepository, mockTokenService, mockMailSenderService, mockGenerateMailContent, mailSenderFacade);
        //Transaction actualTransaction = transactionTest.createNewTransaction(1,2,2,0.3, "BTC", "ETH");
        //assertThat(actualTransaction.getTransactionLog()).isEqualTo(new TransactionLog(2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1));
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