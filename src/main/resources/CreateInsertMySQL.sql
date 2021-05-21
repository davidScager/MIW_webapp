DROP SCHEMA BitBankDB;
CREATE SCHEMA BitBankDB;
use BitBankDB;

-- CREATE TABLES

CREATE TABLE LoginAccount(
    username        VARCHAR(100) NOT NULL PRIMARY KEY,
    password        VARCHAR(100) NOT NULL,
    salt            VARCHAR(45)  NOT NULL,
    token           VARCHAR(500)
    );

CREATE TABLE Role(
    role            VARCHAR(45) NOT NULL PRIMARY KEY
    );

CREATE TABLE Actor(
    userId          INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    checkingAccount VARCHAR(45) NULL,
    role            VARCHAR(45) NOT NULL,
    FOREIGN KEY (role)
    REFERENCES Role (role)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    );

CREATE TABLE User(
    BSN             INT         NOT NULL PRIMARY KEY,
    userId          INT         NOT NULL,
    firstName       VARCHAR(45) NOT NULL,
    infix           VARCHAR(45) NULL,
    surname         VARCHAR(45) NOT NULL,
    dateOfBirth     DATE        NOT NULL,
    address         VARCHAR(45) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    FOREIGN KEY (email)
    REFERENCES LoginAccount (username)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (userId)
    REFERENCES Actor (userId)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    );

CREATE TABLE Asset(
    abbreviation     VARCHAR(45) NOT NULL PRIMARY KEY,
    name             VARCHAR(45) NOT NULL,
    apiName          VARCHAR(100)NOT NULL,
    description      VARCHAR(200)NOT NULL,
    valueInUsd       DOUBLE      NOT NULL,
    adjustmentFactor DOUBLE      NOT NULL,
    valueYesterday   DOUBLE      NOT NULL,
    valueLastWeek    DOUBLE      NOT NULL,
    valueLastMonth   DOUBLE      NOT NULL
    );

CREATE TABLE Portfolio(
    portfolioId     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    actor           INT NOT NULL,
    FOREIGN KEY (actor)
    REFERENCES Actor (userId)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    );

CREATE TABLE AssetPortfolio(
    assetName       VARCHAR(45) NOT NULL,
    portfolioId     INT         NOT NULL,
    amount          DOUBLE      NOT NULL,
    PRIMARY KEY (assetName, portfolioId),
    FOREIGN KEY (portfolioId)
    REFERENCES Portfolio (portfolioId)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (assetName)
    REFERENCES Asset (abbreviation)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    );

CREATE TABLE Transaction(
    transactionId   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    timestamp       DATETIME    NOT NULL,
    seller          INT         NOT NULL,
    buyer           INT         NOT NULL,
    numberOfAssets  DOUBLE      NOT NULL,
    transactionCost DOUBLE      NOT NULL,
    assetSold       VARCHAR(45) NOT NULL,
    assetBought     VARCHAR(45) NOT NULL,
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
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
    );

-- INSERT DATA

USE BitBankDB;

INSERT INTO Asset (name,apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Bitcoin', 'Bitcoin','BTC','Most known cryptocoin' ,'50000','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Ethereum','Ethereum', 'ETH','A crytocoin on an opensourcepaltform' ,'3000','1.1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Binance Coin','binancecoin', 'BNB','Has the most exchangeses of all cyptocoins' ,'600','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Cardano', 'Cardano','ADA','Follows sience to improve its service' ,'2','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Dogecoin','Dogecoin', 'DOGE','Created by software engineers' ,'0.5','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('XRP','xrp-classic','XRP','Ripple is often tought as the same' ,'1.28','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Polkadot','Polkadot', 'DOT','Has the same founder as Ethereum' ,'37.12','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Internet Computer','internet-computer', 'ICP','Relased at may 10th of this year' ,'208.92','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Bitcoin Cash','bitcoin-cash', 'BCH','Branch from bitcoin' ,'1002','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Litecoin','Litecoin', 'LTC','One of the first cryptocoins' ,'261.18','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Stellar', 'Stellar','XLM','A coin from the Lumen organization' ,'0.6','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Vechain','Vechain', 'VET','Former known as DogeCoin-Dark' ,'0.56','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Ethereum Classic','ethereum-classic', 'ETC','Branch from Ethereum' ,'82.21','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Eos','Eos', 'EOS','Runs on the EOS network' ,'8.9','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Theta', 'theta-token','THETA','A big part of the streaming sector' ,'8.29','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Tron','Tron', 'TRX','Aims to build a free digital entertainment system' ,'0.105','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Filecoin', 'Filecoin','FIL','Also works with information storage' ,'93.85','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Monero','Monero', 'XMR','Works anonymous' ,'328.79','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Neo','Neo', 'NEO','Has a vision to realize a smart economy' ,'81.19','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Dash', 'Dash','DASH','Users are called masternodes' ,'200.1','1', '1', '1', '1');
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Dollar','', 'USD' ,'Basic value in this bank', 1, 1, '1', '1', '1');

INSERT INTO role VALUE ('administrator');
INSERT INTO role VALUE ('bank');
INSERT INTO role VALUE ('client');

-- CREATE USER

DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON bitbankdb.* TO 'admin'@'localhost';
