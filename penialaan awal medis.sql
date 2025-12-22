/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy4

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 11/12/2025 15:12:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for penilaian_medis_ralan
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_medis_ralan`;
CREATE TABLE `penilaian_medis_ralan` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `anamnesis` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `hubungan` varchar(30) NOT NULL,
  `keluhan_utama` varchar(2000) NOT NULL DEFAULT '',
  `rps` varchar(2000) NOT NULL,
  `rpd` varchar(1000) NOT NULL DEFAULT '',
  `rpk` varchar(1000) NOT NULL,
  `rpo` varchar(1000) NOT NULL,
  `alergi` varchar(50) NOT NULL DEFAULT '',
  `keadaan` enum('Sehat','Sakit Ringan','Sakit Sedang','Sakit Berat') NOT NULL,
  `gcs` varchar(10) NOT NULL,
  `kesadaran` enum('Compos Mentis','Apatis','Somnolen','Sopor','Koma') NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `rr` varchar(5) NOT NULL,
  `suhu` varchar(5) NOT NULL DEFAULT '',
  `spo` varchar(5) NOT NULL,
  `bb` varchar(5) NOT NULL DEFAULT '',
  `tb` varchar(5) NOT NULL DEFAULT '',
  `kepala` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `gigi` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `tht` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `thoraks` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `abdomen` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `genital` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `ekstremitas` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `kulit` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `ket_fisik` text NOT NULL,
  `ket_lokalis` text NOT NULL,
  `penunjang` text NOT NULL,
  `diagnosis` varchar(500) NOT NULL,
  `tata` text NOT NULL,
  `konsulrujuk` varchar(1000) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_medis_ralan_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_medis_ralan_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for penilaian_medis_ralan_anak
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_medis_ralan_anak`;
CREATE TABLE `penilaian_medis_ralan_anak` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `anamnesis` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `hubungan` varchar(100) NOT NULL,
  `keluhan_utama` varchar(2000) NOT NULL DEFAULT '',
  `rps` varchar(2000) NOT NULL,
  `rpd` varchar(1000) NOT NULL DEFAULT '',
  `rpk` varchar(1000) NOT NULL,
  `rpo` varchar(1000) NOT NULL,
  `alergi` varchar(100) NOT NULL DEFAULT '',
  `keadaan` enum('Sehat','Sakit Ringan','Sakit Sedang','Sakit Berat') NOT NULL,
  `gcs` varchar(10) NOT NULL,
  `kesadaran` enum('Compos Mentis','Apatis','Somnolen','Sopor','Koma') NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `rr` varchar(5) NOT NULL,
  `suhu` varchar(5) NOT NULL DEFAULT '',
  `spo` varchar(5) NOT NULL,
  `bb` varchar(5) NOT NULL DEFAULT '',
  `tb` varchar(5) NOT NULL DEFAULT '',
  `kepala` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `mata` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `gigi` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `tht` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `thoraks` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `abdomen` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `genital` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `ekstremitas` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `kulit` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `ket_fisik` text NOT NULL,
  `ket_lokalis` text NOT NULL,
  `penunjang` text NOT NULL,
  `diagnosis` varchar(500) NOT NULL,
  `tata` text NOT NULL,
  `konsul` varchar(1000) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_medis_ralan_anak_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_medis_ralan_anak_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for penilaian_medis_ralan_bedah
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_medis_ralan_bedah`;
CREATE TABLE `penilaian_medis_ralan_bedah` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `anamnesis` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `hubungan` varchar(30) NOT NULL,
  `keluhan_utama` varchar(2000) NOT NULL DEFAULT '',
  `rps` varchar(2000) NOT NULL,
  `rpd` varchar(1000) NOT NULL DEFAULT '',
  `rpo` varchar(1000) NOT NULL,
  `alergi` varchar(50) NOT NULL DEFAULT '',
  `kesadaran` enum('Compos Mentis','Apatis','Delirum') NOT NULL,
  `status` varchar(50) NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `suhu` varchar(5) NOT NULL,
  `rr` varchar(5) NOT NULL DEFAULT '',
  `bb` varchar(5) NOT NULL,
  `nyeri` varchar(5) NOT NULL DEFAULT '',
  `gcs` varchar(10) NOT NULL,
  `kepala` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `thoraks` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `abdomen` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `ekstremitas` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `genetalia` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `columna` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `muskulos` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `lainnya` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ket_lokalis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lab` varchar(500) NOT NULL,
  `rad` varchar(500) NOT NULL,
  `pemeriksaan` varchar(500) NOT NULL,
  `diagnosis` varchar(500) NOT NULL,
  `diagnosis2` varchar(500) NOT NULL,
  `permasalahan` varchar(500) NOT NULL,
  `terapi` varchar(500) NOT NULL,
  `tindakan` varchar(500) NOT NULL,
  `edukasi` varchar(500) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_medis_ralan_bedah_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_medis_ralan_bedah_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for penilaian_medis_ralan_kandungan
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_medis_ralan_kandungan`;
CREATE TABLE `penilaian_medis_ralan_kandungan` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `anamnesis` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `hubungan` varchar(100) NOT NULL,
  `keluhan_utama` varchar(2000) NOT NULL DEFAULT '',
  `rps` varchar(2000) NOT NULL,
  `rpd` varchar(1000) NOT NULL DEFAULT '',
  `rpk` varchar(1000) NOT NULL,
  `rpo` varchar(1000) NOT NULL,
  `alergi` varchar(100) NOT NULL DEFAULT '',
  `keadaan` enum('Sehat','Sakit Ringan','Sakit Sedang','Sakit Berat') NOT NULL,
  `gcs` varchar(10) NOT NULL,
  `kesadaran` enum('Compos Mentis','Apatis','Somnolen','Sopor','Koma') NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `rr` varchar(5) NOT NULL,
  `suhu` varchar(5) NOT NULL DEFAULT '',
  `spo` varchar(5) NOT NULL,
  `bb` varchar(5) NOT NULL DEFAULT '',
  `tb` varchar(5) NOT NULL DEFAULT '',
  `kepala` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `mata` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `gigi` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `tht` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `thoraks` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `abdomen` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `genital` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `ekstremitas` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `kulit` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `ket_fisik` text NOT NULL,
  `tfu` varchar(10) NOT NULL,
  `tbj` varchar(10) NOT NULL,
  `his` varchar(10) NOT NULL,
  `kontraksi` enum('Ada','Tidak') NOT NULL,
  `djj` varchar(10) NOT NULL,
  `inspeksi` text NOT NULL,
  `inspekulo` text NOT NULL,
  `vt` text NOT NULL,
  `rt` text NOT NULL,
  `ultra` text NOT NULL,
  `kardio` text NOT NULL,
  `lab` text NOT NULL,
  `diagnosis` varchar(500) NOT NULL,
  `tata` text NOT NULL,
  `konsul` varchar(1000) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_medis_ralan_kandungan_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_medis_ralan_kandungan_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for penilaian_medis_ralan_mata
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_medis_ralan_mata`;
CREATE TABLE `penilaian_medis_ralan_mata` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `anamnesis` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `hubungan` varchar(30) NOT NULL,
  `keluhan_utama` varchar(2000) NOT NULL DEFAULT '',
  `rps` varchar(2000) NOT NULL,
  `rpd` varchar(1000) NOT NULL DEFAULT '',
  `rpo` varchar(1000) NOT NULL,
  `alergi` varchar(50) NOT NULL DEFAULT '',
  `status` varchar(50) NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `rr` varchar(5) NOT NULL,
  `suhu` varchar(5) NOT NULL DEFAULT '',
  `nyeri` varchar(50) NOT NULL,
  `bb` varchar(5) NOT NULL DEFAULT '',
  `visuskanan` varchar(100) NOT NULL,
  `visuskiri` varchar(100) NOT NULL,
  `cckanan` varchar(100) NOT NULL,
  `cckiri` varchar(100) NOT NULL,
  `palkanan` varchar(100) NOT NULL,
  `palkiri` varchar(100) NOT NULL,
  `conkanan` varchar(100) NOT NULL,
  `conkiri` varchar(100) NOT NULL,
  `corneakanan` varchar(100) NOT NULL,
  `corneakiri` varchar(100) NOT NULL,
  `coakanan` varchar(100) NOT NULL,
  `coakiri` varchar(100) NOT NULL,
  `pupilkanan` varchar(100) NOT NULL,
  `pupilkiri` varchar(100) NOT NULL,
  `lensakanan` varchar(100) NOT NULL,
  `lensakiri` varchar(100) NOT NULL,
  `funduskanan` varchar(100) NOT NULL,
  `funduskiri` varchar(100) NOT NULL,
  `papilkanan` varchar(100) NOT NULL,
  `papilkiri` varchar(100) NOT NULL,
  `retinakanan` varchar(100) NOT NULL,
  `retinakiri` varchar(100) NOT NULL,
  `makulakanan` varchar(100) NOT NULL,
  `makulakiri` varchar(100) NOT NULL,
  `tiokanan` varchar(100) NOT NULL,
  `tiokiri` varchar(100) NOT NULL,
  `mbokanan` varchar(100) NOT NULL,
  `mbokiri` varchar(100) NOT NULL,
  `lab` text NOT NULL,
  `rad` text NOT NULL,
  `penunjang` text NOT NULL,
  `tes` text NOT NULL,
  `pemeriksaan` text NOT NULL,
  `diagnosis` varchar(500) NOT NULL,
  `diagnosisbdg` varchar(500) NOT NULL,
  `permasalahan` text NOT NULL,
  `terapi` text NOT NULL,
  `tindakan` text NOT NULL,
  `edukasi` varchar(1000) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_medis_ralan_mata_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_medis_ralan_mata_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for penilaian_medis_ralan_penyakit_dalam
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_medis_ralan_penyakit_dalam`;
CREATE TABLE `penilaian_medis_ralan_penyakit_dalam` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `anamnesis` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `hubungan` varchar(30) NOT NULL,
  `keluhan_utama` varchar(2000) NOT NULL DEFAULT '',
  `rps` varchar(2000) NOT NULL,
  `rpd` varchar(1000) NOT NULL DEFAULT '',
  `rpo` varchar(1000) NOT NULL,
  `alergi` varchar(50) NOT NULL DEFAULT '',
  `kondisi` varchar(500) NOT NULL,
  `status` varchar(100) NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `suhu` varchar(5) NOT NULL,
  `rr` varchar(5) NOT NULL DEFAULT '',
  `bb` varchar(5) NOT NULL,
  `nyeri` varchar(50) NOT NULL DEFAULT '',
  `gcs` varchar(10) NOT NULL,
  `kepala` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `keterangan_kepala` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `thoraks` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `keterangan_thorak` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `abdomen` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `keterangan_abdomen` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ekstremitas` enum('Normal','Abnormal','Tidak Diperiksa') NOT NULL,
  `keterangan_ekstremitas` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lainnya` varchar(1000) NOT NULL,
  `lab` varchar(1000) NOT NULL,
  `rad` varchar(1000) NOT NULL,
  `penunjanglain` varchar(1000) NOT NULL,
  `diagnosis` varchar(500) NOT NULL,
  `diagnosis2` varchar(500) NOT NULL,
  `permasalahan` varchar(500) NOT NULL,
  `terapi` varchar(500) NOT NULL,
  `tindakan` varchar(200) NOT NULL,
  `edukasi` varchar(500) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_medis_ralan_penyakit_dalam_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_medis_ralan_penyakit_dalam_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
