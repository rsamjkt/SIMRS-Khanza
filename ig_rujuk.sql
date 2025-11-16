/*
 Navicat Premium Data Transfer

 Source Server         : rsip root
 Source Server Type    : MariaDB
 Source Server Version : 100420 (10.4.20-MariaDB)
 Source Host           : 172.26.4.205:3306
 Source Schema         : khanza_rsip

 Target Server Type    : MariaDB
 Target Server Version : 100420 (10.4.20-MariaDB)
 File Encoding         : 65001

 Date: 24/10/2025 15:48:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ig_rujuk
-- ----------------------------
DROP TABLE IF EXISTS `ig_rujuk`;
CREATE TABLE `ig_rujuk` (
  `no_rujuk` varchar(40) NOT NULL,
  `no_rawat` varchar(17) DEFAULT NULL,
  `rujuk_ke` varchar(150) DEFAULT NULL,
  `tgl_rujuk` date DEFAULT NULL,
  `keterangan_diagnosa` text DEFAULT NULL,
  `kd_dokter` varchar(20) DEFAULT NULL,
  `unit` varchar(100) DEFAULT NULL,
  `ambulance` enum('-','Ambulan','Mobil Pribadi','AGD','SENDIRI','Lain-Lain') DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  `jam` time DEFAULT NULL,
  `pengobatan` text NOT NULL,
  `keadaan` text NOT NULL,
  `alasan` enum('Tempat Penuh','Sesuai Permintaan Pasien','Pemeriksaan Penunjang/Alat Tidak Tersedia','Kebutuhan Pelayanan Lebih Lanjut','Lain-Lain') NOT NULL,
  `tsdr` varchar(100) DEFAULT NULL,
  `penerima` varchar(100) DEFAULT NULL,
  KEY `kd_dokter` (`kd_dokter`),
  KEY `no_rawat` (`no_rawat`),
  KEY `rujuk_ke` (`rujuk_ke`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
