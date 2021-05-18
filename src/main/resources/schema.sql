DROP SCHEMA IF EXISTS BitBankDB;
CREATE SCHEMA BitBankDB;
use BitBankDB;

CREATE TABLE IF NOT EXISTS LoginAccount
(
    username VARCHAR(100) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    salt     VARCHAR(45)  NOT NULL
);

CREATE TABLE IF NOT EXISTS Role
(
    role VARCHAR(45) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Actor
(
    userId          INT         NOT NULL AUTO_INCREMENT,
    checkingAccount VARCHAR(45) NULL,
    role            VARCHAR(45) NOT NULL,
    PRIMARY KEY (userId),
    FOREIGN KEY (role)
        REFERENCES Role (role)
#        ON DELETE RESTRICT
#        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS User
(
    BSN         INT         NOT NULL,
    userId      INT         NOT NULL,
    firstName   VARCHAR(45) NOT NULL,
    infix       VARCHAR(45) NULL,
    surname     VARCHAR(45) NOT NULL,
    dateOfBirth DATE        NOT NULL,
    address     VARCHAR(45) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    PRIMARY KEY (BSN),
#     FOREIGN KEY (email)
#         REFERENCES LoginAccount (username)
#         ON DELETE CASCADE
#         ON UPDATE CASCADE,
    FOREIGN KEY (userId)
        REFERENCES Actor (userId)
#        ON DELETE CASCADE
#        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Asset
(
    `abbreviation`     VARCHAR(45) NOT NULL,
    `name`             VARCHAR(45) NOT NULL,
    `description`      VARCHAR(45) NOT NULL,
    `valueInUsd`       DOUBLE      NOT NULL,
    `adjustmentFactor` DOUBLE      NOT NULL,
    `valueYesterday`   DOUBLE      NOT NULL,
    `valueLastWeek`    DOUBLE      NOT NULL,
    `valueLastMonth`   DOUBLE      NOT NULL,
    PRIMARY KEY (`abbreviation`)
);

CREATE TABLE IF NOT EXISTS Transaction
(
    `transactionId`   INT         NOT NULL AUTO_INCREMENT,
    `timestamp`       DATETIME    NOT NULL,
    `seller`          INT         NOT NULL,
    `buyer`           INT         NOT NULL,
    `numberOfAssets`  DOUBLE      NOT NULL,
    `transactionCost` DOUBLE      NOT NULL,
    `assetSold`       VARCHAR(45) NOT NULL,
    `assetBought`     VARCHAR(45) NOT NULL,
    PRIMARY KEY (`transactionId`),
    INDEX `verzinzelf3_idx` (`seller` ASC) VISIBLE,
    INDEX `verzinzelf4_idx` (`buyer` ASC) VISIBLE,
    INDEX `verzinzelf5_idx` (`assetSold` ASC) VISIBLE,
    INDEX `verzinzelf7_idx` (`assetBought` ASC) VISIBLE,
    CONSTRAINT `verzinzelf3`
        FOREIGN KEY (`seller`)
            REFERENCES `BitBankDB`.`Actor` (`userId`)
#            ON DELETE NO ACTION
#            ON UPDATE NO ACTION,
,    CONSTRAINT `verzinzelf4`
        FOREIGN KEY (`buyer`)
            REFERENCES `BitBankDB`.`Actor` (`userId`)
#            ON DELETE NO ACTION
#            ON UPDATE NO ACTION,
,    CONSTRAINT `verzinzelf5`
        FOREIGN KEY (`assetSold`)
            REFERENCES `BitBankDB`.`Asset` (`abbreviation`)
#            ON DELETE NO ACTION
#            ON UPDATE NO ACTION,
,    CONSTRAINT `verzinzelf7`
        FOREIGN KEY (`assetBought`)
            REFERENCES `BitBankDB`.`Asset` (`abbreviation`)
#            ON DELETE NO ACTION
#            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS Portfolio
(
    `portfolioId` INT NOT NULL AUTO_INCREMENT,
    `actor`       INT NOT NULL,
    PRIMARY KEY (`portfolioId`),
    FOREIGN KEY (`actor`)
        REFERENCES Actor (userId)
#        ON DELETE CASCADE
#        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS AssetPortfolio
(
    `assetName`   VARCHAR(45) NOT NULL,
    `portfolioId` INT         NOT NULL,
    `amount`      DOUBLE      NOT NULL,
    PRIMARY KEY (`assetName`, `portfolioId`),
    INDEX `verzinzelf11_idx` (`portfolioId` ASC) VISIBLE,
    INDEX `verzinzelf10_idx` (`assetName` ASC) VISIBLE,
    CONSTRAINT `verzinzelf11`
        FOREIGN KEY (`portfolioId`)
            REFERENCES `BitBankDB`.`Portfolio` (`portfolioId`)
#            ON DELETE CASCADE
#            ON UPDATE CASCADE,
,    CONSTRAINT `verzinzelf10`
        FOREIGN KEY (`assetName`)
            REFERENCES `BitBankDB`.`Asset` (`abbreviation`)
#            ON DELETE NO ACTION
#            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS `BitBankDB`.`Log`
(
    `transactionId`               INT    NOT NULL,
    `soldAssetTransactionRate`    DOUBLE NOT NULL,
    `boughtAssetTransactionRate`  DOUBLE NOT NULL,
    `soldAssetAdjustmentFactor`   DOUBLE NOT NULL,
    `boughtAssetAdjustmentFactor` DOUBLE NOT NULL,
    `amount`                      DOUBLE NOT NULL,
    PRIMARY KEY (`transactionId`),
    FOREIGN KEY (`transactionId`)
        REFERENCES `BitBankDB`.`Transaction` (`transactionId`)
#        ON DELETE RESTRICT
#        ON UPDATE CASCADE
);

DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON bitbankdb.* TO 'admin'@'localhost';
