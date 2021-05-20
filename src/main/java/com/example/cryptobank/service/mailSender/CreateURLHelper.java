package com.example.cryptobank.service.mailSender;

public class CreateURLHelper {


    public static String generateToken(String url, String token) {
        return url + token;
    }
}
