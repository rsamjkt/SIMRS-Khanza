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

 Date: 01/01/2026 18:04:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pendidikan
-- ----------------------------
DROP TABLE IF EXISTS `pendidikan`;
CREATE TABLE `pendidikan` (
  `tingkat` varchar(80) NOT NULL,
  `indek` tinyint(4) NOT NULL,
  `gapok1` double NOT NULL,
  `kenaikan` double NOT NULL,
  `maksimal` int(11) NOT NULL,
  PRIMARY KEY (`tingkat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for riwayat_pendidikan
-- ----------------------------
DROP TABLE IF EXISTS `riwayat_pendidikan`;
CREATE TABLE `riwayat_pendidikan` (
  `id` int(11) NOT NULL,
  `pendidikan` enum('SD','SMP','SMA','SMK','D I','D II','D III','D IV','S1','S2','S3','Post Doctor') NOT NULL,
  `sekolah` varchar(50) NOT NULL,
  `jurusan` varchar(40) NOT NULL,
  `thn_lulus` year(4) NOT NULL,
  `kepala` varchar(50) NOT NULL,
  `pendanaan` enum('Biaya Sendiri','Biaya Instansi Sendiri','Lembaga Swasta Kerjasama','Lembaga Swasta Kompetisi','Lembaga Pemerintah Kerjasama','Lembaga Pemerintah Kompetisi','Lembaga Internasional') NOT NULL,
  `keterangan` varchar(50) NOT NULL,
  `status` varchar(40) NOT NULL,
  `berkas` varchar(500) NOT NULL,
  PRIMARY KEY (`id`,`pendidikan`,`sekolah`),
  CONSTRAINT `riwayat_pendidikan_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
