set global max_connections = 250;
CREATE DATABASE `cloudb_dev` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `cloudb_dev`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 10.70.51.54    Database: cloudb_dev
-- ------------------------------------------------------
-- Server version	5.7.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `api_cp`
--

DROP TABLE IF EXISTS `api_cp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_cp` (
  `cpId` int(11) NOT NULL AUTO_INCREMENT,
  `cpName` varchar(45) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`cpId`),
  UNIQUE KEY `grpId_UNIQUE` (`cpId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_cp`
--

LOCK TABLES `api_cp` WRITE;
/*!40000 ALTER TABLE `api_cp` DISABLE KEYS */;
INSERT INTO `api_cp` VALUES (1,'Gemtek','2018-08-17 06:51:54',1,NULL,NULL);
/*!40000 ALTER TABLE `api_cp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_cp_mapping`
--

DROP TABLE IF EXISTS `api_cp_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_cp_mapping` (
  `acId` int(11) NOT NULL AUTO_INCREMENT,
  `cpId` int(11) NOT NULL,
  `grpId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`acId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_cp_mapping`
--

LOCK TABLES `api_cp_mapping` WRITE;
/*!40000 ALTER TABLE `api_cp_mapping` DISABLE KEYS */;
INSERT INTO `api_cp_mapping` VALUES (1,1,32,'2018-11-28 12:51:53',1,NULL,NULL);
/*!40000 ALTER TABLE `api_cp_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_device_info`
--

DROP TABLE IF EXISTS `api_device_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_device_info` (
  `deviceId` int(11) NOT NULL AUTO_INCREMENT,
  `device_mac` varchar(30) DEFAULT NULL,
  `device_name` varchar(30) DEFAULT NULL,
  `device_status` tinyint(4) NOT NULL,
  `device_type` varchar(30) DEFAULT NULL,
  `device_share` tinyint(4) NOT NULL,
  `device_active_time` datetime DEFAULT NULL,
  `device_bind_time` datetime DEFAULT NULL,
  `device_cp_id` int(11) DEFAULT NULL,
  `device_user_id` int(11) DEFAULT NULL,
  `device_IoT_org` varchar(6) DEFAULT NULL,
  `device_IoT_type` varchar(30) DEFAULT NULL,
  `device_IoT_secret` varchar(20) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`deviceId`),
  UNIQUE KEY `deviceId_UNIQUE` (`deviceId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `api_function`
--

DROP TABLE IF EXISTS `api_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_function` (
  `functionId` int(11) NOT NULL AUTO_INCREMENT,
  `functionName` varchar(45) DEFAULT NULL,
  `functionUrl` varchar(500) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `sortId` int(11) DEFAULT NULL,
  `hiddenFlg` varchar(1) DEFAULT NULL,
  `delFlg` tinyint(4) NOT NULL,
  `grpId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`functionId`),
  UNIQUE KEY `functionId_UNIQUE` (`functionId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_function`
--

LOCK TABLES `api_function` WRITE;
/*!40000 ALTER TABLE `api_function` DISABLE KEYS */;
INSERT INTO `api_function` VALUES (1,'DASHBOARD','/dashboard',-1,0,'N',0,15,'2018-08-17 06:51:57',1,NULL,NULL),(8,'ACTIVATE','/activate',0,1,'N',0,1,'2018-08-17 06:51:57',1,NULL,NULL),(15,'BINDING','/bind',0,2,'N',0,1,'2018-08-17 06:51:57',1,NULL,NULL),(22,'DEVICE','/device',-1,3,'N',0,22,'2018-08-17 06:51:57',1,NULL,NULL),(29,'USER','/admin/users',0,0,'N',0,29,'2018-08-17 06:51:57',1,'2019-03-08 06:27:57',1),(36,'ROLE','/admin/roles',0,1,'N',0,29,'2018-08-17 06:51:57',1,'2019-03-08 06:27:57',1),(43,'FUNCTION','/admin/functions',0,2,'N',0,29,'2018-08-17 06:51:58',1,'2019-03-08 06:27:57',1),(50,'CP','/admin/cps',0,4,'N',0,29,'2018-08-17 06:51:58',1,NULL,NULL),(57,'GRP','/admin/grps',0,3,'N',0,29,'2018-08-17 06:51:58',1,'2019-03-08 06:27:57',1),(58,'EVENT','/event',-1,1,'N',0,30,'2018-08-17 06:54:55',1,NULL,NULL),(59,'TAG','/tag',-1,1,'N',0,31,'2018-08-17 06:57:58',1,NULL,NULL),(83,'WIP即時生產狀況看板','http://mallbi.walsin.com/views/WIP20180730/WIP',-2,0,'N',1,56,'2019-03-06 07:09:46',1,'2019-03-08 05:54:32',1),(84,'OEE報表','http://mallbi.walsin.com/views/OEE/OEE_2',-2,1,'N',1,56,'2019-03-06 09:00:48',1,'2019-03-08 05:54:32',1),(85,'NOTIFY_DGRP','/notify/dgrps',0,0,'N',1,57,'2019-03-07 06:26:35',1,'2019-03-08 05:54:32',1),(87,'NOTIFY_USER','/notify/users',0,1,'N',1,57,'2019-03-07 06:28:29',1,'2019-03-08 05:54:32',1),(88,'NOTIFY_UGRP','/notify/ugrps',0,2,'N',1,57,'2019-03-07 06:28:40',1,'2019-03-08 05:54:32',1),(92,'PERSONNEL','/personnel',-1,0,'N',1,58,'2019-03-07 07:30:32',1,NULL,NULL),(93,'123','http://www.yahoo.com',-2,0,'N',1,56,'2019-03-07 11:44:13',1,NULL,NULL),(95,'ALARM','/alert',-1,0,'N',1,59,'2019-03-13 08:57:55',1,NULL,NULL);
/*!40000 ALTER TABLE `api_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_function_mapping`
--

DROP TABLE IF EXISTS `api_function_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_function_mapping` (
  `roleId` int(11) NOT NULL,
  `functionId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`roleId`,`functionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_function_mapping`
--

LOCK TABLES `api_function_mapping` WRITE;
/*!40000 ALTER TABLE `api_function_mapping` DISABLE KEYS */;
INSERT INTO `api_function_mapping` VALUES (1,1,'2018-11-29 01:55:11',1,'2019-03-13 09:02:17',1),(1,22,'2018-11-29 01:55:11',1,'2019-03-13 09:02:17',1),(1,29,'2018-11-29 01:55:11',1,'2019-03-13 09:02:17',1),(1,36,'2018-11-29 01:55:12',1,'2019-03-13 09:02:18',1),(1,43,'2018-11-29 01:55:12',1,'2019-03-13 09:02:18',1),(1,57,'2018-11-29 01:55:12',1,'2019-03-13 09:02:18',1),(1,58,'2018-11-29 01:55:12',1,'2019-03-13 09:02:18',1),(1,59,'2019-01-18 02:16:58',1,'2019-03-13 09:02:18',1),(1,83,'2019-03-07 09:25:42',1,'2019-03-13 09:02:18',1),(1,84,'2019-03-06 09:14:48',1,'2019-03-13 09:02:18',1),(1,85,'2019-03-07 06:31:07',1,'2019-03-13 09:02:18',1),(1,87,'2019-03-07 06:31:07',1,'2019-03-13 09:02:18',1),(1,88,'2019-03-07 06:31:07',1,'2019-03-13 09:02:18',1),(1,92,'2019-03-07 07:31:05',1,'2019-03-13 09:02:18',1),(1,95,'2019-03-13 09:02:18',1,NULL,NULL),(15,1,'2018-12-06 08:15:07',1,NULL,NULL),(15,22,'2018-08-17 06:52:29',1,'2018-12-06 08:15:07',1),(16,1,'2018-08-17 09:23:58',1,NULL,NULL),(16,22,'2018-08-17 09:24:24',1,NULL,NULL),(16,58,'2018-08-17 06:55:52',1,NULL,NULL),(17,1,'2018-08-17 09:24:38',1,NULL,NULL),(17,22,'2018-08-17 09:24:27',1,NULL,NULL),(17,58,'2018-08-17 06:55:53',1,NULL,NULL),(17,59,'2018-08-17 09:25:00',1,NULL,NULL);
/*!40000 ALTER TABLE `api_function_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_grp`
--

DROP TABLE IF EXISTS `api_grp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_grp` (
  `grpId` int(11) NOT NULL AUTO_INCREMENT,
  `grpName` varchar(45) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`grpId`),
  UNIQUE KEY `grpId_UNIQUE` (`grpId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_grp`
--

LOCK TABLES `api_grp` WRITE;
/*!40000 ALTER TABLE `api_grp` DISABLE KEYS */;
INSERT INTO `api_grp` VALUES (1,'LoRa','2018-08-17 06:51:54',1,NULL,NULL),(15,'Dashboard','2018-08-17 06:51:54',1,NULL,NULL),(22,'DEVICE','2018-08-17 06:51:54',1,NULL,NULL),(29,'ADMIN','2018-08-17 06:51:55',1,NULL,NULL),(30,'Event','2018-08-17 06:51:55',1,NULL,NULL),(31,'Tag','2018-08-17 06:51:55',1,NULL,NULL),(56,'walsinDept','2019-03-05 08:24:41',1,NULL,NULL),(57,'Notify','2019-03-07 06:23:31',1,'2019-03-07 06:41:03',1),(58,'Personnel','2019-03-07 07:29:29',1,NULL,NULL),(59,'ALARM','2019-03-13 08:53:54',1,NULL,NULL);
/*!40000 ALTER TABLE `api_grp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_login_history`
--

DROP TABLE IF EXISTS `api_login_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_login_history` (
  `historyId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `userToken` varchar(120) NOT NULL,
  `history_login_time` datetime NOT NULL,
  `history_logout_time` datetime DEFAULT NULL,
  `history_ip` varchar(20) DEFAULT NULL,
  `history_type` tinyint(4) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`historyId`),
  UNIQUE KEY `historyId_UNIQUE` (`historyId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `api_ra_mapping`
--

DROP TABLE IF EXISTS `api_ra_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_ra_mapping` (
  `raId` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) NOT NULL,
  `grpId` int(11) NOT NULL,
  `sortId` int(11) DEFAULT NULL,
  `createFlg` tinyint(4) NOT NULL,
  `updateFlg` tinyint(4) NOT NULL,
  `deleteFlg` tinyint(4) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`raId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_ra_mapping`
--

LOCK TABLES `api_ra_mapping` WRITE;
/*!40000 ALTER TABLE `api_ra_mapping` DISABLE KEYS */;
INSERT INTO `api_ra_mapping` VALUES (6,8,15,1,1,1,1,'2018-08-17 06:51:56',1,NULL,NULL),(7,8,1,2,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(8,8,22,3,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(9,8,29,4,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(10,15,15,1,1,1,1,'2018-08-17 06:51:57',1,'2019-03-08 06:27:33',1),(12,15,22,2,1,1,1,'2018-08-17 06:51:57',1,'2019-03-08 06:27:33',1),(13,16,15,1,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(14,16,22,2,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(15,16,30,3,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(16,17,15,1,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(17,17,22,2,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(18,17,30,3,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(19,17,31,3,1,1,1,'2018-08-17 06:51:57',1,NULL,NULL),(21,1,15,1,1,1,1,'2018-11-28 12:56:27',1,'2019-03-13 09:01:41',1),(22,1,22,2,1,1,1,'2018-11-28 12:56:27',1,'2019-03-13 09:01:41',1),(23,1,29,3,1,1,1,'2018-11-28 12:56:27',1,'2019-03-13 09:01:41',1),(25,18,29,1,1,1,1,'2018-11-29 07:25:46',1,NULL,NULL),(26,19,30,1,1,1,1,'2018-11-30 07:03:30',1,NULL,NULL),(27,19,31,2,1,1,1,'2018-11-30 07:03:30',1,NULL,NULL),(28,19,1,3,1,1,1,'2018-11-30 07:03:31',1,NULL,NULL),(29,19,22,4,1,1,1,'2018-11-30 07:03:31',1,NULL,NULL),(32,20,29,1,1,1,1,'2018-11-30 08:16:53',1,'2018-11-30 08:17:02',1),(33,20,30,2,1,1,1,'2018-11-30 08:17:02',1,NULL,NULL),(34,20,31,3,1,1,1,'2018-11-30 08:17:02',1,NULL,NULL),(45,1,31,5,1,1,1,'2019-03-05 08:41:17',1,'2019-03-13 09:01:41',1),(48,1,30,4,1,1,1,'2019-03-05 08:43:26',1,'2019-03-13 09:01:41',1),(58,1,56,6,1,1,1,'2019-03-07 03:26:28',1,'2019-03-13 09:01:41',1),(59,1,57,7,1,1,1,'2019-03-07 06:30:55',1,'2019-03-13 09:01:41',1),(60,1,58,8,1,1,1,'2019-03-07 07:30:58',1,'2019-03-13 09:01:41',1),(61,1,59,9,1,1,1,'2019-03-13 09:01:41',1,NULL,NULL);
/*!40000 ALTER TABLE `api_ra_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_role`
--

DROP TABLE IF EXISTS `api_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(45) DEFAULT NULL,
  `dataset` tinyint(4) NOT NULL,
  `editFlg` tinyint(4) NOT NULL,
  `delFlg` tinyint(4) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `roleId_UNIQUE` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_role`
--

LOCK TABLES `api_role` WRITE;
/*!40000 ALTER TABLE `api_role` DISABLE KEYS */;
INSERT INTO `api_role` VALUES (1,'superAdmin',0,0,0,'2018-08-17 06:51:55',1,NULL,NULL),(8,'generalAdmin',1,0,0,'2018-08-17 06:51:55',1,NULL,NULL),(15,'generalUser',2,0,0,'2018-08-17 06:51:56',1,NULL,NULL),(16,'demoIIoT',0,0,0,'2018-08-17 06:51:56',1,NULL,NULL),(17,'demoWalsin',0,0,0,'2018-08-17 06:51:56',1,NULL,NULL);
/*!40000 ALTER TABLE `api_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_system_properties`
--

DROP TABLE IF EXISTS `api_system_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_system_properties` (
  `p_name` varchar(30) NOT NULL,
  `p_value` varchar(2000) NOT NULL,
  `p_desc` varchar(1000) DEFAULT NULL,
  `p_type` varchar(10) NOT NULL DEFAULT 'ALL',
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`p_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_system_properties`
--

LOCK TABLES `api_system_properties` WRITE;
/*!40000 ALTER TABLE `api_system_properties` DISABLE KEYS */;
INSERT INTO `api_system_properties` VALUES ('ACC_FORMAT','^[a-zA-Z0-9._-s]{3,20}$','pwd check format','ALL','2018-08-17 06:51:54',1,NULL,NULL),('CERT_EXPIRE','600','device token expired time','ALL','2018-08-17 06:51:54',1,NULL,NULL),('DL_DELAY_TIME','1000','DL delay time by millisecond','ALL','2018-10-08 09:26:20',1,NULL,NULL),('EMAIL_FORMAT','^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z-0-9]+\\.)+[a-zA-Z]{2,}))$','email check format','ALL','2018-08-17 06:51:53',1,NULL,NULL),('NOTIFY_TOKEN','1674a1969e6a5665f2140db0f9369a76','notify-svc token','ALL','2019-03-19 03:06:22',1,NULL,NULL),('PWD_FORMAT','^[a-zA-Z0-9%+._@-s]{8,15}$','pwd check format','ALL','2018-08-17 06:51:54',1,NULL,NULL),('TOKEN_EXPIRE','86400','user token expired time','ALL','2018-08-17 06:51:54',1,NULL,NULL);
/*!40000 ALTER TABLE `api_system_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_user`
--

DROP TABLE IF EXISTS `api_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `cpId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  `userName` varchar(45) DEFAULT NULL,
  `userPwd` varchar(60) DEFAULT NULL,
  `nickName` varchar(45) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `deviceToken` varchar(80) DEFAULT NULL,
  `deviceType` tinyint(4) NOT NULL,
  `pic` varchar(60) DEFAULT NULL,
  `email` varchar(60) NOT NULL,
  `userBlock` tinyint(4) NOT NULL,
  `userType` tinyint(4) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId_UNIQUE` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_user`
--

LOCK TABLES `api_user` WRITE;
/*!40000 ALTER TABLE `api_user` DISABLE KEYS */;
INSERT INTO `api_user` VALUES (1,1,1,'sysAdmin','U2FsdGVkX1+i7l3qj1yfscFyNUof914vQUcwaA5bP3M=',NULL,NULL,NULL,0,'','sysAdmin@gemteks.com',0,0,'2018-08-17 06:51:55',1,'2019-01-21 10:13:36',1),(2,1,1,'demoIIoT','U2FsdGVkX1+i7l3qj1yfscFyNUof914vQUcwaA5bP3M=',NULL,NULL,NULL,0,'','demoIIoT@gemteks.com',0,0,'2018-08-17 06:51:55',1,'2019-01-21 11:20:33',1);
/*!40000 ALTER TABLE `api_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `api_user_mapping`
--

DROP TABLE IF EXISTS `api_user_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_user_mapping` (
  `mappingId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `locId` int(11) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` int(11) DEFAULT NULL,
  PRIMARY KEY (`mappingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_user_mapping`
--

LOCK TABLES `api_user_mapping` WRITE;
/*!40000 ALTER TABLE `api_user_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `api_user_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Table structure for table `api_user_social_mapping`
--

DROP TABLE IF EXISTS `api_user_social_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `api_user_social_mapping` (
  `mappingId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `socialType` varchar(30) NOT NULL,
  `socialId` varchar(45) NOT NULL,
  `createTime` datetime NOT NULL,
  `createUser` int(11) NOT NULL,
  PRIMARY KEY (`mappingId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `api_user_social_mapping` WRITE;
/*!40000 ALTER TABLE `api_user_social_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `api_user_social_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-01 14:05:53
-- set account to access db
CREATE USER 'admin'@'%' IDENTIFIED BY 'gemtek1234';
GRANT ALL PRIVILEGES ON cloudb_dev.* TO 'admin'@'%';

CREATE DATABASE `notification_service_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `notification_service_db`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: notification_service_db
-- ------------------------------------------------------
-- Server version	5.7.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `event_serial_data`
--

DROP TABLE IF EXISTS `event_serial_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_serial_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `app_id` varchar(128) DEFAULT NULL COMMENT '应用平台id',
  `notification_group` varchar(20000) DEFAULT NULL COMMENT '通报组名称,json格式，包含通报对象的方式和通报账号信息',
  `terminal_group` varchar(255) DEFAULT NULL COMMENT '终端组名称',
  `mac` varchar(16) DEFAULT NULL COMMENT '终端mac地址',
  `description` varchar(1000) DEFAULT NULL COMMENT '消息内容描述信息',
  `extra` varchar(255) DEFAULT NULL COMMENT '保留字段',
  `recv` datetime DEFAULT NULL COMMENT '来自应用系统的事件时间，即告警时间',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='告警事件流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_serial_data`
--

LOCK TABLES `event_serial_data` WRITE;
/*!40000 ALTER TABLE `event_serial_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_serial_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_object_relation`
--

DROP TABLE IF EXISTS `group_object_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_object_relation` (
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `notification_group_id` bigint(20) NOT NULL COMMENT '通报组id',
  `notification_object_id` bigint(20) NOT NULL COMMENT '通报对象id',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`notification_group_id`,`notification_object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通报组与通报对象关系表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `notification_data`
--

DROP TABLE IF EXISTS `notification_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `app_id` varchar(128) DEFAULT NULL COMMENT '应用平台id',
  `notification_group` varchar(20000) DEFAULT NULL COMMENT '通报组名称,json格式，包含通报对象的方式和通报账号信息',
  `terminal_group` varchar(255) DEFAULT NULL COMMENT '终端组名称',
  `mac` varchar(16) DEFAULT NULL COMMENT '终端mac地址',
  `description` varchar(1000) DEFAULT NULL COMMENT '消息内容描述信息',
  `extra` varchar(255) DEFAULT NULL COMMENT '保留字段',
  `recv` datetime DEFAULT NULL COMMENT '来自应用系统的事件时间，即告警时间',
  `delay` bigint(20) DEFAULT NULL COMMENT '发报延时',
  `delivery_time` datetime DEFAULT NULL COMMENT '发报时间',
  `delivery_sucess` tinyint(4) DEFAULT NULL COMMENT '发报是否成功',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通报消息记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_data`
--

LOCK TABLES `notification_data` WRITE;
/*!40000 ALTER TABLE `notification_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_group`
--

DROP TABLE IF EXISTS `notification_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `group_name` varchar(128) DEFAULT NULL COMMENT '通报组名称',
  `delay` bigint(20) DEFAULT NULL COMMENT '发报延时',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通报组表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `notification_method`
--

DROP TABLE IF EXISTS `notification_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_method` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `method` varchar(64) DEFAULT NULL COMMENT '通报方式',
  `remark` varchar(64) DEFAULT NULL COMMENT '通报方式备注信息',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通报方式表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_method`
--

LOCK TABLES `notification_method` WRITE;
/*!40000 ALTER TABLE `notification_method` DISABLE KEYS */;
INSERT INTO `notification_method` VALUES (1,NULL,'eMail',NULL,'2018-02-07 16:31:41',NULL),(2,NULL,'WeChat',NULL,'2018-02-07 16:31:59',NULL),(3,NULL,'LINE',NULL,'2018-02-07 16:31:59',NULL),(4,NULL,'APP',NULL,'2018-02-07 16:31:59',NULL);
/*!40000 ALTER TABLE `notification_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_object`
--

DROP TABLE IF EXISTS `notification_object`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_object` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `object_name` varchar(64) DEFAULT NULL COMMENT '通报对象名称',
  `remark` varchar(64) DEFAULT NULL COMMENT '通报对象备注信息',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通报对象表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `notification_terminal_relation`
--

DROP TABLE IF EXISTS `notification_terminal_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification_terminal_relation` (
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `notification_group_id` bigint(20) NOT NULL COMMENT '通报组id',
  `terminal_group_id` bigint(20) NOT NULL COMMENT '终端组id',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`notification_group_id`,`terminal_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通报组与终端组关系表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `object_method_relation`
--

DROP TABLE IF EXISTS `object_method_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `object_method_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `notification_object_id` bigint(20) DEFAULT NULL COMMENT '通报对象id',
  `notification_method_id` bigint(20) DEFAULT NULL COMMENT '通报方式id',
  `notification_method` varchar(64) DEFAULT NULL COMMENT '通报方式',
  `notification_account` varchar(64) DEFAULT NULL COMMENT '通报账号',
  `notification_enabled` tinyint(4) DEFAULT NULL COMMENT '通报方式是否启用',
  `remark` varchar(64) DEFAULT NULL COMMENT '通报对象与通报方式关系备注信息',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='通报对象与通报方式关系表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `role_name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(64) DEFAULT NULL COMMENT '角色备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT=' 角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin',NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_relation`
--

DROP TABLE IF EXISTS `session_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session_relation` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `client_session` varchar(128) DEFAULT NULL COMMENT '客户端session信息',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户与客户端会话关系表，暂未启用';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_relation`
--

LOCK TABLES `session_relation` WRITE;
/*!40000 ALTER TABLE `session_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `session_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terminal`
--

DROP TABLE IF EXISTS `terminal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `terminal_group_id` bigint(20) DEFAULT NULL COMMENT '终端组id',
  `mac` varchar(50) DEFAULT NULL COMMENT '终端mac地址',
  `remark` varchar(32) DEFAULT NULL COMMENT '终端备注信息',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mac` (`mac`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='终端表';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `terminal_group`
--

DROP TABLE IF EXISTS `terminal_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terminal_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `group_name` varchar(128) DEFAULT NULL COMMENT '终端组名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '终端组备注信息',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='终端组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库主键',
  `business_id` varchar(128) DEFAULT NULL COMMENT '应用系统平台id',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `pwd` varchar(32) DEFAULT NULL COMMENT '密码',
  `token` varchar(128) DEFAULT NULL COMMENT 'token令牌信息',
  `remark` varchar(64) DEFAULT NULL COMMENT '用户备注信息',
  `in_time` datetime DEFAULT NULL COMMENT '入库时间',
  `up_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,'admin','gemtek2018','1674a1969e6a5665f2140db0f9369a76','','2018-03-09 10:15:07','2018-02-28 11:13:44');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_relation`
--

DROP TABLE IF EXISTS `user_role_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_relation` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户与角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_relation`
--

LOCK TABLES `user_role_relation` WRITE;
/*!40000 ALTER TABLE `user_role_relation` DISABLE KEYS */;
INSERT INTO `user_role_relation` VALUES (1,1);
/*!40000 ALTER TABLE `user_role_relation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-01 14:24:44
-- set account to access db

CREATE USER 'jdfh_db'@'%' IDENTIFIED BY 'jdfhdb@2018';
GRANT ALL PRIVILEGES ON notification_service_db.* TO 'jdfh_db'@'%';
