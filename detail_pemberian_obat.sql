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

 Date: 10/10/2025 18:15:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for detail_pemberian_obat
-- ----------------------------
DROP TABLE IF EXISTS `detail_pemberian_obat`;
CREATE TABLE `detail_pemberian_obat` (
  `tgl_perawatan` date NOT NULL DEFAULT '0000-00-00',
  `jam` time NOT NULL DEFAULT '00:00:00',
  `no_rawat` varchar(17) NOT NULL DEFAULT '',
  `kode_brng` varchar(15) NOT NULL,
  `h_beli` double DEFAULT NULL,
  `biaya_obat` double DEFAULT NULL,
  `jml` double NOT NULL,
  `embalase` double DEFAULT NULL,
  `tuslah` double DEFAULT NULL,
  `total` double NOT NULL,
  `status` enum('Ralan','Ranap') DEFAULT NULL,
  `kd_bangsal` char(5) DEFAULT NULL,
  `no_batch` varchar(20) NOT NULL,
  `no_faktur` varchar(20) NOT NULL,
  PRIMARY KEY (`tgl_perawatan`,`jam`,`no_rawat`,`kode_brng`,`no_batch`,`no_faktur`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `kd_obat` (`kode_brng`) USING BTREE,
  KEY `tgl_perawatan` (`tgl_perawatan`) USING BTREE,
  KEY `jam` (`jam`) USING BTREE,
  KEY `jml` (`jml`) USING BTREE,
  KEY `tambahan` (`embalase`) USING BTREE,
  KEY `total` (`total`) USING BTREE,
  KEY `biaya_obat` (`biaya_obat`) USING BTREE,
  KEY `kd_bangsal` (`kd_bangsal`) USING BTREE,
  KEY `tuslah` (`tuslah`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  CONSTRAINT `detail_pemberian_obat_ibfk_3` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `detail_pemberian_obat_ibfk_4` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `detail_pemberian_obat_ibfk_5` FOREIGN KEY (`kd_bangsal`) REFERENCES `bangsal` (`kd_bangsal`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
