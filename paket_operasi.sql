/*
 Navicat Premium Data Transfer

 Source Server         : produ
 Source Server Type    : MySQL
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.5:3306
 Source Schema         : sikrs4m2106

 Target Server Type    : MySQL
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 19/03/2025 12:14:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for paket_operasi
-- ----------------------------
DROP TABLE IF EXISTS `paket_operasi`;
CREATE TABLE `paket_operasi` (
  `kode_paket` varchar(15) NOT NULL,
  `nm_perawatan` varchar(80) NOT NULL,
  `kategori` enum('Kebidanan','Operasi') DEFAULT NULL,
  `operator1` double NOT NULL,
  `operator2` double NOT NULL,
  `operator3` double NOT NULL,
  `asisten_operator1` double DEFAULT NULL,
  `asisten_operator2` double NOT NULL,
  `asisten_operator3` double DEFAULT NULL,
  `instrumen` double DEFAULT NULL,
  `dokter_anak` double NOT NULL,
  `perawaat_resusitas` double NOT NULL,
  `dokter_anestesi` double NOT NULL,
  `asisten_anestesi` double NOT NULL,
  `asisten_anestesi2` double DEFAULT NULL,
  `bidan` double NOT NULL,
  `bidan2` double DEFAULT NULL,
  `bidan3` double DEFAULT NULL,
  `perawat_luar` double NOT NULL,
  `sewa_ok` double NOT NULL,
  `alat` double NOT NULL,
  `akomodasi` double DEFAULT NULL,
  `bagian_rs` double NOT NULL,
  `omloop` double NOT NULL,
  `omloop2` double DEFAULT NULL,
  `omloop3` double DEFAULT NULL,
  `omloop4` double DEFAULT NULL,
  `omloop5` double DEFAULT NULL,
  `sarpras` double DEFAULT NULL,
  `dokter_pjanak` double DEFAULT NULL,
  `dokter_umum` double DEFAULT NULL,
  `kd_pj` char(3) DEFAULT NULL,
  `status` enum('0','1') DEFAULT NULL,
  `kelas` enum('-','Rawat Jalan','Kelas 1','Kelas 2','Kelas 3','Kelas Utama','Kelas VIP','Kelas VVIP') DEFAULT NULL,
  PRIMARY KEY (`kode_paket`),
  KEY `nm_perawatan` (`nm_perawatan`) USING BTREE,
  KEY `operator1` (`operator1`) USING BTREE,
  KEY `operator2` (`operator2`) USING BTREE,
  KEY `operator3` (`operator3`) USING BTREE,
  KEY `asisten_operator1` (`asisten_operator1`) USING BTREE,
  KEY `asisten_operator2` (`asisten_operator2`) USING BTREE,
  KEY `asisten_operator3` (`instrumen`) USING BTREE,
  KEY `dokter_anak` (`dokter_anak`) USING BTREE,
  KEY `perawat_resusitas` (`perawaat_resusitas`) USING BTREE,
  KEY `dokter_anestasi` (`dokter_anestesi`) USING BTREE,
  KEY `asisten_anastesi` (`asisten_anestesi`) USING BTREE,
  KEY `bidan` (`bidan`) USING BTREE,
  KEY `perawat_luar` (`perawat_luar`) USING BTREE,
  KEY `sewa_ok` (`sewa_ok`) USING BTREE,
  KEY `alat` (`alat`) USING BTREE,
  KEY `sewa_vk` (`akomodasi`) USING BTREE,
  KEY `bagian_rs` (`bagian_rs`) USING BTREE,
  KEY `omloop` (`omloop`) USING BTREE,
  KEY `kd_pj` (`kd_pj`) USING BTREE,
  KEY `asisten_anestesi2` (`asisten_anestesi2`) USING BTREE,
  KEY `omloop2` (`omloop2`) USING BTREE,
  KEY `omloop3` (`omloop3`) USING BTREE,
  KEY `omloop4` (`omloop4`) USING BTREE,
  KEY `omloop5` (`omloop5`) USING BTREE,
  KEY `status` (`status`) USING BTREE,
  KEY `kategori` (`kategori`) USING BTREE,
  KEY `bidan2` (`bidan2`) USING BTREE,
  KEY `bidan3` (`bidan3`) USING BTREE,
  KEY `asisten_operator3_2` (`asisten_operator3`) USING BTREE,
  CONSTRAINT `paket_operasi_ibfk_1` FOREIGN KEY (`kd_pj`) REFERENCES `penjab` (`kd_pj`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
