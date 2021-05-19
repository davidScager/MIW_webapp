CREATE SCHEMA bitbanktestdb;

CREATE TABLE LoginAccount(
    username VARCHAR(100) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    salt     VARCHAR(45)  NOT NULL
);

CREATE TABLE Role(
    role VARCHAR(45) NOT NULL PRIMARY KEY
);

CREATE TABLE Actor(
    userId          INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    checkingAccount VARCHAR(45) NULL,
    role            VARCHAR(45) NOT NULL,
    FOREIGN KEY (role)
    REFERENCES Role (role)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

CREATE TABLE User(
    BSN         INT         NOT NULL,
    userId      INT         NOT NULL,
    firstName   VARCHAR(45) NOT NULL,
    infix       VARCHAR(45) NULL,
    surname     VARCHAR(45) NOT NULL,
    dateOfBirth DATE        NOT NULL,
    address     VARCHAR(45) NOT NULL,
    email       VARCHAR(45) NOT NULL,
    username    VARCHAR(45) NOT NULL,
    PRIMARY KEY (BSN),
    FOREIGN KEY (userId)
    REFERENCES Actor (userId)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE Asset(
    abbreviation     VARCHAR(45) NOT NULL,
    name             VARCHAR(45) NOT NULL,
    description      VARCHAR(45) NOT NULL,
    valueInUsd       DOUBLE      NOT NULL,
    adjustmentFactor DOUBLE      NOT NULL,
    valueYesterday   DOUBLE      NOT NULL,
    valueLastWeek    DOUBLE      NOT NULL,
    valueLastMonth   DOUBLE      NOT NULL,
    PRIMARY KEY (abbreviation)
);

CREATE TABLE Transaction(
    transactionId   INT         NOT NULL AUTO_INCREMENT,
    timestamp       DATETIME    NOT NULL,
    seller          INT         NOT NULL,
    buyer           INT         NOT NULL,
    numberOfAssets  DOUBLE      NOT NULL,
    transactionCost DOUBLE      NOT NULL,
    assetSold       VARCHAR(45) NOT NULL,
    assetBought     VARCHAR(45) NOT NULL,
    PRIMARY KEY (transactionId),
    FOREIGN KEY (seller)
    REFERENCES Actor (userId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (buyer)
    REFERENCES Actor (userId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (assetSold)
    REFERENCES Asset (abbreviation)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    FOREIGN KEY (assetBought)
    REFERENCES Asset (abbreviation)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE Portfolio(
    portfolioId INT NOT NULL AUTO_INCREMENT,
    actor       INT NOT NULL,
    PRIMARY KEY (portfolioId),
    FOREIGN KEY (actor)
    REFERENCES Actor (userId)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE AssetPortfolio(
    assetName   VARCHAR(45) NOT NULL,
    portfolioId INT         NOT NULL,
    amount      DOUBLE      NOT NULL,
    PRIMARY KEY (assetName, portfolioId),
    FOREIGN KEY (portfolioId)
    REFERENCES Portfolio (portfolioId)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (assetName)
    REFERENCES Asset (abbreviation)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE Log(
    transactionId               INT    NOT NULL,
    soldAssetTransactionRate    DOUBLE NOT NULL,
    boughtAssetTransactionRate  DOUBLE NOT NULL,
    soldAssetAdjustmentFactor   DOUBLE NOT NULL,
    boughtAssetAdjustmentFactor DOUBLE NOT NULL,
    amount                      DOUBLE NOT NULL,
    PRIMARY KEY (transactionId),
    FOREIGN KEY (transactionId)
    REFERENCES Transaction (transactionId)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);