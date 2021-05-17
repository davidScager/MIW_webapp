package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//todo HvS: Still needs the right constructor. Abbreviation and description don't get filled now.
public class Asset {
    private String name;
    private String abbreviation;
    private String description;
    private double valueInUsd;
    private double adjustmentFactor;
    private double valueYesterday;
    private double valueLastWeek;
    private double valueLastMonth;

    private final Logger logger = LoggerFactory.getLogger(Asset.class);

    public Asset(String name, String abbreviation, String description) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.valueInUsd = 0;
        this.adjustmentFactor = 1;
        logger.info("New Asset created");
    }

    public Asset(String name, String abbreviation, String description, double valueInUsd/*, double adjustmentFactor*/, double valueYesterday, double valueLastWeek, double valueLastMonth) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        this.valueInUsd = valueInUsd;
        this.adjustmentFactor = 1;
        this.valueYesterday = valueYesterday;
        this.valueLastWeek = valueLastWeek;
        this.valueLastMonth = valueLastMonth;
        logger.info("New Asset created");
    }

    public Asset(String name) {
        this();
        this.name = name;
        this.abbreviation = null;
        this.description = null;
        this.valueInUsd = 0;
        this.adjustmentFactor = 1;
        logger.info("New Asset created");
    }

    public double getValueYesterday() {
        return valueYesterday;
    }

    public void setValueYesterday(double valueYesterday) {
        this.valueYesterday = valueYesterday;
    }

    public double getValueLastWeek() {
        return valueLastWeek;
    }

    public void setValueLastWeek(double valueLastWeek) {
        this.valueLastWeek = valueLastWeek;
    }

    public double getValueLastMonth() {
        return valueLastMonth;
    }

    public void setValueLastMonth(double valueLastMonth) {
        this.valueLastMonth = valueLastMonth;
    }

    public Asset() {
        logger.info("New Asset created");
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

    public double getValueInUsd() {
        return valueInUsd;
    }

    public void setValueInUsd(double valueInUsd) {
        this.valueInUsd = valueInUsd;
    }

    public double getAdjustmentFactor() {
        return adjustmentFactor;
    }

    public void setAdjustmentFactor(double adjustmentFactor) {
        this.adjustmentFactor = adjustmentFactor;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "name='" + name + '\'' +
                ", abbreviation ='" + abbreviation + '\'' +
                ", description ='" + description + '\'' +
                ", value (in USD) = " + valueInUsd +
                ", the adjustment for trading activity = " + adjustmentFactor +
                '}';
    }
}