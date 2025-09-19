/*
 Navicat Premium Data Transfer

 Source Server         : diatrans-tunnel
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 101.255.3.45:3380
 Source Schema         : sik_kmnu

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 18/09/2025 11:02:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for diagnosa_pasien
-- ----------------------------
DROP TABLE IF EXISTS `diagnosa_pasien`;
CREATE TABLE `diagnosa_pasien` (
  `no_rawat` varchar(17) NOT NULL,
  `kd_penyakit` varchar(15) NOT NULL,
  `status` enum('Ralan','Ranap') NOT NULL,
  `prioritas` tinyint(4) NOT NULL,
  `status_penyakit` enum('Lama','Baru') DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`kd_penyakit`,`status`),
  KEY `kd_penyakit` (`kd_penyakit`),
  KEY `status` (`status`),
  KEY `prioritas` (`prioritas`),
  KEY `no_rawat` (`no_rawat`),
  CONSTRAINT `diagnosa_pasien_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON UPDATE CASCADE,
  CONSTRAINT `diagnosa_pasien_ibfk_2` FOREIGN KEY (`kd_penyakit`) REFERENCES `penyakit` (`kd_penyakit`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Records of diagnosa_pasien
-- ----------------------------
BEGIN;
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000001', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000001', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000003', 'N18', 'Ralan', 1, 'Baru');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000003', 'N18.5', 'Ralan', 3, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000003', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000004', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000004', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000005', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000005', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000006', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/01/000006', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000001', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000001', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000003', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000003', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000004', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000004', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000005', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000005', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000006', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000006', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000007', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000007', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000008', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000008', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000010', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000010', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000011', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000011', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000018', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000018', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000019', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000019', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000020', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000020', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000021', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000021', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000026', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000026', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000028', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/03/000028', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000001', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000001', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000003', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000003', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000004', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000004', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000005', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000005', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000006', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/04/000006', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000001', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000001', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000003', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000003', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000004', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000004', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000005', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000005', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000006', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000006', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000007', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000007', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000008', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000008', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000017', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000017', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000018', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000018', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000019', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000019', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000020', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000020', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000021', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000021', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000022', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000022', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000029', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/06/000029', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000001', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000001', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000003', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000003', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000004', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000004', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000005', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000005', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000006', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000006', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000013', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/08/000013', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/09/000001', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/09/000001', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/09/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/09/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000001', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000001', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000003', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000003', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000004', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000004', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000006', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000006', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000007', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000007', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000008', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000008', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000009', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000009', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000019', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000019', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000020', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000020', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000021', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000021', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000022', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000022', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000023', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000023', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000029', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/10/000029', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000001', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000001', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000005', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000005', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000006', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000006', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000007', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000007', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000008', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000008', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000009', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000009', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000010', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/11/000010', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/12/000001', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/12/000001', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/12/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/12/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000001', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000001', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000002', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000002', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000003', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000003', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000004', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000004', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000005', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000005', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000006', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000006', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000007', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000007', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000008', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000008', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000017', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000017', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000018', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000018', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000019', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000019', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000020', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000020', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000021', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000021', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000022', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000022', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000029', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/13/000029', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000001', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000001', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000002', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000002', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000003', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000003', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000004', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000004', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000005', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000005', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000006', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000006', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000007', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/15/000007', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000001', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000001', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000002', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000002', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000003', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000003', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000004', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000004', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000007', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000007', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000010', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000010', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000012', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/16/000012', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000001', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000001', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000002', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000002', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000003', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000003', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000004', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000004', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000005', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000005', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000006', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000006', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000007', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000007', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000008', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000008', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000017', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000017', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000018', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000018', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000019', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000019', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000020', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000020', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000021', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000021', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000022', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/17/000022', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000001', 'N18.5', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000001', 'Z49.0', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000002', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000002', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000003', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000003', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000004', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000004', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000005', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000005', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000006', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000006', 'Z49.0', 'Ralan', 1, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000007', 'N18.5', 'Ralan', 2, 'Lama');
INSERT INTO `diagnosa_pasien` (`no_rawat`, `kd_penyakit`, `status`, `prioritas`, `status_penyakit`) VALUES ('2025/09/18/000007', 'Z49.0', 'Ralan', 1, 'Lama');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
