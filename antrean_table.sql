/*
 Navicat Premium Data Transfer

 Source Server         : produ
 Source Server Type    : MySQL
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.5:3306
 Source Schema         : sikrs4m2106

 Target Server Type    : MySQL
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 19/03/2025 17:30:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for antrian_loket
-- ----------------------------
DROP TABLE IF EXISTS `antrian_loket`;
CREATE TABLE `antrian_loket` (
  `kd` int(50) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `noantrian` varchar(50) NOT NULL,
  `postdate` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL DEFAULT '00:00:00',
  PRIMARY KEY (`kd`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5272 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for antriapotek
-- ----------------------------
DROP TABLE IF EXISTS `antriapotek`;
CREATE TABLE `antriapotek` (
  `loket` int(11) NOT NULL,
  `antrian` int(11) NOT NULL,
  KEY `loket` (`loket`) USING BTREE,
  KEY `antrian` (`antrian`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for antriapotek2
-- ----------------------------
DROP TABLE IF EXISTS `antriapotek2`;
CREATE TABLE `antriapotek2` (
  `no_resep` varchar(14) DEFAULT NULL,
  `status` enum('0','1') DEFAULT NULL,
  `no_rawat` varchar(17) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for antriapotek3
-- ----------------------------
DROP TABLE IF EXISTS `antriapotek3`;
CREATE TABLE `antriapotek3` (
  `no_resep` varchar(14) DEFAULT NULL,
  `status` enum('0','1','2','3') DEFAULT NULL,
  `no_rawat` varchar(17) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for antriloket
-- ----------------------------
DROP TABLE IF EXISTS `antriloket`;
CREATE TABLE `antriloket` (
  `loket` int(11) NOT NULL,
  `antrian` int(11) NOT NULL,
  KEY `loket` (`loket`),
  KEY `antrian` (`antrian`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for antripoli
-- ----------------------------
DROP TABLE IF EXISTS `antripoli`;
CREATE TABLE `antripoli` (
  `kd_dokter` varchar(20) DEFAULT NULL,
  `kd_poli` char(5) DEFAULT NULL,
  `status` enum('0','1','2','3') DEFAULT NULL,
  `no_rawat` varchar(17) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
