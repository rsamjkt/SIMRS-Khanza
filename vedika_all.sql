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

 Date: 18/04/2025 20:48:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mlite_vedika_status_history
-- ----------------------------
DROP TABLE IF EXISTS `mlite_vedika_status_history`;
CREATE TABLE `mlite_vedika_status_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nosep` varchar(40) NOT NULL,
  `no_rawat` varchar(17) DEFAULT NULL,
  `status_lama` varchar(50) DEFAULT NULL,
  `status_baru` varchar(50) NOT NULL,
  `catatan` text DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `tanggal` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `nosep` (`nosep`),
  KEY `tanggal` (`tanggal`),
  KEY `no_rawat` (`no_rawat`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for vedika_users
-- ----------------------------
DROP TABLE IF EXISTS `vedika_users`;
CREATE TABLE `vedika_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `role` enum('Admin','Coder','Verifikator','Pelaporan','Viewer') NOT NULL DEFAULT 'Coder' COMMENT 'Hak akses pengguna (terbatas pada daftar ini)',
  `password` varchar(255) NOT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for vedika_permissions
-- ----------------------------
DROP TABLE IF EXISTS `vedika_permissions`;
CREATE TABLE `vedika_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `permission` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_permission` (`user_id`,`permission`),
  CONSTRAINT `vedika_permissions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `vedika_users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `setting` (`setting`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for mlite_vedika_dokumen_verifikasi
-- ----------------------------
DROP TABLE IF EXISTS `mlite_vedika_dokumen_verifikasi`;
CREATE TABLE `mlite_vedika_dokumen_verifikasi` (
  `no_rawat` varchar(17) NOT NULL,
  `kode_dokumen` varchar(50) NOT NULL,
  `status_verifikasi` varchar(20) DEFAULT NULL,
  `catatan` text DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `tanggal_update` datetime DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`kode_dokumen`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `no_sep` (`no_sep`),
  KEY `user_id` (`user_id`),
  KEY `no_rawat` (`no_rawat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for eklaim_klaim
-- ----------------------------
DROP TABLE IF EXISTS `eklaim_klaim`;
CREATE TABLE `eklaim_klaim` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_sep` varchar(40) NOT NULL,
  `no_rawat` varchar(50) NOT NULL,
  `inacbgs_klaim_id` int(11) NOT NULL,
  `tgl_kirim` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `klaim_id` varchar(40) NOT NULL,
  `klaim_number` varchar(40) NOT NULL,
  `tarif` decimal(17,0) NOT NULL,
  `grouper` varchar(20) NOT NULL,
  `hasil_grouper` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `no_sep` (`no_sep`),
  KEY `no_rawat` (`no_rawat`),
  KEY `inacbgs_klaim_id` (`inacbgs_klaim_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for inacbgs_klaim
-- ----------------------------
DROP TABLE IF EXISTS `inacbgs_klaim`;
CREATE TABLE `inacbgs_klaim` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_sep` varchar(40) NOT NULL,
  `no_rawat` varchar(50) NOT NULL,
  `tgl_kirim` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `klaim_number` varchar(40) NOT NULL,
  `tarif` decimal(17,0) NOT NULL,
  `kelas` varchar(5) NOT NULL,
  `grouper` varchar(20) NOT NULL,
  `hasil_grouper` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `no_sep` (`no_sep`),
  KEY `no_rawat` (`no_rawat`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  PRIMARY KEY (`id`),
  KEY `no_rawat` (`no_rawat`),
  KEY `nosep` (`nosep`),
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  PRIMARY KEY (`id`),
  KEY `nosep` (`nosep`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
