/*
 Navicat Premium Data Transfer

 Source Server         : ci-zero
 Source Server Type    : MySQL
 Source Server Version : 100338 (10.3.38-MariaDB-0ubuntu0.20.04.1-log)
 Source Host           : 192.168.196.251:3306
 Source Schema         : sikci2

 Target Server Type    : MySQL
 Target Server Version : 100338 (10.3.38-MariaDB-0ubuntu0.20.04.1-log)
 File Encoding         : 65001

 Date: 09/12/2025 10:21:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for template_laboratorium
-- ----------------------------
DROP TABLE IF EXISTS `template_laboratorium`;
CREATE TABLE `template_laboratorium` (
  `kd_jenis_prw` varchar(15) NOT NULL,
  `id_template` int(11) NOT NULL AUTO_INCREMENT,
  `Pemeriksaan` varchar(200) NOT NULL,
  `satuan` varchar(20) NOT NULL,
  `nilai_rujukan_ld` varchar(30) NOT NULL,
  `nilai_rujukan_la` varchar(30) NOT NULL,
  `nilai_rujukan_pd` varchar(30) NOT NULL,
  `nilai_rujukan_pa` varchar(30) NOT NULL,
  `bagian_rs` double NOT NULL,
  `bhp` double NOT NULL,
  `bagian_perujuk` double NOT NULL,
  `bagian_dokter` double NOT NULL,
  `bagian_laborat` double NOT NULL,
  `kso` double DEFAULT NULL,
  `menejemen` double DEFAULT NULL,
  `biaya_item` double NOT NULL,
  `urut` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_template`) USING BTREE,
  KEY `kd_jenis_prw` (`kd_jenis_prw`) USING BTREE,
  KEY `Pemeriksaan` (`Pemeriksaan`) USING BTREE,
  KEY `satuan` (`satuan`) USING BTREE,
  KEY `nilai_rujukan_ld` (`nilai_rujukan_ld`) USING BTREE,
  KEY `nilai_rujukan_la` (`nilai_rujukan_la`) USING BTREE,
  KEY `nilai_rujukan_pd` (`nilai_rujukan_pd`) USING BTREE,
  KEY `nilai_rujukan_pa` (`nilai_rujukan_pa`) USING BTREE,
  KEY `bagian_rs` (`bagian_rs`) USING BTREE,
  KEY `bhp` (`bhp`) USING BTREE,
  KEY `bagian_perujuk` (`bagian_perujuk`) USING BTREE,
  KEY `bagian_dokter` (`bagian_dokter`) USING BTREE,
  KEY `bagian_laborat` (`bagian_laborat`) USING BTREE,
  KEY `kso` (`kso`) USING BTREE,
  KEY `menejemen` (`menejemen`) USING BTREE,
  KEY `biaya_item` (`biaya_item`) USING BTREE,
  KEY `urut` (`urut`) USING BTREE,
  CONSTRAINT `template_laboratorium_ibfk_1` FOREIGN KEY (`kd_jenis_prw`) REFERENCES `jns_perawatan_lab` (`kd_jenis_prw`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=181145 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
