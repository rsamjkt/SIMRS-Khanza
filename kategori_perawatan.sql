/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy2

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 10/04/2025 08:53:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kategori_perawatan
-- ----------------------------
DROP TABLE IF EXISTS `kategori_perawatan`;
CREATE TABLE `kategori_perawatan` (
  `kd_kategori` char(5) NOT NULL,
  `nm_kategori` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`kd_kategori`),
  KEY `nm_kategori` (`nm_kategori`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
