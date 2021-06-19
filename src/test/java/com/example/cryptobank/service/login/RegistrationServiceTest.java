package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.mailsenderfacade.SendMailServiceFacade;
import com.example.cryptobank.service.security.TokenService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationServiceTest {
    private RegistrationService testRegistrationService;
    private UserLoginAccount actualUserLoginAccount;
    private static final String EMAIL = "dingetje@email.nl";
    private static final String PASSWORD = "wachtwoord";
    private static final String TOKEN = "not.a.token";
    private static final String JWT_SUBJECT = "Register";
    private static final int DURATION_VALID_MILLIS = 100;

    @Mock
    RootRepository mockRootRepository;

    @Mock
    TokenService mockTokenService;

    @Mock
    SendMailServiceFacade mockSendMailServiceFacade;

    @BeforeEach
    public void setup(){
        mockRootRepository = Mockito.mock(RootRepository.class);
        mockTokenService = Mockito.mock(TokenService.class);
        mockSendMailServiceFacade = Mockito.mock(SendMailServiceFacade.class);
        actualUserLoginAccount = new UserLoginAccount(123456, "Dingetje", "van", "Dinges", "2000-01-01",
                "2345AB", 20, "", "Dorpstraat", "Lutjebroek", EMAIL, PASSWORD);
        testRegistrationService = new RegistrationService(mockRootRepository, mockTokenService, mockSendMailServiceFacade);
    }

    @Test @Order(1)
    public void registrationService_not_null(){
        assertThat(testRegistrationService).isNotNull();
    }

    @Test @Order(2)
    public void set_mockRootRepository_not_null(){
        assertThat(mockRootRepository).isNotNull();
    }

    @Test @Order(3)
    public void set_mockTokenService_not_null(){
        assertThat(mockTokenService).isNotNull();
    }

    @Test @Order(4)
    public void set_mockSendMailServiceFacade_not_null(){
        assertThat(mockSendMailServiceFacade).isNotNull();
    }

    @Test @Order(5)
    void userLoginAccount_unregistered_validates_true() {
        Mockito.when(mockRootRepository.alreadyRegistered(actualUserLoginAccount)).thenReturn(false);
        assertThat(testRegistrationService.validate(actualUserLoginAccount)).isTrue();
    }

    @Test @Order(6)
    void userLoginAccount_registered_validates_false() {
        Mockito.when(mockRootRepository.alreadyRegistered(actualUserLoginAccount)).thenReturn(true);
        assertThat(testRegistrationService.validate(actualUserLoginAccount)).isFalse();
    }

    @Test @Order(7)
    void userLoginAccount_cached(){
        Mockito.when(mockTokenService.generateJwtToken(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(TOKEN);
        String expected = testRegistrationService.cacheNewUserWithToken(actualUserLoginAccount);
        assertThat(expected).isEqualTo(TOKEN);
    }

    @Test @Order(8)
    void userLoginAccount_cached_unregistered_validates_false(){
        Mockito.when(mockRootRepository.alreadyRegistered(actualUserLoginAccount)).thenReturn(false);
        Mockito.when(mockTokenService.generateJwtToken(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(TOKEN);
        testRegistrationService.cacheNewUserWithToken(actualUserLoginAccount);
        assertThat(testRegistrationService.validate(actualUserLoginAccount)).isFalse();
    }

    @Test @Order(9)
    void cache_cleared_on_timer_validates_true() throws InterruptedException {
        Mockito.when(mockTokenService.generateJwtToken(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(TOKEN);
        Mockito.when(mockRootRepository.alreadyRegistered(actualUserLoginAccount)).thenReturn(false);
        testRegistrationService.setRemoveAfter(DURATION_VALID_MILLIS);
        testRegistrationService.cacheNewUserWithToken(actualUserLoginAccount);
        Thread.sleep(200);
        assertThat(testRegistrationService.validate(actualUserLoginAccount)).isTrue();
    }

    @Test @Order(10)
    void confirmationEmailSent_no_exception(){
        assertThat(testRegistrationService.confirmationEmailSent(EMAIL, TOKEN)).isTrue();
    }

    @Test @Order(11)
    void validateToken_token_expired(){
        Mockito.when(mockTokenService.parseToken(TOKEN, JWT_SUBJECT)).thenThrow(RuntimeException.class);
        assertThat(testRegistrationService.validateToken(TOKEN, JWT_SUBJECT)).isFalse();
    }

    @Test @Order(12)
    void validateToken_token_valid_user_not_cached(){
        Mockito.when(mockTokenService.parseToken(TOKEN, JWT_SUBJECT)).thenReturn(EMAIL);
        assertThat(testRegistrationService.validateToken(TOKEN, JWT_SUBJECT)).isFalse();
    }

    @Test @Order(13)
    void validateToken_token_valid_user_cached(){
        Mockito.when(mockTokenService.generateJwtToken(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(TOKEN);
        Mockito.when(mockTokenService.parseToken(TOKEN, JWT_SUBJECT)).thenReturn(EMAIL);
        testRegistrationService.cacheNewUserWithToken(actualUserLoginAccount);
        assertThat(testRegistrationService.validateToken(TOKEN, JWT_SUBJECT)).isTrue();
    }

}