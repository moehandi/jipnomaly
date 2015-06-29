-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 02, 2013 at 10:01 AM
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `rule`
--

INSERT INTO `rule` (`orde`, `protocol`, `source_ip`, `source_port`, `dest_ip`, `dest_port`, `action`) VALUES
(1, 'tcp', '192.168.1.1/32', '0', '0.0.0.0/0', '80', 'deny'),
(2, 'tcp', '0.0.0.0/0', '0', '161.120.33.40/32', '80', 'accept'),
(3, 'tcp', '140.192.37.0/8', '0', '161.120.33.40/32', '80', 'deny'),
(4, 'tcp', '140.192.37.30/32', '0', '0.0.0.0/0', '21', 'deny'),
(5, 'tcp', '140.192.37.0/8', '0', '0.0.0.0/0', '21', 'accept'),
(6, 'tcp', '140.192.37.0/8', '0', '161.120.33.40/32', '21', 'accept'),
(7, 'tcp', '0.0.0.0/0', '0', '0.0.0.0/0', '0', 'deny'),
(8, 'udp', '140.192.37.0/8', '0', '161.120.33.40/32', '53', 'accept'),
(9, 'udp', '0.0.0.0/0', '0', '161.120.33.40/32', '53', 'accept'),
(10, 'udp', '0.0.0.0/0', '0', '0.0.0.0/0', '0', 'deny'),
(11, 'tcp', '140.192.37.0/8', '0', '0.0.0.0/0', '80', 'accept');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
