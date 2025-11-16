/*
 Navicat Premium Data Transfer

 Source Server         : produ
 Source Server Type    : MySQL
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.5:3306
 Source Schema         : sikrs4m2106

 Target Server Type    : MySQL
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 25/09/2025 12:09:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for laporan_operasi
-- ----------------------------
DROP TABLE IF EXISTS `laporan_operasi`;
CREATE TABLE `laporan_operasi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `diagnosa_preop` varchar(100) NOT NULL,
  `diagnosa_postop` varchar(100) NOT NULL,
  `jaringan_dieksekusi` varchar(100) NOT NULL,
  `selesaioperasi` datetime NOT NULL,
  `permintaan_pa` enum('Ya','Tidak') NOT NULL,
  `laporan_operasi` text NOT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  CONSTRAINT `laporan_operasi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
