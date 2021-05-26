package com.example.cryptobank.controller;

import com.example.cryptobank.domain.User;
import com.example.cryptobank.service.security.SaltMaker;
import com.example.cryptobank.service.security.TokenService;
import com.example.cryptobank.service.login.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(SaltMaker.class);
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public LoginController(UserService userService, TokenService tokenService) {
        logger.info("New LoginController");
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser (
            @RequestParam String username,
            @RequestParam String password
    ) {
        logger.info("Login request received from client");
        User user = userService.verifyUser(username, password);
        if (user != null) {
            String token = tokenService.generateJwtToken(username, "session", 60);
            return ResponseEntity.ok().header("Authorization", token).body(user);
        }
        return ResponseEntity.notFound().build();
    }
}
