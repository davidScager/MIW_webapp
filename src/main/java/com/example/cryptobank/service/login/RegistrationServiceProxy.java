package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import com.example.cryptobank.service.mailSender.EmailConfig;
import com.example.cryptobank.service.mailSender.GenerateMailContext;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.security.TokenService;
import org.hibernate.annotations.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Primary
@Component
public class RegistrationServiceProxy implements RegistrationService{
    private final Logger logger = LoggerFactory.getLogger(RegistrationServiceClass.class);
    private final RegistrationService realRegistrationService;
    private final TokenService tokenService;
    private final GenerateMailContext generateMailContext;
    private final MailSenderService mailSenderService;
    //contains tokens as key
    private final Map<String, UserLoginAccount> registrationCache;

    @Autowired
    public RegistrationServiceProxy(RegistrationService registrationService, TokenService tokenService,
                                    GenerateMailContext generateMailContext, MailSenderService mailSenderService) {
        this.realRegistrationService = registrationService;
        this.tokenService = tokenService;
        this.generateMailContext = generateMailContext;
        this.mailSenderService = mailSenderService;
        this.registrationCache = new HashMap<>();
        logger.info("RegistrationProxy active");
    }

    public String cacheNewUser(UserLoginAccount userLoginAccount){
        String token = tokenService.generateJwtToken(userLoginAccount.getEmail(), "Register", 60);
        registrationCache.put(token, userLoginAccount);
        return token;
    }

    public boolean sendConfirmationEmail(String token, String email) {
        try {
            String mailText = generateMailContext.setRegistrationText(token);
            mailSenderService.sendMail(email, mailText, "Bevestig BitBank-registratie");
            logger.info("Registration confirmation email sent to new client");
            return true;
        } catch (MalformedURLException | MessagingException urlMessageError) {
            urlMessageError.printStackTrace();
            return false;
        }
    }

    @Override
    public User register(UserLoginAccount userLoginAccount, Role role, String token) {
        userLoginAccount = registrationCache.get(token);
        realRegistrationService.register(userLoginAccount, Role.CLIENT, token);
        return userLoginAccount.getUser();
    }

    @Override
    public boolean validate(UserLoginAccount userLoginAccount) {
        if (realRegistrationService.validate(userLoginAccount)){
            String token = cacheNewUser(userLoginAccount);
            logger.info("User Data Cached by Proxy");
            return sendConfirmationEmail(token, userLoginAccount.getEmail());
        }
        logger.info("User data invalid.");
        return false;
    }

    @Override
    public boolean validateToken(String token, String subject) {
        try {
            tokenService.parseToken(token, subject);
            return registrationCache.containsKey(token);
        } catch (Exception e) {
            logger.info("Invalid token");
            return false;
        }
    }
}
