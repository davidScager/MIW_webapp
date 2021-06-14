package com.example.cryptobank.service.mailSender;

import com.example.cryptobank.domain.maildata.MailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService {
    private final EmailConfig emailConfig;
    private final JavaMailSenderImpl sender;


    @Autowired
    public MailSenderService(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
        this.sender = new JavaMailSenderImpl();
    }

    public void setSender() {
        sender.setHost(this.emailConfig.getHost());
        sender.setPort(this.emailConfig.getPort());
        sender.setUsername(this.emailConfig.getUsername());
        sender.setPassword(this.emailConfig.getPassword());
    }

    public void sendMail(MailData mailData) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailData.getSender());
        helper.setTo(mailData.getReceiverEmail());
        helper.setText(mailData.getMailContent(), true);
        helper.setSubject(mailData.getMailSubject());
        sender.send(message);
    }
}
