package com.example.cryptobank.service.mailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService {
    private EmailConfig emailConfig;

    @Autowired
    public MailSenderService(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

        public void sendMail(String email, String mailText, String mailSubject) throws MessagingException {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(this.emailConfig.getHost());
        sender.setPort(this.emailConfig.getPort());
        sender.setUsername(this.emailConfig.getUsername());
        sender.setPassword(this.emailConfig.getPassword());
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setText(mailText, true);
        sender.send(message);


    }
}
