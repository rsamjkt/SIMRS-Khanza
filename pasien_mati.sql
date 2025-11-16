/*
 Navicat Premium Data Transfer

 Source Server         : diatrans-tunnel
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 101.255.3.45:3380
 Source Schema         : sik_diatrans

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 06/10/2025 15:59:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pasien_mati
-- ----------------------------
DROP TABLE IF EXISTS `pasien_mati`;
CREATE TABLE `pasien_mati` (
  `tanggal` date DEFAULT NULL,
  `jam` time DEFAULT NULL,
  `no_rkm_medis` varchar(15) NOT NULL DEFAULT '',
  `keterangan` varchar(100) DEFAULT NULL,
  `temp_meninggal` enum('-','Rumah Sakit','Puskesmas','Rumah Bersalin','Rumah Tempat Tinggal','Lain-lain (Termasuk Doa)','Tidak tahu') DEFAULT NULL,
  `icd1` varchar(20) DEFAULT NULL,
  `icd2` varchar(20) DEFAULT NULL,
  `icd3` varchar(20) DEFAULT NULL,
  `icd4` varchar(20) DEFAULT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rkm_medis`),
  KEY `kd_dokter` (`kd_dokter`),
  CONSTRAINT `pasien_mati_ibfk_1` FOREIGN KEY (`no_rkm_medis`) REFERENCES `pasien` (`no_rkm_medis`) ON UPDATE CASCADE,
  CONSTRAINT `pasien_mati_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
