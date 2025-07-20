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

 Date: 16/07/2025 10:48:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for resep_dokter
-- ----------------------------
DROP TABLE IF EXISTS `resep_dokter`;
CREATE TABLE `resep_dokter` (
  `no_resep` varchar(14) DEFAULT NULL,
  `kode_brng` varchar(15) DEFAULT NULL,
  `jml` double DEFAULT NULL,
  `aturan_pakai` varchar(150) DEFAULT NULL,
  KEY `no_resep` (`no_resep`) USING BTREE,
  KEY `kode_brng` (`kode_brng`) USING BTREE,
  CONSTRAINT `resep_dokter_ibfk_1` FOREIGN KEY (`no_resep`) REFERENCES `resep_obat` (`no_resep`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resep_dokter_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for resep_dokter_racikan
-- ----------------------------
DROP TABLE IF EXISTS `resep_dokter_racikan`;
CREATE TABLE `resep_dokter_racikan` (
  `no_resep` varchar(14) NOT NULL,
  `no_racik` varchar(2) NOT NULL,
  `nama_racik` varchar(100) NOT NULL,
  `kd_racik` varchar(3) NOT NULL,
  `jml_dr` int(11) NOT NULL,
  `aturan_pakai` varchar(150) NOT NULL,
  `keterangan` varchar(50) NOT NULL,
  PRIMARY KEY (`no_resep`,`no_racik`),
  KEY `kd_racik` (`kd_racik`) USING BTREE,
  CONSTRAINT `resep_dokter_racikan_ibfk_1` FOREIGN KEY (`no_resep`) REFERENCES `resep_obat` (`no_resep`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resep_dokter_racikan_ibfk_2` FOREIGN KEY (`kd_racik`) REFERENCES `metode_racik` (`kd_racik`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for resep_dokter_racikan_detail
-- ----------------------------
DROP TABLE IF EXISTS `resep_dokter_racikan_detail`;
CREATE TABLE `resep_dokter_racikan_detail` (
  `no_resep` varchar(14) NOT NULL,
  `no_racik` varchar(2) NOT NULL,
  `kode_brng` varchar(15) NOT NULL,
  `p1` double DEFAULT NULL,
  `p2` double DEFAULT NULL,
  `kandungan` varchar(10) DEFAULT NULL,
  `jml` double DEFAULT NULL,
  PRIMARY KEY (`no_resep`,`no_racik`,`kode_brng`),
  KEY `kode_brng` (`kode_brng`) USING BTREE,
  CONSTRAINT `resep_dokter_racikan_detail_ibfk_1` FOREIGN KEY (`no_resep`) REFERENCES `resep_obat` (`no_resep`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resep_dokter_racikan_detail_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for resep_luar
-- ----------------------------
DROP TABLE IF EXISTS `resep_luar`;
CREATE TABLE `resep_luar` (
  `no_resep` varchar(14) NOT NULL DEFAULT '',
  `tgl_perawatan` date DEFAULT NULL,
  `jam` time NOT NULL,
  `no_rawat` varchar(17) NOT NULL DEFAULT '',
  `kd_dokter` varchar(20) NOT NULL,
  `tgl_peresepan` date DEFAULT NULL,
  `jam_peresepan` time DEFAULT NULL,
  PRIMARY KEY (`no_resep`) USING BTREE,
  UNIQUE KEY `tgl_perawatan` (`tgl_perawatan`,`jam`,`no_rawat`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `resep_luar_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON UPDATE CASCADE,
  CONSTRAINT `resep_luar_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for resep_luar_obat
-- ----------------------------
DROP TABLE IF EXISTS `resep_luar_obat`;
CREATE TABLE `resep_luar_obat` (
  `no_resep` varchar(14) NOT NULL,
  `kode_brng` varchar(15) NOT NULL,
  `jml` double DEFAULT NULL,
  `aturan_pakai` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`no_resep`,`kode_brng`) USING BTREE,
  KEY `no_resep` (`no_resep`) USING BTREE,
  KEY `kode_brng` (`kode_brng`) USING BTREE,
  CONSTRAINT `resep_luar_obat_ibfk_1` FOREIGN KEY (`no_resep`) REFERENCES `resep_luar` (`no_resep`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resep_luar_obat_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for resep_luar_racikan
-- ----------------------------
DROP TABLE IF EXISTS `resep_luar_racikan`;
CREATE TABLE `resep_luar_racikan` (
  `no_resep` varchar(14) NOT NULL,
  `no_racik` varchar(2) NOT NULL,
  `nama_racik` varchar(100) NOT NULL,
  `kd_racik` varchar(3) NOT NULL,
  `jml_dr` int(11) NOT NULL,
  `aturan_pakai` varchar(150) NOT NULL,
  `keterangan` varchar(50) NOT NULL,
  PRIMARY KEY (`no_resep`,`no_racik`) USING BTREE,
  KEY `kd_racik` (`kd_racik`) USING BTREE,
  CONSTRAINT `resep_luar_racikan_ibfk_1` FOREIGN KEY (`no_resep`) REFERENCES `resep_luar` (`no_resep`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resep_luar_racikan_ibfk_2` FOREIGN KEY (`kd_racik`) REFERENCES `metode_racik` (`kd_racik`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for resep_luar_racikan_detail
-- ----------------------------
DROP TABLE IF EXISTS `resep_luar_racikan_detail`;
CREATE TABLE `resep_luar_racikan_detail` (
  `no_resep` varchar(14) NOT NULL,
  `no_racik` varchar(2) NOT NULL,
  `kode_brng` varchar(15) NOT NULL,
  `p1` double DEFAULT NULL,
  `p2` double DEFAULT NULL,
  `kandungan` varchar(10) DEFAULT NULL,
  `jml` double DEFAULT NULL,
  PRIMARY KEY (`no_resep`,`no_racik`,`kode_brng`) USING BTREE,
  KEY `kode_brng` (`kode_brng`) USING BTREE,
  CONSTRAINT `resep_luar_racikan_detail_ibfk_1` FOREIGN KEY (`no_resep`) REFERENCES `resep_luar` (`no_resep`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resep_luar_racikan_detail_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for resep_obat
-- ----------------------------
DROP TABLE IF EXISTS `resep_obat`;
CREATE TABLE `resep_obat` (
  `no_resep` varchar(14) NOT NULL DEFAULT '',
  `tgl_perawatan` date DEFAULT NULL,
  `jam` time NOT NULL,
  `no_rawat` varchar(17) NOT NULL DEFAULT '',
  `kd_dokter` varchar(20) NOT NULL,
  `tgl_peresepan` date DEFAULT NULL,
  `jam_peresepan` time DEFAULT NULL,
  `status` enum('ralan','ranap') DEFAULT NULL,
  `tgl_penyerahan` date NOT NULL,
  `jam_penyerahan` time NOT NULL,
  KEY `no_rawat` (`no_rawat`),
  KEY `kd_dokter` (`kd_dokter`),
  KEY `tgl_peresepan` (`tgl_peresepan`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `idx_resep_obat_rawat_dokter` (`no_rawat`,`kd_dokter`) USING BTREE,
  KEY `idx_resep_obat_noresep_rawat` (`no_resep`,`no_rawat`) USING BTREE,
  KEY `idx_resep_obat_peresepan_status` (`tgl_peresepan`,`status`) USING BTREE,
  KEY `idx_resep_tgl_status_jam` (`tgl_peresepan`,`status`,`jam_peresepan`) USING BTREE,
  CONSTRAINT `resep_obat_ibfk_3` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON UPDATE CASCADE,
  CONSTRAINT `resep_obat_ibfk_4` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for resep_pulang
-- ----------------------------
DROP TABLE IF EXISTS `resep_pulang`;
CREATE TABLE `resep_pulang` (
  `no_rawat` varchar(17) NOT NULL,
  `kode_brng` varchar(15) NOT NULL,
  `jml_barang` double NOT NULL,
  `harga` double NOT NULL,
  `total` double NOT NULL,
  `dosis` varchar(150) NOT NULL,
  `tanggal` date NOT NULL,
  `jam` time NOT NULL,
  `kd_bangsal` varchar(5) NOT NULL,
  `no_batch` varchar(20) NOT NULL,
  `no_faktur` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rawat`,`kode_brng`,`tanggal`,`jam`,`no_batch`,`no_faktur`) USING BTREE,
  KEY `kode_brng` (`kode_brng`) USING BTREE,
  KEY `kd_bangsal` (`kd_bangsal`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  CONSTRAINT `resep_pulang_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resep_pulang_ibfk_3` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON UPDATE CASCADE,
  CONSTRAINT `resep_pulang_ibfk_4` FOREIGN KEY (`kd_bangsal`) REFERENCES `bangsal` (`kd_bangsal`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
