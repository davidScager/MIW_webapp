package com.example.cryptobank.controller;

import com.example.cryptobank.domain.urls.UrlAdresses;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.service.security.TokenService;
import com.example.cryptobank.service.login.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/login") //even '/' bijgezet, weet niet of het veel uitmaakt -David
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;
    private final TokenService tokenService;
    UrlAdresses urlAdresses = new UrlAdresses();


    @Autowired
    public LoginController(UserService userService, TokenService tokenService) {
        logger.info("New LoginController");
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public RedirectView showLoginPage(){
        return new RedirectView(urlAdresses.getLoginPage());
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity loginUser (
            @RequestBody Map<String, String> requestParams
    ) {
        logger.info("Login request received from client");
        String username = requestParams.get("username");
        String password = requestParams.get("password");
        User user = userService.verifyUser(username, password);
        if (user != null) {
            String token = tokenService.generateJwtToken(username, "session", 60);
            return ResponseEntity.ok().header("Authorization", token).body(user);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}


