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

 Date: 10/12/2025 17:41:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for signin_sebelum_anestesi
-- ----------------------------
DROP TABLE IF EXISTS `signin_sebelum_anestesi`;
CREATE TABLE `signin_sebelum_anestesi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `sncn` varchar(25) NOT NULL,
  `tindakan` varchar(50) NOT NULL,
  `kd_dokter_bedah` varchar(20) NOT NULL,
  `kd_dokter_anestesi` varchar(20) NOT NULL,
  `identitas` enum('Ya','Tidak') DEFAULT NULL,
  `penandaan_area_operasi` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `alergi` varchar(30) DEFAULT NULL,
  `resiko_aspirasi` enum('Ada','Tidak Ada') DEFAULT NULL,
  `resiko_aspirasi_rencana_antisipasi` varchar(50) DEFAULT NULL,
  `resiko_kehilangan_darah` enum('Tidak Ada','Ada') DEFAULT NULL,
  `resiko_kehilangan_darah_line` varchar(30) DEFAULT NULL,
  `resiko_kehilangan_darah_rencana_antisipasi` varchar(50) DEFAULT NULL,
  `kesiapan_alat_obat_anestesi` enum('Lengkap','Pulsa Oximetri','Tidak Lengkap') DEFAULT NULL,
  `kesiapan_alat_obat_anestesi_rencana_antisipasi` varchar(50) DEFAULT NULL,
  `nip_perawat_ok` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `nip_perawat_ok` (`nip_perawat_ok`) USING BTREE,
  KEY `kd_dokter_anestesi` (`kd_dokter_anestesi`) USING BTREE,
  KEY `kd_dokter_bedah` (`kd_dokter_bedah`) USING BTREE,
  CONSTRAINT `signin_sebelum_anestesi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `signin_sebelum_anestesi_ibfk_2` FOREIGN KEY (`nip_perawat_ok`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `signin_sebelum_anestesi_ibfk_3` FOREIGN KEY (`kd_dokter_anestesi`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `signin_sebelum_anestesi_ibfk_4` FOREIGN KEY (`kd_dokter_bedah`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
