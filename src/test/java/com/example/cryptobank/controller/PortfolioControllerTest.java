package com.example.cryptobank.controller;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import com.example.cryptobank.service.login.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PortfolioController.class)
public class PortfolioControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private static PortfolioService portfolioService;
    private static UserService userService;
    private static Asset asset;

    @Autowired
    public PortfolioControllerTest(MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    public static void setUp() {
        asset = new Asset("Tron", "Tron", "TRX",
                "Aims to build a free digital entertainment system",
                0.076064, 1.0, 1.0, 1.0, 1.0);
    }

    @Test
    public void portfolio_overview() {
        Mockito
                .when(portfolioService.showAssetOverview(5))
                .thenReturn((List<Asset>) asset);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portfoliooverview");
        request.param("userId", String.valueOf(5));
        try {
            ResultActions response = mockMvc.perform(request);
            response.andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void portfolio_value() {
        Mockito
                .when(portfolioService.showValueOfPortfolio(5))
                .thenReturn(String.valueOf(6321));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portfoliovalue");
        request.param("userId", String.valueOf(5));

        try {
            ResultActions response = mockMvc.perform(request);
            response.andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void list_portfolio() {

    }
}
