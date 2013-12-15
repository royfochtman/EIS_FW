SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `musicbox` ;
CREATE SCHEMA IF NOT EXISTS `musicbox` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `musicbox` ;

-- -----------------------------------------------------
-- Table `musicbox`.`music_room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicbox`.`music_room` ;

CREATE TABLE IF NOT EXISTS `musicbox`.`music_room` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicbox`.`working_area`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicbox`.`working_area` ;

CREATE TABLE IF NOT EXISTS `musicbox`.`working_area` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `music_room_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `tempo` INT NOT NULL,
  `owner` VARCHAR(45) NOT NULL,
  `type` ENUM('private', 'public') NOT NULL,
  `beat` FLOAT NOT NULL,
  `length` MEDIUMTEXT NOT NULL COMMENT 'in Milliseconds',
  PRIMARY KEY (`id`),
  INDEX `MusicRoom_ID_idx` (`music_room_id` ASC),
  CONSTRAINT `MusicRoom_WorkingArea`
    FOREIGN KEY (`music_room_id`)
    REFERENCES `musicbox`.`music_room` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicbox`.`music_segment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicbox`.`music_segment` ;

CREATE TABLE IF NOT EXISTS `musicbox`.`music_segment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `music_room_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `instrument` ENUM('Guitar', 'Electric Guitar', 'Keyboard', 'Bass Guitar', 'Drums', 'Piano' ) NOT NULL,
  `owner` VARCHAR(45) NOT NULL,
  `audio_path` VARCHAR(100) NOT NULL,
  `length` MEDIUMTEXT NOT NULL COMMENT 'in Milliseconds',
  PRIMARY KEY (`id`),
  INDEX `MusicRoom_ID_idx` (`music_room_id` ASC),
  CONSTRAINT `MusicRoom_MusicSegment`
    FOREIGN KEY (`music_room_id`)
    REFERENCES `musicbox`.`music_room` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicbox`.`variation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicbox`.`variation` ;

CREATE TABLE IF NOT EXISTS `musicbox`.`variation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `music_segment_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `start_time` MEDIUMTEXT NOT NULL COMMENT 'in Milliseconds',
  `end_time` MEDIUMTEXT NOT NULL COMMENT 'in Milliseconds',
  `owner` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `MusicSegment_ID_idx` (`music_segment_id` ASC),
  CONSTRAINT `MusicSegment_Variation`
    FOREIGN KEY (`music_segment_id`)
    REFERENCES `musicbox`.`music_segment` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicbox`.`track`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicbox`.`track` ;

CREATE TABLE IF NOT EXISTS `musicbox`.`track` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `working_area_id` INT NOT NULL,
  `instrument` ENUM('Guitar', 'Electric Guitar', 'Keyboard', 'Bass Guitar', 'Drums', 'Piano' ) NOT NULL,
  `volume` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `length` MEDIUMTEXT NOT NULL COMMENT 'in Milliseconds',
  PRIMARY KEY (`id`),
  INDEX `WorkingArea_ID_idx` (`working_area_id` ASC),
  CONSTRAINT `WorkingArea_Track`
    FOREIGN KEY (`working_area_id`)
    REFERENCES `musicbox`.`working_area` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `musicbox`.`variation_track`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `musicbox`.`variation_track` ;

CREATE TABLE IF NOT EXISTS `musicbox`.`variation_track` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `variation_id` INT NOT NULL,
  `track_id` INT NOT NULL,
  `start_time` MEDIUMTEXT NOT NULL COMMENT 'in Milliseconds',
  PRIMARY KEY (`id`),
  INDEX `Variation_ID_idx` (`variation_id` ASC),
  INDEX `Track_ID_idx` (`track_id` ASC),
  CONSTRAINT `Variation_VariationTrack`
    FOREIGN KEY (`variation_id`)
    REFERENCES `musicbox`.`variation` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `Track_VariationTrack`
    FOREIGN KEY (`track_id`)
    REFERENCES `musicbox`.`track` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
