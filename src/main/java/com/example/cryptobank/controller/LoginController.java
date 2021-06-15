package com.example.cryptobank.controller;

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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public LoginController(UserService userService, TokenService tokenService) {
        logger.info("New LoginController");
        this.userService = userService;
        this.tokenService = tokenService;
    }

    // was nodig voor redirect vanuit registratie -David
    @GetMapping("/redirect")
    public RedirectView viewHtmlLoginHandler(){
        return new RedirectView("http://localhost:8080/LoginController.html");
    }

    @PostMapping("/login")
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
