package com.example.cryptobank.controller;

import com.example.cryptobank.service.login.LoginAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResetPasswordController.class)
class ResetPasswordControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private LoginAccountService loginAccountService;

    @Autowired
    public ResetPasswordControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    public void sendResetMailTest() {
        try {
            Mockito.when(loginAccountService.verifyAccount("huib@huib.com")).thenReturn(true);

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/resetPassword");
            request.param("email", "huib@huib.com");
            ResultActions response = mockMvc.perform(request);
            response
                    .andExpect(status().isOk())
                    .andExpect(header().exists("Authorization"))
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}