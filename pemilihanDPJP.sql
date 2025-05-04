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

 Date: 04/05/2025 08:18:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for antripemilihandpjp
-- ----------------------------
DROP TABLE IF EXISTS `antripemilihandpjp`;
CREATE TABLE `antripemilihandpjp` (
  `no_surat` varchar(20) DEFAULT NULL COMMENT 'Merujuk ke no_surat di tabel surat_pemilihan_dpjp',
  `no_rawat` varchar(17) NOT NULL COMMENT 'Merujuk ke no_rawat di tabel reg_periksa / surat_pemilihan_dpjp'
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC COMMENT='Tabel antrian untuk proses terkait Surat Pemilihan DPJP';

-- ----------------------------
-- Table structure for surat_pemilihan_dpjp
-- ----------------------------
DROP TABLE IF EXISTS `surat_pemilihan_dpjp`;
CREATE TABLE `surat_pemilihan_dpjp` (
  `no_surat` varchar(20) NOT NULL COMMENT 'Nomor Unik Surat Pemilihan DPJP (Primary Key)',
  `no_rawat` varchar(17) NOT NULL COMMENT 'Nomor Registrasi Rawat Pasien (Link ke registrasi)',
  `tanggal` date NOT NULL COMMENT 'Tanggal Surat Pemilihan Dibuat',
  `no_rkm_medis` varchar(15) NOT NULL COMMENT 'Nomor Rekam Medis Pasien (Verifikasi)',
  `nama_pj` varchar(100) NOT NULL COMMENT 'Nama lengkap penandatangan surat (Pasien/Wali)',
  `alamat_pj` text DEFAULT NULL COMMENT 'Alamat penandatangan surat',
  `bertindak_atas` enum('Diri Sendiri','Suami','Istri','Anak','Ayah','Ibu','Kakak','Adik','Wali','Saudara','Lainnya') NOT NULL COMMENT 'Hubungan penandatangan dengan pasien',
  `kd_dokter` varchar(20) NOT NULL COMMENT 'Kode Dokter DPJP yang dipilih (Link ke tabel dokter)',
  `nip` varchar(20) NOT NULL COMMENT 'NIP Petugas RS yang memproses/menyaksikan (Link ke tabel petugas)',
  PRIMARY KEY (`no_surat`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  KEY `no_rkm_medis_dpjp` (`no_rkm_medis`) USING BTREE,
  KEY `kd_dokter_dpjp` (`kd_dokter`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='Tabel data surat pemilihan DPJP, struktur mirip persetujuan umum v2';

-- ----------------------------
-- Table structure for surat_pemilihan_dpjp_bukti
-- ----------------------------
DROP TABLE IF EXISTS `surat_pemilihan_dpjp_bukti`;
CREATE TABLE `surat_pemilihan_dpjp_bukti` (
  `no_surat` varchar(20) NOT NULL COMMENT 'FK ke surat_pemilihan_dpjp.no_surat',
  `photo` varchar(500) DEFAULT NULL COMMENT 'Path/Nama file bukti/scan surat',
  PRIMARY KEY (`no_surat`) USING BTREE,
  CONSTRAINT `fk_surat_pemilihan_dpjp_bukti` FOREIGN KEY (`no_surat`) REFERENCES `surat_pemilihan_dpjp` (`no_surat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='Tabel bukti/scan untuk Surat Pemilihan DPJP';

SET FOREIGN_KEY_CHECKS = 1;
