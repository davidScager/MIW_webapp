package com.example.cryptobank.controller;

import com.example.cryptobank.service.currency.MethodRunOnScheduleHelper;
import com.example.cryptobank.service.login.LoginAccountServiceClass;
import com.example.cryptobank.service.mailSender.GenerateMailContext;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.security.TokenService;
import com.example.cryptobank.service.transaction.TransactionService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.responseSpecification;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(ResetPasswordController.class)
class ResetPasswordControllerTest {

    @MockBean
    private LoginAccountServiceClass loginAccountService;

//    @MockBean
//    private LoginAccountServiceClass loginAccountServiceClass;

    @MockBean
    private GenerateMailContext generateMailContext;

    @MockBean
    private MailSenderService mailSenderService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private MethodRunOnScheduleHelper methodRunOnScheduleHelper;

    @MockBean
    private TransactionService transactionService;


    private MockMvc mockMvc;

    @Autowired
    public ResetPasswordControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void sendResetMailTestHappyFlow() throws Exception {
        try {
            String emailAddress = "reyndert_mehrer@hotmail.com";
            String token = "12345";
            String mailContext = "ResetMailWithToken: " + token;
            final boolean[] mailIsSent = new boolean[1];
            Mockito.when(loginAccountService.verifyAccount(emailAddress)).thenReturn(true);
            Mockito.when(loginAccountService.addTokenToLoginAccount(emailAddress)).thenReturn(token);
            Mockito.when(generateMailContext.setResetText(token)).thenReturn(mailContext);

            Answer answer = new Answer() {
                @Override
                public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                    System.out.println("Mail is verzonden");
                    mailIsSent[0] = true;

                    return null;
                }
            };
            Mockito.doAnswer(answer).when(mailSenderService).sendMail(emailAddress, mailContext, "Change your password");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/reset/resetpassword");

            request.content("{\"email\": \"reyndert_mehrer@hotmail.com\"}");
            request.header("Accept", "Application/json");
            request.header("Content-Type", "Application/json");
            ResultActions response = mockMvc.perform(request);
            response
                    .andExpect(status().isOk())
                    .andExpect(content().string("Als je een account bij ons hebt ontvang je een email met een reset"));

            assert mailIsSent[0];

        } catch (MalformedURLException | MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendResetMailTestUnhappyFlow() throws Exception{
        try {
            String emailAddress = "reyndert_mehrer@hotmail.com";
            String token = "12345";
            String mailContext = "ResetMailWithToken: " + token;
            final boolean[] mailIsSent = new boolean[1];
            Mockito.when(loginAccountService.verifyAccount(emailAddress)).thenReturn(true);
            Mockito.when(loginAccountService.addTokenToLoginAccount(emailAddress)).thenReturn(token);
            Mockito.when(generateMailContext.setResetText(token)).thenReturn(mailContext);

            Answer answer = new Answer() {
                @Override
                public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                    System.out.println("Mail is verzonden");
                    mailIsSent[0] = true;

                    return null;
                }
            };
            Mockito.doAnswer(answer).when(mailSenderService).sendMail(emailAddress, mailContext, "Change your password");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/reset/resetpassword");

            //sending incorrect mail address in body; status should be OK, but mail should not be sent
            request.content("{\"email\": \"reyndert_mehrerhotmail.com\"}");
            request.header("Accept", "Application/json");
            request.header("Content-Type", "Application/json");
            ResultActions response = mockMvc.perform(request);
            response
                    .andExpect(status().isOk())
                    .andExpect(content().string("Als je een account bij ons hebt ontvang je een email met een reset"));

            assert !mailIsSent[0];

        } catch (MalformedURLException | MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setNewPasswordHappyFlow() throws Exception {
        String token = "1234321";
        String email = "reyndert_mehrer@hotmail.com";
        String password = "nieuweWachtwoord";
        boolean[] passwordUpdated = new boolean[1];

        Mockito.when(tokenService.parseToken(token, "reset")).thenReturn(email);
        Mockito.when(loginAccountService.isTokenStored(email)).thenReturn(true);


        Answer answer = new Answer() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                System.out.println("Wachtwoord is geupdatet");
                passwordUpdated[0] = true;

                return null;
            }
        };
        Mockito.doAnswer(answer).when(loginAccountService).updateResetPassword(email, password);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/reset/createnewpassword");
//        request.content("{\"Authorization\": \"1234321\", \"password\": \"nieuweWachtwoord\"}");
        request.param("Authorization", token);
        request.param("password", password);
        request.header("Accept", "Application/json");
        request.header("Content-Type", "Application/json");
        ResultActions response = mockMvc.perform(request);
        response
                .andExpect(status().isOk())
                .andExpect(content().string("wachtwoord is gereset"));
        assert passwordUpdated[0];
    }

    //TOKEN WORDT 2X GEBRUIKT
    @Test
    public void setNewPasswordUnhappyFlow() throws Exception {
        String token = "1234321";
        String fouteToken = "12321";
        String email = "reyndert_mehrer@hotmail.com";
        String password = "nieuweWachtwoord";
        boolean[] passwordUpdated = new boolean[1];

        Mockito.when(tokenService.parseToken(token, "reset")).thenReturn(email);
        Mockito.when(loginAccountService.isTokenStored(email)).thenReturn(true);


        Answer answer = new Answer() {
            @Override
            public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
                System.out.println("Wachtwoord is geupdatet");
                passwordUpdated[0] = true;

                return null;
            }
        };
        Mockito.doAnswer(answer).when(loginAccountService).updateResetPassword(email, password);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/reset/createnewpassword");
//        request.content("{\"Authorization\": \"1234321\", \"password\": \"nieuweWachtwoord\"}");
        request.param("Authorization", token);
        request.param("password", password);
        request.header("Accept", "Application/json");
        request.header("Content-Type", "Application/json");

        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders.post("/reset/createnewpassword");
//        request.content("{\"Authorization\": \"1234321\", \"password\": \"nieuweWachtwoord\"}");
        request.param("Authorization", token);
        request.param("password", password);
        request.header("Accept", "Application/json");
        request.header("Content-Type", "Application/json");


        mockMvc.perform(request);
        ResultActions response2 = mockMvc.perform(request);
        response2
                .andExpect(status().isOk())
                .andExpect(content().string("Token is al gebruikt"));
        assert !passwordUpdated[0];
    }
}