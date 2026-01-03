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

 Date: 02/01/2026 16:01:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bpjs_prb
-- ----------------------------
DROP TABLE IF EXISTS `bpjs_prb`;
CREATE TABLE `bpjs_prb` (
  `no_sep` varchar(40) NOT NULL,
  `prb` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`no_sep`),
  CONSTRAINT `bpjs_prb_ibfk_1` FOREIGN KEY (`no_sep`) REFERENCES `bridging_sep` (`no_sep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
