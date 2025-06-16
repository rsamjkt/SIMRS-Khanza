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

 Date: 16/06/2025 06:01:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hasil_pemeriksaan_usg_gambar
-- ----------------------------
DROP TABLE IF EXISTS `hasil_pemeriksaan_usg_gambar`;
CREATE TABLE `hasil_pemeriksaan_usg_gambar` (
  `no_rawat` varchar(20) NOT NULL,
  `photo` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  CONSTRAINT `hasil_pemeriksaan_usg_gambar_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `hasil_pemeriksaan_usg` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
