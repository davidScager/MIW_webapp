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
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    private int durationUnitMillis;
    private final int removeAfter;

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
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                registrationCache.remove(token);
                logger.info("Cache cleared");
            }
        }, removeAfter);
        logger.info(registrationCache.get(token).toString());
        return token;
    }

    /**
     * Send user an email with 30 min valid Jwt
     * @param token (String) Jwt
     * @param email (String)
     */
    public void sendConfirmationEmail(String token, String email) {
        MailData registerMailData = new RegisterMailData(email, token);
        try {
            mailSenderFacade.sendMail(registerMailData);
            logger.info("Registration confirmation email sent");
        } catch (MalformedURLException | MessagingException | FileNotFoundException error) {// add custom exception
            logger.info("Failed to send email.");
            logger.error("URL or Messaging error caught." + error.getMessage());
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

    //Mainly for test purposes
    //Every test of cacheNewUserWithToken would otherwise take 30 minutes!
    public void setDurationUnitMillis(int durationUnitMillis) {
        this.durationUnitMillis = durationUnitMillis;
    }

    //todo: verwijder voor einde project
    // momenteel alleen gebruikt in commandlineRunner om gebruikers voor uitproberen te registreren
    public User register(UserLoginAccount userLoginAccount, Role role){
        User user = userLoginAccount.getUser();
        rootRepository.registerLogin(userLoginAccount.getUser(), userLoginAccount.getPassword());
        rootRepository.registerUser(userLoginAccount.getUser(), role);
        return user;
    }

}
