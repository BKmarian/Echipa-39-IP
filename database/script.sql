DROP DATABASE  `proiectip`;
CREATE DATABASE `proiectip`;
USE `proiectip`;

DROP TABLE IF EXISTS `Person`;
DROP TABLE IF EXISTS `Ong`;
DROP TABLE IF EXISTS `Event`;
DROP TABLE IF EXISTS `User2event`;

CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(512) CHARACTER SET utf8 NOT NULL,
  `isadmin` boolean DEFAULT FALSE,
  `password` varchar(512) CHARACTER SET utf8 NOT NULL,
  `email` varchar(512) CHARACTER SET utf8 NOT NULL,
  `full_name` varchar(512) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(16) CHARACTER SET utf8 DEFAULT NULL,
  `approved` boolean DEFAULT false,
  `picture` varchar(512) DEFAULT NULL,
  `age` INTEGER DEFAULT 0,
  `job` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `ong` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(512) CHARACTER SET utf8 NOT NULL,
  `password` varchar(512) CHARACTER SET utf8 NOT NULL,
  `email` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
  `full_name` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
  `phone` varchar(16) CHARACTER SET utf8 DEFAULT NULL,
  `approved` boolean DEFAULT false,
  `picture` varchar(512) DEFAULT NULL,
  `age` INTEGER DEFAULT 0,
  `contact` varchar(160) CHARACTER SET utf8 DEFAULT NULL,
  `fiscalcode` varchar(16) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ong_id` int(11) NOT NULL,
  `name` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
  `date` DATE NOT NULL,
  `location` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
  `description` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
  `approved` varchar(16) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY `fk_3` (ong_id) REFERENCES `ong`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `user2event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY `fk1` (event_id) REFERENCES `event`(`id`) ON DELETE CASCADE,
  FOREIGN KEY `fk2` (user_id) REFERENCES `person`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

SET FOREIGN_KEY_CHECKS=0;
INSERT INTO `person`(`username`,`password`,`full_name`,`email`) VALUES ('marian','parola','marian sichitiu','sichitium@yahoo.com');
INSERT INTO `ong`(`full_name`,`username`,`password`) values ('ong','ong','parola');
INSERT INTO `event`(location,description,name,ong_id,`date`) values ('bucuresti','dans popular','dans popular',1,str_to_date('03/02/2009','%d/%m/%Y'));
INSERT INTO `event`(location,description,name,ong_id,`date`) values ('bucuresti','dans modern','dans modern',1,str_to_date('03/02/2009','%d/%m/%Y'));
INSERT INTO `event`(location,description,name,ong_id,`date`) values ('bucuresti','dans traditional','dans traditional',1,str_to_date('03/02/2009','%d/%m/%Y'));
INSERT INTO `user2event`(event_id,user_id) values (1,1);
INSERT INTO `person`(`username`,`password`,`full_name`,`email`,`isadmin`) VALUES ('admin','admin','marian sichitiu','ms@yahoo.com',true);