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
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `status` smallint DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `clients_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrxqv08hvs3v1b8cyog4y0yxgl` (`clients_id`),
  CONSTRAINT `FKrxqv08hvs3v1b8cyog4y0yxgl` FOREIGN KEY (`clients_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'Colmar-Houssen Airport','2023-02-20',2,93,1),(2,'Santa Genoveva Airport','2023-02-20',2,6,6),(3,'Pimaga Airport','2023-02-20',0,32,9),(4,'Kalibo International Airport','2023-02-20',0,64,6),(5,'Spriggs Payne Airport','2023-02-20',0,97,3),(6,'Nanaimo Harbour Water Airport','2023-02-20',0,90,3),(7,'North Central West Virginia Airport','2023-02-20',2,99,2),(8,'El Minya Airport','2023-02-20',1,61,6),(9,'Tumling Tar Airport','2023-02-20',2,35,3),(10,'Fazenda Vassoural Airport','2023-02-20',1,39,8),(11,'Vestmannaeyjar Airport','2023-02-20',2,72,3),(12,'Ekibastuz Airport','2023-02-20',1,29,9),(13,'La Môle Airport','2023-02-20',0,90,7),(14,'Capital City Airport','2023-02-20',1,68,7),(15,'Fderik Airport','2023-02-20',0,74,6),(16,'Wollongong Airport','2023-02-20',1,50,4),(17,'Kamloops Airport','2023-02-20',2,7,4),(18,'Sacramento Airport','2023-02-20',0,78,4),(19,'Foumban Nkounja Airport','2023-02-20',0,40,4),(20,'Mudgee Airport','2023-02-20',1,11,5);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
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
