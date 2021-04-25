CREATE SCHEMA `BitBank`;
USE `BitBank`;

CREATE TABLE `User`
(
    `userID`   INT         NOT NULL AUTO_INCREMENT,
    `userName` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`userID`)
);
