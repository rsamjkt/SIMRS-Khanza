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

 Date: 07/05/2025 15:06:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for antripernyataanpembiayaantindakan
-- ----------------------------
DROP TABLE IF EXISTS `antripernyataanpembiayaantindakan`;
CREATE TABLE `antripernyataanpembiayaantindakan` (
  `no_surat` varchar(20) NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  PRIMARY KEY (`no_surat`),
  KEY `no_rawat` (`no_rawat`),
  CONSTRAINT `antripernyataanpembiayaantindakan_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `antripernyataanpembiayaantindakan_ibfk_2` FOREIGN KEY (`no_surat`) REFERENCES `surat_pernyataan_pembiayaan_tindakan` (`no_surat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for surat_pernyataan_pembiayaan_tindakan
-- ----------------------------
DROP TABLE IF EXISTS `surat_pernyataan_pembiayaan_tindakan`;
CREATE TABLE `surat_pernyataan_pembiayaan_tindakan` (
  `no_surat` varchar(20) NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` date NOT NULL,
  `nama_pj` varchar(50) NOT NULL,
  `no_ktppj` varchar(20) NOT NULL,
  `tempat_lahirpj` varchar(20) NOT NULL,
  `lahirpj` date NOT NULL,
  `jkpj` enum('L','P') NOT NULL,
  `alamatpj` varchar(100) NOT NULL,
  `hubungan` enum('Suami','Istri','Anak','Ayah','Saudara','Keponakan') NOT NULL,
  `no_telp` varchar(30) NOT NULL,
  `nip` varchar(20) NOT NULL,
  PRIMARY KEY (`no_surat`),
  KEY `no_rawat` (`no_rawat`),
  KEY `nip` (`nip`),
  CONSTRAINT `surat_pernyataan_pembiayaan_tindakan_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `surat_pernyataan_pembiayaan_tindakan_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for surat_pernyataan_pembiayaan_tindakan_pembuat_pernyataan
-- ----------------------------
DROP TABLE IF EXISTS `surat_pernyataan_pembiayaan_tindakan_pembuat_pernyataan`;
CREATE TABLE `surat_pernyataan_pembiayaan_tindakan_pembuat_pernyataan` (
  `no_surat` varchar(20) NOT NULL,
  `photo` varchar(500) NOT NULL,
  PRIMARY KEY (`no_surat`),
  CONSTRAINT `surat_pernyataan_pembiayaan_tindakan_pembuat_pernyataan_ibfk_1` FOREIGN KEY (`no_surat`) REFERENCES `surat_pernyataan_pembiayaan_tindakan` (`no_surat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
