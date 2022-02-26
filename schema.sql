CREATE TABLE  `vibid`.`member` (
  `member_id` BIGINT NOT NULL AUTO_INCREMENT,
  `principal` VARCHAR(320) NOT NULL,
  `credential` VARCHAR(60) NOT NULL,
  `nickname` VARCHAR(20) NOT NULL,
  `regist_date` DATETIME NOT NULL,
  `is_email_authentication` TINYINT(1) NOT NULL,
  `is_lock` TINYINT(1) NOT NULL,
  `is_withdrawal` TINYINT(1) NOT NULL,
  `login_count` INT NOT NULL,
  `login_fail_count` INT NOT NULL,
  `profile_image_url` VARCHAR(255) NULL,
  `vendor` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`member_id`),
  INDEX `principal` (`principal` ASC))
 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

CREATE TABLE `vibid`.`board` (
  `board_id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NULL,
  `title` VARCHAR(255) NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `thumbnail_image_url` VARCHAR(255) NULL,
  `view_count` INT NOT NULL,
  `wish_count` INT NOT NULL,
  `regist_date` DATETIME NOT NULL,
  `is_lift_up` TINYINT(1) NOT NULL,
  `is_live` TINYINT(1) NOT NULL,
  PRIMARY KEY (`board_id`),
  INDEX `member_idx` (`member_id` ASC),
  CONSTRAINT `member`
    FOREIGN KEY (`member_id`)
    REFERENCES `vibid`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

CREATE TABLE `vibid`.`bid_info` (
  `bid_info_id` BIGINT NOT NULL AUTO_INCREMENT,
  `board_id` BIGINT NULL,
  `starting_price` BIGINT NOT NULL,
  `ending_price` BIGINT NULL,
  `bidding` INT NOT NULL,
  `bid_start_time` DATETIME NOT NULL,
  PRIMARY KEY (`bid_info_id`),
  INDEX `bid_info_board_idx` (`board_id` ASC),
  CONSTRAINT `bid_info_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `vibid`.`board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

ALTER TABLE `vibid`.`board` 
ADD COLUMN `bid_info_id` BIGINT NULL AFTER `member_id`,
ADD INDEX `board_bid_info_idx` (`bid_info_id` ASC);
;
ALTER TABLE `vibid`.`board` 
ADD CONSTRAINT `board_bid_info`
  FOREIGN KEY (`bid_info_id`)
  REFERENCES `vibid`.`bid_info` (`bid_info_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


CREATE TABLE `vibid`.`bid` (
  `bid_id` BIGINT NOT NULL AUTO_INCREMENT,
  `price` BIGINT NOT NULL,
  `regist_time` DATETIME NOT NULL,
  `board_id` BIGINT NULL,
  `member_id` BIGINT NULL,
  PRIMARY KEY (`bid_id`),
  INDEX `member_idx` (`member_id` ASC),
  INDEX `board_idx` (`board_id` ASC),
  CONSTRAINT `member_id`
    FOREIGN KEY (`member_id`)
    REFERENCES `vibid`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `board_id`
    FOREIGN KEY (`board_id`)
    REFERENCES `vibid`.`board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4 ;

CREATE TABLE `vibid`.`authority` (
  `authority_id` BIGINT NOT NULL AUTO_INCREMENT,
  `authority` VARCHAR(45) NOT NULL,
  `member_id` BIGINT NULL,
  PRIMARY KEY (`authority_id`),
  INDEX `authority_member_idx` (`member_id` ASC),
  CONSTRAINT `authority_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `vibid`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4 ;

CREATE TABLE `vibid`.`email_auth_token` (
  `email_auth_token` CHAR(36) NOT NULL,
  `member_id` BIGINT NULL,
  `create_date` DATETIME NOT NULL,
  `expiration` DATETIME NOT NULL,
  PRIMARY KEY (`email_auth_token`),
  INDEX `email_auth_member_idx` (`member_id` ASC),
  CONSTRAINT `email_auth_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `vibid`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4 ;

CREATE TABLE `vibid`.`password_auth_token` (
  `password_auth_token` CHAR(36) NOT NULL,
  `member_id` BIGINT NULL,
  `create_date` DATETIME NOT NULL,
  `expiration` DATETIME NOT NULL,
  PRIMARY KEY (`password_auth_token`),
  INDEX `password_auth_member_idx` (`member_id` ASC),
  CONSTRAINT `password_auth_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `vibid`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4 ;

CREATE TABLE `vibid`.`wish` (
  `wish_id` INT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NULL,
  `board_id` BIGINT NULL,
  PRIMARY KEY (`wish_id`),
  INDEX `wish_member_idx` (`member_id` ASC),
  INDEX `wish_board_idx` (`board_id` ASC),
  CONSTRAINT `wish_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `vibid`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `wish_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `vibid`.`board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4 ;

CREATE TABLE `vibid`.`notify` (
  `notify_id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NULL,
  `board_id` BIGINT NULL,
  `content` VARCHAR(255) NOT NULL,
  `is_read` BIT(1) NOT NULL,
  `regist_date` DATETIME NOT NULL,
  PRIMARY KEY (`notify_id`),
  INDEX `notify_member_idx` (`member_id` ASC),
  INDEX `notify_board_idx` (`board_id` ASC),
  CONSTRAINT `notify_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `vibid`.`member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `notify_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `vibid`.`board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4 ;

CREATE TABLE `vibid`.`content_image` (
  `content_image_id` BIGINT NOT NULL AUTO_INCREMENT,
  `board_id` BIGINT NULL,
  `image_url` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`content_image_id`),
  INDEX `content_image_board_idx` (`board_id` ASC),
  CONSTRAINT `content_image_board`
    FOREIGN KEY (`board_id`)
    REFERENCES `vibid`.`board` (`board_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE `vibid`.`hashtag` (
  `tag_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`tag_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE `vibid`.`board_hashtag` (
  `board_hashtag_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `board_id` bigint(20) NULL,
  `tag_id` bigint(20) NULL,
  PRIMARY KEY (`board_hashtag_id`),
  KEY `board_hashtag_board_idx` (`board_id`),
  KEY `board_hashtag_tag_idx` (`tag_id`),
  CONSTRAINT `board_hashtag_board` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `board_hashtag_tag` FOREIGN KEY (`tag_id`) REFERENCES `hashtag` (`tag_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `trade` (
  `trade_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trade_type` varchar(45) NOT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `board_id` bigint(20) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `is_confirm` bit(1) NOT NULL,
  `is_end` bit(1) NOT NULL,
  `trade_info_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`trade_id`),
  KEY `board_idx` (`board_id`),
  KEY `trade_member_idx` (`member_id`),
  KEY `trade_trade_info_idx` (`trade_info_id`),
  CONSTRAINT `trade_board` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `trade_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `trade_trade_info` FOREIGN KEY (`trade_info_id`) REFERENCES `trade_info` (`trade_info_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
  
CREATE TABLE `vibid`.`trade_info` (
  `trade_info_id` BIGINT NOT NULL AUTO_INCREMENT,
  `trade_id` BIGINT NULL,
  `name` VARCHAR(100) NULL,
  `address` VARCHAR(255) NULL,
  `zipcode` VARCHAR(10) NULL,
  `account` VARCHAR(100) NULL,
  `bank` VARCHAR(255) NULL,
  `contact` VARCHAR(255) NULL,
  `type` VARCHAR(10) NULL,
  PRIMARY KEY (`trade_info_id`),
  INDEX `trade_info_trade_idx` (`trade_id` ASC),
  CONSTRAINT `trade_info_trade`
    FOREIGN KEY (`trade_id`)
    REFERENCES `vibid`.`trade` (`trade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    
    ALTER TABLE `vibid`.`trade` 
ADD COLUMN `trade_info_id` BIGINT NULL AFTER `is_end`,
ADD INDEX `trade_member_idx` (`member_id` ASC),
ADD INDEX `trade_trade_info_idx` (`trade_info_id` ASC);
;
ALTER TABLE `vibid`.`trade` 
ADD CONSTRAINT `trade_member`
  FOREIGN KEY (`member_id`)
  REFERENCES `vibid`.`member` (`member_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `trade_trade_info`
  FOREIGN KEY (`trade_info_id`)
  REFERENCES `vibid`.`trade_info` (`trade_info_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


