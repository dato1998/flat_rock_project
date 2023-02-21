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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `barcode` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'12355','Epoxy Flooring','2023-02-20 21:01:50.000000','Control of epistaxis by ligation of ethmoidal arteries','Divavu',45,6,'2023-02-20 21:01:50.000000'),(2,'12123','Plumbing & Medical Gas','2023-02-20 21:01:59.000000','Microscopic examination of specimen from upper gastrointestinal tract and of vomitus, culture and sensitivity','Jabberbean',35,25,'2023-02-20 21:01:59.000000'),(3,'1234','Prefabricated Aluminum Metal Canopies','2023-02-20 21:01:59.000000','Fiber-optic bronchoscopy','Quinu',10,56,'2023-02-20 21:01:59.000000'),(4,'11123','Masonry','2023-02-20 21:02:47.000000','Conization of cervix','Tagopia',65,45,'2023-02-20 21:02:47.000000'),(5,'1234','Construction Clean and Final Clean','2023-02-20 21:02:47.000000','Other removal of both ovaries and tubes at same operative episode','Divanoodle',34,26,'2023-02-20 21:02:47.000000'),(6,'12123','Electrical','2023-02-20 21:02:47.000000','Closure of fistula of uterus','Topiczoom',74,32,'2023-02-20 21:02:47.000000'),(7,'1234','Plumbing & Medical Gas','2023-02-20 21:02:47.000000','Repair of laceration of gallbladder','Brainlounge',58,39,'2023-02-20 21:02:47.000000'),(8,'1111','Plumbing & Medical Gas','2023-02-20 21:02:47.000000','Other dilation and curettage','Tagopia',59,100,'2023-02-20 21:02:47.000000'),(9,'12355','Marlite Panels (FED)','2023-02-20 21:02:47.000000','Operative esophagoscopy by incision','Viva',43,66,'2023-02-20 21:02:47.000000'),(10,'12355','Epoxy Flooring','2023-02-20 21:02:47.000000','Control of epistaxis by ligation of ethmoidal arteries','Divavu',45,6,'2023-02-20 21:02:47.000000');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
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
