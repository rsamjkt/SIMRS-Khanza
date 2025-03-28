/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 28/03/2025 15:21:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hemodialisa
-- ----------------------------
DROP TABLE IF EXISTS `hemodialisa`;
CREATE TABLE `hemodialisa` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) DEFAULT NULL,
  `hd_ke` varchar(5) DEFAULT NULL,
  `no_mesin` varchar(20) DEFAULT NULL,
  `bb_kering` varchar(10) DEFAULT NULL,
  `bb_hd_terakhir` varchar(10) DEFAULT NULL,
  `lama` varchar(5) DEFAULT NULL,
  `akses` varchar(30) DEFAULT NULL,
  `dializer` varchar(30) DEFAULT NULL,
  `dialisat` varchar(30) DEFAULT NULL,
  `transfusi` varchar(5) DEFAULT NULL,
  `penarikan` varchar(5) DEFAULT NULL,
  `qb` varchar(5) DEFAULT NULL,
  `qd` varchar(5) DEFAULT NULL,
  `ufg` varchar(20) DEFAULT NULL,
  `heparin_awal` varchar(10) DEFAULT NULL,
  `heparin_pemeliharaan` varchar(10) DEFAULT NULL,
  `heparin_sirkulasi` varchar(10) DEFAULT NULL,
  `volume_priming` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`),
  KEY `kd_dokter` (`kd_dokter`),
  KEY `no_rawat` (`no_rawat`),
  CONSTRAINT `hemodialisa_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON UPDATE CASCADE,
  CONSTRAINT `hemodialisa_ibfk_3` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
