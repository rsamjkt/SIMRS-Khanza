/*
 Navicat Premium Data Transfer

 Source Server         : rsip local
 Source Server Type    : MariaDB
 Source Server Version : 100428 (10.4.28-MariaDB)
 Source Host           : 192.168.100.150:3306
 Source Schema         : khanza_rsip

 Target Server Type    : MariaDB
 Target Server Version : 100428 (10.4.28-MariaDB)
 File Encoding         : 65001

 Date: 22/09/2025 11:55:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permintaan_pemeriksaan_radiologi
-- ----------------------------
DROP TABLE IF EXISTS `permintaan_pemeriksaan_radiologi`;
CREATE TABLE `permintaan_pemeriksaan_radiologi` (
  `noorder` varchar(15) NOT NULL,
  `kd_jenis_prw` varchar(15) NOT NULL,
  `stts_bayar` enum('Sudah','Belum') DEFAULT NULL,
  PRIMARY KEY (`noorder`,`kd_jenis_prw`),
  KEY `kd_jenis_prw` (`kd_jenis_prw`),
  CONSTRAINT `permintaan_pemeriksaan_radiologi_ibfk_1` FOREIGN KEY (`noorder`) REFERENCES `permintaan_radiologi` (`noorder`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `permintaan_pemeriksaan_radiologi_ibfk_2` FOREIGN KEY (`kd_jenis_prw`) REFERENCES `jns_perawatan_radiologi` (`kd_jenis_prw`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for permintaan_radiologi
-- ----------------------------
DROP TABLE IF EXISTS `permintaan_radiologi`;
CREATE TABLE `permintaan_radiologi` (
  `noorder` varchar(15) NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  `tgl_permintaan` date NOT NULL,
  `jam_permintaan` time NOT NULL,
  `tgl_sampel` date NOT NULL,
  `jam_sampel` time NOT NULL,
  `tgl_hasil` date NOT NULL,
  `jam_hasil` time NOT NULL,
  `dokter_perujuk` varchar(20) NOT NULL,
  `status` enum('ralan','ranap') NOT NULL,
  `informasi_tambahan` varchar(60) NOT NULL,
  `diagnosa_klinis` varchar(80) NOT NULL,
  PRIMARY KEY (`noorder`),
  KEY `dokter_perujuk` (`dokter_perujuk`),
  KEY `no_rawat` (`no_rawat`),
  CONSTRAINT `permintaan_radiologi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `permintaan_radiologi_ibfk_3` FOREIGN KEY (`dokter_perujuk`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
