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

 Date: 17/09/2025 13:24:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for booking_operasi
-- ----------------------------
DROP TABLE IF EXISTS `booking_operasi`;
CREATE TABLE `booking_operasi` (
  `no_rawat` varchar(17) NOT NULL,
  `kode_paket` varchar(15) NOT NULL,
  `tanggal` date NOT NULL,
  `jam_mulai` time NOT NULL,
  `jam_selesai` time DEFAULT NULL,
  `status` enum('Menunggu','Proses Operasi','Selesai') DEFAULT NULL,
  `kd_dokter` varchar(20) DEFAULT NULL,
  `kd_ruang_ok` varchar(3) NOT NULL,
  PRIMARY KEY (`no_rawat`,`kode_paket`,`tanggal`,`jam_mulai`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `kode_paket` (`kode_paket`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  KEY `kd_ruang_ok` (`kd_ruang_ok`) USING BTREE,
  CONSTRAINT `booking_operasi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON UPDATE CASCADE,
  CONSTRAINT `booking_operasi_ibfk_2` FOREIGN KEY (`kode_paket`) REFERENCES `paket_operasi` (`kode_paket`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `booking_operasi_ibfk_3` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `booking_operasi_ibfk_4` FOREIGN KEY (`kd_ruang_ok`) REFERENCES `ruang_ok` (`kd_ruang_ok`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for catatan_pengkajian_paska_operasi
-- ----------------------------
DROP TABLE IF EXISTS `catatan_pengkajian_paska_operasi`;
CREATE TABLE `catatan_pengkajian_paska_operasi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `rawat_paska_operasi` varchar(250) DEFAULT NULL,
  `cairan` varchar(500) DEFAULT NULL,
  `antibiotika` varchar(500) DEFAULT NULL,
  `analgetika` varchar(500) DEFAULT NULL,
  `medikamentosa_lain` varchar(500) DEFAULT NULL,
  `diet` varchar(500) DEFAULT NULL,
  `pemeriksaan_laborat` varchar(500) DEFAULT NULL,
  `tranfusi` varchar(250) DEFAULT NULL,
  `lainlain` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `catatan_pengkajian_paska_operasi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catatan_pengkajian_paska_operasi_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for checklist_post_operasi
-- ----------------------------
DROP TABLE IF EXISTS `checklist_post_operasi`;
CREATE TABLE `checklist_post_operasi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `sncn` varchar(25) NOT NULL,
  `tindakan` varchar(50) NOT NULL,
  `kd_dokter_bedah` varchar(20) NOT NULL,
  `kd_dokter_anestesi` varchar(20) NOT NULL,
  `keadaan_umum` enum('Sadar','Tidur','Terintubasi') DEFAULT NULL,
  `pemeriksaan_penunjang_rontgen` enum('Ada','Tidak Ada') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_rontgen` varchar(20) DEFAULT NULL,
  `pemeriksaan_penunjang_ekg` enum('Ada','Tidak Ada') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_ekg` varchar(20) DEFAULT NULL,
  `pemeriksaan_penunjang_usg` enum('Ada','Tidak Ada') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_usg` varchar(20) DEFAULT NULL,
  `pemeriksaan_penunjang_ctscan` enum('Ada','Tidak Ada') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_ctscan` varchar(20) DEFAULT NULL,
  `pemeriksaan_penunjang_mri` enum('Ada','Tidak Ada') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_mri` varchar(20) DEFAULT NULL,
  `jenis_cairan_infus` varchar(40) DEFAULT NULL,
  `kateter_urine` enum('Ada','Tidak Ada') DEFAULT NULL,
  `tanggal_pemasangan_kateter` datetime DEFAULT NULL,
  `warna_kateter` enum('Jernih','Keruh','-') DEFAULT NULL,
  `jumlah_kateter` varchar(4) DEFAULT NULL,
  `area_luka_operasi` varchar(120) DEFAULT NULL,
  `drain` enum('Ada','Tidak Ada') DEFAULT NULL,
  `jumlah_drain` varchar(2) DEFAULT NULL,
  `letak_drain` varchar(40) DEFAULT NULL,
  `warna_drain` varchar(30) DEFAULT NULL,
  `jaringan_pa` enum('Ada','Tidak Ada') DEFAULT NULL,
  `nip_perawat_ok` varchar(20) DEFAULT NULL,
  `nip_perawat_anestesi` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `nip_perawat_ok` (`nip_perawat_ok`) USING BTREE,
  KEY `kd_dokter_anestesi` (`kd_dokter_anestesi`) USING BTREE,
  KEY `kd_dokter_bedah` (`kd_dokter_bedah`) USING BTREE,
  KEY `nip_perawat_anestesi` (`nip_perawat_anestesi`) USING BTREE,
  CONSTRAINT `checklist_post_operasi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `checklist_post_operasi_ibfk_2` FOREIGN KEY (`nip_perawat_ok`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `checklist_post_operasi_ibfk_3` FOREIGN KEY (`nip_perawat_anestesi`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `checklist_post_operasi_ibfk_4` FOREIGN KEY (`kd_dokter_anestesi`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `checklist_post_operasi_ibfk_5` FOREIGN KEY (`kd_dokter_bedah`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for checklist_pre_operasi
-- ----------------------------
DROP TABLE IF EXISTS `checklist_pre_operasi`;
CREATE TABLE `checklist_pre_operasi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `sncn` varchar(25) NOT NULL,
  `tindakan` varchar(50) NOT NULL,
  `kd_dokter_bedah` varchar(20) NOT NULL,
  `kd_dokter_anestesi` varchar(20) NOT NULL,
  `identitas` enum('Ya','Tidak') DEFAULT NULL,
  `surat_ijin_bedah` enum('Ada','Tidak Ada') DEFAULT NULL,
  `surat_ijin_anestesi` enum('Ada','Tidak Ada') DEFAULT NULL,
  `surat_ijin_transfusi` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `penandaan_area_operasi` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `keadaan_umum` enum('Baik','Sedang','Lemah') DEFAULT NULL,
  `pemeriksaan_penunjang_rontgen` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_rontgen` varchar(20) DEFAULT NULL,
  `pemeriksaan_penunjang_ekg` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_ekg` varchar(20) DEFAULT NULL,
  `pemeriksaan_penunjang_usg` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_usg` varchar(20) DEFAULT NULL,
  `pemeriksaan_penunjang_ctscan` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_ctscan` varchar(20) DEFAULT NULL,
  `pemeriksaan_penunjang_mri` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `keterangan_pemeriksaan_penunjang_mri` varchar(20) DEFAULT NULL,
  `persiapan_darah` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `keterangan_persiapan_darah` varchar(20) DEFAULT NULL,
  `perlengkapan_khusus` enum('Ada','Tidak Ada','Tidak Diperlukan') DEFAULT NULL,
  `nip_petugas_ruangan` varchar(20) DEFAULT NULL,
  `nip_perawat_ok` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `nip_petugas_ruangan` (`nip_petugas_ruangan`) USING BTREE,
  KEY `nip_perawat_ok` (`nip_perawat_ok`) USING BTREE,
  KEY `kd_dokter_anestesi` (`kd_dokter_anestesi`) USING BTREE,
  KEY `kd_dokter_bedah` (`kd_dokter_bedah`) USING BTREE,
  CONSTRAINT `checklist_pre_operasi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `checklist_pre_operasi_ibfk_2` FOREIGN KEY (`nip_petugas_ruangan`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `checklist_pre_operasi_ibfk_3` FOREIGN KEY (`nip_perawat_ok`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `checklist_pre_operasi_ibfk_4` FOREIGN KEY (`kd_dokter_anestesi`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `checklist_pre_operasi_ibfk_5` FOREIGN KEY (`kd_dokter_bedah`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for penilaian_pre_operasi
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_pre_operasi`;
CREATE TABLE `penilaian_pre_operasi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `ringkasan_klinik` varchar(500) DEFAULT NULL,
  `pemeriksaan_fisik` varchar(500) DEFAULT NULL,
  `pemeriksaan_diagnostik` varchar(500) DEFAULT NULL,
  `diagnosa_pre_operasi` varchar(500) DEFAULT NULL,
  `rencana_tindakan_bedah` varchar(500) DEFAULT NULL,
  `hal_hal_yang_perludi_persiapkan` varchar(500) DEFAULT NULL,
  `terapi_pre_operasi` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_pre_operasi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_pre_operasi_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
