package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.service.RegistrationService;
import com.example.cryptobank.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    private final Logger logger = LoggerFactory.getLogger(RegistrationControllerTest.class);

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RegistrationService registrationService;

    @Autowired
    public RegistrationControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        logger.info("New RegistrationControllerTest");
    }

    @Test
    public void RegisterClientTest(){
        User user = new User(12345, "Testman", "t", "Van Der Test", "12-06-1980", "Testlaan 23, Amsterdam", "Testman@hotmail.com", "tester");

        try{
            Mockito.when(userService.register(12345, "Testman", "t", "Van Der Test", "12-06-1980", "Testlaan 23, Amsterdam", "Testman@hotmail.com", "tester", Role.CLIENT)).thenReturn(user);
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/registerClient");
            request.param("BSN", "12345");
            request.param("firstName", "Testman");
            request.param("infix", "t");
            request.param("surname", "Van Der Test");
            request.param("dateOfBirth", "12-06-1980");
            request.param("address", "Testlaan 23, Amsterdam");
            request.param("email", "Testman@hotmail.com");
            request.param("password", "");
            request.param("username", "tester");

            ResultActions response = mockMvc.perform(request);

            response
                    .andExpect(status().isOk())
                    .andDo(print());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
