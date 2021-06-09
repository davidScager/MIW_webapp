package com.example.cryptobank.controller;

import com.example.cryptobank.service.login.RegistrationService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.cryptobank.domain.*;


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
        Mockito.when(mockRegistrationService.register(Mockito.any(UserLoginAccount.class), Mockito.any(Role.class)))
                .thenReturn(new User(1234567, new FullName("Johnny", "", "Bravo"), "1997-11-01",
                        new UserAddress("dorpstraat", 10, "bis", "1234AB", "Zaltbommel"), "johnny.bravo@cartoonnetwork.com"));
        String jsonBody = """
                {
                    "bsn":"1234567",
                    "firstname":"Johnny",
                    "infix":"",
                    "surname":"Bravo",
                    "dateofbirth":"1997-11-01",
                    "postalCode":"1234AB",
                    "houseNr":"10",
                    "addition":"bis",
                    "streetName":"dorpstraat",
                    "residence":"Zaltbommel",
                    "email":"johnny.bravo@cartoonnetwork.com",
                    "password":"ho-M0mm4"
                }""";

        given()
                .body(jsonBody)
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