package com.example.cryptobank.controller;

import com.example.cryptobank.domain.User;
import com.example.cryptobank.service.TokenService;
import com.example.cryptobank.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import static org.hamcrest.MatcherAssert.assertThat;


import static org.junit.jupiter.api.Assertions.*;

/**
    @Auth HvS
 **/

class LoginControllerTest {

    @MockBean
    private static UserService mockUserService = Mockito.mock(UserService.class);
    @MockBean
    private static TokenService mockTokenService = Mockito.mock(TokenService.class);

    public LoginController loginController = new LoginController(mockUserService, mockTokenService);


    @BeforeAll
    public static void setUp() {
        Mockito.when(mockUserService.verifyUser("huib", "biuh")).thenReturn(
                new User(13167, "huib", "van", "Straten", "29-01-1982", "van lierdreef", "huibvanstraten@gmail.com", "huib"));
        Mockito.when(mockTokenService.getToken()).thenReturn("DitIsEenToken");
    }

//    @Test //test is TDD geschreven. Na slagen test is methode veranderd.
//    void loginUser() {
//        User user = loginController.loginUser("huib", "biuh");
//        int expectedBSN = 13167;
//        int actualBSN = user.getBSN();
//        assertEquals(expectedBSN, actualBSN);
//    }

//    @Test //test is TDD geschreven. Na slagen test is methode veranderd.
//    void getToken() {
//        String actualToken = loginController.loginUser("huib", "biuh");
//        String expectedToken = "DitIsEenToken";
//        assertEquals(expectedToken, actualToken);
//    }

    @Test
    void responseEntityIsNotNull() {
        ResponseEntity<User> actualEntity = loginController.loginUser("huib", "biuh");
        assertNotNull(actualEntity);
    }

    @Test
    void responseEntityIs200() {
        ResponseEntity<User> actualEntity = loginController.loginUser("huib", "biuh");
        assertTrue(actualEntity.getStatusCode().is2xxSuccessful());
    }

//    @Test
//    void responseEntityContainsKey() {
//        assertTrue(loginController.loginUser("huib", "biuh").getHeaders(). // sth ..);
//    }

}