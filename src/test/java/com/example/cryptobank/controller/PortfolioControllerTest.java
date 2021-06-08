package com.example.cryptobank.controller;

import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PortfolioController.class)
public class PortfolioControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private static PortfolioService portfolioService;

    @Autowired
    public PortfolioControllerTest(MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public static void setUp() {

    }

    @Test
    public void portfolio_overview() {
        try {
//            Mockito.when(portfolioService.showAssetOverview(1)).thenReturn();

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portfoliooverview");
            request.param("userId", "1");
            ResultActions response = mockMvc.perform(request);
            response.andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void portfolio_value() {

    }

    @Test
    public void list_portfolio() {

    }
}
