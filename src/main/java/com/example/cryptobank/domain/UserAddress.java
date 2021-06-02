package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAddress {
    private Logger logger = LoggerFactory.getLogger(UserAddress.class);
    private String postalCode;
    private int houseNr;
    private String addition;
    private String streetName;
    private String residence;

    public UserAddress(){
    }

    public UserAddress(String postalCode, int houseNr, String addition, String streetName, String residence) {
        this();
        logger.info("New UserAddress");
        this.postalCode = postalCode;
        this.houseNr = houseNr;
        this.addition = addition;
        this.streetName = streetName;
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
