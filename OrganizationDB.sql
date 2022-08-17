CREATE DATABASE  IF NOT EXISTS `ralf_threat_master_kitchen` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ralf_threat_master_kitchen`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ralf_threat_master_kitchen
-- ------------------------------------------------------
-- Server version	5.7.29-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attack_motivating_factor_weights`
--

DROP TABLE IF EXISTS `attack_motivating_factor_weights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attack_motivating_factor_weights` (
  `organization_id` int(11) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `factor_id` int(11) NOT NULL,
  `weight` decimal(3,2) NOT NULL,
  `attack_motivating_questionnaire_id` int(11) NOT NULL,
  PRIMARY KEY (`attack_motivating_questionnaire_id`,`factor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attack_motivating_question_response`
--

DROP TABLE IF EXISTS `attack_motivating_question_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attack_motivating_question_response` (
  `attack_motivating_question_response_id` int(11) NOT NULL AUTO_INCREMENT,
  `attack_motivating_questionnaire_response_id` int(11) NOT NULL,
  `attack_motivating_factor_question_id` int(11) NOT NULL,
  `factor_id` int(11) NOT NULL,
  `response` tinyint(1) NOT NULL,
  `justification` text,
  PRIMARY KEY (`attack_motivating_question_response_id`)
) ENGINE=InnoDB AUTO_INCREMENT=485 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attack_motivating_questionnaire`
--

DROP TABLE IF EXISTS `attack_motivating_questionnaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attack_motivating_questionnaire` (
  `organization_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `attack_motivating_questionnaire_id` int(11) NOT NULL AUTO_INCREMENT,
  `loadTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`attack_motivating_questionnaire_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attack_motivating_questionnaire_content`
--

DROP TABLE IF EXISTS `attack_motivating_questionnaire_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attack_motivating_questionnaire_content` (
  `attack_motivating_questionnaire_id` int(11) NOT NULL,
  `factor_id1` int(11) NOT NULL,
  `factor_id2` int(11) NOT NULL,
  `comparison_operator` varchar(4) NOT NULL,
  `comparison` int(11) NOT NULL,
  PRIMARY KEY (`attack_motivating_questionnaire_id`,`factor_id1`,`factor_id2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `attack_motivating_questionnaire_response`
--

DROP TABLE IF EXISTS `attack_motivating_questionnaire_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attack_motivating_questionnaire_response` (
  `attack_motivating_questionnaire_response_id` int(11) NOT NULL AUTO_INCREMENT,
  `loadTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `organization_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`attack_motivating_questionnaire_response_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `business_risk`
--

DROP TABLE IF EXISTS `business_risk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_risk` (
  `business_risk_id` int(11) NOT NULL AUTO_INCREMENT,
  `business_risk_name` varchar(1000) NOT NULL,
  `business_risk_description` text NOT NULL,
  `business_risk_impact` bigint(20) DEFAULT NULL,
  `business_risk_default_order` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`business_risk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `business_risk_capability_killer`
--

DROP TABLE IF EXISTS `business_risk_capability_killer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_risk_capability_killer` (
  `business_risk_id` int(11) NOT NULL,
  `capability_killer_id` int(11) NOT NULL,
  PRIMARY KEY (`business_risk_id`,`capability_killer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `business_risk_custom_order`
--

DROP TABLE IF EXISTS `business_risk_custom_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_risk_custom_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `business_risk_custom_order_list`
--

DROP TABLE IF EXISTS `business_risk_custom_order_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_risk_custom_order_list` (
  `order_id` int(11) NOT NULL,
  `business_risk_id` int(11) NOT NULL,
  `business_risk_impact_min` bigint(20) DEFAULT NULL,
  `business_risk_impact_max` bigint(20) DEFAULT NULL,
  `weight` decimal(5,4) DEFAULT NULL,
  PRIMARY KEY (`order_id`,`business_risk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `business_risk_pairwise_comparison`
--

DROP TABLE IF EXISTS `business_risk_pairwise_comparison`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `business_risk_pairwise_comparison` (
  `service_id` int(11) NOT NULL,
  `business_risk_id1` int(11) NOT NULL,
  `business_risk_id2` int(11) NOT NULL,
  `comparison` int(11) NOT NULL,
  PRIMARY KEY (`service_id`,`business_risk_id1`,`business_risk_id2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `capability_killer`
--

DROP TABLE IF EXISTS `capability_killer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `capability_killer` (
  `capability_killer_id` int(11) NOT NULL,
  `capability_killer_name` varchar(1000) NOT NULL,
  PRIMARY KEY (`capability_killer_id`),
  CONSTRAINT `capability_killer_id_fk` FOREIGN KEY (`capability_killer_id`) REFERENCES `ralf_threat_data_warehouse`.`c_capability_killer` (`capability_killer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `control_report`
--

DROP TABLE IF EXISTS `control_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `control_report` (
  `control_report_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` int(11) NOT NULL,
  `report_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`control_report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `control_report_control`
--

DROP TABLE IF EXISTS `control_report_control`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `control_report_control` (
  `control_report_control_id` int(11) NOT NULL AUTO_INCREMENT,
  `control_id` int(11) NOT NULL,
  `control_report_id` int(11) NOT NULL,
  `appliesTo` varchar(23) NOT NULL,
  `allThreats` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`control_report_control_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2983 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `control_report_control_excludedthreats`
--

DROP TABLE IF EXISTS `control_report_control_excludedthreats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `control_report_control_excludedthreats` (
  `control_report_control_id` int(11) NOT NULL,
  `threat_id` int(11) NOT NULL,
  PRIMARY KEY (`control_report_control_id`,`threat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `control_report_platforms`
--

DROP TABLE IF EXISTS `control_report_platforms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `control_report_platforms` (
  `control_report_platforms_id` int(11) NOT NULL AUTO_INCREMENT,
  `control_report_id` int(11) NOT NULL,
  `platform_id` int(11) NOT NULL,
  `platform` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`control_report_platforms_id`)
) ENGINE=InnoDB AUTO_INCREMENT=482 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization` (
  `organization_id` int(11) NOT NULL AUTO_INCREMENT,
  `organization_name` varchar(1000) NOT NULL,
  PRIMARY KEY (`organization_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_category`
--

DROP TABLE IF EXISTS `risk_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_category` (
  `risk_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `risk_category_bundle_id` int(11) NOT NULL,
  `risk_value_min` int(11) NOT NULL,
  `risk_value_max` int(11) NOT NULL,
  `risk_category_type` varchar(11) DEFAULT NULL,
  `category_name` varchar(150) NOT NULL,
  PRIMARY KEY (`risk_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_category_bundle`
--

DROP TABLE IF EXISTS `risk_category_bundle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_category_bundle` (
  `risk_category_bundle_id` int(11) NOT NULL AUTO_INCREMENT,
  `organization_id` int(11) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `loadTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`risk_category_bundle_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_phi`
--

DROP TABLE IF EXISTS `risk_phi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_phi` (
  `risk_phi_id` int(11) NOT NULL AUTO_INCREMENT,
  `organization_id` int(11) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `risk_phi` decimal(3,2) NOT NULL,
  `loadTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `risk_phi_type` varchar(21) DEFAULT NULL,
  PRIMARY KEY (`risk_phi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_report`
--

DROP TABLE IF EXISTS `risk_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_report` (
  `risk_report_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` int(11) NOT NULL,
  `assessmentTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `assessed_success_probability_order` int(11) NOT NULL DEFAULT '0',
  `service_existence_probability_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`risk_report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_report_business_risk`
--

DROP TABLE IF EXISTS `risk_report_business_risk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_report_business_risk` (
  `risk_report_business_risk_id` int(11) NOT NULL AUTO_INCREMENT,
  `risk_report_id` int(11) NOT NULL,
  `business_risk_id` int(11) NOT NULL,
  PRIMARY KEY (`risk_report_business_risk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_report_capability_killer_success_probability`
--

DROP TABLE IF EXISTS `risk_report_capability_killer_success_probability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_report_capability_killer_success_probability` (
  `risk_report_capability_killer_success_probability_id` int(11) NOT NULL AUTO_INCREMENT,
  `risk_report_id` int(11) NOT NULL,
  `capability_killer_id` int(11) NOT NULL,
  `business_risk_id` int(11) NOT NULL,
  `success_probability_order` int(11) NOT NULL,
  PRIMARY KEY (`risk_report_capability_killer_success_probability_id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_report_excluded_threat`
--

DROP TABLE IF EXISTS `risk_report_excluded_threat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_report_excluded_threat` (
  `risk_report_id` int(11) NOT NULL,
  `threat_id` int(11) NOT NULL,
  PRIMARY KEY (`risk_report_id`,`threat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_report_threat`
--

DROP TABLE IF EXISTS `risk_report_threat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_report_threat` (
  `threat_id` int(11) NOT NULL,
  `risk_report_id` int(11) NOT NULL,
  `threat_name` varchar(900) DEFAULT NULL,
  `threat_description` text,
  `success_probability_order` int(11) NOT NULL,
  `success_probability_name` varchar(900) DEFAULT NULL,
  PRIMARY KEY (`threat_id`,`risk_report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_report_threat_control`
--

DROP TABLE IF EXISTS `risk_report_threat_control`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_report_threat_control` (
  `control_id` int(11) NOT NULL,
  `threat_id` int(11) NOT NULL,
  `risk_report_id` int(11) NOT NULL,
  `control_name` varchar(900) DEFAULT NULL,
  `control_description` text,
  PRIMARY KEY (`control_id`,`threat_id`,`risk_report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `risk_report_threat_control_pivoted_threat`
--

DROP TABLE IF EXISTS `risk_report_threat_control_pivoted_threat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `risk_report_threat_control_pivoted_threat` (
  `pivoted_threat_id` int(11) NOT NULL AUTO_INCREMENT,
  `threat_id` int(11) NOT NULL,
  `kill_chain_phase_order` int(11) NOT NULL,
  `kill_chain_phase_name` varchar(900) DEFAULT NULL,
  `kill_chain_name` varchar(900) NOT NULL,
  `risk_report_id` int(11) NOT NULL,
  `succeeding_threat_id` int(11) NOT NULL,
  `succeeding_kill_chain_phase_order` int(11) NOT NULL,
  `succeeding_kill_chain_phase_name` varchar(900) DEFAULT NULL,
  `succeeding_kill_chain_name` varchar(900) NOT NULL,
  `success_probability_order` int(11) NOT NULL,
  `success_probability_name` varchar(900) DEFAULT NULL,
  PRIMARY KEY (`pivoted_threat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=472254 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(1000) NOT NULL,
  `service_description` text,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_business_risk`
--

DROP TABLE IF EXISTS `service_business_risk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_business_risk` (
  `business_risk_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`business_risk_id`,`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_existence_probability`
--

DROP TABLE IF EXISTS `service_existence_probability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_existence_probability` (
  `service_existence_probability_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` int(11) NOT NULL,
  `existence_probability` decimal(3,2) NOT NULL,
  `attack_motivation_score` decimal(3,2) NOT NULL,
  `attack_enabling_score` decimal(3,2) NOT NULL,
  `loadTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `organization_id` int(11) NOT NULL,
  PRIMARY KEY (`service_existence_probability_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vulnerability_enabling_factor_weights`
--

DROP TABLE IF EXISTS `vulnerability_enabling_factor_weights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vulnerability_enabling_factor_weights` (
  `organization_id` int(11) NOT NULL,
  `service_id` int(11) DEFAULT NULL,
  `factor_id` int(11) NOT NULL,
  `weight` decimal(3,2) NOT NULL,
  `vulnerability_enabling_factor_questionnaire_id` int(11) NOT NULL,
  PRIMARY KEY (`vulnerability_enabling_factor_questionnaire_id`,`factor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vulnerability_enabling_question_response`
--

DROP TABLE IF EXISTS `vulnerability_enabling_question_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vulnerability_enabling_question_response` (
  `vulnerability_enabling_question_response_id` int(11) NOT NULL AUTO_INCREMENT,
  `vulnerability_enabling_questionnaire_response_id` int(11) NOT NULL,
  `vulnerability_enabling_factor_question_id` int(11) NOT NULL,
  `factor_id` int(11) NOT NULL,
  `response` tinyint(1) NOT NULL,
  `justification` text,
  PRIMARY KEY (`vulnerability_enabling_question_response_id`)
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vulnerability_enabling_questionnaire`
--

DROP TABLE IF EXISTS `vulnerability_enabling_questionnaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vulnerability_enabling_questionnaire` (
  `organization_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `vulnerability_enabling_questionnaire_id` int(11) NOT NULL AUTO_INCREMENT,
  `loadTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`vulnerability_enabling_questionnaire_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vulnerability_enabling_questionnaire_content`
--

DROP TABLE IF EXISTS `vulnerability_enabling_questionnaire_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vulnerability_enabling_questionnaire_content` (
  `vulnerability_enabling_questionnaire_id` int(11) NOT NULL,
  `factor_id1` int(11) NOT NULL,
  `factor_id2` int(11) NOT NULL,
  `comparison_operator` varchar(4) NOT NULL,
  `comparison` int(11) NOT NULL,
  PRIMARY KEY (`vulnerability_enabling_questionnaire_id`,`factor_id1`,`factor_id2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vulnerability_enabling_questionnaire_response`
--

DROP TABLE IF EXISTS `vulnerability_enabling_questionnaire_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vulnerability_enabling_questionnaire_response` (
  `vulnerability_enabling_questionnaire_response_id` int(11) NOT NULL AUTO_INCREMENT,
  `loadTimestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `organization_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  PRIMARY KEY (`vulnerability_enabling_questionnaire_response_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'ralf_threat_master_kitchen'
--

--
-- Dumping routines for database 'ralf_threat_master_kitchen'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-17 16:03:09
