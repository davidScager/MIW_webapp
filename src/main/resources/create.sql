-- MySQL Workbench Forward Engineering
​
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
​
-- -----------------------------------------------------
-- Schema BitBankDB
-- -----------------------------------------------------
​Volgens
-- -----------------------------------------------------
-- Schema BitBankDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BitBankDB` DEFAULT CHARACTER SET utf8 ;
USE `BitBankDB` ;
​
-- -----------------------------------------------------
-- Table `BitBankDB`.`Inlogaccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Inlogaccount` (
                                                          `gebruikersnaam` VARCHAR(45) NOT NULL,
                                                          `wachtwoord` VARCHAR(45) NOT NULL,
                                                          PRIMARY KEY (`gebruikersnaam`))
    ENGINE = InnoDB;
​
​
-- -----------------------------------------------------
-- Table `BitBankDB`.`Handelaar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Handelaar` (
                                                       `rekeningnummer` INT NOT NULL,
                                                       `saldo` DOUBLE NOT NULL,
                                                       PRIMARY KEY (`rekeningnummer`))
    ENGINE = InnoDB;
​
​
-- -----------------------------------------------------
-- Table `BitBankDB`.`Klant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Klant` (
                                                   `BSN` INT NOT NULL,
                                                   `voornaam` VARCHAR(45) NOT NULL,
                                                   `tussenvoegsel` VARCHAR(45) NULL,
                                                   `achternaam` VARCHAR(45) NOT NULL,
                                                   `geboortedatum` DATE NOT NULL,
                                                   `adres` VARCHAR(45) NOT NULL,
                                                   `gebruikersnaam` VARCHAR(45) NOT NULL,
                                                   `rekeningnummer` INT NOT NULL,
                                                   PRIMARY KEY (`BSN`),
                                                   INDEX `verzinzelf1_idx` (`gebruikersnaam` ASC) VISIBLE,
                                                   INDEX `verzinzelf6_idx` (`rekeningnummer` ASC) VISIBLE,
                                                   CONSTRAINT `verzinzelf1`
                                                       FOREIGN KEY (`gebruikersnaam`)
                                                           REFERENCES `BitBankDB`.`Inlogaccount` (`gebruikersnaam`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION,
                                                   CONSTRAINT `verzinzelf6`
                                                       FOREIGN KEY (`rekeningnummer`)
                                                           REFERENCES `BitBankDB`.`Handelaar` (`rekeningnummer`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION)
    ENGINE = InnoDB;
​
​
-- -----------------------------------------------------
-- Table `BitBankDB`.`Beheerder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Beheerder` (
                                                       `gebruikersnaam` VARCHAR(45) NOT NULL,
                                                       PRIMARY KEY (`gebruikersnaam`),
                                                       CONSTRAINT `verzinzelf7`
                                                           FOREIGN KEY (`gebruikersnaam`)
                                                               REFERENCES `BitBankDB`.`Inlogaccount` (`gebruikersnaam`)
                                                               ON DELETE NO ACTION
                                                               ON UPDATE NO ACTION)
    ENGINE = InnoDB;
​
​
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
​
​
-- -----------------------------------------------------
-- Table `BitBankDB`.`Transactie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Transactie` (
                                                        `transactieId` INT NOT NULL AUTO_INCREMENT,
                                                        `asset` VARCHAR(45) NOT NULL,
                                                        `koper` INT NOT NULL,
                                                        `verkoper` INT NOT NULL,
                                                        `datum` DATETIME NOT NULL,
                                                        `aantal` DOUBLE NOT NULL,
                                                        PRIMARY KEY (`transactieId`),
                                                        INDEX `verzinzelf5_idx` (`asset` ASC) VISIBLE,
                                                        INDEX `verzinzelf3_idx` (`koper` ASC) VISIBLE,
                                                        INDEX `verzinzelf4_idx` (`verkoper` ASC) VISIBLE,
                                                        CONSTRAINT `verzinzelf5`
                                                            FOREIGN KEY (`asset`)
                                                                REFERENCES `BitBankDB`.`Asset` (`naam`)
                                                                ON DELETE NO ACTION
                                                                ON UPDATE NO ACTION,
                                                        CONSTRAINT `verzinzelf3`
                                                            FOREIGN KEY (`koper`)
                                                                REFERENCES `BitBankDB`.`Handelaar` (`rekeningnummer`)
                                                                ON DELETE NO ACTION
                                                                ON UPDATE NO ACTION,
                                                        CONSTRAINT `verzinzelf4`
                                                            FOREIGN KEY (`verkoper`)
                                                                REFERENCES `BitBankDB`.`Handelaar` (`rekeningnummer`)
                                                                ON DELETE NO ACTION
                                                                ON UPDATE NO ACTION)
    ENGINE = InnoDB;
​
​
-- -----------------------------------------------------
-- Table `BitBankDB`.`Bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Bank` (
                                                  `rekeningnummer` INT NOT NULL,
                                                  PRIMARY KEY (`rekeningnummer`),
                                                  CONSTRAINT `verzinzelf8`
                                                      FOREIGN KEY (`rekeningnummer`)
                                                          REFERENCES `BitBankDB`.`Handelaar` (`rekeningnummer`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION)
    ENGINE = InnoDB;
​
​
-- -----------------------------------------------------
-- Table `BitBankDB`.`Portfolio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BitBankDB`.`Portfolio` (
                                                       `handelaar` INT NOT NULL,
                                                       `asset` VARCHAR(45) NOT NULL,
                                                       `aantal` DOUBLE NOT NULL,
                                                       PRIMARY KEY (`handelaar`, `asset`),
                                                       INDEX `verzinzelf10_idx` (`asset` ASC) VISIBLE,
                                                       INDEX `verzinzelf2_idx` (`handelaar` ASC) VISIBLE,
                                                       CONSTRAINT `verzinzelf2`
                                                           FOREIGN KEY (`handelaar`)
                                                               REFERENCES `BitBankDB`.`Handelaar` (`rekeningnummer`)
                                                               ON DELETE NO ACTION
                                                               ON UPDATE NO ACTION,
                                                       CONSTRAINT `verzinzelf10`
                                                           FOREIGN KEY (`asset`)
                                                               REFERENCES `BitBankDB`.`Asset` (`naam`)
                                                               ON DELETE NO ACTION
                                                               ON UPDATE NO ACTION)
    ENGINE = InnoDB;
​
​
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
​
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON bitbankdb.* TO 'admin'@'localhost';
Collapse

