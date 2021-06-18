package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.maildata.MailData;
import com.example.cryptobank.domain.maildata.RegisterMailData;
import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.mailsenderfacade.MailSenderFacade;
import com.example.cryptobank.service.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.*;

/**
 * Service for registering new users
 * @author David_Scager
 */
@Service
public class RegistrationService {
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private final RootRepository rootRepository;
    private final TokenService tokenService;
    private final MailSenderFacade mailSenderFacade;
    private final Map<String, UserLoginAccount> registrationCache;
    private final int durationValid;
    private final int durationUnitMillis;
    private int removeAfter;

    @Autowired
    public RegistrationService(RootRepository rootRepository, TokenService tokenService,
                               @Qualifier("sendMailServiceFacade") MailSenderFacade mailSenderFacade) {
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        this.mailSenderFacade = mailSenderFacade;
        this.registrationCache = new HashMap<>();
        logger.info("RegistrationService active");
        this.durationValid = 30;
        this.durationUnitMillis = 60000;
        this.removeAfter = durationValid * durationUnitMillis;
    }

    /**
     * Check against registrationCache and in database if user is already registered
     * @param userLoginAccount (UserLoginAccount)
     * @return (boolean)
     */
    public boolean validate(UserLoginAccount userLoginAccount){
        logger.info("Validating registration information with database");
        return !rootRepository.alreadyRegistered(userLoginAccount) && !registrationCache.containsValue(userLoginAccount);
    }

    /**
     * Generate Jwt token
     * Cache user with token as key
     * Set timer for clearing user from cache
     * return Jwt token
     *
     * @param userLoginAccount (UserLoginAccount)
     * @return (String) Jwt
     */
    public String cacheNewUserWithToken(UserLoginAccount userLoginAccount){
        String token = tokenService.generateJwtToken(userLoginAccount.getUser().getEmail(), "Register", durationValid);
        registrationCache.put(token, userLoginAccount);
        logger.info("Registration Cached");
        final Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        registrationCache.remove(token);
                        logger.info("Cache cleared");
                    }
                }, removeAfter
        );
        logger.info(registrationCache.get(token).toString());
        return token;
    }

    /**
     * Send user an email with 30 min valid Jwt
     * @param token (String) Jwt
     * @param email (String)
     */
    public boolean confirmationEmailSent(String token, String email)  {
        MailData registerMailData = new RegisterMailData(email, token);
        try {
            mailSenderFacade.sendMail(registerMailData);
            logger.info("Registration confirmation email sent");
            return true;
        } catch (MalformedURLException | MessagingException | FileNotFoundException error) {
            logger.info("Failed to send email.");
            logger.error(error.getMessage());
            return false;
        }
    }

    /**
     * Check validity of token
     * @param token (String) Jwt
     * @param subject (String)
     * @return (boolean)
     */
    public boolean validateToken(String token, String subject) {
        try {
            tokenService.parseToken(token, subject);
            return registrationCache.containsKey(token);
        } catch (Exception e) {
            logger.info("Invalid token");
            registrationCache.remove(token);
            logger.info("Cache cleared");
            return false;
        }
    }

    /**
     * Retrieve user data from cache with token
     * Store in database
     * @param token (String) Jwt
     */
    public void registerUser(String token){
        UserLoginAccount userLoginAccount = registrationCache.get(token) ;
        rootRepository.registerLogin(userLoginAccount.getUser(), userLoginAccount.getPassword());
        rootRepository.registerUser(userLoginAccount.getUser(), Role.CLIENT);
        registrationCache.remove(token);
        logger.info("New Client registered and cache cleared");
    }

    /**
     * Set unit for duration of Jwt validity
     * unit is multiplied by durationValid parameter
     * set to 1 second by default (60,000 millis)
     * @param durationUnitMillis (int)
     */
    public void setRemoveAfter(int durationUnitMillis) {
        this.removeAfter = durationUnitMillis;
    }

}
