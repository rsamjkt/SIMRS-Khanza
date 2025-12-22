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

 Date: 09/12/2025 11:33:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_episode
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_episode`;
CREATE TABLE `satu_sehat_episode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jenis_program` varchar(30) NOT NULL,
  `no_rkm_medis` varchar(30) NOT NULL,
  `period_key` varchar(10) NOT NULL,
  `id_eoc` varchar(80) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_kia` (`jenis_program`,`no_rkm_medis`,`period_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1871 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
