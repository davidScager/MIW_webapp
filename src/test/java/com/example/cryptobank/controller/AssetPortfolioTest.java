package com.example.cryptobank.controller;


import com.example.cryptobank.domain.FullName;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserAddress;
import com.example.cryptobank.service.login.UserService;
import com.example.cryptobank.service.security.TokenService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssetPortfolioTest {

    @Mock
    private static UserService mockUserService;
    @Mock
    private static TokenService mockTokenService;

    @BeforeEach
    public void initRestAssuredMockMvcStandAlone() {
        mockUserService = Mockito.mock(UserService.class);
        mockTokenService = Mockito.mock(TokenService.class);


    }
}
