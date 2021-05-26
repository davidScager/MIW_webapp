package com.example.cryptobank.domain;

public class UserAddress {
    private String streetName;
    private int houseNr;
    private String addition;
    private String postalCode;
    private String residence;

    public UserAddress(String streetName, int houseNr, String addition, String postalCode, String residence) {
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
