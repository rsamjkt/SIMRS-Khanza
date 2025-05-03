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

 Date: 02/05/2025 18:41:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for surat_persetujuan_umum
-- ----------------------------
DROP TABLE IF EXISTS `surat_persetujuan_umum`;
CREATE TABLE `surat_persetujuan_umum` (
  `no_surat` varchar(20) NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` date NOT NULL,
  `pengobatan_kepada` enum('Suami','Istri','Anak','Ayah','Ibu','Saudara','Keponakan','Adik','Kakak','Orang Tua','Diri Sendiri','-') NOT NULL,
  `nilai_kepercayaan` varchar(50) NOT NULL,
  `nama_pj` varchar(50) NOT NULL,
  `umur_pj` varchar(7) NOT NULL,
  `no_ktppj` varchar(20) NOT NULL,
  `jkpj` enum('L','P') NOT NULL,
  `bertindak_atas` enum('Suami','Istri','Anak','Ayah','Ibu','Saudara','Keponakan','Diri Sendiri','Kakak','Adik','Orang Tua') NOT NULL,
  `no_telp` varchar(30) NOT NULL,
  `nip` varchar(20) NOT NULL,
  `persetujuan_pengobatan` enum('Ya','Tidak') NOT NULL,
  `pelepasan_informasi` enum('Ya','Tidak') NOT NULL,
  `keluarga` varchar(50) NOT NULL,
  `privacy` enum('Mengijinkan','Tidak Mengijinkan') NOT NULL,
  `nama_privacy` varchar(50) NOT NULL,
  `profesi` varchar(50) NOT NULL,
  `kelas_rawat` varchar(50) NOT NULL,
  PRIMARY KEY (`no_surat`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
