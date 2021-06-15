INSERT INTO asset (name,apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Bitcoin', 'Bitcoin','BTC','Most known cryptocoin' ,'50000','1', '1', '1', '1');
INSERT INTO asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Ethereum','Ethereum', 'ETH','A crytocoin on an opensourcepaltform' ,'3000','1', '1', '1', '1');
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
INSERT INTO Asset (name, apiName ,abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Dollar','', 'USD' ,'Basic value in this bank', '1', '1', '1', '1', '1');

INSERT INTO role VALUES ('administrator');
INSERT INTO role VALUES ('bank');
INSERT INTO role VALUES ('client');

INSERT INTO loginaccount (username, password, token) VALUES ('niekmol1994@gmail.com', '$argon2id$v=19$m=15360,t=2,p=1$0trGK6yztdFqZJtZdbmYig$IcdrP4+vTchnFQ1Gr0ZmFEfF5ic8jSdgmLG0uk6clLA', null);
INSERT INTO loginaccount (username, password, token) VALUES ('huib@huib.com', '$argon2id$v=19$m=15360,t=2,p=1$0trGK6yztdFqZJtZdbmYig$IcdrP4+vTchnFQ1Gr0ZmFEfF5ic8jSdgmLG0uk6clLA', null);

INSERT INTO actor (userId, checkingAccount, role) Values (1,12345678,'bank');
INSERT INTO actor (userId, checkingAccount, role) Values (2,87654321,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (3,45612387,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (4,32145678,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (5,32187456,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (6,32187456,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (7,32187456,'client');

INSERT INTO User (BSN, userId, firstName, infix, surname, dateOfBirth, postalCode, houseNr, addition, streetName, residence, email) values
(636363, 2, 'Niek', null, 'Mol', '1994-05-04', '1234AA', 2, null, 'HIERRRRR', 'UUUUUUUU', 'niekmol1994@gmail.com');
INSERT INTO User(BSN, userId, firstName, infix, surname, dateOfBirth, postalCode, houseNr, addition, streetName, residence, email) values
 (123456, 7, 'Huib', 'van', 'Straten', '1982-01-29', '2252BX', 8, 'b', 'van lierdreef' , 'Voorschoten', 'huib@huib.com');

INSERT INTO portfolio (portfolioId, actor) Values (101,1);
INSERT INTO portfolio (portfolioId, actor) Values (102,2);
INSERT INTO portfolio (portfolioId, actor) Values (103,3);
INSERT INTO portfolio (portfolioId, actor) Values (104,4);
INSERT INTO portfolio (portfolioId, actor) Values (105,5);
INSERT INTO portfolio (portfolioId, actor) Values (106,6);
INSERT INTO portfolio (portfolioId, actor) Values (107,7);

INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('BTC',106,0.67, 0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('ETH',102,3000000, 0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('BTC',102,6363, 0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('BTC',107,6363, 0);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('USD',102,1000000);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('FIL',106,2,0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('XMR',102,6,0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('ADA',104,31,0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('TRX',104,26,0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('THETA',104,7,0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('THETA',107,7,0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('TRX',105,6321,0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('VET',103,11,0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('DASH',103,986,0);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('BTC',101,3030, 3030);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('ADA',101,1030, 1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('BCH',101,2396, 2396);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('BNB',101,4132, 4132);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('DASH',101,1030, 1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('DOGE',101,3240, 3240);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('DOT',101,3395, 3395);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('EOS',101,3030, 3030);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('ETC',101,1030, 1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('ETH',101,2396, 2396);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('FIL',101,4132, 4132);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('ICP',101,1030, 1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('LTC',101,3240, 3240);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('NEO',101,3395, 3395);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('THETA',101,3030, 3030);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('TRX',101,1030, 1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('VET',101,2396, 2396);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('XLM',101,4132, 4132);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('XMR',101,1030, 1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('XRP',101,3240, 3240);
INSERT INTO assetportfolio (assetName, portfolioId, amount, forSale) Values ('USD',101,5000000, 5000000);
