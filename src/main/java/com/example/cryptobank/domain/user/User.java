package com.example.cryptobank.domain.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
public class User {
    private final Logger logger = LoggerFactory.getLogger(User.class);
    private int BSN;
    private long id;
    private FullName fullName;
    private String dateOfBirth;
    private UserAddress userAddress;

    @Email
    @NotNull
    private String email;

    public User(int BSN, FullName fullName, String dateOfBirth, UserAddress userAddress, String email) {
        this.BSN = BSN;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.userAddress = userAddress;
        this.email = email;
        this.id = 0;
        logger.info("New User");
    }

    @Override
    public String toString() {
        return "User{" +
                "BSN=" + BSN +
                ", id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public User() {
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBSN() {
        return BSN;
    }

    public void setBSN(int BSN) {
        this.BSN = BSN;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getBSN() == user.getBSN() && getId() == user.getId() && Objects.equals(getFullName(), user.getFullName()) && Objects.equals(getDateOfBirth(), user.getDateOfBirth()) && Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBSN(), getId(), getFullName(), getDateOfBirth(), getUserAddress(), getEmail());
    }
}

