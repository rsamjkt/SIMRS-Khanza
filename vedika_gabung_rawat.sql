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

 Date: 14/06/2025 11:28:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vedika_gabung_rawat
-- ----------------------------
DROP TABLE IF EXISTS `vedika_gabung_rawat`;
CREATE TABLE `vedika_gabung_rawat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat_utama` varchar(17) NOT NULL COMMENT 'Nomor rawat utama (misal: Ibu)',
  `no_rawat_gabung` varchar(17) NOT NULL COMMENT 'Nomor rawat yang digabungkan (misal: Bayi)',
  `username_creator` varchar(30) NOT NULL COMMENT 'Username yang membuat relasi',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_gabungan` (`no_rawat_utama`,`no_rawat_gabung`),
  KEY `idx_no_rawat_utama` (`no_rawat_utama`),
  KEY `idx_no_rawat_gabung` (`no_rawat_gabung`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
