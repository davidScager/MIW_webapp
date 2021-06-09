package com.example.cryptobank.domain;

import com.example.cryptobank.service.security.HashService;
import com.example.cryptobank.service.security.PepperService;

public class UserLoginAccount {
    private User user;
    private String email;
    private String password;

    // default const. needed for testing/mocking, but doesn't work with client requests
    //public UserLoginAccount(){}

    public UserLoginAccount(int bsn, String firstName, String infix, String surname, String dateOfBirth, String postalCode, int houseNr, String addition, String streetName, String residence, String email, String password){
        this.user = new User(bsn, new FullName(firstName, infix, surname), dateOfBirth, new UserAddress(postalCode, houseNr, addition, streetName, residence), email);
        HashService hashService = new HashService(new PepperService());
        this.email = email;
        this.password = hashService.argon2idHash(password);
    }

    @Override
    public String toString() {
        return "UserLoginAccount{" +
                "user=" + user +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
