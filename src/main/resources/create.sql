-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema BitBankDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema BitBankDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS BitBankDB;
CREATE SCHEMA `BitBankDB` DEFAULT CHARACTER SET utf8 ;
USE `BitBankDB` ;

-- -----------------------------------------------------
-- Table `BitBankDB`.`LoginAccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`LoginAccount` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `salt` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BitBankDB`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Role` (
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BitBankDB`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`User` (
  `userId` INT NOT NULL,
  `checkingAccount` VARCHAR(45) NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`userId`),
  INDEX `verzinzelf9_idx` (`role` ASC) VISIBLE,
  CONSTRAINT `verzinzelf9`
    FOREIGN KEY (`role`)
    REFERENCES `BitBankDB`.`Role` (`role`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BitBankDB`.`Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Client` (
  `BSN` INT NOT NULL,
  `userId` INT NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `infix` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `dateOfBirth` DATE NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`BSN`, `userId`),
  INDEX `verzinzelf1_idx` (`username` ASC) VISIBLE,
  INDEX `verzinzelf6_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf1`
    FOREIGN KEY (`username`)
    REFERENCES `BitBankDB`.`LoginAccount` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `verzinzelf6`
    FOREIGN KEY (`userId`)
    REFERENCES `BitBankDB`.`User` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BitBankDB`.`Asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Asset` (
  `naam` VARCHAR(45) NOT NULL,
  `afkorting` VARCHAR(45) NOT NULL,
  `beschrijving` VARCHAR(45) NOT NULL,
  `koers` DOUBLE NOT NULL,
  PRIMARY KEY (`naam`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BitBankDB`.`Conversion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Conversion` (
  `rate` VARCHAR(45) NOT NULL,
  `asset1` VARCHAR(45) NOT NULL,
  `asset2` VARCHAR(45) NOT NULL,
  `conversionValue` DOUBLE NOT NULL,
  PRIMARY KEY (`rate`),
  INDEX `verzinzelf8_idx` (`asset2` ASC) VISIBLE,
  INDEX `verzinzelf7_idx` (`asset1` ASC) VISIBLE,
  CONSTRAINT `verzinzelf7`
    FOREIGN KEY (`asset1`)
    REFERENCES `BitBankDB`.`Asset` (`naam`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `verzinzelf8`
    FOREIGN KEY (`asset2`)
    REFERENCES `BitBankDB`.`Asset` (`naam`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BitBankDB`.`Transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Transaction` (
  `transactionId` INT NOT NULL AUTO_INCREMENT,
  `rate` VARCHAR(45) NOT NULL,
  `date` DATETIME NOT NULL,
  `amount` DOUBLE NOT NULL,
  `seller` INT NOT NULL,
  `buyer` INT NOT NULL,
  PRIMARY KEY (`transactionId`),
  INDEX `verzinzelf5_idx` (`rate` ASC) VISIBLE,
  INDEX `verzinzelf3_idx` (`seller` ASC) VISIBLE,
  INDEX `verzinzelf4_idx` (`buyer` ASC) VISIBLE,
  CONSTRAINT `verzinzelf5`
    FOREIGN KEY (`rate`)
    REFERENCES `BitBankDB`.`Conversion` (`rate`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf3`
    FOREIGN KEY (`seller`)
    REFERENCES `BitBankDB`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf4`
    FOREIGN KEY (`buyer`)
    REFERENCES `BitBankDB`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BitBankDB`.`Portfolio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Portfolio` (
  `portfolioId` INT NOT NULL,
  `user` INT NOT NULL,
  PRIMARY KEY (`portfolioId`),
  INDEX `verzinzelf2_idx` (`user` ASC) VISIBLE,
  CONSTRAINT `verzinzelf2`
    FOREIGN KEY (`user`)
    REFERENCES `BitBankDB`.`User` (`userId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BitBankDB`.`AssetPortfolio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`AssetPortfolio` (
  `assetName` VARCHAR(45) NOT NULL,
  `portfolioId` INT NOT NULL,
  `amount` DOUBLE NOT NULL,
  PRIMARY KEY (`assetName`, `portfolioId`),
  INDEX `verzinzelf10_idx` (`assetName` ASC) VISIBLE,
  INDEX `verzinzelf11_idx` (`portfolioId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf10`
    FOREIGN KEY (`assetName`)
    REFERENCES `BitBankDB`.`Asset` (`naam`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `verzinzelf11`
    FOREIGN KEY (`portfolioId`)
    REFERENCES `BitBankDB`.`Portfolio` (`portfolioId`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

DROP USER IF EXISTS 'admin'@'localhost';
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON bitbankdb.* TO 'admin'@'localhost';
