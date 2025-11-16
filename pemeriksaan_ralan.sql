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

 Date: 09/10/2025 14:32:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pemeriksaan_ralan
-- ----------------------------
DROP TABLE IF EXISTS `pemeriksaan_ralan`;
CREATE TABLE `pemeriksaan_ralan` (
  `no_rawat` varchar(17) NOT NULL,
  `tgl_perawatan` date NOT NULL,
  `jam_rawat` time NOT NULL,
  `suhu_tubuh` varchar(5) DEFAULT NULL,
  `tensi` varchar(8) NOT NULL,
  `nadi` varchar(3) DEFAULT NULL,
  `respirasi` varchar(3) DEFAULT NULL,
  `tinggi` varchar(5) DEFAULT NULL,
  `berat` varchar(5) DEFAULT NULL,
  `spo2` varchar(3) NOT NULL,
  `gcs` varchar(10) DEFAULT NULL,
  `kesadaran` enum('Compos Mentis','Somnolence','Sopor','Coma','Alert','Confusion','Voice','Pain','Unresponsive','Apatis','Delirium') NOT NULL,
  `keluhan` varchar(2000) DEFAULT NULL,
  `pemeriksaan` varchar(2000) DEFAULT NULL,
  `alergi` varchar(80) DEFAULT NULL,
  `lingkar_perut` varchar(5) DEFAULT NULL,
  `rtl` varchar(2000) NOT NULL,
  `penilaian` varchar(2000) NOT NULL,
  `instruksi` varchar(2000) NOT NULL,
  `evaluasi` varchar(2000) NOT NULL,
  `nip` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rawat`,`tgl_perawatan`,`jam_rawat`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  CONSTRAINT `pemeriksaan_ralan_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pemeriksaan_ralan_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `pegawai` (`nik`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
