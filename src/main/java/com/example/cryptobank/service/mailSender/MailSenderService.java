package com.example.cryptobank.service.mailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    private EmailConfig emailConfig;

    @Autowired
    public MailSenderService(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

        public void sendMail(String email, String mailText, String mailSubject) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        //here we can compose email (or if we need multiple, we can make the mail extract method
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("Baas@BitBank.com");
        mailMessage.setTo(email);
        mailMessage.setSubject(mailSubject);
        mailMessage.setText(mailText);

        mailSender.send(mailMessage);
    }
}
