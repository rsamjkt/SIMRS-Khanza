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

 Date: 31/07/2025 21:10:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for antripendaftaran_nomor
-- ----------------------------
DROP TABLE IF EXISTS `antripendaftaran_nomor`;
CREATE TABLE `antripendaftaran_nomor` (
  `nomor` char(5) NOT NULL,
  `status` enum('0','1','2','3') DEFAULT NULL,
  `jam` datetime NOT NULL,
  `loket` varchar(100) DEFAULT NULL,
  `waktu_panggil` datetime DEFAULT NULL,
  PRIMARY KEY (`nomor`,`jam`),
  KEY `nomor` (`nomor`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
