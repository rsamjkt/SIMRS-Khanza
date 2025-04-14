/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy2

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 11/04/2025 16:41:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for surat_pernyataan_pembiayaan_naik_kelas_bukti
-- ----------------------------
DROP TABLE IF EXISTS `surat_pernyataan_pembiayaan_naik_kelas_bukti`;
CREATE TABLE `surat_pernyataan_pembiayaan_naik_kelas_bukti` (
  `no_surat` varchar(20) NOT NULL COMMENT 'Nomor surat pernyataan, FK ke surat_pernyataan_pembiayaan_naik_kelas',
  `photo` varchar(255) DEFAULT NULL COMMENT 'Nama file atau path gambar bukti persetujuan',
  PRIMARY KEY (`no_surat`),
  CONSTRAINT `fk_spnkb_pernyataan` FOREIGN KEY (`no_surat`) REFERENCES `surat_pernyataan_pembiayaan_naik_kelas` (`no_surat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
