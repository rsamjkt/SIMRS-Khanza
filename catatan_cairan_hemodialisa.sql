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

 Date: 28/03/2025 15:24:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for catatan_cairan_hemodialisa
-- ----------------------------
DROP TABLE IF EXISTS `catatan_cairan_hemodialisa`;
CREATE TABLE `catatan_cairan_hemodialisa` (
  `no_rawat` varchar(17) NOT NULL,
  `tgl_perawatan` date NOT NULL,
  `jam_rawat` time NOT NULL,
  `minum` varchar(10) DEFAULT NULL,
  `infus` varchar(10) DEFAULT NULL,
  `tranfusi` varchar(10) DEFAULT NULL,
  `sisa_priming` varchar(10) DEFAULT NULL,
  `wash_out` varchar(10) DEFAULT NULL,
  `urine` varchar(10) DEFAULT NULL,
  `pendarahan` varchar(10) DEFAULT NULL,
  `muntah` varchar(10) DEFAULT NULL,
  `keterangan` varchar(1000) DEFAULT NULL,
  `nip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tgl_perawatan`,`jam_rawat`),
  KEY `no_rawat` (`no_rawat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
