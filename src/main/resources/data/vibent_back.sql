-- CREATE DATABASE  IF NOT EXISTS `vibent_back` /*!40100 DEFAULT CHARACTER SET utf8 */;
-- USE `vibent_back`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: vibent_back
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `alimentation_bring`

DROP TABLE IF EXISTS `alimentation_bring`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alimentation_bring` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entry_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `alimentation_bring_alimentry__fk` (`entry_id`),
  CONSTRAINT `alimentation_bring_alimentry__fk` FOREIGN KEY (`entry_id`) REFERENCES `alimentation_entry` (`id`),
  CONSTRAINT `alimentation_bring_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alimentation_bring`
--

LOCK TABLES `alimentation_bring` WRITE;
/*!40000 ALTER TABLE `alimentation_bring` DISABLE KEYS */;
/*!40000 ALTER TABLE `alimentation_bring` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alimentation_bubble`
--

DROP TABLE IF EXISTS `alimentation_bubble`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alimentation_bubble` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `alimentation_bubble_event__fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `alimentation_bubble_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alimentation_bubble`
--

LOCK TABLES `alimentation_bubble` WRITE;
/*!40000 ALTER TABLE `alimentation_bubble` DISABLE KEYS */;
/*!40000 ALTER TABLE `alimentation_bubble` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alimentation_entry`
--

DROP TABLE IF EXISTS `alimentation_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alimentation_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bubble_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `total_requested` int(11) DEFAULT NULL,
  `type` enum('FOOD','DRINK','OTHER') DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `alimentation_entry_id` (`id`),
  KEY `alimentation_entry_bubble__fk` (`bubble_id`),
  CONSTRAINT `alimentation_entry_bubble__fk` FOREIGN KEY (`bubble_id`) REFERENCES `alimentation_bubble` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alimentation_entry`
--

LOCK TABLES `alimentation_entry` WRITE;
/*!40000 ALTER TABLE `alimentation_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `alimentation_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkbox_option`
--

DROP TABLE IF EXISTS `checkbox_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkbox_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bubble_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `checkbox_option_id` (`id`),
  KEY `user_id` (`user_id`),
  KEY `checkbox_option_bubble__fk` (`bubble_id`),
  CONSTRAINT `checkbox_option_bubble__fk` FOREIGN KEY (`bubble_id`) REFERENCES `checkbox_bubble` (`id`),
  CONSTRAINT `checkbox_option_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkbox_option`
--

LOCK TABLES `checkbox_option` WRITE;
/*!40000 ALTER TABLE `checkbox_option` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkbox_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkbox_bubble`
--

DROP TABLE IF EXISTS `checkbox_bubble`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkbox_bubble` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `checkbox_bubble_id` (`id`),
  CONSTRAINT `checkbox_bubble_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `checkbox_bubble_event__fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkbox_bubble`
--

LOCK TABLES `checkbox_bubble` WRITE;
/*!40000 ALTER TABLE `checkbox_bubble` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkbox_bubble` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ref` char(36) NOT NULL,
  `group_id` int(11) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `event_id` (`id`),
  UNIQUE KEY `event_ref` (`ref`),
  KEY `ref` (`ref`),
  KEY `event_group__fk` (`group_id`),
  CONSTRAINT `event_group__fk` FOREIGN KEY (`group_id`) REFERENCES `group_t` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_participation`
--

DROP TABLE IF EXISTS `event_participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_participation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `answer` enum('YES','NO','MAYBE','UNANSWERED') DEFAULT 'UNANSWERED',
  `is_visible` tinyint(1) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `event_id` (`event_id`),
  CONSTRAINT `event_participation_event__fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `event_participation_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_participation`
--

LOCK TABLES `event_participation` WRITE;
/*!40000 ALTER TABLE `event_participation` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `free_bubble`
--

DROP TABLE IF EXISTS `free_bubble`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `free_bubble` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `free_bubble_id` (`id`),
  CONSTRAINT `free_bubble_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `free_bubble_event__fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `free_bubble`
--

LOCK TABLES `free_bubble` WRITE;
/*!40000 ALTER TABLE `free_bubble` DISABLE KEYS */;
/*!40000 ALTER TABLE `free_bubble` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_t`
--

DROP TABLE IF EXISTS `group_t`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ref` char(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `has_default_admin` tinyint(1) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_ref` (`ref`),
  KEY `ref` (`ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_t`
--

LOCK TABLES `group_t` WRITE;
/*!40000 ALTER TABLE `group_t` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_t` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `membership`
--

DROP TABLE IF EXISTS `membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `membership` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `deleted` tinyint(1) DEFAULT 0,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `membership_group__fk` FOREIGN KEY (`group_id`) REFERENCES `group_t` (`id`),
  CONSTRAINT `membership_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membership`
--

LOCK TABLES `membership` WRITE;
/*!40000 ALTER TABLE `membership` DISABLE KEYS */;
/*!40000 ALTER TABLE `membership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `membership_request`
--

DROP TABLE IF EXISTS `membership_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `membership_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `deleted` tinyint(1) DEFAULT 0,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `membership_request_group__fk` FOREIGN KEY (`group_id`) REFERENCES `group_t` (`id`),
  CONSTRAINT `membership_request_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membership_request`
--

LOCK TABLES `membership_request` WRITE;
/*!40000 ALTER TABLE `membership_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `membership_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_bubble`
--

DROP TABLE IF EXISTS `location_bubble`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_bubble` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coord` varchar(255) DEFAULT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `location_bubble_id` (`id`),
  CONSTRAINT `location_bubble_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `location_bubble_event__fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_bubble`
--

LOCK TABLES `location_bubble` WRITE;
/*!40000 ALTER TABLE `location_bubble` DISABLE KEYS */;
/*!40000 ALTER TABLE `location_bubble` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planning_bubble`
--

DROP TABLE IF EXISTS `planning_bubble`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `planning_bubble` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `planning_bubble_id` (`id`),
  CONSTRAINT `planning_bubble_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `planning_bubble_event__fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planning_bubble`
--

LOCK TABLES `planning_bubble` WRITE;
/*!40000 ALTER TABLE `planning_bubble` DISABLE KEYS */;
/*!40000 ALTER TABLE `planning_bubble` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planning_entry`
--

DROP TABLE IF EXISTS `planning_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `planning_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` datetime DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `has_time` tinyint(1) DEFAULT '0',
  `bubble_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `planning_entry_id` (`id`),
  KEY `user_id` (`user_id`),
  KEY `planning_entry_bubble__fk` (`bubble_id`),
  CONSTRAINT `planning_entry_bubble__fk` FOREIGN KEY (`bubble_id`) REFERENCES `planning_bubble` (`id`),
  CONSTRAINT `planning_entry_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planning_entry`
--

LOCK TABLES `planning_entry` WRITE;
/*!40000 ALTER TABLE `planning_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `planning_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_option`
--

DROP TABLE IF EXISTS `survey_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bubble_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `survey_option_id` (`id`),
  KEY `user_id` (`user_id`),
  KEY `survey_option_bubble__fk` (`bubble_id`),
  CONSTRAINT `survey_option_bubble__fk` FOREIGN KEY (`bubble_id`) REFERENCES `survey_bubble` (`id`),
  CONSTRAINT `survey_option_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_option`
--

LOCK TABLES `survey_option` WRITE;
/*!40000 ALTER TABLE `survey_option` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_bubble`
--

DROP TABLE IF EXISTS `survey_bubble`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_bubble` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `survey_bubble_id` (`id`),
  CONSTRAINT `survey_bubble_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `survey_bubble_event__fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_bubble`
--

LOCK TABLES `survey_bubble` WRITE;
/*!40000 ALTER TABLE `survey_bubble` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey_bubble` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_bubble`
--

DROP TABLE IF EXISTS `travel_bubble`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_bubble` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `travel_bubble_event__fk` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  CONSTRAINT `travel_bubble_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_bubble`
--

LOCK TABLES `travel_bubble` WRITE;
/*!40000 ALTER TABLE `travel_bubble` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_bubble` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_proposal`
--

DROP TABLE IF EXISTS `travel_proposal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_proposal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bubble_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `pass_by_cities` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `travel_proposal_id` (`id`),
  KEY `user_id` (`user_id`),
  KEY `travel_proposal_bubble__fk` (`bubble_id`),
  CONSTRAINT `travel_proposal_bubble__fk` FOREIGN KEY (`bubble_id`) REFERENCES `travel_bubble` (`id`),
  CONSTRAINT `travel_proposal_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_proposal`
--

LOCK TABLES `travel_proposal` WRITE;
/*!40000 ALTER TABLE `travel_proposal` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_proposal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `travel_request`
--

DROP TABLE IF EXISTS `travel_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `travel_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `bubble_id` int(11) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `proposal_id`  int(11) DEFAULT NULL,
  `pass_by_cities` varchar(100) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `travel_request_id` (`id`),
  KEY `user_id` (`user_id`),
  KEY `travel_request_bubble__fk` (`bubble_id`),
  KEY `travel_request_proposal__fk` (`proposal_id`),
  CONSTRAINT `travel_request_proposal__fk` FOREIGN KEY (`proposal_id`) REFERENCES `travel_proposal` (`id`),
  CONSTRAINT `travel_request_bubble__fk` FOREIGN KEY (`bubble_id`) REFERENCES `travel_bubble` (`id`),
  CONSTRAINT `travel_request_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_request`
--

LOCK TABLES `travel_request` WRITE;
/*!40000 ALTER TABLE `travel_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `travel_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ref` char(36) NOT NULL UNIQUE,
  `email` varchar(50) UNIQUE,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL UNIQUE,
  `deleted` tinyint(1) DEFAULT '0',
  `profile_pic_location` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `account_non_expired` tinyint(1) DEFAULT '1',
  `account_non_locked` tinyint(1) DEFAULT '1',
  `credentials_non_expired` tinyint(1) DEFAULT '1',
  `last_password_reset` datetime DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `social_credentials`
--

DROP TABLE IF EXISTS `social_credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_credentials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider` int(2) NOT NULL,
  `user_id` int(11) NOT NULL,
  `provider_id` varchar(50) NOT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `social_credentials_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_credentials`
--

LOCK TABLES `social_credentials` WRITE;
/*!40000 ALTER TABLE `social_credentials` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkbox_answer`
--

DROP TABLE IF EXISTS `checkbox_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkbox_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  option_id int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `checkbox_answer_bubble__fk` (`option_id`),
  CONSTRAINT `checkbox_answer_bubble__fk` FOREIGN KEY (`option_id`) REFERENCES `checkbox_option` (`id`),
  CONSTRAINT `checkbox_answer_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkbox_answer`
--

LOCK TABLES `checkbox_answer` WRITE;
/*!40000 ALTER TABLE `checkbox_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `checkbox_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_survey_answers`
--

DROP TABLE IF EXISTS `survey_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `option_id` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `survey_answer_bubble__fk` (`option_id`),
  CONSTRAINT `survey_answer_bubble__fk` FOREIGN KEY (`option_id`) REFERENCES `survey_option` (`id`),
  CONSTRAINT `survey_answer_user__fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_answer`
--

LOCK TABLES `survey_answer` WRITE;
/*!40000 ALTER TABLE `survey_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey_answer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-20 22:46:15
