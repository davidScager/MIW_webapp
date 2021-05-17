USE BitBankDB;

INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Bitcoin', 'BTC','Mooie munt' ,'50000','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Etherium', 'ETH','Mooie munt' ,'3000','1.1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Binance Coin', 'BNB','Mooie munt' ,'600','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Cardano', 'ADA','Mooie munt' ,'2','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Dogecoin', 'DOGE','Mooie munt' ,'0.5','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('XRP','XRP','Mooie munt' ,'1.28','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Polkadot', 'DOT','Mooie munt' ,'37.12','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('INternet Computer', 'ICP','Mooie munt' ,'208.92','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Bitcoin Cash', 'BCH','Mooie munt' ,'1002','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Litecoin', 'LTC','Mooie munt' ,'261.18','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Stellar', 'XLM','Mooie munt' ,'0.6','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('VeChain', 'VET','Mooie munt' ,'0.56','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Ethereum Classis', 'ETC','Mooie munt' ,'82.21','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('EOS', 'EOS','Mooie munt' ,'8.9','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('THETA', 'THETA','Mooie munt' ,'8.29','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('TRON', 'TRX','Mooie munt' ,'0.105','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Filecoin', 'FIL','Mooie munt' ,'93.85','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Monero', 'XMR','Mooie munt' ,'328.79','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('NEo', 'NEO','Mooie munt' ,'81.19','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('DASH', 'DASH','Mooie munt' ,'200.1','1');
INSERT INTO Asset (name, abbreviation, description, valueInUsd, adjustmentFactor) Values ('Dollar', 'USD' ,'valutaatje', 1, 1);

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
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('Bitcoin',101,0.67);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('Etherium',102,0.45);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('Filecoin',101,2);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('Monero',102,6);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('Cordano',104,31);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('TRON',104,26);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('THETA',104,7);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('TRON',105,6321);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('VeChain',103,11);
INSERT INTO assetportfolio (assetName, portfolioId, amount) Values ('DASH',103,986);

# Testdata voor tijdelijk gebruik
INSERT INTO portfolio (portfolioId, actor) Values (101,1);
INSERT INTO portfolio (portfolioId, actor) Values (102,2);
INSERT INTO portfolio (portfolioId, actor) Values (103,3);
INSERT INTO portfolio (portfolioId, actor) Values (104,4);
INSERT INTO portfolio (portfolioId, actor) Values (105,5);

# Testdata voor tijdelijk gebruik
INSERT INTO transaction (transactionId, timestamp, seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought) Values (2001,'2021-02-16',6,2,1,10.2,'Dollar','Etherium');
INSERT INTO transaction (transactionId, timestamp, seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought) Values (2002,'2021-03-26',2,6,0.55,5.01,'Etherium','Dollar');
INSERT INTO transaction (transactionId, timestamp, seller, buyer, numberOfAssets, transactionCost, assetSold, assetBought) Values (2003,'2021-04-03',6,2,6,6.45,'Dollar','Monero');

# Testdata voor tijdelijk gebruik
INSERT INTO log (transactionId, soldAssetTransactionRate, boughtAssetTransactionRate, soldAssetAdjustmentFactor, boughtAssetAdjustmentFactor,amount) Values (2001,1,3201.2,1,1,3201.2);
INSERT INTO log (transactionId, soldAssetTransactionRate, boughtAssetTransactionRate, soldAssetAdjustmentFactor, boughtAssetAdjustmentFactor,amount) Values (2002,3560.45,1,1,1,0.55);
INSERT INTO log (transactionId, soldAssetTransactionRate, boughtAssetTransactionRate, soldAssetAdjustmentFactor, boughtAssetAdjustmentFactor,amount) Values (2003,1,352.56,1,1,2115.36);

