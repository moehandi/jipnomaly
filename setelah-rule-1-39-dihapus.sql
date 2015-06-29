-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: iptables_db
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu0.12.04.1

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
-- Current Database: `iptables_db`
--

/*!40000 DROP DATABASE IF EXISTS `iptables_db`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `iptables_db` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `iptables_db`;

--
-- Table structure for table `rule`
--

DROP TABLE IF EXISTS `rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rule` (
  `orde` int(4) NOT NULL AUTO_INCREMENT,
  `protocol` varchar(4) NOT NULL,
  `source_ip` varchar(18) NOT NULL,
  `source_port` varchar(6) NOT NULL,
  `dest_ip` varchar(18) NOT NULL,
  `dest_port` varchar(6) NOT NULL,
  `action` varchar(6) NOT NULL,
  PRIMARY KEY (`orde`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rule`
--

LOCK TABLES `rule` WRITE;
/*!40000 ALTER TABLE `rule` DISABLE KEYS */;
INSERT INTO `rule` VALUES (1,'tcp','192.168.1.11/32','0','172.16.0.0/24','80','accept'),(2,'tcp','192.168.1.11/32','0','172.16.0.0/24','21','drop'),(3,'tcp','192.168.1.11/32','0','172.16.0.0/24','22','drop'),(4,'udp','192.168.1.11/32','0','172.16.0.11/32','112','accept'),(5,'udp','192.168.1.11/32','0','172.16.0.12/32','112','drop'),(6,'udp','192.168.1.11/32','0','172.16.0.13/32','112','drop'),(7,'tcp','192.168.1.12/32','0','172.16.0.0/24','80','drop'),(8,'tcp','192.168.1.12/32','0','172.16.0.0/24','21','accept'),(9,'tcp','192.168.1.12/32','0','172.16.0.0/24','22','drop'),(10,'udp','192.168.1.12/32','0','172.16.0.11/32','112','drop'),(11,'udp','192.168.1.12/32','0','172.16.0.12/32','112','accept'),(12,'udp','192.168.1.12/32','0','172.16.0.13/32','112','drop'),(13,'tcp','192.168.1.13/32','0','172.16.0.0/24','80','drop'),(14,'tcp','192.168.1.13/32','0','172.16.0.0/24','21','drop'),(15,'tcp','192.168.1.13/32','0','172.16.0.0/24','22','accept'),(16,'udp','192.168.1.13/32','0','172.16.0.11/32','112','drop'),(17,'udp','192.168.1.13/32','0','172.16.0.12/32','112','drop'),(18,'udp','192.168.1.13/32','0','172.16.0.13/32','112','accept');
/*!40000 ALTER TABLE `rule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-07  3:02:53
