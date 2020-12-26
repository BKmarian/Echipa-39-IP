DROP DATABASE  `proiectip`;
CREATE DATABASE `proiectip`;
USE `proiectip`;

DROP TABLE IF EXISTS `Person`;
DROP TABLE IF EXISTS `Ong`;
DROP TABLE IF EXISTS `Event`;
DROP TABLE IF EXISTS `User2event`;

CREATE TABLE `user`(
                       `id` int(11) NOT NULL AUTO_INCREMENT,
                       `user_type` varchar(50) NOT NULL,
                       `username` varchar(512) CHARACTER SET utf8,
                       `password` varchar(512) CHARACTER SET utf8,
                       `email` varchar(512) CHARACTER SET utf8,
                       `isadmin` boolean DEFAULT FALSE,
                       `full_name` varchar(512) CHARACTER SET utf8,
                       `phone` varchar(16) CHARACTER SET utf8,
                       `approved` boolean DEFAULT FALSE,
                       `picture` varchar(512) ,
                       `job` varchar(100) CHARACTER SET utf8,
                       `age` INTEGER DEFAULT 0,
                       `contact` varchar(160) CHARACTER SET utf8,
                       `fiscalcode` varchar(16) CHARACTER SET utf8,
                       PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `event` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `ong_id` int(11) NOT NULL,
                         `name` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
                         `date` DATE NOT NULL,
                         `location` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
                         `description` varchar(512) CHARACTER SET utf8 DEFAULT NULL,
                         `approved` boolean default false,
                         PRIMARY KEY (`id`),
                         FOREIGN KEY `fk_3` (ong_id) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `user2event` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `event_id` int(11) NOT NULL,
                              `user_id` int(11) NOT NULL,
                              PRIMARY KEY (`id`),
                              FOREIGN KEY `fk1` (event_id) REFERENCES `event`(`id`) ON DELETE CASCADE,
                              FOREIGN KEY `fk2` (user_id) REFERENCES `user`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- INSERT INTO `user`(`username`,`password`,`full_name`,`email`,`isadmin`,`user_type`) VALUES ('admin','admin','marian sichitiu','ms@yahoo.com',true,'person');
-- INSERT INTO `user`(`username`,`password`,`full_name`,`email`,`user_type`) VALUES ('marian','parola','marian sichitiu','sichitium@yahoo.com','person');
-- INSERT INTO `user`(`full_name`,`username`,`password`,`email`,`user_type`) values ('ong','ong','parola','sichitiu.marian@yahoo.com','ong');

-- select * from `user`;
-- delete from `user`;
-- INSERT INTO `event`(location,description,name,ong_id,`date`) values ('bucuresti','dans popular','dans popular',3,str_to_date('03/02/2009','%d/%m/%Y'));
-- INSERT INTO `event`(location,description,name,ong_id,`date`) values ('bucuresti','dans modern','dans modern',3,str_to_date('03/02/2009','%d/%m/%Y'));
-- INSERT INTO `event`(location,description,name,ong_id,`date`) values ('bucuresti','dans traditional','dans traditional',3,str_to_date('03/02/2009','%d/%m/%Y'));
-- INSERT INTO `user2event`(event_id,user_id) values (1,1);
