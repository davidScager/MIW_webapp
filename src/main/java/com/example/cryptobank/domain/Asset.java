package com.example.cryptobank.domain;

//todo HvS: Still needs the right constructor. Abbreviation and description don't get filled now.
public class Asset {
    private String name;
    private String abbreviation;
    private String description;
    private Number valueUSD;
    private Number valueEUR;

    public Asset(String name, String abbreviation, String description, Number valueUSD, Number valueEUR) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.valueUSD = valueUSD;
        this.valueEUR = valueEUR;
    }

    public Asset() {
    }

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

    public Number getValueUSD() {
        return valueUSD;
    }

    public void setValueUSD(Number valueUSD) {
        this.valueUSD = valueUSD;
    }

    public Number getValueEUR() {
        return valueEUR;
    }

    public void setValueEUR(Number valueEUR) {
        this.valueEUR = valueEUR;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", description='" + description + '\'' +
                ", valueUSD=" + valueUSD +
                ", valueEUR=" + valueEUR +
                '}';
    }
}