DROP SCHEMA bitbankdb;
CREATE SCHEMA bitbankdb;
use bitbankdb;

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

-- INSERT DATA

INSERT INTO asset VALUES ('ADA','Cardano','Cardano','Follows sience to improve its service',2,1,1,1.58,2.17),
                         ('BCH','Bitcoin Cash','bitcoin-cash','Branch from bitcoin',1002,1,1,597.97,1175.6),
                         ('BNB','Binance Coin','binancecoin','Has the most exchangeses of all cyptocoins',600,1,1,353.76,563.51),
                         ('BTC','Bitcoin','Bitcoin','Most known cryptocoin',50000,1,1,3355.7,46780.7),
                         ('DASH','Dash','Dash','Users are called masternodes',200.1,1,1,166.51,337.33),
                         ('DOGE','Dogecoin','Dogecoin','Created by software engineers',0.5,1,1,0.327442,0.506792),
                         ('DOT','Polkadot','Polkadot','Has the same founder as Ethereum',37.12,1,1,21.87,43.19),
                         ('EOS','Eos','Eos','Runs on the EOS network',8.9,1,1,5.07,10.19),
                         ('ETC','Ethereum Classic','ethereum-classic','Branch from Ethereum',82.21,1,1,57.01,94.9),
                         ('ETH','Ethereum','Ethereum','A crytocoin on an opensourcepaltform',3000,1,1,2528.02,3659.92),
                         ('FIL','Filecoin','Filecoin','Also works with information storage',93.85,1,1,76.22,109.98),
                         ('ICP','Internet Computer','internet-computer','Relased at may 10th of this year',208.92,1,1,81.29,245.53),
                         ('LTC','Litecoin','Litecoin','One of the first cryptocoins',261.18,1,1,161.02,299.07),
                         ('NEO','Neo','Neo','Has a vision to realize a smart economy',81.19,1,1,49.08,92.27),
                         ('THETA','Theta','theta-token','A big part of the streaming sector',8.29,1,1,8.99,9.29),
                         ('TRX','Tron','Tron','Aims to build a free digital entertainment system',0.105,1,1,0.072709,0.120676),
                         ('USD','Dollar','','Basic value in this bank',1,1,1,1,1),
                         ('VET','Vechain','Vechain','Former known as DogeCoin-Dark',0.56,1,1,0.113745,0.673461),
                         ('XLM','Stellar','Stellar','A coin from the Lumen organization',0.6,1,1,0.351863,0.673461),
                         ('XMR','Monero','Monero','Works anonymous',328.79,1,1,254.1,382.84);

INSERT INTO role VALUES ('administrator');
INSERT INTO role VALUES ('bank');
INSERT INTO role VALUES ('client');

INSERT INTO actor VALUES (1,'12345678','bank'),
                         (2,'NL99 BITB 5561373276','CLIENT'),
                         (3,'NL99 BITB 2627160050','CLIENT'),
                         (4,'NL99 BITB 2877740812','CLIENT'),
                         (5,'NL99 BITB 2001335063','CLIENT'),
                         (6,'NL99 BITB 3428800845','CLIENT');

INSERT INTO loginaccount VALUES ('boy.wonder@smartguy.com','$argon2id$v=19$m=15360,t=2,p=1$A3mFtyTa3sW80dowU6/INA$I2CvP5j4wIqULo9rVnRgg4jsRNuK/XA86rC1XYB0zbg',NULL),
                                ('johnny.bravo@cartoonnetwork.com','$argon2id$v=19$m=15360,t=2,p=1$c5b7W72KzkP4yp8nBoKckQ$+XaVQ0nFswcmC6YVNl33c5oGVgHjnp0XQTSCabj7GHM',NULL),
                                ('mojo.jojo@badguy.com','$argon2id$v=19$m=15360,t=2,p=1$sOFBFXRg80kkIpz08lcaig$KxuYQfIl1IVRHtXr6MTrG9pZrRNMyGqcNAqzZpSz0N8',NULL),
                                ('testman.tester@hotmail.com','$argon2id$v=19$m=15360,t=2,p=1$Htm9lgaaCxJV3V3yZizmZw$b9+o9S1vQQcZO/Wko2ju0a8s6J+nUYdgLcTEZu+n+qY',NULL),
                                ('testvrouw.tester@hotmail.com','$argon2id$v=19$m=15360,t=2,p=1$m97Hew7Uv1Ka9VP+HX+e0w$WLJ7A9QeHf/os/q7eFpVNG0d51NaGlIEuooPajNsMF0',NULL);


INSERT INTO user VALUES (123456,2,'Johnny','','Bravo','1997-11-01','dorpstraat',10,'bis','1234AB','Townsville','johnny.bravo@cartoonnetwork.com'),
                        (234567,3,'Mojo','','Jojo','1997-11-01','EvilLairstreet',11,'','1234AB','Townsville','mojo.jojo@badguy.com'),
                        (345678,4,'Dexter','','Genius','1997-11-01','SecretLabstreet',12,'bis','1234AB','Townsville','boy.wonder@smartguy.com'),
                        (743292,6,'Testvrouw','','Tester','1997-11-01','SecretLabstreet',12,'bis','1234AB','Townsville','testvrouw.tester@hotmail.com'),
                        (758274,5,'Testman','','Tester','1997-11-01','SecretLabstreet',12,'bis','1234AB','Townsville','testman.tester@hotmail.com');



INSERT INTO portfolio VALUES (101,1),
                             (102,2),
                             (103,3),
                             (104,4),
                             (105,5),
                             (106,6);

INSERT INTO assetportfolio VALUES ('ADA',101,1030,1030),('ADA',102,1,0),('ADA',103,1,0),('ADA',104,1,0),('ADA',105,1,0),('ADA',106,1,0),('BCH',101,2396,2396),('BCH',102,1,0),('BCH',103,1,0),('BCH',104,1,0),('BCH',105,1,0),('BCH',106,1,0),('BNB',101,4132,4132),('BNB',102,1,0),('BNB',103,1,0),('BNB',104,1,0),('BNB',105,1,0),('BNB',106,1,0),('BTC',101,3030,3030),('BTC',102,1,0),('BTC',103,1,0),('BTC',104,1,0),('BTC',105,1,0),('BTC',106,1,0),('DASH',101,1030,1030),('DASH',102,1,0),('DASH',103,1,0),('DASH',104,1,0),('DASH',105,1,0),('DASH',106,1,0),('DOGE',101,3240,3240),('DOGE',102,1,0),('DOGE',103,1,0),('DOGE',104,1,0),('DOGE',105,1,0),('DOGE',106,1,0),('DOT',101,3395,3395),('DOT',102,1,0),('DOT',103,1,0),('DOT',104,1,0),('DOT',105,1,0),('DOT',106,1,0),('EOS',101,3030,3030),('EOS',102,1,0),('EOS',103,1,0),('EOS',104,1,0),('EOS',105,1,0),('EOS',106,1,0),('ETC',101,1030,1030),('ETC',102,1,0),('ETC',103,1,0),('ETC',104,1,0),('ETC',105,1,0),('ETC',106,1,0),('ETH',101,2396,2396),('ETH',102,1,0),('ETH',103,1,0),('ETH',104,1,0),('ETH',105,1,0),('ETH',106,1,0),('FIL',101,4132,4132),('FIL',102,1,0),('FIL',103,1,0),('FIL',104,1,0),('FIL',105,1,0),('FIL',106,1,0),('ICP',101,1030,1030),('ICP',102,1,0),('ICP',103,1,0),('ICP',104,1,0),('ICP',105,1,0),('ICP',106,1,0),('LTC',101,3240,3240),('LTC',102,1,0),('LTC',103,1,0),('LTC',104,1,0),('LTC',105,1,0),('LTC',106,1,0),('NEO',101,3395,3395),('NEO',102,1,0),('NEO',103,1,0),('NEO',104,1,0),('NEO',105,1,0),('NEO',106,1,0),('THETA',101,3030,3030),('THETA',102,1,0),('THETA',103,1,0),('THETA',104,1,0),('THETA',105,1,0),('THETA',106,1,0),('TRX',101,1030,1030),('TRX',102,1,0),('TRX',103,1,0),('TRX',104,1,0),('TRX',105,1,0),('TRX',106,1,0),('USD',101,5000000,5000000),('USD',102,2000,0),('USD',103,2000,0),('USD',104,2000,0),('USD',105,2000,0),('USD',106,2000,0),('VET',101,2396,2396),('VET',102,1,0),('VET',103,1,0),('VET',104,1,0),('VET',105,1,0),('VET',106,1,0),('XLM',101,4132,4132),('XLM',102,1,0),('XLM',103,1,0),('XLM',104,1,0),('XLM',105,1,0),('XLM',106,1,0),('XMR',101,1030,1030),('XMR',102,1,0),('XMR',103,1,0),('XMR',104,1,0),('XMR',105,1,0),('XMR',106,1,0);

-- CREATE USER

DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON bitbankdb.* TO 'admin'@'localhost';

