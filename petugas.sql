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

 Date: 11/10/2025 11:31:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for petugas
-- ----------------------------
DROP TABLE IF EXISTS `petugas`;
CREATE TABLE `petugas` (
  `nip` varchar(20) NOT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `jk` enum('L','P') DEFAULT NULL,
  `tmp_lahir` varchar(20) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `gol_darah` enum('A','B','O','AB','-') DEFAULT NULL,
  `agama` varchar(12) DEFAULT NULL,
  `stts_nikah` enum('BELUM MENIKAH','MENIKAH','JANDA','DUDHA','JOMBLO') DEFAULT NULL,
  `alamat` varchar(60) DEFAULT NULL,
  `kd_jbtn` char(4) DEFAULT NULL,
  `no_telp` varchar(13) DEFAULT NULL,
  `email` varchar(70) NOT NULL,
  `status` enum('0','1') DEFAULT NULL,
  PRIMARY KEY (`nip`),
  KEY `kd_jbtn` (`kd_jbtn`) USING BTREE,
  KEY `nama` (`nama`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  KEY `tmp_lahir` (`tmp_lahir`) USING BTREE,
  KEY `tgl_lahir` (`tgl_lahir`) USING BTREE,
  KEY `agama` (`agama`) USING BTREE,
  KEY `stts_nikah` (`stts_nikah`) USING BTREE,
  KEY `alamat` (`alamat`) USING BTREE,
  CONSTRAINT `petugas_ibfk_4` FOREIGN KEY (`nip`) REFERENCES `pegawai` (`nik`) ON UPDATE CASCADE,
  CONSTRAINT `petugas_ibfk_5` FOREIGN KEY (`kd_jbtn`) REFERENCES `jabatan` (`kd_jbtn`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
