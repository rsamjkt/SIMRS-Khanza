/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sik

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 18/04/2025 15:14:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_medicationstatement
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_medicationstatement`;
CREATE TABLE `satu_sehat_medicationstatement` (
  `no_resep` varchar(14) NOT NULL,
  `kode_brng` varchar(15) NOT NULL,
  `id_medicationstatement` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`no_resep`,`kode_brng`),
  KEY `kode_brng` (`kode_brng`),
  CONSTRAINT `satu_sehat_medicationstatement_ibfk_1` FOREIGN KEY (`no_resep`) REFERENCES `resep_obat` (`no_resep`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `satu_sehat_medicationstatement_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for satu_sehat_medicationstatement_racikan
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_medicationstatement_racikan`;
CREATE TABLE `satu_sehat_medicationstatement_racikan` (
  `no_resep` varchar(14) NOT NULL,
  `kode_brng` varchar(15) NOT NULL,
  `no_racik` varchar(2) NOT NULL,
  `id_medicationstatement` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`no_resep`,`kode_brng`,`no_racik`),
  KEY `kode_brng` (`kode_brng`),
  CONSTRAINT `satu_sehat_medicationstatement_racikan_ibfk_1` FOREIGN KEY (`no_resep`) REFERENCES `resep_obat` (`no_resep`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `satu_sehat_medicationstatement_racikan_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
