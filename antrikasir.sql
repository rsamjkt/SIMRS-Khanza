/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy3

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 28/09/2025 16:51:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for antrikasir
-- ----------------------------
DROP TABLE IF EXISTS `antrikasir`;
CREATE TABLE `antrikasir` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(17) NOT NULL,
  `tgl_antri` date NOT NULL,
  `jam_antri` time NOT NULL,
  `status` enum('0','1','2','3','4') NOT NULL COMMENT '0: Menunggu, 1: Panggil Suara, 2: Sedang Dipanggil, 3: Selesai, 4: Terlewat',
  PRIMARY KEY (`id`),
  UNIQUE KEY `no_rawat_unique` (`no_rawat`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
