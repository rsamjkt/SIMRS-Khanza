/*
 Navicat Premium Data Transfer

 Source Server         : diatrans-tunnel
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 101.255.3.45:3380
 Source Schema         : sik_diatrans

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 31/12/2025 10:00:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hd_ktv
-- ----------------------------
DROP TABLE IF EXISTS `hd_ktv`;
CREATE TABLE `hd_ktv` (
  `no_rawat` varchar(17) NOT NULL,
  `ktv` decimal(4,2) NOT NULL,
  `tanggal_input` datetime DEFAULT current_timestamp(),
  `nip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
