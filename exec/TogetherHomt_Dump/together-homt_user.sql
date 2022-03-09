-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: together-homt
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'kimssafy@ssafy.com','/upload/default/exercise.png','','$2a$10$GYW968eZbYYqr2MT0shODu0Env0UywvmPk0ifyQ2OY8mZvVORBPqO','ROLE_USER','KIM',NULL),(2,'leessafy@ssafy.com','/upload/default/exercise.png','','$2a$10$J04W0F9K6jBmoizCN5W8xeSxLfdS/VwdZwhga4WBSnYM7BNmw0XUW','ROLE_USER','LEE',NULL),(3,'parkssafy@ssafy.com','/upload/default/exercise.png','','$2a$10$N3C45aKk/DFfzA3QWknVNuzvPVaftk/IAeZo23pDP839ixOtmQBR2','ROLE_USER','PARK',NULL),(4,'choissafy@ssafy.com','/upload/default/exercise.png','','$2a$10$xp1vuXsiYD7b8tRBwdL7vuJSOgfwfn5JhhC95Kxi39UxFHDvehgy2','ROLE_USER','CHOI',NULL),(5,'jungssafy@ssafy.com','/upload/default/exercise.png','','$2a$10$J0STSlh8CYLA0og5nDteZ.LDBbBYPab9b6ajkNa3KQGfCzJjyhDH2','ROLE_USER','JUNG',NULL),(6,'kimpro@ssafy.com','/upload/default/exercise.png','','$2a$10$tovhodCD5ckcnxA4R23TROUY0mn/apKCgwLiX4dJ6ZwcfUNjUI.HC','ROLE_ADMIN','KIM Edu. Pro.',1),(7,'leepro@ssafy.com','/upload/default/exercise.png','','$2a$10$S.xiA73IYVcoLJF0vQ3FTOwV3Drsu8V9TRUh5CUDhoJHtAwWZZnI6','ROLE_ADMIN','LEE Edu. Pro.',2),(8,'parkpro@ssafy.com','/upload/default/exercise.png','','$2a$10$qjqPCwP9yP.z4yMIzzChFeiOJ9sFnL27wnNCq4fop6kL8D1BwVVCW','ROLE_ADMIN','PARK Edu. Pro.',3);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-17  3:22:54
