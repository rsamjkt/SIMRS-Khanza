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

 Date: 11/12/2025 15:37:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_allergy
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_allergy`;
CREATE TABLE `satu_sehat_allergy` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(50) NOT NULL,
  `tgl_perawatan` date NOT NULL,
  `jam` time NOT NULL,
  `id_allergy` varchar(64) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_rawat_waktu` (`no_rawat`,`tgl_perawatan`,`jam`),
  KEY `idx_id_allergy` (`id_allergy`)
) ENGINE=InnoDB AUTO_INCREMENT=363 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- ----------------------------
-- Table structure for satu_sehat_dicom_log_radiologi
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_dicom_log_radiologi`;
CREATE TABLE `satu_sehat_dicom_log_radiologi` (
  `noorder` varchar(15) NOT NULL,
  `kd_jenis_prw` varchar(15) NOT NULL,
  `orthanc_study_id` varchar(100) DEFAULT NULL,
  `status` enum('success','failed') NOT NULL DEFAULT 'failed',
  `keterangan` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`noorder`,`kd_jenis_prw`) USING BTREE,
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB AUTO_INCREMENT=1949 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
-- Table structure for satu_sehat_imagingstudy_rad
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_imagingstudy_rad`;
CREATE TABLE `satu_sehat_imagingstudy_rad` (
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

-- ----------------------------
-- Table structure for satu_sehat_imagingstudy_radiologi
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_imagingstudy_radiologi`;
CREATE TABLE `satu_sehat_imagingstudy_radiologi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `noorder` varchar(20) NOT NULL,
  `kd_jenis_prw` varchar(15) NOT NULL,
  `accession_number` varchar(50) DEFAULT NULL,
  `study_uid` varchar(100) DEFAULT NULL,
  `id_imagingstudy` varchar(100) DEFAULT NULL,
  `id_servicerequest` varchar(100) DEFAULT NULL,
  `dicom_status` enum('pending','sent','failed') DEFAULT 'pending',
  `satusehat_status` enum('pending','sent','failed') DEFAULT 'pending',
  `dicom_sent_at` timestamp NULL DEFAULT NULL,
  `satusehat_sent_at` timestamp NULL DEFAULT NULL,
  `response_json` text DEFAULT NULL,
  `error_message` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_order_prw_imaging` (`noorder`,`kd_jenis_prw`),
  KEY `idx_accession` (`accession_number`),
  KEY `idx_study_uid` (`study_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

-- ----------------------------
-- Table structure for satu_sehat_imunisasi
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_imunisasi`;
CREATE TABLE `satu_sehat_imunisasi` (
  `tgl_perawatan` date NOT NULL,
  `jam` time NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  `kode_brng` varchar(15) NOT NULL,
  `no_batch` varchar(20) NOT NULL,
  `no_faktur` varchar(20) NOT NULL,
  `id_immunization` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`tgl_perawatan`,`jam`,`no_rawat`,`kode_brng`,`no_batch`,`no_faktur`),
  KEY `no_rawat` (`no_rawat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for satu_sehat_mapping_alergi
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_mapping_alergi`;
CREATE TABLE `satu_sehat_mapping_alergi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(100) NOT NULL,
  `snomed_code` varchar(20) NOT NULL,
  `snomed_display` varchar(255) NOT NULL,
  `category` enum('food','medication','environment','biologic') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_keyword` (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for satu_sehat_servicerequest_usg
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_servicerequest_usg`;
CREATE TABLE `satu_sehat_servicerequest_usg` (
  `no_rawat` varchar(17) NOT NULL,
  `id_servicerequest` varchar(255) NOT NULL,
  `accession_number` varchar(50) DEFAULT NULL,
  `status` enum('success','failed') NOT NULL DEFAULT 'success',
  `response` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `idx_accession` (`accession_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
