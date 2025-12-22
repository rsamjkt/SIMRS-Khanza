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

 Date: 10/12/2025 16:31:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for skor_bromage_pasca_anestesi
-- ----------------------------
DROP TABLE IF EXISTS `skor_bromage_pasca_anestesi`;
CREATE TABLE `skor_bromage_pasca_anestesi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `penilaian_skala1` enum('Gerakan Penuh Dari Tungkai','Tidak Mampu Extensi Tungkai','Tidak Mampu Flexi Lutut','Tidak Mampu Flexi Pergelangan Kaki') DEFAULT NULL,
  `penilaian_nilai1` tinyint(4) DEFAULT NULL,
  `keluar` varchar(200) DEFAULT NULL,
  `instruksi` varchar(200) DEFAULT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `nip` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  CONSTRAINT `skor_bromage_pasca_anestesi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skor_bromage_pasca_anestesi_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skor_bromage_pasca_anestesi_ibfk_3` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
