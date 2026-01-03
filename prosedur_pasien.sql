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

 Date: 02/01/2026 18:16:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for prosedur_pasien
-- ----------------------------
DROP TABLE IF EXISTS `prosedur_pasien`;
CREATE TABLE `prosedur_pasien` (
  `no_rawat` varchar(17) NOT NULL,
  `kode` varchar(8) NOT NULL,
  `status` enum('Ralan','Ranap') NOT NULL,
  `prioritas` tinyint(4) NOT NULL,
  `jumlah` varchar(3) NOT NULL,
  PRIMARY KEY (`no_rawat`,`kode`,`status`),
  KEY `kode` (`kode`) USING BTREE,
  CONSTRAINT `prosedur_pasien_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `prosedur_pasien_ibfk_2` FOREIGN KEY (`kode`) REFERENCES `icd9` (`kode`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
