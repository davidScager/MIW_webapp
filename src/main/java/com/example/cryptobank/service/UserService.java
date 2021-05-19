package com.example.cryptobank.service;

import com.example.cryptobank.domain.LoginAccount;
import com.example.cryptobank.domain.Role;
import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.RootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final RootRepository rootRepository;
    private final PepperService pepperService;
    private final HashService hashService;

    @Autowired
    public UserService(RootRepository rootRepository, PepperService pepperService, HashService hashService) {
        this.rootRepository = rootRepository;
        this.pepperService = pepperService;
        this.hashService = hashService;
    }

    public User register(int BSN, String firstName, String infix, String surname, String dateOfBirth, String address, String email, String username, Role role){
        User user = new User (BSN, firstName, infix, surname, dateOfBirth, address, email, username);
        rootRepository.registerUser(user, role);
        return user;
    }

    public User verifyUser(String username, String password) {
//        LoginAccount loginAccount = rootRepository.getLoginAccountByUsername(username);
//        boolean correctLogin = false;
//        if (loginAccount != null) {
//            String hash = loginAccount.getHash();
//            String pepperedPassword = pepperService.getPepper() + password;
//            correctLogin = hashService.argon2idVerify(hash, pepperedPassword);
//        }
//        if (correctLogin) {
//            return rootRepository.getUserByUsername(username);
//        }
        return null;
    }
}
