/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy3

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 05/08/2025 13:04:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mlite_vedika
-- ----------------------------
DROP TABLE IF EXISTS `mlite_vedika`;
CREATE TABLE `mlite_vedika` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tanggal` date DEFAULT NULL,
  `no_rkm_medis` varchar(6) NOT NULL,
  `no_rawat` varchar(100) NOT NULL,
  `tgl_registrasi` varchar(100) NOT NULL,
  `nosep` varchar(40) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `jenis` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Table structure for mlite_vedika_dokumen_verifikasi
-- ----------------------------
DROP TABLE IF EXISTS `mlite_vedika_dokumen_verifikasi`;
CREATE TABLE `mlite_vedika_dokumen_verifikasi` (
  `no_rawat` varchar(17) NOT NULL,
  `kode_dokumen` varchar(50) NOT NULL,
  `status_verifikasi` varchar(20) DEFAULT NULL,
  `catatan` text DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `tanggal_update` datetime DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`kode_dokumen`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for mlite_vedika_feedback
-- ----------------------------
DROP TABLE IF EXISTS `mlite_vedika_feedback`;
CREATE TABLE `mlite_vedika_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nosep` varchar(100) NOT NULL,
  `tanggal` date DEFAULT NULL,
  `catatan` text DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Table structure for mlite_vedika_status_history
-- ----------------------------
DROP TABLE IF EXISTS `mlite_vedika_status_history`;
CREATE TABLE `mlite_vedika_status_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nosep` varchar(40) DEFAULT NULL,
  `no_rawat` varchar(17) DEFAULT NULL,
  `status_lama` varchar(50) DEFAULT NULL,
  `status_baru` varchar(50) NOT NULL,
  `catatan` text DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `tanggal` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `nosep` (`nosep`) USING BTREE,
  KEY `tanggal` (`tanggal`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
