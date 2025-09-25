/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy3

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 23/09/2025 10:08:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for penilaian_medis_ralan_mata
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_medis_ralan_mata`;
CREATE TABLE `penilaian_medis_ralan_mata` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `anamnesis` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `hubungan` varchar(30) NOT NULL,
  `keluhan_utama` varchar(2000) NOT NULL DEFAULT '',
  `rps` varchar(2000) NOT NULL,
  `rpd` varchar(1000) NOT NULL DEFAULT '',
  `rpo` varchar(1000) NOT NULL,
  `alergi` varchar(50) NOT NULL DEFAULT '',
  `status` varchar(50) NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `rr` varchar(5) NOT NULL,
  `suhu` varchar(5) NOT NULL DEFAULT '',
  `nyeri` varchar(50) NOT NULL,
  `bb` varchar(5) NOT NULL DEFAULT '',
  `visuskanan` varchar(100) NOT NULL,
  `visuskiri` varchar(100) NOT NULL,
  `cckanan` varchar(100) NOT NULL,
  `cckiri` varchar(100) NOT NULL,
  `palkanan` varchar(100) NOT NULL,
  `palkiri` varchar(100) NOT NULL,
  `conkanan` varchar(100) NOT NULL,
  `conkiri` varchar(100) NOT NULL,
  `corneakanan` varchar(100) NOT NULL,
  `corneakiri` varchar(100) NOT NULL,
  `coakanan` varchar(100) NOT NULL,
  `coakiri` varchar(100) NOT NULL,
  `pupilkanan` varchar(100) NOT NULL,
  `pupilkiri` varchar(100) NOT NULL,
  `lensakanan` varchar(100) NOT NULL,
  `lensakiri` varchar(100) NOT NULL,
  `funduskanan` varchar(100) NOT NULL,
  `funduskiri` varchar(100) NOT NULL,
  `papilkanan` varchar(100) NOT NULL,
  `papilkiri` varchar(100) NOT NULL,
  `retinakanan` varchar(100) NOT NULL,
  `retinakiri` varchar(100) NOT NULL,
  `makulakanan` varchar(100) NOT NULL,
  `makulakiri` varchar(100) NOT NULL,
  `tiokanan` varchar(100) NOT NULL,
  `tiokiri` varchar(100) NOT NULL,
  `mbokanan` varchar(100) NOT NULL,
  `mbokiri` varchar(100) NOT NULL,
  `lab` text NOT NULL,
  `rad` text NOT NULL,
  `penunjang` text NOT NULL,
  `tes` text NOT NULL,
  `pemeriksaan` text NOT NULL,
  `diagnosis` varchar(500) NOT NULL,
  `diagnosisbdg` varchar(500) NOT NULL,
  `permasalahan` text NOT NULL,
  `terapi` text NOT NULL,
  `tindakan` text NOT NULL,
  `edukasi` varchar(1000) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_medis_ralan_mata_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_medis_ralan_mata_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
