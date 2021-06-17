package com.example.cryptobank.service.mailSender.mailsenderfacade;

import com.example.cryptobank.domain.maildata.MailData;
import com.example.cryptobank.service.mailSender.CreateURLHelper;
import com.example.cryptobank.service.mailSender.GenerateMailContent;
import com.example.cryptobank.service.mailSender.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class SendMailServiceFacade implements MailSenderFacade {
    private final MailSenderService mailSenderService;
    private final GenerateMailContent generateMailContent;

    @Autowired
    public SendMailServiceFacade(MailSenderService mailSenderService, GenerateMailContent generateMailContent) {
        this.mailSenderService = mailSenderService;
        this.generateMailContent = generateMailContent;
    }

    @Override
    public void sendMail(MailData mailData) throws IOException, MessagingException {
        this.mailSenderService.setSender();
        String resetLink = CreateURLHelper.generateToken(mailData);
        mailData.setMailContent(this.generateMailContent.setHtmlMail(mailData, resetLink));
        this.mailSenderService.sendMail(mailData);
    }
}


