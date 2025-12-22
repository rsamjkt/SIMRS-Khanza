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

 Date: 26/11/2025 14:31:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jawaban_konsultasi_medik
-- ----------------------------
DROP TABLE IF EXISTS `jawaban_konsultasi_medik`;
CREATE TABLE `jawaban_konsultasi_medik` (
  `no_permintaan` varchar(20) NOT NULL,
  `tanggal` datetime NOT NULL,
  `diagnosa_kerja` varchar(200) DEFAULT NULL,
  `uraian_jawaban` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`no_permintaan`) USING BTREE,
  CONSTRAINT `jawaban_konsultasi_medik_ibfk_1` FOREIGN KEY (`no_permintaan`) REFERENCES `konsultasi_medik` (`no_permintaan`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for konsultasi_medik
-- ----------------------------
DROP TABLE IF EXISTS `konsultasi_medik`;
CREATE TABLE `konsultasi_medik` (
  `no_permintaan` varchar(20) NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `jenis_permintaan` enum('Konsultasi','Evaluasi','Rawat Bersama','Alih Rawat','Pre/Post Operasi') DEFAULT NULL,
  `kd_dokter` varchar(20) DEFAULT NULL,
  `kd_dokter_dikonsuli` varchar(20) DEFAULT NULL,
  `diagnosa_kerja` varchar(200) DEFAULT NULL,
  `uraian_konsultasi` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`no_permintaan`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  KEY `kd_dokter_dikonsuli` (`kd_dokter_dikonsuli`) USING BTREE,
  CONSTRAINT `konsultasi_medik_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `konsultasi_medik_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `konsultasi_medik_ibfk_3` FOREIGN KEY (`kd_dokter_dikonsuli`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
