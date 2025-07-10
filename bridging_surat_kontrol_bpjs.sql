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

 Date: 07/07/2025 16:28:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bridging_surat_kontrol_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `bridging_surat_kontrol_bpjs`;
CREATE TABLE `bridging_surat_kontrol_bpjs` (
  `no_sep` varchar(40) DEFAULT NULL,
  `tgl_surat` date NOT NULL,
  `no_surat` varchar(40) NOT NULL,
  `tgl_rencana` date DEFAULT NULL,
  `kd_dokter_bpjs` varchar(20) DEFAULT NULL,
  `nm_dokter_bpjs` varchar(50) DEFAULT NULL,
  `kd_poli_bpjs` varchar(15) DEFAULT NULL,
  `nm_poli_bpjs` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`no_surat`) USING BTREE,
  KEY `bridging_surat_kontrol_bpjs_ibfk_1` (`no_sep`) USING BTREE,
  CONSTRAINT `bridging_surat_kontrol_bpjs_ibfk_1` FOREIGN KEY (`no_sep`) REFERENCES `bridging_sep` (`no_sep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
