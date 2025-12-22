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

 Date: 12/12/2025 13:27:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for penilaian_medis_igd
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_medis_igd`;
CREATE TABLE `penilaian_medis_igd` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `anamnesis` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `hubungan` varchar(100) NOT NULL,
  `keluhan_utama` varchar(2000) NOT NULL DEFAULT '',
  `rps` varchar(2000) NOT NULL,
  `rpd` varchar(1000) NOT NULL DEFAULT '',
  `rpk` varchar(1000) NOT NULL,
  `rpo` varchar(1000) NOT NULL,
  `alergi` varchar(100) NOT NULL DEFAULT '',
  `keadaan` enum('Sehat','Sakit Ringan','Sakit Sedang','Sakit Berat') NOT NULL,
  `gcs` varchar(10) NOT NULL,
  `kesadaran` enum('Compos Mentis','Apatis','Somnolen','Sopor','Koma') NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `rr` varchar(5) NOT NULL,
  `suhu` varchar(5) NOT NULL DEFAULT '',
  `spo` varchar(5) NOT NULL,
  `bb` varchar(5) NOT NULL DEFAULT '',
  `tb` varchar(5) NOT NULL DEFAULT '',
  `kepala` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `mata` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `gigi` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `leher` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `thoraks` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `abdomen` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `genital` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `ekstremitas` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `ket_fisik` text NOT NULL,
  `ket_lokalis` text NOT NULL,
  `ekg` text NOT NULL,
  `rad` text NOT NULL,
  `lab` text NOT NULL,
  `diagnosis` varchar(500) NOT NULL,
  `tata` text NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_medis_igd_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_medis_igd_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
