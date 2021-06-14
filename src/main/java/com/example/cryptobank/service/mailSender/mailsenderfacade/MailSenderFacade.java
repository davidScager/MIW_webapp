package com.example.cryptobank.service.mailSender.mailsenderfacade;

import com.example.cryptobank.domain.maildata.MailData;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public interface MailSenderFacade {
    public void sendMail(MailData mailData) throws MalformedURLException, MessagingException, FileNotFoundException;
}