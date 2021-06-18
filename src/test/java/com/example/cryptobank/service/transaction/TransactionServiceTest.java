//package com.example.cryptobank.service.transaction;
//
//import com.example.cryptobank.domain.transaction.Transaction;
//import com.example.cryptobank.domain.transaction.TransactionData;
//import com.example.cryptobank.domain.transaction.TransactionHTMLClient;
//import com.example.cryptobank.domain.transaction.TransactionLog;
//import com.example.cryptobank.repository.jdbcklasses.RootRepository;
//import com.example.cryptobank.service.mailSender.mailsenderfacade.MailSenderFacade;
//import com.example.cryptobank.service.security.TokenService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class TransactionServiceTest {
//    private TransactionHTMLClient testTransActionClient;
//    private TransactionLog expectedLog;
//    private TransactionData transactionData;
//
//    @Mock
//    private RootRepository mockRootRepository;
//    @Mock
//    private TokenService mockTokenService;
//    @Mock
//    private MailSenderFacade mockMailSenderFacade;
//
//    @BeforeEach
//    public void setUp() {
//        testTransActionClient = new TransactionHTMLClient("a", 12.0, "ab", 11.0);
//        ArrayList<TransactionHTMLClient> htmlClients = new ArrayList<>();
//        htmlClients.add(testTransActionClient);
//        expectedLog = new TransactionLog(2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1);
//        mockRootRepository = Mockito.mock(RootRepository.class);
//        mockTokenService = Mockito.mock(TokenService.class);
//        mockMailSenderFacade = Mockito.mock(MailSenderFacade.class);
//        transactionData = new TransactionData(1, 2, 2.0, "BTC", "ETH", 30000.0, "huibEnNiek");
//        transactionData.setTransactionCost(1);
//        Mockito.when(mockTokenService.parseToken("123", "session")).thenReturn("huibEnNiek");
//        Mockito.when(mockRootRepository.clientListForTransactionHTML("huibEnNiek")).thenReturn(htmlClients);
//        Mockito.when(mockRootRepository.createNewTransactionLog("BTC", "ETH", 2.0, 1)).thenReturn(expectedLog);
//    }
//
//    public TransactionServiceTest() {
//        super();
//    }
//
//    @Test
//    void returns_null_if_token_is_invalid() {
//        var transactionTest = new TransactionService(mockRootRepository, mockTokenService, mockMailSenderFacade, triggerService);
//        List<TransactionHTMLClient> actualList = transactionTest.authorizeAndGetAssets("456");
//        assertThat(actualList).isEmpty();
//    }
//
//    @Test
//    void mocked_tokenservice_returns_right_username() {
//        String username = mockTokenService.parseToken("123", "session");
//        assertThat(username).isEqualTo("huibEnNiek");
//    }
//
//    @Test
//    void mocked_rootrepository_returns_filled_arraylist() {
//        ArrayList<TransactionHTMLClient> actualList = mockRootRepository.clientListForTransactionHTML("huibEnNiek");
//        assertThat(actualList.get(0).getAssetName()).isEqualTo("a");
//    }
//
//    @Test
//    void valid_token_check_returns_filled_List() {
//        var transactionTest = new TransactionService(mockRootRepository, mockTokenService, mockMailSenderFacade, triggerService);
//        List<TransactionHTMLClient> actualList = transactionTest.authorizeAndGetAssets("123");
//        assertThat(actualList.get(0)).isEqualTo(testTransActionClient);
//
//    }
//
//    @Test
//    void method_returns_NotNull() {
//        var transactionTest = new TransactionService(mockRootRepository, mockTokenService, mockMailSenderFacade, triggerService);
//        Transaction actualTransaction = transactionTest.createNewTransaction(new TransactionData(1, 2, 2.0, "BTC", "ETH", 30000.0, "huibEnNiek"));
//        assertThat(actualTransaction).isNotNull();
//    }
//
//    @Test
//    void method_returns_Transaction_with_checked_value() {
//        var transactionTest = new TransactionService(mockRootRepository, mockTokenService, mockMailSenderFacade, triggerService);
//        TransactionData actualData = new TransactionData(1, 2, 2.0, "BTC", "ETH", 30000.0, "huibEnNiek");
//        actualData.setTransactionCost(1);
//        Transaction actualTransaction = transactionTest.createNewTransaction(actualData);
//        assertThat(actualTransaction.getTransactionLog()).isEqualTo(expectedLog);
//    }
//}