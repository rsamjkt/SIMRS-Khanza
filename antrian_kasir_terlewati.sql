/*
 Navicat Premium Data Transfer

 Source Server         : Server Dhia
 Source Server Type    : MySQL
 Source Server Version : 100618 (10.6.18-MariaDB-0ubuntu0.22.04.1)
 Source Host           : 172.26.102.185:3306
 Source Schema         : sik

 Target Server Type    : MySQL
 Target Server Version : 100618 (10.6.18-MariaDB-0ubuntu0.22.04.1)
 File Encoding         : 65001

 Date: 05/08/2025 15:13:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for antrian_kasir_terlewati
-- ----------------------------
DROP TABLE IF EXISTS `antrian_kasir_terlewati`;
CREATE TABLE `antrian_kasir_terlewati` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(20) NOT NULL,
  `no_antrian` varchar(10) NOT NULL,
  `kd_kasir` varchar(10) NOT NULL,
  `nm_kasir` varchar(50) NOT NULL,
  `tgl_antrian` date NOT NULL,
  `user_skip` varchar(50) NOT NULL,
  `waktu_skip` timestamp NOT NULL DEFAULT current_timestamp(),
  `alasan_skip` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
