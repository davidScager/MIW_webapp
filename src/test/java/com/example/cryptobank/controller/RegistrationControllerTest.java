package com.example.cryptobank.controller;

import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.domain.urls.UrlAdresses;
import com.example.cryptobank.service.login.RegistrationService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationControllerTest {
    private final UrlAdresses urlAdresses = new UrlAdresses();
    private static final String TOKEN = "notoken";
    private static final String JWT_SUBJECT = "Register";
    private static final String JSON_BODY =
            "{\"user\":{" +
                "\"bsn\":\"123456\"," +
                "\"fullName\": {" +
                    "\"firstName\":\"Dingetje\"," +
                    "\"infix\":\"van\"," +
                    "\"surname\":\"Dinges\"}," +
                "\"dateOfBirth\":\"2000-01-01\"," +
                "\"userAddress\":{" +
                    "\"postalCode\":\"2345AB\"," +
                    "\"houseNr\":\"20\"," +
                    "\"addition\":\"\"," +
                    "\"streetName\":\"Dorpstraat\"," +
                    "\"residence\":\"Lutjebroek\"}," +
                "\"email\":\"not.an@email.org\"}," +
            "\"password\":\"password\"" +
            "}";

    @Mock
    private RegistrationService mockRegistrationService;

    @BeforeEach
    public void initRestAssuredMockMvcStandAlone(){
        mockRegistrationService = Mockito.mock(RegistrationService.class);
        RestAssuredMockMvc.standaloneSetup(new RegistrationController(mockRegistrationService));
    }

    @Test @Order(1)
    public void viewHtmlRegisterHandler_returns_302_moved_temporarily(){
        when()
                .get("/register")
        .then()
                .statusCode(302);
    }

    @Test @Order(2)
    public void viewHtmlRegisterHandler_header_contains_right_url(){
        String registerUrl = urlAdresses.getRegistrationPageUrl();

        when()
                .get("/register")
        .then()
                .header("Location", registerUrl);
    }

    @Test @Order(3)
    public void viewHtmlRegisterFailedHandler_returns_302_moved_temporarily(){
        when()
                .get("/register/failed")
        .then()
                .statusCode(302);
    }

    @Test @Order(4)
    public void viewHtmlRegisterFailedHandler_header_contains_right_url(){
        String registerFailedUrl = urlAdresses.getRegistrationFailedPageUrl();

        when()
                .get("/register/failed")
        .then()
                .header("Location", registerFailedUrl);
    }

    @Test @Order(5)
    public void register_request_given_json_body_returns_200(){
        Mockito.when(mockRegistrationService.validate(Mockito.any(UserLoginAccount.class))).thenReturn(true);
        Mockito.when(mockRegistrationService.cacheNewUserWithToken(Mockito.any(UserLoginAccount.class))).thenReturn(TOKEN);
        Mockito.when(mockRegistrationService.confirmationEmailSent(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        given()
                .body(JSON_BODY)
                .contentType(ContentType.JSON)
        .when()
                .post("/register/request")
        .then()
                .statusCode(200);

    }

    @Test @Order(6)
    public void register_request_given_json_body_email_fails_returns_400(){
        Mockito.when(mockRegistrationService.validate(Mockito.any(UserLoginAccount.class))).thenReturn(true);
        Mockito.when(mockRegistrationService.cacheNewUserWithToken(Mockito.any(UserLoginAccount.class))).thenReturn(TOKEN);
        Mockito.when(mockRegistrationService.confirmationEmailSent(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        given()
                .body(JSON_BODY)
                .contentType(ContentType.JSON)
        .when()
                .post("/register/request")
        .then()
                .statusCode(400);

    }

    @Test @Order(7)
    public void register_request_given_json_body_user_already_registered_returns_400(){
        Mockito.when(mockRegistrationService.validate(Mockito.any(UserLoginAccount.class))).thenReturn(false);

        given()
                .body(JSON_BODY)
                .contentType(ContentType.JSON)
        .when()
                .post("/register/request")
        .then()
                .statusCode(400);

    }

    @Test @Order(8)
    public void register_finalize_given_valid_token_param_returns_302(){
        Mockito.when(mockRegistrationService.validateToken(TOKEN, JWT_SUBJECT)).thenReturn(true);

        given()
                .param("Authorization", TOKEN)
        .when()
                .get("/register/finalize")
        .then()
                .statusCode(302);
    }

    @Test @Order(9)
    public void register_finalize_given_valid_token_param_header_contains_right_url(){
        String loginRedirect = urlAdresses.getLoginPage();
        Mockito.when(mockRegistrationService.validateToken(TOKEN, JWT_SUBJECT)).thenReturn(true);

        given()
                .param("Authorization", TOKEN)
        .when()
                .get("/register/finalize")
        .then()
                .header("Location", loginRedirect);
    }

    @Test @Order(10)
    public void register_finalize_given_invalid_token_param_returns_302(){
        Mockito.when(mockRegistrationService.validateToken(TOKEN, JWT_SUBJECT)).thenReturn(false);

        given()
                .param("Authorization", TOKEN)
        .when()
                .get("/register/finalize")
        .then()
                .statusCode(302);
    }

    @Test @Order(11)
    public void register_finalize_given_invalid_token_param_header_contains_right_url(){
        String registrationFailedUrl = urlAdresses.getRegistrationFailedPageUrl();
        Mockito.when(mockRegistrationService.validateToken(TOKEN, JWT_SUBJECT)).thenReturn(false);

        given()
                .param("Authorization", TOKEN)
        .when()
                .get("/register/finalize")
        .then()
                .header("Location", registrationFailedUrl);
    }

}