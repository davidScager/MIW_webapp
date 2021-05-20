package com.example.cryptobank.service.mailSender;

import org.springframework.stereotype.Component;

@Component
public class GenerateMailContext {
    private String resetURL;


    public GenerateMailContext() {
        this.resetURL = "localhost:8080/createnewpassword";
    }

    public String setResetText(String token) {
        return "blablablablablablablablablabalbalabalbalabaolbal Dit is je url. klik er maar op, eikel. En niet meer vergeten!" + CreateURLHelper.generateToken(resetURL, token);
    }



}


