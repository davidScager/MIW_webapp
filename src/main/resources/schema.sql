CREATE SCHEMA BitBankDB;
use BitBankDB;

-- CREATE TABLES

CREATE TABLE LoginAccount(
                             username        VARCHAR(100) NOT NULL PRIMARY KEY,
                             password        VARCHAR(100) NOT NULL,
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
                     streetName      VARCHAR(45) NOT NULL,
                     houseNr         INT         NOT NULL,
                     addition        varchar(5)  NULL,
                     postalCode      varchar(6)  NOT NULL,
                     residence       varchar(45) NOT NULL,
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
                               forSale         DOUBLE      NULL,
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

CREATE TABLE IF NOT EXISTS Log(
                                  transactionId               INT    NOT NULL,
                                  soldAssetTransactionRate    DOUBLE NOT NULL,
                                  boughtAssetTransactionRate  DOUBLE NOT NULL,
                                  soldAssetAdjustmentFactor   DOUBLE NOT NULL,
                                  boughtAssetAdjustmentFactor DOUBLE NOT NULL,
                                  soldAssetAmount             DOUBLE NOT NULL,
                                  boughtAssetAmount           DOUBLE NOT NULL,
                                  transactionCost             DOUBLE NOT NULL,
                                  PRIMARY KEY (transactionId),
                                  FOREIGN KEY (transactionId)
                                      REFERENCES Transaction (transactionId)
                                      ON DELETE NO ACTION
                                      ON UPDATE NO ACTION
);
