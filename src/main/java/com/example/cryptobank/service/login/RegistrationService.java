package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.user.Role;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.login.UserLoginAccount;
import com.example.cryptobank.repository.jdbcklasses.RootRepository;
import com.example.cryptobank.service.mailSender.GenerateMailContent;
import com.example.cryptobank.service.mailSender.MailSenderService;
import com.example.cryptobank.service.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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
    private Logger logger = LoggerFactory.getLogger(RegistrationService.class);
    private final RootRepository rootRepository;
    private final TokenService tokenService;
    private final GenerateMailContent generateMailContent;
    private final MailSenderService mailSenderService;
    private final Map<String, UserLoginAccount> registrationCache;

    @Autowired
    public RegistrationService(RootRepository rootRepository, TokenService tokenService,
                               GenerateMailContent generateMailContent, MailSenderService mailSenderService) {
        this.rootRepository = rootRepository;
        this.tokenService = tokenService;
        this.generateMailContent = generateMailContent;
        this.mailSenderService = mailSenderService;
        this.registrationCache = new HashMap<>();
        logger.info("RegistrationService active");
    }

    public boolean validate(UserLoginAccount userLoginAccount){
        logger.info("Validating registration information with database");
        User user = userLoginAccount.getUser();
        return user != null
                && rootRepository.getLoginByUsername(userLoginAccount.getUser().getEmail()) == null
                && rootRepository.getUserByBsn(user.getBSN()) == null;
    }

    public String cacheNewUserWithToken(UserLoginAccount userLoginAccount){
        String token = tokenService.generateJwtToken(userLoginAccount.getUser().getEmail(), "Register", 30);
        registrationCache.put(token, userLoginAccount);
        logger.info("Registration Cached");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                registrationCache.remove(token);
                logger.info("Cache cleared");
            }
        }, 1800000);
        logger.info(registrationCache.get(token).toString());
        return token;
    }

//    public void sendConfirmationEmail(String token, String email) {
//        try {
////            String mailText = generateMailContent.setRegistrationText(token);
////            mailSenderService.sendMail(email, "Bevestig BitBank-registratie", token);
//            logger.info("Registration confirmation email sent to new client");
//        } catch (MalformedURLException urlMessageError) {
//            logger.info("Failed to send email.");
//            logger.error("URL or Messaging error caught.", urlMessageError);
//        }
//    }

    public void registerUser(String token){
        UserLoginAccount userLoginAccount = registrationCache.get(token) ;
        rootRepository.registerLogin(userLoginAccount.getUser(), userLoginAccount.getPassword());
        rootRepository.registerUser(userLoginAccount.getUser(), Role.CLIENT);
        registrationCache.remove(token);
        logger.info("Cache cleared");
        logger.info("New Client registered");
    }

    public boolean validateToken(String token, String subject) {
        try {
            tokenService.parseToken(token, subject);
            if (registrationCache.containsKey(token)){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.info("Invalid token");
            registrationCache.remove(token);
            logger.info("Cache cleared");
            return false;
        }
    }

    //for old way of registration without confirmation email
    public User register(UserLoginAccount userLoginAccount, Role role){
        User user = userLoginAccount.getUser();
        rootRepository.registerLogin(user, userLoginAccount.getPassword());
        rootRepository.registerUser(user, role);
        return user;
    }
}
