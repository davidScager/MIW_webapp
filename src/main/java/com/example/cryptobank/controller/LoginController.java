package com.example.cryptobank.controller;

import com.example.cryptobank.domain.User;
import com.example.cryptobank.service.TokenService;
import com.example.cryptobank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public LoginController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser (
            @RequestParam String username,
            @RequestParam String password
    ) {
        User user = userService.verifyUser(username, password);
        if (user != null) {
            String token = tokenService.getToken();
            return ResponseEntity.ok().header("Authorization", token).body(user);
        }
        return ResponseEntity.notFound().build();
    }
}
