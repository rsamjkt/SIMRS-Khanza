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

 Date: 05/01/2026 13:02:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_dicom_log
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_dicom_log`;
CREATE TABLE `satu_sehat_dicom_log` (
  `no_rawat` varchar(17) NOT NULL,
  `orthanc_study_id` varchar(255) DEFAULT NULL,
  `status` enum('success','failed') NOT NULL,
  `keterangan` text DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`no_rawat`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
