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

 Date: 27/12/2025 19:51:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for resume_pasien_ranap
-- ----------------------------
DROP TABLE IF EXISTS `resume_pasien_ranap`;
CREATE TABLE `resume_pasien_ranap` (
  `no_rawat` varchar(17) NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `diagnosa_awal` varchar(100) NOT NULL,
  `alasan` varchar(100) NOT NULL,
  `keluhan_utama` text NOT NULL,
  `pemeriksaan_fisik` text NOT NULL,
  `jalannya_penyakit` text NOT NULL,
  `pemeriksaan_penunjang` text NOT NULL,
  `hasil_laborat` text NOT NULL,
  `tindakan_dan_operasi` text NOT NULL,
  `obat_di_rs` text NOT NULL,
  `diagnosa_utama` varchar(80) NOT NULL,
  `kd_diagnosa_utama` varchar(10) NOT NULL,
  `diagnosa_sekunder` varchar(80) NOT NULL,
  `kd_diagnosa_sekunder` varchar(10) NOT NULL,
  `diagnosa_sekunder2` varchar(150) NOT NULL,
  `kd_diagnosa_sekunder2` varchar(10) NOT NULL,
  `diagnosa_sekunder3` varchar(150) NOT NULL,
  `kd_diagnosa_sekunder3` varchar(10) NOT NULL,
  `diagnosa_sekunder4` varchar(150) NOT NULL,
  `kd_diagnosa_sekunder4` varchar(10) NOT NULL,
  `prosedur_utama` varchar(150) NOT NULL,
  `kd_prosedur_utama` varchar(8) NOT NULL,
  `prosedur_sekunder` varchar(150) NOT NULL,
  `kd_prosedur_sekunder` varchar(8) NOT NULL,
  `prosedur_sekunder2` varchar(150) NOT NULL,
  `kd_prosedur_sekunder2` varchar(8) NOT NULL,
  `prosedur_sekunder3` varchar(150) NOT NULL,
  `kd_prosedur_sekunder3` varchar(8) NOT NULL,
  `alergi` varchar(100) NOT NULL,
  `diet` text NOT NULL,
  `lab_belum` text NOT NULL,
  `edukasi` text NOT NULL,
  `cara_keluar` enum('Atas Izin Dokter','Pindah RS','Pulang Atas Permintaan Sendiri','Lainnya') NOT NULL,
  `ket_keluar` varchar(50) DEFAULT NULL,
  `keadaan` enum('Membaik','Sembuh','Keadaan Khusus','Meninggal','Atas Permintaan Sendiri','APS','Dirujuk') NOT NULL,
  `ket_keadaan` varchar(50) DEFAULT NULL,
  `dilanjutkan` enum('Kembali Ke RS','RS Lain','Dokter Luar','Puskesmes','Lainnya') NOT NULL,
  `ket_dilanjutkan` varchar(50) DEFAULT NULL,
  `kontrol` datetime DEFAULT NULL,
  `obat_pulang` text NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `resume_pasien_ranap_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resume_pasien_ranap_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
