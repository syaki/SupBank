/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.0.87-community-nt : Database - supbank
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`supbank` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `supbank`;

/*Table structure for table `td_block` */

DROP TABLE IF EXISTS `td_block`;

CREATE TABLE `td_block` (
  `blockid` varchar(255) NOT NULL COMMENT '主键',
  `hash` varchar(255) default NULL COMMENT '本区块的哈希值',
  `height` int(11) default NULL COMMENT '区块高度',
  `prehash` varchar(255) default NULL COMMENT '上一区块的哈希值',
  `merkleroothash` varchar(255) default NULL COMMENT '本区块的MerkleRoot',
  `transactionnumber` int(11) default NULL COMMENT '区块储存的交易数量',
  `nonce` int(11) default NULL COMMENT '随机数',
  `blockreward` double default NULL COMMENT '区块奖励',
  `age` int(11) default NULL COMMENT '从出块到现在的存在时间',
  `miner` varchar(255) default NULL COMMENT '来自矿工的名字',
  `size` double default NULL COMMENT '区块大小',
  `timestamp` timestamp NULL default NULL,
  `islegal` int(11) default NULL COMMENT '区块是否合法',
  `flag` int(11) default '1' COMMENT '状态 0：不可用 1：可用',
  PRIMARY KEY  (`blockid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `td_block` */

insert  into `td_block`(`blockid`,`hash`,`height`,`prehash`,`merkleroothash`,`transactionnumber`,`nonce`,`blockreward`,`age`,`miner`,`size`,`timestamp`,`islegal`,`flag`) values 
('123456','asdf',1,NULL,'qwe',1,56,2,23,'kong',20,'2019-05-11 17:13:59',1,1),
('2222222','zczczcv',3,'wqerqte','zv',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1),
('789999','wqerqte',2,'asdf','(NULL)xzcc',3,234,2,34,'weijia',34,'2019-05-11 21:13:00',1,1);

/*Table structure for table `td_transaction` */

DROP TABLE IF EXISTS `td_transaction`;

CREATE TABLE `td_transaction` (
  `transactionid` varchar(255) NOT NULL COMMENT '主键',
  `input` varchar(255) default NULL COMMENT '花钱方',
  `output` varchar(255) default NULL COMMENT '收钱方',
  `sum` double default NULL COMMENT '交易金额',
  `signature` varchar(255) default NULL COMMENT '签名',
  `blockid` varchar(255) default NULL COMMENT '所在区块id',
  `timestamp` timestamp NULL default NULL,
  `status` int(11) default NULL COMMENT '1:未验证  2:已验证',
  `flag` int(11) default '1' COMMENT '状态 0：不可用 1：可用',
  PRIMARY KEY  (`transactionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `td_transaction` */

insert  into `td_transaction`(`transactionid`,`input`,`output`,`sum`,`signature`,`blockid`,`timestamp`,`status`,`flag`) values 
('1904271708530001','2187e984a6c38cc35830e139e9ac2ba29ab32476','56aa8b2fd3c2109f598ae37ccc3dd19173d60497',10,'EOYVo0GEOtAXyFz9HmUEYbZ3UBAAhnnp4DBBqT9BI4RTFKFFjqgPRCUfd8dwuzzyRzfwfzTV1r2f\n2Fd1YS4REFyUib6n+H4crYtasqL6zaIsw14hXNDwUqDWFXzBwjNV2dAP4X7DpZo9PLN2Ayo2YpZb\nLGE+o0dkXroGsH5E5fY=','ere','2019-05-11 20:59:48',1,1),
('1905112051190001','sdfassdf','weereter',30,'qianming_dfsdf','dfa','2019-05-11 20:59:52',1,1),
('4561215615613213','2187e984a6c38cc35830e139e9ac2ba29ab32476','56aa8b2fd3c2109f598ae37ccc3dd19173d60497',20,'(NULL)EOYVo0GEOtAXyFz9HmUEYbZ3UBAAhnnp4DBBqT9BI4RTFKFFjqgPRCUfd8dwuzzyRzfwfzTV1r2f\n2Fd1YS4REFyUib6n+H4crYtasqL6zaIsw14hXNDwUqDWFXzBwjNV2dAP4X7DpZo9PLN2Ayo2YpZb\nLGE+o0dkXroGsH5E5fY=','(NULsdfaL)','2019-05-11 21:00:02',1,1);

/*Table structure for table `td_user` */

DROP TABLE IF EXISTS `td_user`;

CREATE TABLE `td_user` (
  `userid` varchar(255) NOT NULL COMMENT '主键，唯一标识',
  `username` varchar(255) default NULL COMMENT '用户名',
  `password` varchar(255) default NULL COMMENT '密码',
  `firstname` varchar(255) default NULL,
  `lastname` varchar(255) default NULL,
  `sex` varchar(20) default NULL,
  `email` varchar(255) default NULL,
  `phonenumber` varchar(30) default NULL,
  `timestamp` timestamp NULL default NULL,
  `flag` int(11) default '1',
  PRIMARY KEY  (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `td_user` */

insert  into `td_user`(`userid`,`username`,`password`,`firstname`,`lastname`,`sex`,`email`,`phonenumber`,`timestamp`,`flag`) values 
('1904211506550002','kwjia','5aa5ef5a12f017ea68d96800e6c0e23b',NULL,NULL,NULL,'1138500436@qq.com',NULL,NULL,1),
('1904211845090003','J','5aa5ef5a12f017ea68d96800e6c0e23b',NULL,NULL,NULL,'302589689@qq.com',NULL,NULL,1),
('1905062024480002','admin','5aa5ef5a12f017ea68d96800e6c0e23b',NULL,NULL,NULL,'8965500436@qq.com',NULL,NULL,1),
('1905112057540004','Jia','5aa5ef5a12f017ea68d96800e6c0e23b',NULL,NULL,NULL,'113850036@qq.com',NULL,NULL,1);

/*Table structure for table `td_wallet` */

DROP TABLE IF EXISTS `td_wallet`;

CREATE TABLE `td_wallet` (
  `walletid` varchar(255) NOT NULL COMMENT '主键',
  `address` varchar(255) default NULL COMMENT '地址',
  `publicKey` varchar(255) default NULL COMMENT '公钥',
  `password` varchar(255) default NULL COMMENT '每次请求用到的密码',
  `balance` double default NULL COMMENT '余额',
  `flag` int(11) default '1',
  PRIMARY KEY  (`walletid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `td_wallet` */

insert  into `td_wallet`(`walletid`,`address`,`publicKey`,`password`,`balance`,`flag`) values 
('1904211506530001','2187e984a6c38cc35830e139e9ac2ba29ab32476','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd0ko/baDps8o1uyd5bL0JTFmyCg/+OfkHEQBP\nakZKabfUYfgR2AQOm25+JLAAv27DRZrbucpCDoKH/YHeSE8vTT6hNZTHW+nN3M0/QKcK6xP8NZDs\n0BbTytzixBn/P/Pqy0wdG6av3xi1/i9G5v1XneoYeATUwXG0q1SnWEA1OQIDAQAB',NULL,30,1),
('1904211845090004','56aa8b2fd3c2109f598ae37ccc3dd19173d60497','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCif/rqUFvYUxqhdw8mxOSrdmGeGC+YI8QgDcRi\nSRF0EwTSEngZEp33syPSOhW5oNhcIBAup7Qb9VSz4eLgZdzNdP8P3q69H7+jSdJjN/M7P1vgYpI9\n6nti+ltJT1V2MXJiHejqQtP8uH7WBS2JGUDDdM5cr+QfxsBPB4Cr4zvJnwIDAQAB',NULL,45,1),
('1905062024480003','2876c0a31138ea3029b9aa4b7ae977ee4fb02bf5','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbdj69Ikfqlp8jLfavG2G3s4AuTX/Ajn62iw3Nvb1Um7KfNJ7fjzaBSQOBscg7eGBj6coO6X/6xYTdyv74IWpF9Itn/1/gRmNpj+DeiAz/lYLBoZWQcdfNNgLiTtpwfjSqtGT70j1Y9l+2FgRKMGJ0XVHOrsuXNzaDX+UadWTRIQIDAQAB',NULL,0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
