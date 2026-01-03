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

 Date: 01/01/2026 18:03:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for resiko_kerja
-- ----------------------------
DROP TABLE IF EXISTS `resiko_kerja`;
CREATE TABLE `resiko_kerja` (
  `kode_resiko` varchar(3) NOT NULL,
  `nama_resiko` varchar(100) DEFAULT NULL,
  `indek` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`kode_resiko`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
