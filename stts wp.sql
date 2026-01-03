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

 Date: 01/01/2026 18:03:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stts_kerja
-- ----------------------------
DROP TABLE IF EXISTS `stts_kerja`;
CREATE TABLE `stts_kerja` (
  `stts` char(3) NOT NULL,
  `ktg` varchar(20) NOT NULL,
  `indek` tinyint(4) NOT NULL,
  `hakcuti` tinyint(11) NOT NULL,
  PRIMARY KEY (`stts`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for stts_wp
-- ----------------------------
DROP TABLE IF EXISTS `stts_wp`;
CREATE TABLE `stts_wp` (
  `stts` char(5) NOT NULL,
  `ktg` varchar(50) NOT NULL,
  PRIMARY KEY (`stts`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
