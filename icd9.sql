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

 Date: 03/10/2025 06:37:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for icd9
-- ----------------------------
DROP TABLE IF EXISTS `icd9`;
CREATE TABLE `icd9` (
  `kode` varchar(8) NOT NULL,
  `deskripsi_panjang` varchar(250) DEFAULT NULL,
  `deskripsi_pendek` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`kode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
