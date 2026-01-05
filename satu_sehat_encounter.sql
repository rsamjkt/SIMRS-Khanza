/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy5

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 04/01/2026 20:34:43
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
