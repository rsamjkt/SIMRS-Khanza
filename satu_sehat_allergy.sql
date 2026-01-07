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

 Date: 06/01/2026 10:36:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_allergy
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_allergy`;
CREATE TABLE `satu_sehat_allergy` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(50) NOT NULL,
  `tgl_perawatan` date NOT NULL,
  `jam` time NOT NULL,
  `id_allergy` varchar(64) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_rawat_waktu` (`no_rawat`,`tgl_perawatan`,`jam`),
  KEY `idx_id_allergy` (`id_allergy`)
) ENGINE=InnoDB AUTO_INCREMENT=369 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
