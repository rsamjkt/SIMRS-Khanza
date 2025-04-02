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

 Date: 02/04/2025 11:39:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for catatan_observasi_hemodialisa
-- ----------------------------
DROP TABLE IF EXISTS `catatan_observasi_hemodialisa`;
CREATE TABLE `catatan_observasi_hemodialisa` (
  `no_rawat` varchar(17) NOT NULL,
  `tgl_perawatan` date NOT NULL,
  `jam_rawat` time NOT NULL,
  `qb` varchar(10) DEFAULT NULL,
  `qd` varchar(8) NOT NULL,
  `tekanan_arteri` varchar(5) DEFAULT NULL,
  `tekanan_vena` varchar(5) DEFAULT NULL,
  `tmp` varchar(5) DEFAULT NULL,
  `ufr` varchar(5) DEFAULT NULL,
  `tensi` varchar(8) DEFAULT NULL,
  `nadi` varchar(6) DEFAULT NULL,
  `suhu` varchar(5) DEFAULT NULL,
  `spo2` varchar(5) DEFAULT NULL,
  `tindakan` varchar(100) DEFAULT NULL,
  `ufg` varchar(10) DEFAULT NULL,
  `nip` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rawat`,`tgl_perawatan`,`jam_rawat`),
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  CONSTRAINT `catatan_observasi_hemodialisa_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catatan_observasi_hemodialisa_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
