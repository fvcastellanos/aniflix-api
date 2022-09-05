-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema aniflix
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema aniflix
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `aniflix` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;
USE `aniflix` ;

-- -----------------------------------------------------
-- Table `aniflix`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`department` (
  `id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(150) NOT NULL,
  `active` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_department_active` (`active` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `aniflix`.`worker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`worker` (
  `id` VARCHAR(50) NOT NULL,
  `department_id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(150) NOT NULL,
  `cui` VARCHAR(50) NOT NULL,
  `email` VARCHAR(300) NOT NULL,
  `active` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_worker_id` (`cui` ASC) VISIBLE,
  INDEX `idx_worker_active` (`active` ASC) VISIBLE,
  INDEX `fk_worker_department_idx` (`department_id` ASC) VISIBLE,
  UNIQUE INDEX `uq_worker_email` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_worker_department`
    FOREIGN KEY (`department_id`)
    REFERENCES `aniflix`.`department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `aniflix`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`event` (
  `id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(150) NOT NULL,
  `type` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_event_type` (`name` ASC, `type` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `aniflix`.`worker_event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`worker_event` (
  `id` VARCHAR(50) NOT NULL,
  `worker_id` VARCHAR(50) NOT NULL,
  `event_id` VARCHAR(50) NOT NULL,
  `event_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_worker_event_worker1_idx` (`worker_id` ASC) INVISIBLE,
  INDEX `fk_worker_event_event1_idx` (`event_id` ASC) VISIBLE,
  INDEX `idx_worker_event_date` (`event_date` ASC) VISIBLE,
  CONSTRAINT `fk_worker_event_worker1`
    FOREIGN KEY (`worker_id`)
    REFERENCES `aniflix`.`worker` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_worker_event_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `aniflix`.`event` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `aniflix`.`admin_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`admin_user` (
  `id` VARCHAR(50) NOT NULL,
  `worker_id` VARCHAR(50) NOT NULL,
  `user` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `active` INT NOT NULL,
  `role` VARCHAR(50) NOT NULL DEFAULT 'worker',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_admin_user_user` (`user` ASC) VISIBLE,
  INDEX `idx_admin_user_active` (`active` ASC) VISIBLE,
  INDEX `fk_admin_user_worker1_idx` (`worker_id` ASC) VISIBLE,
  CONSTRAINT `fk_admin_user_worker1`
    FOREIGN KEY (`worker_id`)
    REFERENCES `aniflix`.`worker` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `aniflix`.`plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`plan` (
  `id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(150) NOT NULL,
  `costo` DOUBLE NOT NULL DEFAULT 0.00,
  `active` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_plan_active` (`active` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `aniflix`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`customer` (
  `id` VARCHAR(50) NOT NULL,
  `plan_id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(150) NOT NULL,
  `subscription_date` TIMESTAMP NOT NULL,
  `email` VARCHAR(300) NOT NULL,
  `active` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_customer_active` (`active` ASC) VISIBLE,
  INDEX `fk_customer_plan1_idx` (`plan_id` ASC) VISIBLE,
  UNIQUE INDEX `uq_customer_email` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_customer_plan1`
    FOREIGN KEY (`plan_id`)
    REFERENCES `aniflix`.`plan` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `aniflix`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`user` (
  `id` VARCHAR(50) NOT NULL,
  `customer_id` VARCHAR(50) NOT NULL,
  `user` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `active` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_customer1_idx` (`customer_id` ASC) VISIBLE,
  UNIQUE INDEX `uq_user_user` (`user` ASC) INVISIBLE,
  INDEX `idx_user_active` (`active` ASC) VISIBLE,
  CONSTRAINT `fk_user_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `aniflix`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `aniflix`.`transaction_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`transaction_type` (
  `id` VARCHAR(50) NOT NULL,
  `name` VARCHAR(150) NOT NULL,
  `type` VARCHAR(1) NOT NULL DEFAULT 'D',
  PRIMARY KEY (`id`),
  INDEX `idx_transaction_type_type` (`type` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `aniflix`.`user_transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `aniflix`.`user_transaction` (
  `id` VARCHAR(50) NOT NULL,
  `customer_id` VARCHAR(50) NOT NULL,
  `transaction_type_id` VARCHAR(50) NOT NULL,
  `amount` DOUBLE NOT NULL DEFAULT 0.00,
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_user_transaction_transaction_type1_idx` (`transaction_type_id` ASC) VISIBLE,
  INDEX `fk_user_transaction_customer1_idx` (`customer_id` ASC) VISIBLE,
  INDEX `idx_user_transction_date` (`date` ASC) VISIBLE,
  CONSTRAINT `fk_user_transaction_transaction_type1`
    FOREIGN KEY (`transaction_type_id`)
    REFERENCES `aniflix`.`transaction_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_transaction_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `aniflix`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
