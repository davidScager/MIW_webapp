package com.example.cryptobank.service.mailSender.mailsenderfacade;

import com.example.cryptobank.domain.maildata.MailData;
import com.example.cryptobank.service.mailSender.GenerateMailContent;
import com.example.cryptobank.service.mailSender.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@Service
public class TransactionTriggerMailFacade implements MailSenderFacade {
    private final MailSenderService mailSenderService;
    private final GenerateMailContent generateMailContent;

    @Autowired
    public TransactionTriggerMailFacade(MailSenderService mailSenderService, GenerateMailContent generateMailContent) {
        this.mailSenderService = mailSenderService;
        this.generateMailContent = generateMailContent;
    }

    @Override
    public void sendMail(MailData mailData) throws MalformedURLException, MessagingException, FileNotFoundException {
        this.mailSenderService.setSender();
        mailData.setMailContent(this.generateMailContent.setHtmlMail(mailData));
        this.mailSenderService.sendMail(mailData);
        //geen createUrl?
    }
}
