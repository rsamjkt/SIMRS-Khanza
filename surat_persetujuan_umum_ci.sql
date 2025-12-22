/*
 Navicat Premium Data Transfer

 Source Server         : ci-zero
 Source Server Type    : MySQL
 Source Server Version : 100338 (10.3.38-MariaDB-0ubuntu0.20.04.1-log)
 Source Host           : 192.168.196.251:3306
 Source Schema         : sikci2

 Target Server Type    : MySQL
 Target Server Version : 100338 (10.3.38-MariaDB-0ubuntu0.20.04.1-log)
 File Encoding         : 65001

 Date: 18/11/2025 09:36:11
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
  `bertindak_atas` enum('Suami','Istri','Anak','Ayah','Saudara','Keponakan','Cucu','Kakek','Nenek','Kakak','Adik','Diri Sendiri','Ibu') NOT NULL,
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
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  CONSTRAINT `surat_persetujuan_umum_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `surat_persetujuan_umum_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
