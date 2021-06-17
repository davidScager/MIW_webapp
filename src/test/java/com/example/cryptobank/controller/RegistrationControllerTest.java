package com.example.cryptobank.controller;

import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.domain.urls.UrlAdresses;
import com.example.cryptobank.domain.user.FullName;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.user.UserAddress;
import com.example.cryptobank.service.login.RegistrationService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationControllerTest {

    @Mock
    private RegistrationService mockRegistrationService;

    @Mock
    private UserLoginAccount mockUserLoginAccount;

    @BeforeEach
    public void initRestAssuredMockMvcStandAlone(){
        this.mockRegistrationService = Mockito.mock(RegistrationService.class);
        this.mockUserLoginAccount = Mockito.mock(UserLoginAccount.class);
        RestAssuredMockMvc.standaloneSetup(new RegistrationController(mockRegistrationService));
    }

    // requirement registering client
    // 0- Body: application/json format
    // 1- URL: POST "/registerclient"
    // 2- status code 200 OK
    // 3- application/json body format in response
    @Test @Order(1)
    public void registrationRequest_returns_200_OK(){
        Mockito.when(mockRegistrationService.validate(Mockito.any(UserLoginAccount.class))).thenReturn(true);

        given()
                .contentType("application/json")
        .when()
                .post("/register/client")
        .then()
                .statusCode(200);
    }

    @Test @Order(2)
    public void registrationRequest_no_request_body_returns_400(){
        given()
                .body("")
                .contentType("application/json")
        .when()
                .post("/register/client")
        .then()
                .statusCode(400);
    }
}