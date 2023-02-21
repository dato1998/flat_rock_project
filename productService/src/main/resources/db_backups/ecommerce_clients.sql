CREATE DATABASE  IF NOT EXISTS `users` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `users`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: users
-- ------------------------------------------------------
-- Server version	8.0.32-0ubuntu0.22.04.2

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
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `role` smallint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'2023-02-20','hkibardj@indiatimes.com','Hermione','Kibard','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','431-983-9963',2,'hkibardj'),(2,'2023-02-20','hover1@yandex.ru','Harald','Over','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','597-750-3533',2,'hover1'),(3,'2023-02-20','gwinson2@artisteer.com','Giordano','Winson','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','581-782-6216',0,'gwinson2'),(4,'2023-02-20','gwinson2@artisteer.com','Giordano','Winson','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','581-782-6216',0,'gwinson2'),(5,'2023-02-20','csaunderson3@sciencedaily.com','Cos','Saunderson','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','902-213-4106',1,'csaunderson3'),(6,'2023-02-20','kupsale4@nifty.com','Karry','Upsale','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','467-927-5147',2,'kupsale4'),(7,'2023-02-20','aeastbrook5@ocn.ne.jp','Angy','Eastbrook','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','223-535-0194',2,'aeastbrook5'),(8,'2023-02-20','bfarenden6@skype.com','Bobbee','Farenden','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','116-433-6812',1,'bfarenden6'),(9,'2023-02-20','hkinvan7@house.gov','Hendrik','Kinvan','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','656-657-1435',2,'hkinvan7'),(10,'2023-02-20','cnormavell8@weebly.com','Carmella','Normavell','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','430-259-1944',1,'cnormavell8'),(11,'2023-02-20','hdrackford9@skyrock.com','Hedvig','Drackford','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','269-913-7972',0,'hdrackford9'),(12,'2023-02-20','dodeorana@example.com','Darcey','O\'Deoran','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','399-300-3240',2,'dodeorana'),(13,'2023-02-20','slysaghtb@ucoz.ru','Shep','Lysaght','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','993-203-3102',2,'slysaghtb'),(14,'2023-02-20','sbraimec@irs.gov','Serge','Braime','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','144-599-4361',1,'sbraimec'),(15,'2023-02-20','fhubachd@amazonaws.com','Fay','Hubach','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','970-436-8616',1,'fhubachd'),(16,'2023-02-20','vcainse@senate.gov','Victoria','Cains','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','995-493-9688',0,'vcainse'),(17,'2023-02-20','hstowf@japanpost.jp','Harbert','Stow','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','634-532-7117',0,'hstowf'),(18,'2023-02-20','holiphardg@ebay.com','Hynda','Oliphard','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','656-866-5844',2,'holiphardg'),(19,'2023-02-20','hdeclercqh@boston.com','Hall','de Clercq','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','515-977-8502',0,'hdeclercqh'),(20,'2023-02-20','gjollandsi@walmart.com','Gwenette','Jollands','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','251-823-2778',1,'gjollandsi'),(21,'2023-02-20','hkibardj@indiatimes.com','Hermione','Kibard','$2a$12$YrmWxgIJqXyyg4VBdPjXUuNCzoaOXBRWTfCxk3459Cs9Lc9LfuBzq','431-983-9963',2,'hkibardj');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-20 13:05:54
