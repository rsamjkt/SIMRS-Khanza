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

 Date: 01/01/2026 14:23:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for berkas_pegawai
-- ----------------------------
DROP TABLE IF EXISTS `berkas_pegawai`;
CREATE TABLE `berkas_pegawai` (
  `nik` varchar(20) NOT NULL,
  `tgl_uploud` date NOT NULL,
  `kode_berkas` varchar(10) NOT NULL,
  `berkas` varchar(500) NOT NULL,
  KEY `nik` (`nik`) USING BTREE,
  KEY `kode_berkas` (`kode_berkas`) USING BTREE,
  CONSTRAINT `berkas_pegawai_ibfk_1` FOREIGN KEY (`nik`) REFERENCES `pegawai` (`nik`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `berkas_pegawai_ibfk_2` FOREIGN KEY (`kode_berkas`) REFERENCES `master_berkas_pegawai` (`kode`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for master_berkas_pegawai
-- ----------------------------
DROP TABLE IF EXISTS `master_berkas_pegawai`;
CREATE TABLE `master_berkas_pegawai` (
  `kode` varchar(10) NOT NULL,
  `kategori` enum('Tenaga klinis Dokter Umum','Tenaga klinis Dokter Spesialis','Tenaga klinis Perawat dan Bidan','Tenaga klinis Profesi Lain','Tenaga Non Klinis') NOT NULL,
  `nama_berkas` varchar(300) NOT NULL,
  `no_urut` tinyint(4) NOT NULL,
  PRIMARY KEY (`kode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for pegawai
-- ----------------------------
DROP TABLE IF EXISTS `pegawai`;
CREATE TABLE `pegawai` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nik` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jk` enum('Pria','Wanita') NOT NULL,
  `jbtn` varchar(25) NOT NULL,
  `jnj_jabatan` varchar(5) NOT NULL,
  `kode_kelompok` varchar(3) NOT NULL,
  `kode_resiko` varchar(3) NOT NULL,
  `kode_emergency` varchar(3) NOT NULL,
  `departemen` char(4) NOT NULL,
  `bidang` varchar(15) NOT NULL,
  `stts_wp` char(5) NOT NULL,
  `stts_kerja` char(3) NOT NULL,
  `npwp` varchar(15) NOT NULL,
  `pendidikan` varchar(80) NOT NULL,
  `gapok` double NOT NULL,
  `tmp_lahir` varchar(20) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `alamat` varchar(60) NOT NULL,
  `kota` varchar(20) NOT NULL,
  `mulai_kerja` date NOT NULL,
  `ms_kerja` enum('<1','PT','FT>1') NOT NULL,
  `indexins` char(4) NOT NULL,
  `bpd` varchar(50) NOT NULL,
  `rekening` varchar(25) NOT NULL,
  `stts_aktif` enum('AKTIF','CUTI','KELUAR','TENAGA LUAR','NON AKTIF') NOT NULL,
  `wajibmasuk` tinyint(2) NOT NULL,
  `pengurang` double NOT NULL,
  `indek` tinyint(4) NOT NULL,
  `mulai_kontrak` date DEFAULT NULL,
  `cuti_diambil` int(11) NOT NULL,
  `dankes` double NOT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `no_ktp` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nik_2` (`nik`) USING BTREE,
  KEY `departemen` (`departemen`) USING BTREE,
  KEY `bidang` (`bidang`) USING BTREE,
  KEY `stts_wp` (`stts_wp`) USING BTREE,
  KEY `stts_kerja` (`stts_kerja`) USING BTREE,
  KEY `pendidikan` (`pendidikan`) USING BTREE,
  KEY `indexins` (`indexins`) USING BTREE,
  KEY `jnj_jabatan` (`jnj_jabatan`) USING BTREE,
  KEY `bpd` (`bpd`) USING BTREE,
  KEY `nama` (`nama`) USING BTREE,
  KEY `jbtn` (`jbtn`) USING BTREE,
  KEY `npwp` (`npwp`) USING BTREE,
  KEY `dankes` (`dankes`) USING BTREE,
  KEY `cuti_diambil` (`cuti_diambil`) USING BTREE,
  KEY `mulai_kontrak` (`mulai_kontrak`) USING BTREE,
  KEY `stts_aktif` (`stts_aktif`) USING BTREE,
  KEY `tmp_lahir` (`tmp_lahir`) USING BTREE,
  KEY `alamat` (`alamat`) USING BTREE,
  KEY `mulai_kerja` (`mulai_kerja`) USING BTREE,
  KEY `gapok` (`gapok`) USING BTREE,
  KEY `kota` (`kota`) USING BTREE,
  KEY `pengurang` (`pengurang`) USING BTREE,
  KEY `indek` (`indek`) USING BTREE,
  KEY `jk` (`jk`) USING BTREE,
  KEY `ms_kerja` (`ms_kerja`) USING BTREE,
  KEY `tgl_lahir` (`tgl_lahir`) USING BTREE,
  KEY `rekening` (`rekening`) USING BTREE,
  KEY `wajibmasuk` (`wajibmasuk`) USING BTREE,
  KEY `kode_emergency` (`kode_emergency`) USING BTREE,
  KEY `kode_kelompok` (`kode_kelompok`) USING BTREE,
  KEY `kode_resiko` (`kode_resiko`) USING BTREE,
  CONSTRAINT `pegawai_ibfk_1` FOREIGN KEY (`jnj_jabatan`) REFERENCES `jnj_jabatan` (`kode`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_10` FOREIGN KEY (`kode_kelompok`) REFERENCES `kelompok_jabatan` (`kode_kelompok`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_11` FOREIGN KEY (`kode_resiko`) REFERENCES `resiko_kerja` (`kode_resiko`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_2` FOREIGN KEY (`departemen`) REFERENCES `departemen` (`dep_id`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_3` FOREIGN KEY (`bidang`) REFERENCES `bidang` (`nama`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_4` FOREIGN KEY (`stts_wp`) REFERENCES `stts_wp` (`stts`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_5` FOREIGN KEY (`stts_kerja`) REFERENCES `stts_kerja` (`stts`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_6` FOREIGN KEY (`pendidikan`) REFERENCES `pendidikan` (`tingkat`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_7` FOREIGN KEY (`indexins`) REFERENCES `departemen` (`dep_id`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_8` FOREIGN KEY (`bpd`) REFERENCES `bank` (`namabank`) ON UPDATE CASCADE,
  CONSTRAINT `pegawai_ibfk_9` FOREIGN KEY (`kode_emergency`) REFERENCES `emergency_index` (`kode_emergency`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=582 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
