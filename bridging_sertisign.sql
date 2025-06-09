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

 Date: 04/06/2025 13:52:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bridging_sertisign
-- ----------------------------
DROP TABLE IF EXISTS `bridging_sertisign`;
CREATE TABLE `bridging_sertisign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_rawat` varchar(20) NOT NULL COMMENT 'Nomor rawat dari sistem SIMRS',
  `no_sep` varchar(50) DEFAULT NULL COMMENT 'Nomor SEP BPJS, bisa juga ID unik lain jika bukan BPJS',
  `tgl_sep` date DEFAULT NULL COMMENT 'Tanggal SEP atau tanggal relevan dokumen',
  `transaction_id_sertisign` varchar(100) DEFAULT NULL COMMENT 'Transaction ID dari SertifiSign',
  `sertisign_api_url` varchar(512) DEFAULT NULL COMMENT 'URL dokumen dari API SertifiSign (sebelum di MinIO)',
  `signed_document_url_minio` varchar(512) DEFAULT NULL COMMENT 'URL dokumen yang sudah ditandatangani dan diupload ke MinIO S3',
  `filename_minio` varchar(255) DEFAULT NULL COMMENT 'Nama file dokumen di MinIO S3',
  `synology_file_path` varchar(512) DEFAULT NULL COMMENT 'Path lengkap file di Synology NAS',
  `google_drive_url` varchar(512) DEFAULT NULL COMMENT 'URL file di Google Drive',
  `status_tte` varchar(50) NOT NULL DEFAULT 'PENDING' COMMENT 'Status TTE (PENDING, SENT_TO_SERTISIGN, WAITING_CALLBACK, PDF_GEN_FAILED, API_SEND_FAILED, NO_SIGNER_EMAIL, TTE_SUCCESS, TTE_PARTIAL, TTE_FAILED, TTE_S3_UPLOAD_FAILED)',
  `keterangan` varchar(500) DEFAULT NULL COMMENT 'Keterangan tambahan mengenai status TTE',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() COMMENT 'Timestamp pembuatan record',
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT 'Timestamp update terakhir record',
  `callback_received_at` timestamp NULL DEFAULT NULL COMMENT 'Timestamp ketika callback dari SertifiSign diterima',
  `done_date_sertisign` varchar(50) DEFAULT NULL COMMENT 'Tanggal selesai penandatanganan dari callback SertifiSign',
  `tte_mode` varchar(20) DEFAULT 'individual' COMMENT 'Mode TTE (individual atau combined)',
  `doctor_id_dpjp` varchar(20) DEFAULT NULL COMMENT 'Kode dokter DPJP utama yang terlibat',
  `doctor_name_dpjp` varchar(100) DEFAULT NULL COMMENT 'Nama dokter DPJP utama',
  `doctor_ids_additional` text DEFAULT NULL COMMENT 'JSON array atau CSV dari ID dokter tambahan yang terlibat (jika multiple)',
  `document_payload` text DEFAULT NULL COMMENT 'Payload data yang dikirim untuk generate PDF (opsional, untuk audit)',
  `sertisign_request_payload` text DEFAULT NULL COMMENT 'Payload yang dikirim ke API SertifiSign (opsional, untuk audit)',
  `sertisign_callback_payload` text DEFAULT NULL COMMENT 'Payload lengkap yang diterima dari callback SertifiSign (opsional, untuk audit)',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_bs_no_rawat` (`no_rawat`) USING BTREE,
  KEY `idx_bs_no_sep` (`no_sep`) USING BTREE,
  KEY `idx_bs_transaction_id` (`transaction_id_sertisign`) USING BTREE,
  KEY `idx_bs_status_tte` (`status_tte`) USING BTREE,
  KEY `idx_bs_created_at` (`created_at`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=271 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
