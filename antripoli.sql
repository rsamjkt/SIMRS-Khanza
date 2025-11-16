/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy3

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 07/10/2025 16:38:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for antripoli
-- ----------------------------
DROP TABLE IF EXISTS `antripoli`;
CREATE TABLE `antripoli` (
  `kd_dokter` varchar(20) DEFAULT NULL,
  `kd_poli` char(5) DEFAULT NULL,
  `status` enum('0','1','2','3','4') DEFAULT NULL,
  `no_rawat` varchar(17) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of antripoli
-- ----------------------------
BEGIN;
INSERT INTO `antripoli` (`kd_dokter`, `kd_poli`, `status`, `no_rawat`) VALUES ('D0000004', 'U0059', '3', '2025/09/27/000004');
INSERT INTO `antripoli` (`kd_dokter`, `kd_poli`, `status`, `no_rawat`) VALUES ('D0000047', 'U0058', '2', '2025/09/27/000002');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
