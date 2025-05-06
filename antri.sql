/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy2

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 06/05/2025 17:00:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for antripemilihandpjp
-- ----------------------------
DROP TABLE IF EXISTS `antripemilihandpjp`;
CREATE TABLE `antripemilihandpjp` (
  `no_surat` varchar(20) DEFAULT NULL COMMENT 'Merujuk ke no_surat di tabel surat_pemilihan_dpjp',
  `no_rawat` varchar(17) NOT NULL COMMENT 'Merujuk ke no_rawat di tabel reg_periksa / surat_pemilihan_dpjp'
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC COMMENT='Tabel antrian untuk proses terkait Surat Pemilihan DPJP';

-- ----------------------------
-- Table structure for antripernyataanshk
-- ----------------------------
DROP TABLE IF EXISTS `antripernyataanshk`;
CREATE TABLE `antripernyataanshk` (
  `no_pernyataan` varchar(25) NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  PRIMARY KEY (`no_pernyataan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='Tabel antrian untuk pengambilan foto bukti SHK';

SET FOREIGN_KEY_CHECKS = 1;
