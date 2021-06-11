package com.example.cryptobank.controller;

import com.example.cryptobank.domain.user.FullName;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.user.UserAddress;
import com.example.cryptobank.service.login.UserService;
import com.example.cryptobank.service.security.TokenService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

/**
 @Auth HvS
 **/

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginControllerTest {

    @Mock
    private static UserService mockUserService;
    @Mock
    private static TokenService mockTokenService;

    @BeforeEach
    public void initRestAssuredMockMvcStandAlone() {
        mockUserService = Mockito.mock(UserService.class);
        mockTokenService = Mockito.mock(TokenService.class);
        RestAssuredMockMvc.standaloneSetup(new LoginController(mockUserService, mockTokenService));
        Mockito.when(mockUserService.verifyUser("huibvanstraten@gmail.com", "biuh")).thenReturn(
                new User(13167, new FullName("huib", "van", "Straten"), "29-01-1982",
                        new UserAddress("van lierdreef", 10, "a", "1234ab", "Blaricum"), "huibvanstraten@gmail.com"));
        Mockito.when(mockTokenService.generateJwtToken("huibvanstraten@gmail.com", "session", 60 )).thenReturn("ditiseentoken");
    }

    @Test
    public void method_doesnt_allow_get() {
        when()
                .get("/login")
        .then()
                .statusCode(405);
    }

    @Test
    public void login_required_parameters_not_present_returns_400() {
        when()
                .post("/login")
        .then()
                .statusCode(400);
    }

    @Test
    public void wrong_login_params_returns_401_UNAUTHORIZED() {
        String requestBody = "{\"username\": \"huib\",\n" +
                "\"password\": \"biuh\"}";
        given()
                .header("Content-type", "application/json")
                .body(requestBody).
        when()
                .post("/login")
        .then()
                .statusCode(401);
    }

    @Test
    public void with_right_login_returns_200() {
        String requestBody = "{\"username\": \"huibvanstraten@gmail.com\",\n" +
                "\"password\": \"biuh\"}";
        given()
                .header("Content-type", "application/json")
                .body(requestBody).
        when()
                .post("/login").
        then()
                .statusCode(200);
    }

    @Test
    public void with_right_login_returns_JSON() {
        String requestBody = "{\"username\": \"huibvanstraten@gmail.com\",\n" +
                "\"password\": \"biuh\"}";
        given()
                .header("Content-type", "application/json")
                .body(requestBody).
        when()
                .post("/login").
        then()
                .contentType(ContentType.JSON);
    }

    @Test
    public void with_right_login_returns_User() {
        String requestBody = "{\"username\": \"huibvanstraten@gmail.com\",\n" +
                "\"password\": \"biuh\"}";
        given()
                .header("Content-type", "application/json")
                .body(requestBody).
        when()
                .post("/login").
        then()
                .extract()
                .body()
                .as(User.class);
    }

    @Test
    public void with_right_login_returns_token() {
        String requestBody = "{\"username\": \"huibvanstraten@gmail.com\",\n" +
                "\"password\": \"biuh\"}";
        given()
                .header("Content-type", "application/json")
                .body(requestBody).
        when()
                .post("/login").
        then()
                .header("Authorization", "ditiseentoken");
    }
}