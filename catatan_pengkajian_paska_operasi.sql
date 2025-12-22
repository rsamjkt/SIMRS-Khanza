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

 Date: 10/12/2025 17:13:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for catatan_pengkajian_paska_operasi
-- ----------------------------
DROP TABLE IF EXISTS `catatan_pengkajian_paska_operasi`;
CREATE TABLE `catatan_pengkajian_paska_operasi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `rawat_paska_operasi` varchar(250) DEFAULT NULL,
  `cairan` varchar(500) DEFAULT NULL,
  `antibiotika` varchar(500) DEFAULT NULL,
  `analgetika` varchar(500) DEFAULT NULL,
  `medikamentosa_lain` varchar(500) DEFAULT NULL,
  `diet` varchar(500) DEFAULT NULL,
  `pemeriksaan_laborat` varchar(500) DEFAULT NULL,
  `tranfusi` varchar(250) DEFAULT NULL,
  `lainlain` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `catatan_pengkajian_paska_operasi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catatan_pengkajian_paska_operasi_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
