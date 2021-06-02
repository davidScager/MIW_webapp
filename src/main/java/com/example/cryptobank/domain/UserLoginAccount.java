package com.example.cryptobank.domain;

public class UserLoginAccount {
    private User user;
    private String email;
    private String password;

    public UserLoginAccount(){}

    public UserLoginAccount(int bsn, String firstname, String infix, String surname, String dateofbirth, String postalCode, int houseNr, String addition, String streetName, String residence, String email, String password){
        this.user = new User(bsn, new FullName(firstname, infix, surname), dateofbirth, new UserAddress(postalCode, houseNr, addition, streetName, residence), email);
        this.email = email;
        this.password = password;
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
