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

 Date: 10/10/2025 18:30:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_encounter
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_encounter`;
CREATE TABLE `satu_sehat_encounter` (
  `no_rawat` varchar(17) NOT NULL,
  `id_encounter` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  CONSTRAINT `satu_sehat_encounter_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
