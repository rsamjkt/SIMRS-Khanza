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

 Date: 19/10/2025 15:50:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_mapping_radiologi
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_mapping_radiologi`;
CREATE TABLE `satu_sehat_mapping_radiologi` (
  `kd_jenis_prw` varchar(15) NOT NULL,
  `code` varchar(15) DEFAULT NULL,
  `system` varchar(100) NOT NULL,
  `display` varchar(80) DEFAULT NULL,
  `sampel_code` varchar(15) NOT NULL,
  `sampel_system` varchar(100) NOT NULL,
  `sampel_display` varchar(80) NOT NULL,
  PRIMARY KEY (`kd_jenis_prw`),
  CONSTRAINT `satu_sehat_mapping_radiologi_ibfk_1` FOREIGN KEY (`kd_jenis_prw`) REFERENCES `jns_perawatan_radiologi` (`kd_jenis_prw`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
