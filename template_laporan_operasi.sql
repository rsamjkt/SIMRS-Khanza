/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy4

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 11/12/2025 12:44:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for template_laporan_operasi
-- ----------------------------
DROP TABLE IF EXISTS `template_laporan_operasi`;
CREATE TABLE `template_laporan_operasi` (
  `no_template` varchar(5) NOT NULL,
  `nama_operasi` varchar(100) NOT NULL,
  `diagnosa_preop` varchar(100) NOT NULL,
  `diagnosa_postop` varchar(100) NOT NULL,
  `jaringan_dieksisi` varchar(100) NOT NULL,
  `permintaan_pa` enum('Ya','Tidak') NOT NULL,
  `laporan_operasi` text NOT NULL,
  PRIMARY KEY (`no_template`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
