/*
 Navicat Premium Data Transfer

 Source Server         : rsip root
 Source Server Type    : MariaDB
 Source Server Version : 100420 (10.4.20-MariaDB)
 Source Host           : 172.26.4.205:3306
 Source Schema         : khanza_rsip

 Target Server Type    : MariaDB
 Target Server Version : 100420 (10.4.20-MariaDB)
 File Encoding         : 65001

 Date: 15/09/2025 10:31:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ig_konsul
-- ----------------------------
DROP TABLE IF EXISTS `ig_konsul`;
CREATE TABLE `ig_konsul` (
  `no_konsultasi` varchar(100) CHARACTER SET latin1 NOT NULL,
  `no_rkm_medis` varchar(100) NOT NULL,
  `no_rawat` varchar(100) NOT NULL,
  `kategori_formulir` enum('Biasa','Cito') NOT NULL,
  `kategori_konsultasi` enum('Konsultasi','Rawat Bersama','Alih Rawat','Toleransi Operasi') NOT NULL,
  `kd_dokter` varchar(100) NOT NULL,
  `subjek` varchar(100) NOT NULL,
  `objek` varchar(100) NOT NULL,
  `asesmen` varchar(100) NOT NULL,
  `plan` varchar(100) NOT NULL,
  `kddokter_tuju` varchar(100) NOT NULL,
  `nm_dokter_dituju` varchar(100) NOT NULL,
  `ambil_alih` enum('YA','TIDAK') NOT NULL,
  `j_subjektif` varchar(100) NOT NULL,
  `j_objektif` varchar(100) NOT NULL,
  `j_asesmen` varchar(100) NOT NULL,
  `j_plan` varchar(100) NOT NULL,
  `tgl` date NOT NULL,
  PRIMARY KEY (`no_konsultasi`),
  KEY `no_konsultasi` (`no_konsultasi`) USING BTREE KEY_BLOCK_SIZE=74
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
