package com.example.cryptobank.controller;

import com.example.cryptobank.service.login.RegistrationService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import com.example.cryptobank.domain.*;


import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;

    @BeforeEach
    public void initRestAssuredMockMvcStandAlone(){
        this.registrationService = Mockito.mock(RegistrationService.class);
        RestAssuredMockMvc.standaloneSetup(new RegistrationController(registrationService));
    }

    // requirement registering client
    // 0- Body: application/json format
    // 1- URL: POST "/registerclient"
    // 2- status code 200 OK
    // 3- application/json body format in response
    @Test @Order(1)
    public void registrationRequest_returns_200_OK(){
        Mockito.when(registrationService.validate(Mockito.any(UserLoginAccount.class))).thenReturn(true);
        Mockito.when(registrationService.register(Mockito.any(UserLoginAccount.class), Mockito.any(Role.class)))
                .thenReturn(new User(1234567, new FullName("Johnny", "", "Bravo"), "1997-11-01",
                        new UserAddress("dorpstraat", 10, "bis", "1234AB", "Zaltbommel"), "johnny.bravo@cartoonnetwork.com"));
        String jsonBody = """
                {
                    "bsn":"1234567",
                    "firstname":"Johnny",
                    "infix":"",
                    "surname":"Bravo",
                    "houseNr":"10",
                    "addition":"bis",
                    "dateofbirth":"1997-11-01",
                    "streetName":"dorpstraat",
                    "houseNr":"10",
                    "postalCode":"1234AB",
                    "residence":"Zaltbommel",
                    "email":"johnny.bravo@cartoonnetwork.com",
                    "password":"ho-M0mm4"
                }""";

        given()
                .body(jsonBody)
                .contentType("application/json")
        .when()
                .post("/registerclient")
        .then()
                .statusCode(200);
    }

    @Test @Order(2)
    public void registrationRequest_no_request_body_returns_400(){
        String jsonBody = "";

        given()
                .body(jsonBody)
                .contentType("application/json")
        .when()
                .post("/registerclient")
        .then()
                .statusCode(400);
    }

    @Test @Order(3)
    public void registrationRequest_request_body_fields_empty_returns_400(){
        Mockito.when(registrationService.validate(Mockito.any(UserLoginAccount.class))).thenReturn(false);
        String jsonBody = """
                {
                    "bsn":"",
                    "firstname":"",
                    "infix":"",
                    "surname":"",
                    "houseNr":"",
                    "addition":"",
                    "dateofbirth":"",
                    "streetName":"",
                    "houseNr":"",
                    "postalCode":"",
                    "residence":"",
                    "email":""
                }""";

        given()
                .body(jsonBody)
                .contentType("application/json")
        .when()
                .post("/registerclient")
        .then()
                .statusCode(400);
    }
}