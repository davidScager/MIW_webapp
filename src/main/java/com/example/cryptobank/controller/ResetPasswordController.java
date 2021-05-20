package com.example.cryptobank.controller;

import com.example.cryptobank.service.LoginAccountService;
import com.example.cryptobank.service.TokenService;
import com.example.cryptobank.service.mailSender.GenerateMailContext;
import com.example.cryptobank.service.mailSender.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
public class ResetPasswordController {
    private Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
    private final LoginAccountService loginAccountService;
    private final MailSenderService mailSenderService;
    private final GenerateMailContext generateMailContext;
    private final TokenService tokenService;
    private String token;


    @Autowired
    public ResetPasswordController(LoginAccountService loginAccountService, MailSenderService mailSenderService, GenerateMailContext generateMailContext, TokenService tokenService) {
        this.tokenService = tokenService;
        logger.info("New MailSenderController");
        this.generateMailContext = generateMailContext;
        this.loginAccountService = loginAccountService;
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/resetpassword")
    public HttpEntity<? extends Object> sendResetPassword(@RequestParam String email) throws MalformedURLException {
        if (loginAccountService.verifyAccount(email)) {
            logger.info("LoginAccount contains " + email);
            token = loginAccountService.addTokenToLoginAccount(email);
            logger.info(token);
            String mailText = generateMailContext.setResetText(token);
            mailSenderService.sendMail(email, mailText, "Change your password");
        } else {
            logger.info("email bestaat niet");

        }
        return ResponseEntity.ok().header("Authorization").body("Als je een account bij ons hebt ontvang je een email met een reset");
    }

    @PostMapping("/createnewpassword") //javascript haalt token uit url // checken of token in DB staat // token blijft zelfde..
    public HttpEntity<? extends Object> setNewPassword(@RequestParam("Authorization") String token, String password) {
        logger.info(token);
        String email = tokenService.parseToken(token, "reset");
        loginAccountService.updateResetPassword(email);
        logger.info(email);
        return ResponseEntity.ok().header("Authorization").body("wachtwoord is gereset");
    }
}

