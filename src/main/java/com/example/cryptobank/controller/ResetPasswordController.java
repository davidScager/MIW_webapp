package com.example.cryptobank.controller;

import com.example.cryptobank.service.login.LoginAccountService;
import com.example.cryptobank.service.security.TokenService;
import com.example.cryptobank.service.mailSender.GenerateMailContext;
import com.example.cryptobank.service.mailSender.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.net.MalformedURLException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/reset")
public class ResetPasswordController {
    private Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
    private final LoginAccountService loginAccountService;
    private final MailSenderService mailSenderService;
    private final GenerateMailContext generateMailContext;
    private final TokenService tokenService;
    private String token;
    private String email;


    @Autowired
    public ResetPasswordController(LoginAccountService loginAccountService, MailSenderService mailSenderService, GenerateMailContext generateMailContext, TokenService tokenService) {
        this.tokenService = tokenService;
        logger.info("New MailSenderController");
        this.generateMailContext = generateMailContext;
        this.loginAccountService = loginAccountService;
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/resetpassword")
    public HttpEntity<? extends Object> sendResetPassword(@RequestBody Map<String, String> mailMap) throws MalformedURLException, MessagingException {
        String email = mailMap.values().stream().findFirst().orElse(null);
        logger.info(email);
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

    @PostMapping("/createnewpassword") //javascript haalt token uit url
    public HttpEntity<? extends Object> setNewPassword(@RequestParam("Authorization") String token,@RequestParam String password) {
        try {
            email = tokenService.parseToken(token, "reset");
            logger.info(email + "vanuit Token");
        } catch (Exception e) {
            return ResponseEntity.ok().header("Authorization").body("Token is ongeldig");
        }
        if (loginAccountService.isTokenStored(email)) {
                loginAccountService.updateResetPassword(email, password);
                return ResponseEntity.ok().header("Authorization").body("wachtwoord is gereset");
        }

        return ResponseEntity.ok().header("Authorization").body("Token is al gebruikt");
    }
}

