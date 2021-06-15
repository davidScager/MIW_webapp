package com.example.cryptobank.service.mailSender;

import com.example.cryptobank.domain.maildata.MailData;

import java.net.MalformedURLException;
import java.net.URL;

public class CreateURLHelper {

    public static String generateToken(MailData mailData) throws MalformedURLException {
        URL url = new URL(mailData.getLinkUrl() + "?Authorization=" + mailData.getToken());
        return url.toString();
    }
}
