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

 Date: 22/11/2025 18:51:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_servicerequest_radiologi
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_servicerequest_radiologi`;
CREATE TABLE `satu_sehat_servicerequest_radiologi` (
  `noorder` varchar(15) NOT NULL,
  `kd_jenis_prw` varchar(15) NOT NULL,
  `id_servicerequest` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`noorder`,`kd_jenis_prw`) USING BTREE,
  KEY `kd_jenis_prw` (`kd_jenis_prw`) USING BTREE,
  CONSTRAINT `satu_sehat_servicerequest_radiologi_ibfk_1` FOREIGN KEY (`noorder`) REFERENCES `permintaan_radiologi` (`noorder`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `satu_sehat_servicerequest_radiologi_ibfk_2` FOREIGN KEY (`kd_jenis_prw`) REFERENCES `jns_perawatan_radiologi` (`kd_jenis_prw`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for satu_sehat_servicerequest_usg
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_servicerequest_usg`;
CREATE TABLE `satu_sehat_servicerequest_usg` (
  `no_rawat` varchar(17) NOT NULL,
  `id_servicerequest` varchar(255) NOT NULL,
  `accession_number` varchar(50) DEFAULT NULL,
  `status` enum('success','failed') NOT NULL DEFAULT 'success',
  `response` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `idx_accession` (`accession_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
