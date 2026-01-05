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

 Date: 05/01/2026 13:03:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_imagingstudy
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_imagingstudy`;
CREATE TABLE `satu_sehat_imagingstudy` (
  `no_rawat` varchar(17) NOT NULL,
  `id_imagingstudy` varchar(255) NOT NULL,
  `status` enum('success','failed') NOT NULL DEFAULT 'success',
  `response` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`no_rawat`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for satu_sehat_imagingstudy_usg
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_imagingstudy_usg`;
CREATE TABLE `satu_sehat_imagingstudy_usg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(50) NOT NULL,
  `id_imagingstudy` varchar(100) DEFAULT NULL,
  `orthanc_study_id` varchar(100) DEFAULT NULL,
  `status` enum('pending','processing','success','failed') DEFAULT 'pending',
  `response` text DEFAULT NULL,
  `error_message` text DEFAULT NULL,
  `retry_count` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `no_rawat` (`no_rawat`),
  KEY `idx_status` (`status`),
  KEY `idx_created` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
