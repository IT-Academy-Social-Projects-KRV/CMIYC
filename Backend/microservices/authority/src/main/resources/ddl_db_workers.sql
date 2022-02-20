-- -----------------------------------------------------
-- Schema db_workers
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_workers` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `db_workers` ;

-- -----------------------------------------------------
-- Table `db_workers`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_workers`.`roles` ;

CREATE TABLE IF NOT EXISTS `db_workers`.`roles` (
  `ID_ROLES` INT NOT NULL AUTO_INCREMENT,
  `ROLE` VARCHAR(25) NULL ,
  `ROLE_DESCRIPTON` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_ROLES`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_workers`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_workers`.`users` ;

CREATE TABLE IF NOT EXISTS `db_workers`.`users` (
  `ID_USER` INT NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` VARCHAR(50) NULL DEFAULT NULL,
  `LAST_NAME` VARCHAR(50) NULL DEFAULT NULL,
  `EMAIL` VARCHAR(50) NULL ,
  `USER_LOGIN` VARCHAR(50) NULL DEFAULT NULL,
  `REGISTER_DATE` DATE NULL DEFAULT (CURRENT_DATE),
  `IS_ACTIVE` TINYINT NULL DEFAULT '0',
  PRIMARY KEY (`ID_USER`),
UNIQUE INDEX `UN_USERS_EMAIL` (`EMAIL` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `db_workers`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `db_workers`.`user_roles` ;

CREATE TABLE IF NOT EXISTS `db_workers`.`user_roles` (
  `ID_USER` INT NOT NULL,
  `ID_ROLE` INT NOT NULL,
  PRIMARY KEY (`ID_USER`, `ID_ROLE`),
  INDEX `fk_roles` (`ID_ROLE` ASC) VISIBLE,
  CONSTRAINT `fk_roles`
    FOREIGN KEY (`ID_ROLE`)
    REFERENCES `db_workers`.`roles` (`ID_ROLES`),
  CONSTRAINT `fk_users`
    FOREIGN KEY (`ID_USER`)
    REFERENCES `db_workers`.`users` (`ID_USER`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

