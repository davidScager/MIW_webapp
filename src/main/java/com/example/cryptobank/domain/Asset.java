package com.example.cryptobank.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//todo HvS: Still needs the right constructor. Abbreviation and description don't get filled now.
public class Asset {
    private String name;
    private String abbreviation;
    private String description;
    public int assetId;

    private final Logger logger = LoggerFactory.getLogger(Asset.class);

    public Asset(String name, String abbreviation, String description) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.description = description;
        logger.info("New Asset created");
    }

    public Asset(String name) {
        this();
        this.name = name;
        this.abbreviation = null;
        this.description = null;
        logger.info("New Asset created");
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

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}