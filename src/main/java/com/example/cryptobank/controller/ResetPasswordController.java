package com.example.cryptobank.controller;

import com.example.cryptobank.service.LoginAccountService;
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

@RestController
public class ResetPasswordController {
    private Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
    private final LoginAccountService loginAccountService;
    private final MailSenderService mailSenderService;
    private final GenerateMailContext generateMailContext;


    @Autowired
    public ResetPasswordController(LoginAccountService loginAccountService, MailSenderService mailSenderService, GenerateMailContext generateMailContext) {
        logger.info("New MailSenderController");
        this.generateMailContext = generateMailContext;
        this.loginAccountService = loginAccountService;
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/resetpassword")
    public HttpEntity<? extends Object> sendResetPassword(@RequestParam String email) {
        if (!loginAccountService.verifyAccount(email)) {
            logger.info("LoginAccount contains " + email);
            String token = loginAccountService.addTokenToLoginAccount(email);
            String mailText = generateMailContext.setResetText(token);
            mailSenderService.sendMail(email, mailText, "Change your password");
        } else {
            logger.info("email bestaat niet");
        }
        return ResponseEntity.ok().header("Authorization").body("Als je een account bij ons hebt ontvang je een email met een reset");
    }
}

