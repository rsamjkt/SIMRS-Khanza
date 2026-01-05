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

 Date: 05/01/2026 13:03:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dicom_conversion_log
-- ----------------------------
DROP TABLE IF EXISTS `dicom_conversion_log`;
CREATE TABLE `dicom_conversion_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(17) NOT NULL,
  `tgl_periksa` date NOT NULL,
  `jam` time NOT NULL,
  `lokasi_gambar` varchar(500) NOT NULL,
  `no_rkm_medis` varchar(15) NOT NULL,
  `nm_pasien` varchar(40) DEFAULT NULL,
  `series_number` int(11) NOT NULL DEFAULT 1,
  `status` enum('success','failed','processing') NOT NULL DEFAULT 'processing',
  `dicom_filename` varchar(255) DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `output_path` varchar(500) DEFAULT NULL,
  `orthanc_uploaded` tinyint(1) NOT NULL DEFAULT 0,
  `orthanc_id` varchar(100) DEFAULT NULL,
  `orthanc_patient_id` varchar(100) DEFAULT NULL,
  `orthanc_study_id` varchar(100) DEFAULT NULL,
  `orthanc_series_id` varchar(100) DEFAULT NULL,
  `error_message` text DEFAULT NULL,
  `execution_time` decimal(10,3) DEFAULT NULL,
  `converted_by` varchar(50) DEFAULT NULL,
  `converted_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_conversion` (`no_rawat`,`tgl_periksa`,`jam`,`lokasi_gambar`),
  KEY `idx_no_rawat` (`no_rawat`),
  KEY `idx_status` (`status`),
  KEY `idx_orthanc_uploaded` (`orthanc_uploaded`),
  KEY `idx_converted_at` (`converted_at`),
  KEY `idx_no_rkm_medis` (`no_rkm_medis`),
  KEY `idx_tgl_periksa` (`tgl_periksa`),
  CONSTRAINT `fk_log_no_rawat` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=525 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
