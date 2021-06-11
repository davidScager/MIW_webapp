package com.example.cryptobank.domain.login;

import com.example.cryptobank.domain.user.FullName;
import com.example.cryptobank.domain.user.User;
import com.example.cryptobank.domain.user.UserAddress;
import com.example.cryptobank.service.security.HashService;
import com.example.cryptobank.service.security.PepperService;

public class UserLoginAccount {
    private User user;
    private String password;

    public UserLoginAccount(){
    }

    public UserLoginAccount(int bsn, String firstName, String infix, String surname, String dateOfBirth,
                            String postalCode, int houseNr, String addition, String streetName,
                            String residence, String email, String password){
        this.user = new User(bsn, new FullName(firstName, infix, surname), dateOfBirth,
                new UserAddress(postalCode, houseNr, addition, streetName, residence), email);
        setPassword(password);
    }

    @Override
    public String toString() {
        return "UserLoginAccount{" +
                "user=" + user +
                ", password='" + password + '\'' +
                '}';
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPassword(String password) {
        HashService hashService = new HashService(new PepperService());
        this.password = hashService.argon2idHash(password);
    }

    public User getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
