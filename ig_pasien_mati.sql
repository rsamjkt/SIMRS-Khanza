/*
 Navicat Premium Data Transfer

 Source Server         : rsip local
 Source Server Type    : MariaDB
 Source Server Version : 100428 (10.4.28-MariaDB)
 Source Host           : 192.168.100.150:3306
 Source Schema         : khanza_rsip

 Target Server Type    : MariaDB
 Target Server Version : 100428 (10.4.28-MariaDB)
 File Encoding         : 65001

 Date: 15/09/2025 12:17:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ig_pasien_mati
-- ----------------------------
DROP TABLE IF EXISTS `ig_pasien_mati`;
CREATE TABLE `ig_pasien_mati` (
  `no_urut_mati` varchar(100) NOT NULL,
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
  `tgl_periksa` date NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `pemeriksa_jenazah` enum('-','DOKTER','PARAMEDIS') NOT NULL,
  `penyebab_lansung` varchar(100) DEFAULT NULL,
  `penyebab_mendasar` varchar(100) NOT NULL,
  `penyebab_utamabayi` varchar(100) NOT NULL,
  `penyebab_utamaibu` varchar(100) NOT NULL,
  PRIMARY KEY (`no_rkm_medis`),
  KEY `kd_dokter` (`kd_dokter`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
