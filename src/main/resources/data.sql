USE BitBankDB;

INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Bitcoin', 'BTC','Most known cryptocoin' ,'50000','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Ethereum', 'ETH','A crytocoin on an opensourcepaltform' ,'3000','1.1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Binance Coin', 'BNB','Has the most exchangeses of all cyptocoins' ,'600','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Cardano', 'ADA','Follows sience to improve its service' ,'2','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Dogecoin', 'DOGE','Created by software engineers' ,'0.5','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('XRP','XRP','Ripple is often tought as the same' ,'1.28','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Polkadot', 'DOT','Has the same founder as Ethereum' ,'37.12','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Internet Computer', 'ICP','Relased at may 10th of this year' ,'208.92','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Bitcoin Cash', 'BCH','Branch from bitcoin' ,'1002','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Litecoin', 'LTC','One of the first cryptocoins' ,'261.18','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Stellar', 'XLM','A coin from the Lumen organization' ,'0.6','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Vechain', 'VET','Former known as DogeCoin-Dark' ,'0.56','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Ethereum Classic', 'ETC','Branch from Ethereum' ,'82.21','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Eos', 'EOS','Runs on the EOS network' ,'8.9','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Theta', 'THETA','A big part of the streaming sector' ,'8.29','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Tron', 'TRX','Aims to build a free digital entertainment system' ,'0.105','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Filecoin', 'FIL','Also works with information storage' ,'93.85','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Monero', 'XMR','Works anonymous' ,'328.79','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Neo', 'NEO','Has a vision to realize a smart economy' ,'81.19','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Dash', 'DASH','Users are called masternodes' ,'200.1','1', '1', '1', '1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor, valueYesterday, valueLastWeek, valueLastMonth) Values ('Dollar', 'USD' ,'Basic value in this bank', 1, 1, '1', '1', '1');

INSERT INTO role VALUE ('administrator');
INSERT INTO role VALUE ('bank');
INSERT INTO role VALUE ('client');

# Testdata voor tijdelijk gebruik
INSERT INTO actor (userId, checkingAccount, role) Values (1,12345678,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (2,87654321,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (3,45612387,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (4,32145678,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (5,32187456,'client');
INSERT INTO actor (userId, checkingAccount, role) Values (6,98765432,'bank');

# Testdata voor tijdelijk gebruik
INSERT INTO portfolio (portfolioId, actor) Values (101,1);
INSERT INTO portfolio (portfolioId, actor) Values (102,2);
INSERT INTO portfolio (portfolioId, actor) Values (103,3);
INSERT INTO portfolio (portfolioId, actor) Values (104,4);
INSERT INTO portfolio (portfolioId, actor) Values (105,5);
INSERT INTO portfolio (portfolioId, actor) Values (106,6);

# Testdata voor tijdelijk gebruik
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('BTC',101,0.67);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('ETH',102,0.45);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('FIL',101,2);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('XMR',102,6);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('ADA',104,31);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('TRX',104,26);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('THETA',104,7);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('TRX',105,6321);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('VET',103,11);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('DASH',103,986);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('BTC',106,3030);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('ADA',106,1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('BCH',106,2396);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('BNB',106,4132);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('DASH',106,1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('DOGE',106,3240);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('DOT',106,3395);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('EOS',106,3030);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('ETC',106,1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('ETH',106,2396);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('FIL',106,4132);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('ICP',106,1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('LTC',106,3240);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('NEO',106,3395);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('THETA',106,3030);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('TRX',106,1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('VET',106,2396);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('XLM',106,4132);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('XMR',106,1030);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('XRP',106,3240);


# Testdata voor tijdelijk gebruik
INSERT INTO transaction (transactionId, timestamp, seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought) Values (2001,'2021-02-16',6,2,1,10.2,'Dollar','Etherium');
INSERT INTO transaction (transactionId, timestamp, seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought) Values (2002,'2021-03-26',2,6,0.55,5.01,'Etherium','Dollar');
INSERT INTO transaction (transactionId, timestamp, seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought) Values (2003,'2021-04-03',6,2,6,6.45,'Dollar','Monero');

# Testdata voor tijdelijk gebruik
INSERT INTO log (transactionId, soldAssetTransactionRate, boughtAssetTransactionRate, soldAssetAdjustmentFactor, boughtAssetAdjustmentFactor,amount) Values (2001,1,3201.2,1,1,3201.2);
INSERT INTO log (transactionId, soldAssetTransactionRate, boughtAssetTransactionRate, soldAssetAdjustmentFactor, boughtAssetAdjustmentFactor,amount) Values (2002,3560.45,1,1,1,0.55);
INSERT INTO log (transactionId, soldAssetTransactionRate, boughtAssetTransactionRate, soldAssetAdjustmentFactor, boughtAssetAdjustmentFactor,amount) Values (2003,1,352.56,1,1,2115.36);

