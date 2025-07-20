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

 Date: 17/07/2025 15:55:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vedika_users
-- ----------------------------
DROP TABLE IF EXISTS `vedika_users`;
CREATE TABLE `vedika_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `role` enum('Admin','Coder','Verifikator','Kordinator','Pelaporan','Viewer') NOT NULL DEFAULT 'Coder' COMMENT 'Hak akses pengguna (terbatas pada daftar ini)',
  `password` varchar(255) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of vedika_users
-- ----------------------------
BEGIN;
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (1, 'admin', 'Randy Mandala', 'Admin', '$2y$10$OCWO96Nsyg9IgJ/YPDNDFeROavmMRi/YNFSP06Ybjs2pwXEMuAUVm', '2025-07-14 12:54:23');
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (2, 'dr-adra', 'dr. Adra', 'Verifikator', '$2y$10$VFpo7tEBU6uHxuxT5LBMnO5sQh38LEp2T.gE0jo.PPn7mAwaNVjpy', '2025-06-18 09:17:41');
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (3, 'regita', 'Regita', 'Coder', '$2y$10$66.UigBRHmW4iVslNa9w6.aRCT8CI6fb6Y2znEDVb7twLO7gAktCC', '2025-06-10 12:31:42');
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (4, 'idarlena', 'Idarlena Siregar', 'Verifikator', '$2y$10$oRqQBzPOk5Vbn0qAf4OaB.71x.TumTOIuqu35MIagmIdccI..moOi', '2025-06-20 17:23:07');
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (6, 'debora', 'Debora, Amd.Keb', 'Kordinator', '$2y$10$e0qbAAHGqYHAllMJj5DVV.g4yOhbZqVf/.nmjb2yyQXJm9eH2xuFm', '2025-07-14 12:44:24');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
