SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `musicBoxDB`.`Musicroom`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicBoxDB`.`Musicroom` (
  `M_ID` INT NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`M_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicBoxDB`.`WorkingArea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicBoxDB`.`WorkingArea` (
  `WA_ID` INT NOT NULL,
  `M_ID` INT NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Tempo` INT NOT NULL,
  `Owner` VARCHAR(45) NULL,
  `Type` VARCHAR(45) NOT NULL,
  `Beat` FLOAT NOT NULL,
  `Length` MEDIUMTEXT NOT NULL COMMENT 'in Milliseconds',
  PRIMARY KEY (`WA_ID`),
  INDEX `MusicRoom_ID_idx` (`M_ID` ASC),
  CONSTRAINT `MusicRoom_ID`
    FOREIGN KEY (`M_ID`)
    REFERENCES `musicBoxDB`.`Musicroom` (`M_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicBoxDB`.`MusicSegment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicBoxDB`.`MusicSegment` (
  `MS_ID` INT NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Instrument` VARCHAR(45) NOT NULL,
  `Owner` VARCHAR(45) NOT NULL,
  `AudioPath` VARCHAR(100) NOT NULL,
  `Length` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`MS_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicBoxDB`.`Varation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicBoxDB`.`Varation` (
  `V_ID` INT NOT NULL,
  `MS_ID` INT NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `StartTime` MEDIUMTEXT NOT NULL,
  `EndTime` MEDIUMTEXT NOT NULL,
  `Owner` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`V_ID`),
  INDEX `MusicSegment_ID_idx` (`MS_ID` ASC),
  CONSTRAINT `MusicSegment_ID`
    FOREIGN KEY (`MS_ID`)
    REFERENCES `musicBoxDB`.`MusicSegment` (`MS_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicBoxDB`.`Track`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicBoxDB`.`Track` (
  `T_ID` INT NOT NULL,
  `WA_ID` INT NOT NULL,
  `Instrument` VARCHAR(45) NOT NULL,
  `Volume` INT NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Length` MEDIUMTEXT NULL,
  PRIMARY KEY (`T_ID`),
  INDEX `WorkingArea_ID_idx` (`WA_ID` ASC),
  CONSTRAINT `WorkingArea_ID`
    FOREIGN KEY (`WA_ID`)
    REFERENCES `musicBoxDB`.`WorkingArea` (`WA_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicBoxDB`.`Variation/Track`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `musicBoxDB`.`Variation/Track` (
  `VT_ID` VARCHAR(45) NOT NULL,
  `V_ID` INT NOT NULL,
  `T_ID` INT NOT NULL,
  `StartTime` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`VT_ID`),
  INDEX `Variation_ID_idx` (`V_ID` ASC),
  INDEX `Track_ID_idx` (`T_ID` ASC),
  CONSTRAINT `Variation_ID`
    FOREIGN KEY (`V_ID`)
    REFERENCES `musicBoxDB`.`Varation` (`V_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Track_ID`
    FOREIGN KEY (`T_ID`)
    REFERENCES `musicBoxDB`.`Track` (`T_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
