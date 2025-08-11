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

 Date: 05/08/2025 11:07:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for vedika_ai_summary
-- ----------------------------
DROP TABLE IF EXISTS `vedika_ai_summary`;
CREATE TABLE `vedika_ai_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(17) NOT NULL,
  `kelengkapan_dokumen` tinyint(1) DEFAULT 0,
  `konsistensi_dokumen` tinyint(1) DEFAULT 0,
  `klaim_valid` tinyint(1) DEFAULT 0,
  `rekomendasi` enum('Terima','Tolak','Perlu Verifikasi Manual') DEFAULT 'Perlu Verifikasi Manual',
  `alasan_utama` text DEFAULT NULL,
  `score_klaim` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `no_rawat` (`no_rawat`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_ai_summary
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vedika_ai_verifications
-- ----------------------------
DROP TABLE IF EXISTS `vedika_ai_verifications`;
CREATE TABLE `vedika_ai_verifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(17) NOT NULL,
  `versus` varchar(100) NOT NULL,
  `flag` varchar(10) NOT NULL,
  `masalah` text DEFAULT NULL,
  `saran` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_no_rawat` (`no_rawat`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_ai_verifications
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vedika_claims
-- ----------------------------
DROP TABLE IF EXISTS `vedika_claims`;
CREATE TABLE `vedika_claims` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tanggal` date NOT NULL,
  `no_rkm_medis` varchar(50) NOT NULL,
  `no_rawat` varchar(50) NOT NULL,
  `tgl_registrasi` date NOT NULL,
  `nosep` varchar(50) NOT NULL,
  `jenis` tinyint(4) NOT NULL COMMENT '1=Ranap, 2=Ralan',
  `status` enum('Lengkap','Pengajuan','Perbaiki') NOT NULL,
  `username` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `nosep` (`nosep`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_claims
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vedika_cron_tte_log
-- ----------------------------
DROP TABLE IF EXISTS `vedika_cron_tte_log`;
CREATE TABLE `vedika_cron_tte_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(100) NOT NULL,
  `tgl_proses` datetime NOT NULL,
  `status` enum('SUCCESS','FAILED') NOT NULL,
  `keterangan` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `no_rawat_unique` (`no_rawat`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_cron_tte_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vedika_eklaim_log
-- ----------------------------
DROP TABLE IF EXISTS `vedika_eklaim_log`;
CREATE TABLE `vedika_eklaim_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(17) NOT NULL,
  `no_sep` varchar(40) DEFAULT NULL,
  `status_kirim` enum('Terkirim','Gagal','Proses') NOT NULL,
  `pesan` text DEFAULT NULL,
  `id_klaim_api` varchar(40) DEFAULT NULL,
  `timestamp` datetime NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `no_rawat` (`no_rawat`),
  KEY `no_sep` (`no_sep`),
  KEY `status_kirim` (`status_kirim`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Records of vedika_eklaim_log
-- ----------------------------
BEGIN;
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (1, '2025/06/02/000047', '0115R0500625V000035', 'Terkirim', 'Klaim berhasil dikirim. ID Klaim API: 0115R0500625V000035, No. SEP Terkait: 0115R0500625V000035.', '0115R0500625V000035', '2025-07-28 08:32:15', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (2, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim. ID Klaim API: 0115R0500625V000050, No. SEP Terkait: 0115R0500625V000050.', '0115R0500625V000050', '2025-07-28 08:32:32', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (3, '2025/06/02/000047', '0115R0500625V000035', 'Terkirim', 'Klaim berhasil dikirim. ID Klaim API: 3174510ICC2507001B, No. SEP Terkait: 0115R0500625V000035.', '3174510ICC2507001B', '2025-07-28 08:33:25', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (4, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim. ID Klaim API: 0115R0500625V000050, No. SEP Terkait: 0115R0500625V000050.', '0115R0500625V000050', '2025-07-28 08:46:24', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (5, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim. ID Klaim API: 0115R0500725V000034, No. SEP Terkait: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 09:17:35', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (6, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim. ID Klaim API: 0115R0500725V000034, No. SEP Terkait: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 09:18:53', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (7, '2025/07/01/000307', '0115R0500725V000035', 'Terkirim', 'Klaim berhasil dikirim. ID Klaim API: 0115R0500725V000035, No. SEP Terkait: 0115R0500725V000035.', '0115R0500725V000035', '2025-07-28 09:23:46', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (8, '2025/07/08/000146', '0115R0500725V000348', 'Terkirim', 'Klaim berhasil dikirim. ID Klaim API: 0115R0500725V000348, No. SEP Terkait: 0115R0500725V000348.', '0115R0500725V000348', '2025-07-28 09:29:44', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (9, '2025/07/03/000377', '0115R0500725V000125', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000125, No. SEP: 0115R0500725V000125.', '0115R0500725V000125', '2025-07-28 09:33:47', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (10, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500625V000970, No. SEP: 0115R0500625V000970.', '0115R0500625V000970', '2025-07-28 09:44:31', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (11, '2025/06/03/000003', '0115R0500625V000096', 'Gagal', 'Proses Gagal: Klaim ini sudah pernah dikirim/final dan tidak dapat diubah lagi.', NULL, '2025-07-28 09:54:36', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (12, '2025/06/03/000003', '0115R0500625V000096', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC2507001C, No. SEP: 0115R0500625V000096.', '3174510ICC2507001C', '2025-07-28 09:55:12', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (13, '2025/06/03/000003', '0115R0500625V000096', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500625V000096, No. SEP: 0115R0500625V000096.', '0115R0500625V000096', '2025-07-28 09:56:27', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (14, '2025/06/03/000003', '0115R0500625V000096', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500625V000096, No. SEP: 0115R0500625V000096.', '0115R0500625V000096', '2025-07-28 09:58:43', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (15, '2025/05/30/000022', '0115R0500525V001312', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC2507001D, No. SEP: 0115R0500525V001312.', '3174510ICC2507001D', '2025-07-28 10:15:05', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (16, '2025/05/30/000022', '0115R0500525V001312', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500525V001312, No. SEP: 0115R0500525V001312.', '0115R0500525V001312', '2025-07-28 10:33:35', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (17, '2025/05/30/000020', '0115R0500525V001310', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC2507001E, No. SEP: 0115R0500525V001310.', '3174510ICC2507001E', '2025-07-28 13:19:40', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (18, '2025/05/30/000020', '0115R0500525V001310', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC2507001F, No. SEP: 0115R0500525V001310.', '3174510ICC2507001F', '2025-07-28 13:21:10', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (19, '2025/07/01/000325', '0115R0500725V000003', 'Gagal', 'Proses Gagal: Klaim ini sudah pernah dikirim/final dan tidak dapat diubah lagi.', NULL, '2025-07-28 13:24:24', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (20, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 13:43:33', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (21, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 13:50:23', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (22, '2025/07/01/000325', '0115R0500725V000003', 'Gagal', 'Proses Gagal: Klaim ini sudah pernah dikirim/final dan tidak dapat diubah lagi.', NULL, '2025-07-28 13:50:34', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (23, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 13:56:45', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (24, '2025/07/01/000325', '0115R0500725V000003', 'Gagal', 'Proses Gagal: Klaim ini sudah pernah dikirim/final dan tidak dapat diubah lagi.', NULL, '2025-07-28 14:03:45', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (25, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 14:16:24', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (26, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 15:03:42', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (27, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 15:15:05', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (28, '2025/07/14/000109', '0115R0500725V000658', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000658, No. SEP: 0115R0500725V000658.', '0115R0500725V000658', '2025-07-28 15:19:31', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (29, '2025/07/14/000109', '0115R0500725V000658', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000658, No. SEP: 0115R0500725V000658.', '0115R0500725V000658', '2025-07-28 15:24:55', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (30, '2025/07/14/000109', '0115R0500725V000658', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000658, No. SEP: 0115R0500725V000658.', '0115R0500725V000658', '2025-07-28 15:31:47', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (31, '2025/06/10/000190', '0115R0500625V000377', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500625V000377, No. SEP: 0115R0500625V000377.', '0115R0500625V000377', '2025-07-28 15:40:33', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (32, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 15:42:23', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (33, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 16:11:28', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (34, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-28 17:08:58', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (35, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 03:30:56', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (36, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 03:52:09', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (37, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 03:52:57', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (38, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 04:05:17', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (39, '2025/07/01/000325', '0115R0500725V000003', 'Gagal', 'Proses Gagal: Klaim ini sudah pernah dikirim/final dan tidak dapat diubah lagi.', NULL, '2025-07-29 04:05:24', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (40, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 04:08:17', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (41, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 04:11:30', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (42, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 04:36:38', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (43, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:10:58', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (44, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:14:28', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (45, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:15:01', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (46, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:19:58', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (47, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:20:15', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (48, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:20:36', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (49, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:20:56', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (50, '2025/07/08/000146', '0115R0500725V000348', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000348, No. SEP: 0115R0500725V000348.', '0115R0500725V000348', '2025-07-29 05:28:51', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (51, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:29:17', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (52, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:41:07', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (53, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:41:32', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (54, '2025/07/01/000307', '0115R0500725V000035', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000035, No. SEP: 0115R0500725V000035.', '0115R0500725V000035', '2025-07-29 05:49:29', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (55, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:54:02', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (56, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 05:54:21', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (57, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 06:12:32', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (58, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 06:12:52', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (59, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 06:16:29', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (60, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 06:16:46', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (61, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 06:16:58', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (62, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 06:34:08', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (63, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 06:34:48', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (64, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 06:35:07', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (65, '2025/07/01/000322', '0115R0500725V000034', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000034, No. SEP: 0115R0500725V000034.', '0115R0500725V000034', '2025-07-29 09:56:30', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (66, '2025/07/01/000325', '0115R0500725V000003', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500725V000003, No. SEP: 0115R0500725V000003.', '0115R0500725V000003', '2025-07-29 09:56:59', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (67, '2025/07/01/000325', '0115R0500725V000003', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500725V000003, No. SEP: 0115R0500725V000003.', '0115R0500725V000003', '2025-07-29 09:57:26', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (68, '2025/06/02/000048', '0115R0500625V000029', 'Gagal', 'Proses Gagal: Klaim ini sudah pernah dikirim/final dan tidak dapat diubah lagi.', NULL, '2025-07-29 09:59:33', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (69, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 10:00:15', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (70, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 10:22:44', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (71, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 10:25:24', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (72, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 10:26:19', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (73, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 12:20:04', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (74, '2025/06/02/000048', '0115R0500625V000029', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-29 12:21:10', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (75, '2025/06/02/000048', '0115R0500625V000029', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-29 12:21:14', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (76, '2025/06/02/000048', '0115R0500625V000029', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-29 12:25:01', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (77, '2025/06/02/000048', '0115R0500625V000029', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-29 12:25:13', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (78, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC25070020, No. SEP: 0115R0500625V000029.', '3174510ICC25070020', '2025-07-29 12:27:12', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (79, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 12:38:35', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (80, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 12:38:46', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (81, '2025/06/02/000048', '0115R0500625V000029', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-29 12:39:08', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (82, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC25070021, No. SEP: 0115R0500625V000029.', '3174510ICC25070021', '2025-07-29 12:39:22', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (83, '2025/06/02/000048', '0115R0500625V000029', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-29 12:39:47', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (84, '2025/06/02/000048', '0115R0500625V000029', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-29 12:40:29', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (85, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC25070022, No. SEP: 0115R0500625V000029.', '3174510ICC25070022', '2025-07-29 12:58:15', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (86, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC25070024, No. SEP: 0115R0500625V000029.', '3174510ICC25070024', '2025-07-29 12:59:50', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (87, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 13:00:23', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (88, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 13:03:32', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (89, '2025/06/02/000067', '0115R0500625V000045', 'Gagal', 'Proses Gagal: Klaim ini sudah pernah dikirim/final dan tidak dapat diubah lagi.', NULL, '2025-07-29 13:31:45', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (90, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 17:39:00', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (91, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 17:40:11', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (92, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 17:41:30', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (93, '2025/05/30/000020', '0115R0500525V001310', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500525V001310, No. SEP: 0115R0500525V001310.', '0115R0500525V001310', '2025-07-29 17:45:04', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (94, '2025/05/30/000020', '0115R0500525V001310', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500525V001310, No. SEP: 0115R0500525V001310.', '0115R0500525V001310', '2025-07-29 17:52:26', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (95, '2025/05/30/000020', '0115R0500525V001310', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC25070025, No. SEP: 0115R0500525V001310.', '3174510ICC25070025', '2025-07-29 17:53:08', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (96, '2025/05/30/000020', '0115R0500525V001310', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500525V001310, No. SEP: 0115R0500525V001310.', '0115R0500525V001310', '2025-07-29 17:53:27', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (97, '2025/05/30/000020', '0115R0500525V001310', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500525V001310, No. SEP: 0115R0500525V001310.', '0115R0500525V001310', '2025-07-29 17:59:54', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (98, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 18:32:56', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (99, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 18:37:02', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (100, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-29 18:37:29', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (101, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 03:36:05', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (102, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000970, No. SEP: 0115R0500625V000970.', '0115R0500625V000970', '2025-07-30 04:02:00', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (103, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000970, No. SEP: 0115R0500625V000970.', '0115R0500625V000970', '2025-07-30 04:02:39', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (104, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000970, No. SEP: 0115R0500625V000970.', '0115R0500625V000970', '2025-07-30 04:08:15', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (105, '2025/06/23/000037', '0115R0500625V000970', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-30 04:09:37', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (106, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC25070026, No. SEP: 0115R0500625V000970.', '3174510ICC25070026', '2025-07-30 04:09:47', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (107, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000970, No. SEP: 0115R0500625V000970.', '0115R0500625V000970', '2025-07-30 04:10:10', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (108, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 04:16:44', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (109, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000970, No. SEP: 0115R0500625V000970.', '0115R0500625V000970', '2025-07-30 04:17:20', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (110, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 04:36:35', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (111, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 04:38:14', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (112, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000970, No. SEP: 0115R0500625V000970.', '0115R0500625V000970', '2025-07-30 04:39:43', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (113, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 05:07:25', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (114, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000050, No. SEP: 0115R0500625V000050.', '0115R0500625V000050', '2025-07-30 06:17:57', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (115, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim/diupdate. ID Klaim API: 0115R0500625V000050', '0115R0500625V000050', '2025-07-30 06:18:24', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (116, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000050, No. SEP: 0115R0500625V000050.', '0115R0500625V000050', '2025-07-30 06:19:27', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (117, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim/diupdate. ID Klaim API: 0115R0500625V000050', '0115R0500625V000050', '2025-07-30 06:19:51', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (118, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000050, No. SEP: 0115R0500625V000050.', '0115R0500625V000050', '2025-07-30 06:20:09', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (119, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000050, No. SEP: 0115R0500625V000050.', '0115R0500625V000050', '2025-07-30 06:21:34', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (120, '2025/06/02/000006', '0115R0500625V000050', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-30 06:22:35', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (121, '2025/06/02/000006', '0115R0500625V000050', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-30 06:22:56', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (122, '2025/06/02/000006', '0115R0500625V000050', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-30 06:23:09', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (123, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC25070027, No. SEP: 0115R0500625V000050.', '3174510ICC25070027', '2025-07-30 06:23:36', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (124, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000050, No. SEP: 0115R0500625V000050.', '0115R0500625V000050', '2025-07-30 06:26:04', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (125, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 06:45:55', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (126, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 06:48:37', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (127, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 06:49:04', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (128, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 06:56:35', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (129, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 07:26:07', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (130, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-30 09:38:47', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (131, '2025/06/23/000037', '0115R0500625V000970', 'Gagal', 'Langkah 1 (new_claim) Gagal: [400] Nomor Klaim sudah terpakai. Silakan generate_claim_number lagi.', NULL, '2025-07-30 10:45:05', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (132, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim ulang/diupdate. ID Klaim API: 3174510ICC25070028, No. SEP: 0115R0500625V000970.', '3174510ICC25070028', '2025-07-30 10:45:12', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (133, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000970, No. SEP: 0115R0500625V000970.', '0115R0500625V000970', '2025-07-30 10:45:28', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (134, '2025/06/10/000190', '0115R0500625V000377', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000377, No. SEP: 0115R0500625V000377.', '0115R0500625V000377', '2025-07-30 11:30:34', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (135, '2025/06/03/000003', '0115R0500625V000096', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000096, No. SEP: 0115R0500625V000096.', '0115R0500625V000096', '2025-07-30 11:33:01', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (136, '2025/06/02/000006', '0115R0500625V000050', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000050, No. SEP: 0115R0500625V000050.', '0115R0500625V000050', '2025-07-30 14:30:12', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (137, '2025/06/02/000048', '0115R0500625V000029', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000029, No. SEP: 0115R0500625V000029.', '0115R0500625V000029', '2025-07-31 09:25:31', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (138, '2025/06/23/000037', '0115R0500625V000970', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000970, No. SEP: 0115R0500625V000970.', '0115R0500625V000970', '2025-07-31 09:56:27', 'admin');
INSERT INTO `vedika_eklaim_log` (`id`, `no_rawat`, `no_sep`, `status_kirim`, `pesan`, `id_klaim_api`, `timestamp`, `username`) VALUES (139, '2025/06/02/000047', '0115R0500625V000035', 'Terkirim', 'Klaim berhasil dikirim/diupdate dari form. ID Klaim API: 0115R0500625V000035, No. SEP: 0115R0500625V000035.', '0115R0500625V000035', '2025-07-31 09:56:42', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for vedika_feedback
-- ----------------------------
DROP TABLE IF EXISTS `vedika_feedback`;
CREATE TABLE `vedika_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nosep` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `catatan` text NOT NULL,
  `username` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  KEY `nosep` (`nosep`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_feedback
-- ----------------------------
BEGIN;
COMMIT;

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_unique_gabungan` (`no_rawat_utama`,`no_rawat_gabung`) USING BTREE,
  KEY `idx_no_rawat_utama` (`no_rawat_utama`) USING BTREE,
  KEY `idx_no_rawat_gabung` (`no_rawat_gabung`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_gabung_rawat
-- ----------------------------
BEGIN;
INSERT INTO `vedika_gabung_rawat` (`id`, `no_rawat_utama`, `no_rawat_gabung`, `username_creator`, `created_at`) VALUES (20, '2025/05/30/000020', '2025/05/30/000025', 'admin', '2025-07-28 12:14:07');
INSERT INTO `vedika_gabung_rawat` (`id`, `no_rawat_utama`, `no_rawat_gabung`, `username_creator`, `created_at`) VALUES (21, '2025/05/29/000024', '2025/05/29/000032', 'debora', '2025-07-28 13:04:07');
COMMIT;

-- ----------------------------
-- Table structure for vedika_grouped_rawat
-- ----------------------------
DROP TABLE IF EXISTS `vedika_grouped_rawat`;
CREATE TABLE `vedika_grouped_rawat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat_utama` varchar(17) NOT NULL COMMENT 'No rawat yang menjadi induk/utama',
  `no_rawat_gabungan` varchar(17) NOT NULL COMMENT 'No rawat yang digabungkan ke utama',
  `urutan` tinyint(1) NOT NULL DEFAULT 1 COMMENT 'Urutan dalam grup (1-3)',
  `keterangan` text DEFAULT NULL COMMENT 'Keterangan kenapa digabung',
  `username_creator` varchar(50) NOT NULL COMMENT 'User yang membuat penggabungan',
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT NULL ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_rawat_gabungan` (`no_rawat_gabungan`) USING BTREE,
  KEY `idx_no_rawat_utama` (`no_rawat_utama`) USING BTREE,
  KEY `idx_creator` (`username_creator`) USING BTREE,
  KEY `idx_utama_urutan` (`no_rawat_utama`,`urutan`) USING BTREE,
  CONSTRAINT `chk_different_rawat` CHECK (`no_rawat_utama` <> `no_rawat_gabungan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='Tabel penggabungan rekam medis untuk vedika';

-- ----------------------------
-- Records of vedika_grouped_rawat
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vedika_permissions
-- ----------------------------
DROP TABLE IF EXISTS `vedika_permissions`;
CREATE TABLE `vedika_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `permission` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_permission` (`user_id`,`permission`) USING BTREE,
  CONSTRAINT `vedika_permissions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `vedika_users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_permissions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for vedika_settings
-- ----------------------------
DROP TABLE IF EXISTS `vedika_settings`;
CREATE TABLE `vedika_settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `setting` varchar(100) NOT NULL,
  `value` text DEFAULT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `setting` (`setting`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_settings
-- ----------------------------
BEGIN;
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (208, 'vedika.carabayar', 'BPJ', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (209, 'vedika.individual', '001', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (210, 'vedika.claim_period_start', '2025-06-01', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (211, 'vedika.claim_period_end', '2025-06-30', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (212, 'vedika.inacbgs_prosedur_bedah', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (213, 'vedika.inacbgs_prosedur_non_bedah', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (214, 'vedika.inacbgs_konsultasi', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (215, 'vedika.inacbgs_tenaga_ahli', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (216, 'vedika.inacbgs_keperawatan', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (217, 'vedika.inacbgs_penunjang', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (218, 'vedika.inacbgs_radiologi', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (219, 'vedika.inacbgs_laboratorium', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (220, 'vedika.inacbgs_rehabilitasi', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (221, 'vedika.inacbgs_rawat_intensif', '', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (222, 'eklaim.url', 'http://eklaim.com/eklaim/ws.php', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (223, 'eklaim.key', 'your-eklaim-key', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (224, 'eklaim.kelasrs', 'B', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (225, 'eklaim.payor_id', '3', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (226, 'eklaim.payor_cd', 'JKN', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (227, 'bpjs.api_url', 'https://apijkn-dev.bpjs-kesehatan.go.id/vclaim-rest-dev/', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (228, 'bpjs.cons_id', 'your-cons-id', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (229, 'bpjs.secret_key', 'your-secret-key', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
INSERT INTO `vedika_settings` (`id`, `setting`, `value`, `description`, `created_at`, `updated_at`) VALUES (230, 'bpjs.user_key', 'your-user-key', NULL, '2025-07-17 15:56:40', '2025-07-17 15:56:40');
COMMIT;

-- ----------------------------
-- Table structure for vedika_tte_process_log
-- ----------------------------
DROP TABLE IF EXISTS `vedika_tte_process_log`;
CREATE TABLE `vedika_tte_process_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `history_id` int(11) NOT NULL COMMENT 'FK ke mlite_vedika_status_history.id',
  `no_rawat` varchar(17) NOT NULL,
  `process_status` enum('SUCCESS','FAILED_PDF','FAILED_NO_SIGNER','FAILED_API_SEND','FAILED_UNKNOWN') NOT NULL COMMENT 'Status akhir proses TTE',
  `message` text DEFAULT NULL COMMENT 'Pesan sukses atau detail error',
  `transaction_id` varchar(100) DEFAULT NULL COMMENT 'Transaction ID dari SertifiSign jika sukses',
  `signed_document_url` text DEFAULT NULL COMMENT 'URL dokumen final dari Minio/S3',
  `processed_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `history_id_unique` (`history_id`) USING BTREE COMMENT 'Pastikan satu histori hanya diproses sekali',
  KEY `no_rawat_idx` (`no_rawat`) USING BTREE,
  KEY `status_idx` (`process_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_tte_process_log
-- ----------------------------
BEGIN;
COMMIT;

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
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vedika_users
-- ----------------------------
BEGIN;
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (1, 'admin', 'Randy Mandala', 'Admin', '$2y$10$OCWO96Nsyg9IgJ/YPDNDFeROavmMRi/YNFSP06Ybjs2pwXEMuAUVm', '2025-08-04 06:25:34');
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (2, 'dr-adra', 'dr. Adra', 'Verifikator', '$2y$10$VFpo7tEBU6uHxuxT5LBMnO5sQh38LEp2T.gE0jo.PPn7mAwaNVjpy', '2025-06-18 09:17:41');
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (3, 'regita', 'Regita', 'Coder', '$2y$10$66.UigBRHmW4iVslNa9w6.aRCT8CI6fb6Y2znEDVb7twLO7gAktCC', '2025-06-10 12:31:42');
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (4, 'idarlena', 'Idarlena Siregar', 'Verifikator', '$2y$10$oRqQBzPOk5Vbn0qAf4OaB.71x.TumTOIuqu35MIagmIdccI..moOi', '2025-07-22 11:39:31');
INSERT INTO `vedika_users` (`id`, `username`, `fullname`, `role`, `password`, `last_login`) VALUES (6, 'debora', 'Debora, Amd.Keb', 'Kordinator', '$2y$10$e0qbAAHGqYHAllMJj5DVV.g4yOhbZqVf/.nmjb2yyQXJm9eH2xuFm', '2025-07-28 12:37:56');
COMMIT;

-- ----------------------------
-- Table structure for verification_logs
-- ----------------------------
DROP TABLE IF EXISTS `verification_logs`;
CREATE TABLE `verification_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `quality_check` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `liveness_score` float DEFAULT NULL,
  `challenge_response` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `client_ip` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of verification_logs
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for verifikasi_klaim
-- ----------------------------
DROP TABLE IF EXISTS `verifikasi_klaim`;
CREATE TABLE `verifikasi_klaim` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(17) NOT NULL,
  `no_sep` varchar(40) NOT NULL,
  `tgl_verifikasi` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` enum('Proses','Lengkap','Tidak Lengkap') NOT NULL DEFAULT 'Proses',
  `catatan` text DEFAULT NULL,
  `is_sep_valid` tinyint(1) NOT NULL DEFAULT 0,
  `is_resume_valid` tinyint(1) NOT NULL DEFAULT 0,
  `is_billing_valid` tinyint(1) NOT NULL DEFAULT 0,
  `is_penunjang_valid` tinyint(1) NOT NULL DEFAULT 0,
  `exported` tinyint(1) NOT NULL DEFAULT 0,
  `exported_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `no_sep` (`no_sep`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of verifikasi_klaim
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for verifikasi_log
-- ----------------------------
DROP TABLE IF EXISTS `verifikasi_log`;
CREATE TABLE `verifikasi_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_sep` varchar(40) NOT NULL,
  `tgl_log` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` varchar(50) NOT NULL,
  `keterangan` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `no_sep` (`no_sep`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of verifikasi_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
