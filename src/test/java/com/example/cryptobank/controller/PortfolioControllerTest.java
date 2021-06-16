package com.example.cryptobank.controller;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringbootTest
@AutoConfigureMockMvc
public class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;

    @Autowired
    public PortfolioControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void portfolio_overview() {
//        Asset asset = new Asset("Tron", "Tron", "TRX",
//                "Aims to build a free digital entertainment system",
//                0.076064, 1.0, 1.0, 1.0, 1.0);
//
//        try {
//        Mockito
//                .when(portfolioService.showAssetOverview(5))
//                .thenReturn(Arrays.asList(asset));
//
//            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portfoliooverview");
//            request.param("userId", "5");
//
//            ResultActions response = mockMvc.perform(request);
//            response.andExpect(status().isOk());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void portfolio_value() {
        try {
//        Mockito
//                .when(portfolioService.showValueOfPortfolio(5))
//                .thenReturn(6321);

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/portfoliovalue");
            request.param("userId", "5");

            ResultActions response = mockMvc.perform(request);
            response.andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
