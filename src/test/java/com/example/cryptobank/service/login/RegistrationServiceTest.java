package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.mailsenderfacade.SendMailServiceFacade;
import com.example.cryptobank.service.security.TokenService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationServiceTest {
    private final RegistrationService testRegistrationService;
    private final UserLoginAccount actualUserLoginAccount;
    private static final String EMAIL = "dingetje@gmol.nl";
    private static final String PASSWORD = "wachtwoord";
    private static final String TOKEN = "not.a.token";
    private static final String JWT_SUBJECT = "Register";
    private static final Role ROLE = Role.CLIENT;

    @Mock
    RootRepository mockRootRepository;

    @Mock
    TokenService mockTokenService;

    @Mock
    SendMailServiceFacade mockSendMailServiceFacade;

    @Autowired
    public RegistrationServiceTest(){
        this.mockRootRepository = Mockito.mock(RootRepository.class);
        this.mockTokenService = Mockito.mock(TokenService.class);
        this.mockSendMailServiceFacade = Mockito.mock(SendMailServiceFacade.class);
        this.testRegistrationService = new RegistrationService(mockRootRepository, mockTokenService, mockSendMailServiceFacade);
        this.actualUserLoginAccount = new UserLoginAccount(123456, "Dingetje", "van", "Dinges", "2000-01-01",
                "2345AB", 20, "", "Dorpstraat", "Lutjebroek", EMAIL, PASSWORD);
        testRegistrationService.setDurationUnitMillis(100);
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
    void userLoginAccount_cached_with_token(){
        Mockito.when(mockTokenService.generateJwtToken(actualUserLoginAccount.getUser().getEmail(), "Register", 30)).thenReturn(TOKEN);
        String expected = testRegistrationService.cacheNewUserWithToken(actualUserLoginAccount);
        assertThat(expected).isEqualTo(TOKEN);
    }


}