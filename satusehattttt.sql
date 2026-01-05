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

 Date: 04/01/2026 20:35:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_careplan
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_careplan`;
CREATE TABLE `satu_sehat_careplan` (
  `no_rawat` varchar(17) NOT NULL,
  `tgl_perawatan` date NOT NULL,
  `jam_rawat` time NOT NULL,
  `status` enum('Ralan','Ranap') NOT NULL,
  `id_careplan` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tgl_perawatan`,`jam_rawat`,`status`) USING BTREE,
  CONSTRAINT `satu_sehat_careplan_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for satu_sehat_clinicalimpression
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_clinicalimpression`;
CREATE TABLE `satu_sehat_clinicalimpression` (
  `no_rawat` varchar(17) NOT NULL,
  `tgl_perawatan` date NOT NULL,
  `jam_rawat` time NOT NULL,
  `status` enum('Ralan','Ranap') NOT NULL,
  `id_clinicalimpression` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tgl_perawatan`,`jam_rawat`,`status`) USING BTREE,
  CONSTRAINT `satu_sehat_clinicalimpression_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for satu_sehat_condition
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_condition`;
CREATE TABLE `satu_sehat_condition` (
  `no_rawat` varchar(17) NOT NULL,
  `kd_penyakit` varchar(15) NOT NULL,
  `status` enum('Ralan','Ranap') NOT NULL,
  `id_condition` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`kd_penyakit`,`status`) USING BTREE,
  KEY `kd_penyakit` (`kd_penyakit`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  CONSTRAINT `satu_sehat_condition_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `satu_sehat_condition_ibfk_2` FOREIGN KEY (`kd_penyakit`) REFERENCES `penyakit` (`kd_penyakit`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
