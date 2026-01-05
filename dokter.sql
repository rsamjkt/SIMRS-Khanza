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

 Date: 04/01/2026 20:36:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dokter
-- ----------------------------
DROP TABLE IF EXISTS `dokter`;
CREATE TABLE `dokter` (
  `kd_dokter` varchar(20) NOT NULL,
  `nm_dokter` varchar(50) DEFAULT NULL,
  `jk` enum('L','P') DEFAULT NULL,
  `tmp_lahir` varchar(20) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `gol_drh` enum('A','B','O','AB','-') DEFAULT NULL,
  `agama` varchar(12) DEFAULT NULL,
  `almt_tgl` varchar(60) DEFAULT NULL,
  `no_telp` varchar(13) DEFAULT NULL,
  `email` varchar(70) NOT NULL,
  `stts_nikah` enum('BELUM MENIKAH','MENIKAH','JANDA','DUDHA','JOMBLO') DEFAULT NULL,
  `kd_sps` char(5) DEFAULT NULL,
  `alumni` varchar(60) DEFAULT NULL,
  `no_ijn_praktek` varchar(120) DEFAULT NULL,
  `status` enum('0','1') NOT NULL,
  PRIMARY KEY (`kd_dokter`),
  KEY `kd_sps` (`kd_sps`) USING BTREE,
  KEY `nm_dokter` (`nm_dokter`) USING BTREE,
  KEY `jk` (`jk`) USING BTREE,
  KEY `tmp_lahir` (`tmp_lahir`) USING BTREE,
  KEY `tgl_lahir` (`tgl_lahir`) USING BTREE,
  KEY `gol_drh` (`gol_drh`) USING BTREE,
  KEY `agama` (`agama`) USING BTREE,
  KEY `almt_tgl` (`almt_tgl`) USING BTREE,
  KEY `no_telp` (`no_telp`) USING BTREE,
  KEY `stts_nikah` (`stts_nikah`) USING BTREE,
  KEY `alumni` (`alumni`) USING BTREE,
  KEY `no_ijn_praktek` (`no_ijn_praktek`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  CONSTRAINT `dokter_ibfk_2` FOREIGN KEY (`kd_sps`) REFERENCES `spesialis` (`kd_sps`) ON UPDATE CASCADE,
  CONSTRAINT `dokter_ibfk_3` FOREIGN KEY (`kd_dokter`) REFERENCES `pegawai` (`nik`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
