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

 Date: 31/10/2025 10:58:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vedika_sebelum_klaim
-- ----------------------------
DROP TABLE IF EXISTS `vedika_sebelum_klaim`;
CREATE TABLE `vedika_sebelum_klaim` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(17) NOT NULL,
  `catatan` text DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `tanggal_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `no_rawat` (`no_rawat`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
