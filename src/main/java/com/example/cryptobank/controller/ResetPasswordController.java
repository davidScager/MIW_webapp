package com.example.cryptobank.controller;

import com.example.cryptobank.domain.urls.UrlAdresses;
import com.example.cryptobank.service.login.LoginAccountService;
import com.example.cryptobank.service.mailSender.mailsenderfacade.MailSenderFacade;
import com.example.cryptobank.service.mailSender.mailsenderfacade.SendMailServiceFacade;
import com.example.cryptobank.domain.maildata.MailData;
import com.example.cryptobank.domain.maildata.ResetMailData;
import com.example.cryptobank.service.security.TokenService;
import com.example.cryptobank.service.mailSender.GenerateMailContent;
import com.example.cryptobank.service.mailSender.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reset")
public class ResetPasswordController {
    private Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
    private final LoginAccountService loginAccountService;
    private final MailSenderFacade mailSenderFacade;
    private final TokenService tokenService;
    private String email;
    private String insert;
    UrlAdresses urlAdresses = new UrlAdresses();
    Map<String, Boolean> validToken = new HashMap<>();

    @Autowired
    public ResetPasswordController(LoginAccountService loginAccountService, @Qualifier("sendMailServiceFacade") MailSenderFacade mailSenderFacade, TokenService tokenService) {
        this.mailSenderFacade = mailSenderFacade;
        this.tokenService = tokenService;
        logger.info("New MailSenderController");
        this.loginAccountService = loginAccountService;
    }

    @GetMapping
    public RedirectView showResetPage() {
        return new RedirectView(urlAdresses.getResetPasswordPage());
    }

    @GetMapping
    public RedirectView showNewPasswordPage() {
        return new RedirectView(urlAdresses.getCreateNewPasswordPage());
    }

    @GetMapping("/confirmed")
    public RedirectView showConfirmed() {
        return new RedirectView(urlAdresses.getResetConfirmedPage());

    }@GetMapping("/denied")
    public RedirectView showDeniedPage() {
        return new RedirectView(urlAdresses.getResetDeniedPage());
    }

    @PostMapping("/resetpassword")
    public RedirectView sendMailForReset(@RequestBody Map<String, String> mailMap) throws IOException, MessagingException {
        MailData resetData = new ResetMailData(insert = mailMap.values().stream().findFirst().orElse(null), null);
        if (loginAccountService.verifyAccount(insert)) {
            resetData.setToken(loginAccountService.addTokenToLoginAccount(insert));
            mailSenderFacade.sendMail(resetData);
        } else {
            logger.info("email bestaat niet");
        }
        return new RedirectView(urlAdresses.getResetConfirmed());
    }

    @PostMapping("/createnewpassword")
    public HttpEntity<?> loadPasswordPage(@RequestBody Map<String, String> tokenMap) {
        insert = tokenMap.values().stream().findFirst().orElse("");
        try {
            email = tokenService.parseToken(insert, "reset");
            if (loginAccountService.isTokenStored(email)) {
                return ResponseEntity.ok().body(validToken.put("token", true));
            } else {
                return ResponseEntity.ok().body(validToken.put("token", false));
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(validToken.put("token", false));
        }
    }

    @PostMapping("/setnewpassword")
    public HttpEntity<?> setNewPassword(@RequestBody Map<String, String> passwordMap) {
        String password = passwordMap.values().stream().findFirst().orElse(null);
        if (password.length() >= 8 && password.length() <= 100) {
            loginAccountService.updateResetPassword(email, password);
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlAdresses.getLoginPage())).build();
        } else {
            return ResponseEntity.ok().body(validToken.put("token", false));
        }
    }
}

