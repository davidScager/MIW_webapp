package com.example.cryptobank.domain.asset;

public class AssetViewForSale {
    private String name;
    private String abbreviation;
    private String description;

    private double valueInUsd;
    private double availableBank;
    private double availableOthers;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValueInUsd() {
        return valueInUsd;
    }

    public void setValueInUsd(double valueInUsd) {
        this.valueInUsd = valueInUsd;
    }

    public double getAvailableBank() {
        return availableBank;
    }

    public void setAvailableBank(double availableBank) {
        this.availableBank = availableBank;
    }

    public double getAvailableOthers() {
        return availableOthers;
    }

    public void setAvailableOthers(double availableOthers) {
        this.availableOthers = availableOthers;
    }



}
