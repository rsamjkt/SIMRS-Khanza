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

 Date: 09/01/2026 20:58:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for antrian_terlewati
-- ----------------------------
DROP TABLE IF EXISTS `antrian_terlewati`;
CREATE TABLE `antrian_terlewati` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(17) NOT NULL,
  `no_reg` varchar(8) NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `kd_poli` varchar(5) NOT NULL,
  `tgl_registrasi` date NOT NULL,
  `user_skip` varchar(50) NOT NULL,
  `waktu_skip` datetime NOT NULL DEFAULT current_timestamp(),
  `alasan_skip` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_no_rawat` (`no_rawat`) USING BTREE,
  KEY `idx_tgl_registrasi` (`tgl_registrasi`) USING BTREE,
  KEY `idx_dokter_poli` (`kd_dokter`,`kd_poli`) USING BTREE,
  KEY `idx_tgl_dokter_poli` (`tgl_registrasi`,`kd_dokter`,`kd_poli`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
