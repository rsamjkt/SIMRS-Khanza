/*
 Navicat Premium Data Transfer

 Source Server         : PermataIbunda
 Source Server Type    : MariaDB
 Source Server Version : 100420 (10.4.20-MariaDB)
 Source Host           : 172.26.69.18:3306
 Source Schema         : sik

 Target Server Type    : MariaDB
 Target Server Version : 100420 (10.4.20-MariaDB)
 File Encoding         : 65001

 Date: 03/04/2025 12:43:35
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
