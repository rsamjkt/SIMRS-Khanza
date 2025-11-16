/*
 Navicat Premium Data Transfer

 Source Server         : produ
 Source Server Type    : MySQL
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.5:3306
 Source Schema         : sikrs4m2106

 Target Server Type    : MySQL
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 03/10/2025 06:35:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for penyakit
-- ----------------------------
DROP TABLE IF EXISTS `penyakit`;
CREATE TABLE `penyakit` (
  `kd_penyakit` varchar(15) NOT NULL,
  `nm_penyakit` varchar(100) DEFAULT NULL,
  `ciri_ciri` text DEFAULT NULL,
  `keterangan` varchar(60) DEFAULT NULL,
  `kd_ktg` varchar(8) DEFAULT NULL,
  `status` enum('Menular','Tidak Menular') NOT NULL,
  PRIMARY KEY (`kd_penyakit`),
  KEY `kd_ktg` (`kd_ktg`) USING BTREE,
  KEY `nm_penyakit` (`nm_penyakit`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  CONSTRAINT `penyakit_ibfk_1` FOREIGN KEY (`kd_ktg`) REFERENCES `kategori_penyakit` (`kd_ktg`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
