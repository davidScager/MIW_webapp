package com.example.cryptobank.controller;

import com.example.cryptobank.domain.asset.Asset;
import com.example.cryptobank.domain.asset.AssetPortfolioView;
import com.example.cryptobank.domain.portfolio.PortfolioReturnData;
import com.example.cryptobank.domain.urls.UrlAdresses;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.repository.daointerfaces.AssetPortfolioDao;
import com.example.cryptobank.repository.daointerfaces.PortfolioDao;
import com.example.cryptobank.service.assetenportfolio.PortfolioService;
import com.example.cryptobank.service.login.UserService;
import com.example.cryptobank.service.security.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final Logger logger = LoggerFactory.getLogger(AssetController.class);

    private final PortfolioService portfolioService;
    private final PortfolioDao portfolioDao;
    private final AssetPortfolioDao assetPortfolioDao;
    private final UserService userService;
    private UrlAdresses urlAdresses = new UrlAdresses();

    @Autowired
    public PortfolioController(PortfolioService portfolioService, PortfolioDao portfolioDao,
                               AssetPortfolioDao assetPortfolioDao, TokenService tokenService, UserService userService) {
        super();
        this.portfolioService = portfolioService;
        this.portfolioDao = portfolioDao;
        this.assetPortfolioDao = assetPortfolioDao;
        this.userService = userService;
        logger.info("New PortfolioController");
    }

    @GetMapping
    public RedirectView portfolioHtmlHandler() {
        return new RedirectView(urlAdresses.getPortfolioPageUrl());
    }

    @GetMapping("/returnspage")
    public RedirectView portfolioReturnsHtmlHandler() {
        return new RedirectView(urlAdresses.getPortfolioReturnsPageUrl());
    }

    @GetMapping("/overview")
    public List<Asset> portfolioOverviewHandler(@RequestParam int userId) throws JsonProcessingException {
        return portfolioService.showAssetOverview(userId);
    }

    @GetMapping("/value")
    public String portfolioValueHandler(@RequestParam int userId) {
        return portfolioService.showValueOfPortfolio(userId);
    }

    @GetMapping("/returns")
    public List<PortfolioReturnData> portfolioReturnsHandler(@RequestHeader(value = "Authorization") String token, @RequestParam String date) throws IOException {
        User user = userService.getUserFromToken(token);
        return portfolioService.showListOfAssets((int)user.getId(), date);
    }

    @PostMapping("/list")
    public ResponseEntity<List<AssetPortfolioView>> listPortFolio(@RequestHeader(value = "Authorization") String token) {
        //TODO get token
//        System.out.println("fake token "+token);

        User user = userService.getUserFromToken(token);
        if(user == null){

        }
        int portfolioId = portfolioDao.getPortfolioIdByUserId((int)user.getId()).getPortfolioId();
        //Portfolio portfolio = portfolioService.getByActor(userId);
        System.out.println("portfolio "+ portfolioId);
        List<AssetPortfolioView> assetPortfolioView = assetPortfolioDao.getOverviewWithAmount(portfolioId);
        return new ResponseEntity<>(assetPortfolioView, HttpStatus.OK);
    }



}
