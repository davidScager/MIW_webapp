package com.example.cryptobank.domain;

public class UserLoginAccount {
    private User user;
    private String email;
    private String password;

    public UserLoginAccount(int bsn, String firstname, String infix, String surname, String dateofbirth,
                String streetName, int houseNr, String addition, String postalCode, String residence, String email, String password){
        UserAddress userAddress = new UserAddress(streetName, houseNr, addition, postalCode, residence);
        this.user = new User(bsn, firstname, infix, surname, dateofbirth, userAddress, email);
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
