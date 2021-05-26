package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAddress {
    private Logger logger = LoggerFactory.getLogger(UserAddress.class);
    private String streetName;
    private int houseNr;
    private String addition;
    private String postalCode;
    private String residence;

    public UserAddress(String streetName, int houseNr, String addition, String postalCode, String residence) {
        logger.info("New UserAddress");
        this.streetName = streetName;
        this.houseNr = houseNr;
        this.addition = addition;
        this.postalCode = postalCode;
        this.residence = residence;
    }

    @Override
    public String toString() {
        return "UserAddress{" +
                "streetName='" + streetName + '\'' +
                ", houseNr=" + houseNr +
                ", addition='" + addition + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", residence='" + residence + '\'' +
                '}';
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setHouseNr(int houseNr) {
        this.houseNr = houseNr;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getHouseNr() {
        return houseNr;
    }

    public String getAddition() {
        return addition;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getResidence() {
        return residence;
    }
}
