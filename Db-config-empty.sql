-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 03, 2013 at 12:01 PM
-- Server version: 5.5.29
-- PHP Version: 5.3.10-1ubuntu3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `iptables_db`
--

-- --------------------------------------------------------
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `iptables_db` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `iptables_db`;


--
-- Table structure for table `rule`
--

CREATE TABLE IF NOT EXISTS `rule` (
  `orde` int(4) NOT NULL AUTO_INCREMENT,
  `protocol` varchar(4) NOT NULL,
  `source_ip` varchar(18) NOT NULL,
  `source_port` varchar(6) NOT NULL,
  `dest_ip` varchar(18) NOT NULL,
  `dest_port` varchar(6) NOT NULL,
  `action` varchar(6) NOT NULL,
  PRIMARY KEY (`orde`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
