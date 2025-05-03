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

 Date: 02/05/2025 17:00:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for catatan_observasi_bayi
-- ----------------------------
DROP TABLE IF EXISTS `catatan_observasi_bayi`;
CREATE TABLE `catatan_observasi_bayi` (
  `no_rawat` varchar(17) NOT NULL COMMENT 'Nomor Registrasi Rawat (Foreign Key ke reg_periksa)',
  `tgl_perawatan` date NOT NULL COMMENT 'Tanggal Observasi',
  `jam_rawat` time NOT NULL COMMENT 'Jam Observasi',
  `hr` smallint(5) unsigned DEFAULT NULL COMMENT 'Heart Rate (Denyut Nadi) per menit',
  `rr` tinyint(3) unsigned DEFAULT NULL COMMENT 'Respiratory Rate (Frekuensi Nafas) per menit',
  `suhu` decimal(4,1) DEFAULT NULL COMMENT 'Suhu Tubuh (Celsius)',
  `spo2` tinyint(3) unsigned DEFAULT NULL COMMENT 'Saturasi Oksigen (%)',
  `td` varchar(8) DEFAULT NULL COMMENT 'Tekanan Darah (jika diukur)',
  `gcs` varchar(10) DEFAULT NULL COMMENT 'Glasgow Coma Scale (jika diukur/dinilai)',
  `retraksi_dada` varchar(50) DEFAULT NULL COMMENT 'Deskripsi Retraksi Dada (Tidak Ada, Ringan, Sedang, Berat, Jenisnya)',
  `ogt_residu` varchar(50) DEFAULT NULL COMMENT 'Residu OGT/NGT (Jumlah, Warna, Karakter)',
  `asi_jumlah` varchar(50) DEFAULT NULL COMMENT 'Asupan ASI (cth: Menyusu Langsung, 15 menit, 30 cc via dot, Tidak Ada)',
  `pasi_jumlah` varchar(50) DEFAULT NULL COMMENT 'Asupan PASI/Formula (cth: 30 cc, 1 oz, Tidak Ada)',
  `bak_status` varchar(30) DEFAULT NULL COMMENT 'Status Buang Air Kecil (Ada, Tidak Ada, Jumlah, Warna)',
  `bab_status` varchar(30) DEFAULT NULL COMMENT 'Status Buang Air Besar (Ada, Tidak Ada, Konsistensi, Warna, Mekonium)',
  `ikterik_status` varchar(50) DEFAULT NULL COMMENT 'Status Ikterik/Kuning (Tidak Ada, Kramer 1-5, Sampai Mana)',
  `nch` varchar(100) DEFAULT NULL COMMENT 'Catatan Alat Bantu Nafas (Nasal Cannula, Headbox, CPAP, dll) beserta setting jika ada',
  `nip` varchar(20) NOT NULL COMMENT 'NIP Petugas yang mencatat (Foreign Key ke petugas)',
  PRIMARY KEY (`no_rawat`,`tgl_perawatan`,`jam_rawat`) USING BTREE,
  KEY `idx_bayi_no_rawat` (`no_rawat`) USING BTREE,
  KEY `idx_bayi_nip` (`nip`) USING BTREE,
  KEY `idx_bayi_tgl_jam` (`tgl_perawatan`,`jam_rawat`) USING BTREE,
  CONSTRAINT `fk_observasi_bayi_petugas` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_observasi_bayi_rawat` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC COMMENT='Tabel Pencatatan Observasi Khusus Bayi';

SET FOREIGN_KEY_CHECKS = 1;
