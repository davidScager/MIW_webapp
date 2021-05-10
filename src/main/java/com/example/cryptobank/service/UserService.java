package com.example.cryptobank.service;

import com.example.cryptobank.domain.User;
import com.example.cryptobank.repository.RootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UserService {

    private final RootRepository rootRepository;

    @Autowired
    public UserService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
    }

    public User register(int BSN, String firstName, String infix, String surname, String dateOfBirth, String address, String email, String username){
        User user = new User (BSN, firstName, infix, surname, dateOfBirth, address, email, username);
        rootRepository.saveUser(user);
        return user;
    }
}
