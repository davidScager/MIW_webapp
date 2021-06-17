package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.mailsenderfacade.SendMailServiceFacade;
import com.example.cryptobank.service.security.TokenService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationServiceTest {
    private RegistrationService testRegistrationService;
    private UserLoginAccount actualUserLoginAccount;
    private static final String EMAIL = "dingetje@gmol.nl";
    private static final String PASSWORD = "wachtwoord";
    private static final String TOKEN = "not.a.token";
    private static final String JWT_SUBJECT = "Register";
    private static final Role ROLE = Role.CLIENT;
    private static final int DURATON_VALID = 100;

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
        testRegistrationService.setDurationUnitMillis(DURATON_VALID);
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
    void userLoginAccount_validated() {
        Mockito.when(mockRootRepository.alreadyRegistered(actualUserLoginAccount)).thenReturn(false);
        assertThat(testRegistrationService.validate(actualUserLoginAccount)).isTrue();
    }

    @Test @Order(6)
    void userLoginAccount_not_validated() {
        assertThat(testRegistrationService.validate(actualUserLoginAccount)).isFalse();
    }

    @Test @Order(7)
    void userLoginAccount_cached_with_token(){
        Mockito.when(mockTokenService.generateJwtToken(actualUserLoginAccount.getUser().getEmail(), JWT_SUBJECT, DURATON_VALID)).thenReturn(TOKEN);
        String expected = testRegistrationService.cacheNewUserWithToken(actualUserLoginAccount);
        assertThat(expected).isEqualTo(TOKEN);
    }

    @Test @Order(8)
    void userLoginAccount_cached_revalidate_returns_false(){

    }

}