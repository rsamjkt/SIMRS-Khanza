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

 Date: 05/01/2026 13:02:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_diagnosticreport_usg
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_diagnosticreport_usg`;
CREATE TABLE `satu_sehat_diagnosticreport_usg` (
  `no_rawat` varchar(17) NOT NULL,
  `id_diagnosticreport` varchar(64) NOT NULL DEFAULT '',
  `status` enum('success','failed') NOT NULL DEFAULT 'success',
  `response` mediumtext DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`no_rawat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
