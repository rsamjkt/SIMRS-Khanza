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

 Date: 10/12/2025 16:04:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for skor_aldrette_pasca_anestesi
-- ----------------------------
DROP TABLE IF EXISTS `skor_aldrette_pasca_anestesi`;
CREATE TABLE `skor_aldrette_pasca_anestesi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `penilaian_skala1` enum('Tidak Sanggup Menggerakan Satupun Anggota Gerak','Sanggup Gerak 2 Anggota Tubuh','Sanggup Gerak 4 Anggota Tubuh') DEFAULT NULL,
  `penilaian_nilai1` tinyint(4) DEFAULT NULL,
  `penilaian_skala2` enum('Apnea Atau Napas Tidak Adekuat','Sesak Atau Pernapasan Sedikit Terbatas','Sanggup Bernafas Dalam Serta Disuruh Batuk') DEFAULT NULL,
  `penilaian_nilai2` tinyint(4) DEFAULT NULL,
  `penilaian_skala3` enum('± 50% Tekanan Darah Pra Anestesi','± 20% - 50% Tekanan Darah Pra Anestesi','± 20% Tekanan Darah Pra Anestesi') DEFAULT NULL,
  `penilaian_nilai3` tinyint(4) DEFAULT NULL,
  `penilaian_skala4` enum('Tidak Ada Respon','Respon Terhadap Panggilan','Sadar Penuh') DEFAULT NULL,
  `penilaian_nilai4` tinyint(4) DEFAULT NULL,
  `penilaian_skala5` enum('Cianosis','Pucat','Kemerahan / Normal') DEFAULT NULL,
  `penilaian_nilai5` tinyint(4) DEFAULT NULL,
  `penilaian_totalnilai` tinyint(4) DEFAULT NULL,
  `keluar` varchar(200) DEFAULT NULL,
  `instruksi` varchar(250) DEFAULT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `nip` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  CONSTRAINT `skor_aldrette_pasca_anestesi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skor_aldrette_pasca_anestesi_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `skor_aldrette_pasca_anestesi_ibfk_3` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
