package com.example.cryptobank.service.mailSender;

import java.net.MalformedURLException;
import java.net.URL;

public class CreateURLHelper {


    public static URL generateToken(String urlForMail, String token) throws MalformedURLException {
        return new URL(urlForMail + "?Authorization=" + token);
    }
}
