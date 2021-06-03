package com.example.cryptobank.service.login;

import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.domain.UserLoginAccount;
import com.example.cryptobank.service.security.TokenService;
import org.hibernate.annotations.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Proxy
public class RegistrationServiceProxy implements RegistrationService{
    private final Logger logger = LoggerFactory.getLogger(RegistrationServiceClass.class);
    private final RegistrationServiceClass realRegistrationService;
    private final TokenService tokenService;
    //contains tokens as key
    private Map<String, UserLoginAccount> registrationCache;

    @Autowired
    public RegistrationServiceProxy(RegistrationServiceClass realRegistrationService, TokenService tokenService) {
        this.realRegistrationService = realRegistrationService;
        this.tokenService = tokenService;
        registrationCache = new HashMap<>();
        logger.info("RegistrationProxy active");
    }

    public void cacheNewUser(UserLoginAccount userLoginAccount){
        String token = tokenService.generateJwtToken(userLoginAccount.getEmail(), "Register", 60);
        registrationCache.put(token, userLoginAccount);
    }

    @Override
    public User register(UserLoginAccount userLoginAccount, Role role) {
        return realRegistrationService.register(userLoginAccount, role);
    }

    @Override
    public boolean validate(UserLoginAccount userLoginAccount) {
        if (realRegistrationService.validate(userLoginAccount)){
            cacheNewUser(userLoginAccount);
            return true;
        }
        return false;
    }

    @Override
    public boolean validateToken(String token, String subject) {
        try {
            tokenService.parseToken(token, subject);
            if (registrationCache.containsKey(token)) {
                UserLoginAccount userLoginAccount = registrationCache.get(token);
                realRegistrationService.register(userLoginAccount, Role.CLIENT);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
