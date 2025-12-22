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

 Date: 18/12/2025 11:19:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for template_soap
-- ----------------------------
DROP TABLE IF EXISTS `template_soap`;
CREATE TABLE `template_soap` (
  `id_template` int(11) NOT NULL AUTO_INCREMENT,
  `nama_template` varchar(50) NOT NULL,
  `keluhan` text NOT NULL,
  `pemeriksaan` text NOT NULL,
  `penilaian` text NOT NULL,
  `rtl` text NOT NULL,
  `instruksi` text NOT NULL,
  `evaluasi` text NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  PRIMARY KEY (`id_template`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
