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

 Date: 09/10/2025 12:00:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_servicerequest_usg
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_servicerequest_usg`;
CREATE TABLE `satu_sehat_servicerequest_usg` (
  `no_rawat` varchar(17) NOT NULL,
  `id_servicerequest` varchar(255) NOT NULL,
  `status` enum('success','failed') NOT NULL DEFAULT 'success',
  `response` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`no_rawat`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
