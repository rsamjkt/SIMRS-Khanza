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

 Date: 21/04/2025 14:49:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for template_pemeriksaan_dokter
-- ----------------------------
DROP TABLE IF EXISTS `template_pemeriksaan_dokter`;
CREATE TABLE `template_pemeriksaan_dokter` (
  `no_template` varchar(20) NOT NULL,
  `kd_dokter` varchar(20) DEFAULT NULL,
  `keluhan` varchar(2000) DEFAULT NULL,
  `pemeriksaan` varchar(2000) DEFAULT NULL,
  `penilaian` varchar(2000) DEFAULT NULL,
  `rencana` varchar(2000) NOT NULL,
  `instruksi` varchar(2000) NOT NULL,
  `evaluasi` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`no_template`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `template_pemeriksaan_dokter_ibfk_1` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
