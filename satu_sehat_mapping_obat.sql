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

 Date: 09/12/2025 12:07:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_mapping_obat
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_mapping_obat`;
CREATE TABLE `satu_sehat_mapping_obat` (
  `kode_brng` varchar(15) NOT NULL,
  `obat_code` varchar(15) DEFAULT NULL,
  `obat_system` varchar(100) NOT NULL,
  `obat_display` varchar(80) DEFAULT NULL,
  `form_code` varchar(30) DEFAULT NULL,
  `form_system` varchar(100) DEFAULT NULL,
  `form_display` varchar(80) DEFAULT NULL,
  `numerator_code` varchar(15) DEFAULT NULL,
  `numerator_system` varchar(80) DEFAULT NULL,
  `denominator_code` varchar(15) DEFAULT NULL,
  `denominator_system` varchar(80) DEFAULT NULL,
  `route_code` varchar(30) DEFAULT NULL,
  `route_system` varchar(100) DEFAULT NULL,
  `route_display` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`kode_brng`) USING BTREE,
  CONSTRAINT `satu_sehat_mapping_obat_ibfk_1` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
