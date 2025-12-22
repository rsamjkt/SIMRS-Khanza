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

 Date: 10/12/2025 15:38:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for penilaian_pre_anestesi
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_pre_anestesi`;
CREATE TABLE `penilaian_pre_anestesi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `tanggal_operasi` datetime DEFAULT NULL,
  `diagnosa` varchar(100) DEFAULT NULL,
  `rencana_tindakan` varchar(100) DEFAULT NULL,
  `tb` varchar(5) NOT NULL DEFAULT '',
  `bb` varchar(5) NOT NULL DEFAULT '',
  `td` varchar(8) NOT NULL DEFAULT '',
  `io2` varchar(5) NOT NULL,
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `pernapasan` varchar(5) NOT NULL,
  `suhu` varchar(5) NOT NULL DEFAULT '',
  `fisik_cardiovasculer` varchar(100) DEFAULT NULL,
  `fisik_paru` varchar(100) DEFAULT NULL,
  `fisik_abdomen` varchar(100) DEFAULT NULL,
  `fisik_extrimitas` varchar(100) DEFAULT NULL,
  `fisik_endokrin` varchar(100) DEFAULT NULL,
  `fisik_ginjal` varchar(100) DEFAULT NULL,
  `fisik_obatobatan` varchar(100) DEFAULT NULL,
  `fisik_laborat` varchar(100) DEFAULT NULL,
  `fisik_penunjang` varchar(100) DEFAULT NULL,
  `riwayat_penyakit_alergiobat` varchar(50) DEFAULT NULL,
  `riwayat_penyakit_alergilainnya` varchar(50) DEFAULT NULL,
  `riwayat_penyakit_terapi` varchar(100) DEFAULT NULL,
  `riwayat_kebiasaan_merokok` enum('Tidak','Ya') NOT NULL,
  `riwayat_kebiasaan_ket_merokok` varchar(5) NOT NULL,
  `riwayat_kebiasaan_alkohol` enum('Tidak','Ya') NOT NULL,
  `riwayat_kebiasaan_ket_alkohol` varchar(5) NOT NULL,
  `riwayat_kebiasaan_obat` enum('-','Obat Obatan','Vitamin','Jamu Jamuan') NOT NULL,
  `riwayat_kebiasaan_ket_obat` varchar(100) NOT NULL,
  `riwayat_medis_cardiovasculer` varchar(100) DEFAULT NULL,
  `riwayat_medis_respiratory` varchar(100) DEFAULT NULL,
  `riwayat_medis_endocrine` varchar(100) DEFAULT NULL,
  `riwayat_medis_lainnya` varchar(100) DEFAULT NULL,
  `asa` enum('1','2','3','4','5','E') DEFAULT NULL,
  `puasa` datetime DEFAULT NULL,
  `rencana_anestesi` enum('GA','RA Spinal','RA Epidural','RA Combined','Blok Syaraf') DEFAULT NULL,
  `rencana_perawatan` varchar(40) DEFAULT NULL,
  `catatan_khusus` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_pre_anestesi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_pre_anestesi_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
