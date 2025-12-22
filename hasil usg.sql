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

 Date: 19/12/2025 10:41:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hasil_pemeriksaan_usg
-- ----------------------------
DROP TABLE IF EXISTS `hasil_pemeriksaan_usg`;
CREATE TABLE `hasil_pemeriksaan_usg` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `usiakehamilanhpht` varchar(50) DEFAULT NULL,
  `janin` enum('Hidup','Tidak hidup','-') NOT NULL,
  `jumlahjanin` enum('Tunggal','Gemeli','-') NOT NULL,
  `lokasi` enum('Intrauterine','Ekstrauterine','-') NOT NULL,
  `letakjanin` enum('Memanjang','Sungsang','Oblique','Dinamis','-') NOT NULL,
  `frekuensi_hr` varchar(40) DEFAULT NULL,
  `presentasi` enum('Kepala','Sungsang','Melintang','Bokong','Dinamis','-') NOT NULL,
  `kantong_gestasi` varchar(6) DEFAULT NULL,
  `ukuran_bokongkepala` varchar(6) DEFAULT NULL,
  `diameter_biparietal` varchar(6) DEFAULT NULL,
  `panjang_femur` varchar(6) DEFAULT NULL,
  `lingkar_abdomen` varchar(6) DEFAULT NULL,
  `tafsiran_berat_janin` varchar(6) DEFAULT NULL,
  `diagnosa_klinis` varchar(150) DEFAULT NULL,
  `plasenta_berimplatansi` varchar(150) DEFAULT NULL,
  `derajat_maturitas` enum('0','1','2','3') DEFAULT NULL,
  `jumlah_air_ketuban` enum('Cukup','Berkurang','Lebih','-') DEFAULT NULL,
  `peluang_sex` enum('Belum Dapat di Tentukan','Laki-laki','Perempuan') DEFAULT NULL,
  `indek_cairan_ketuban` varchar(150) DEFAULT NULL,
  `kelainan_kongenital` varchar(150) DEFAULT NULL,
  `kesimpulan` text DEFAULT NULL,
  `kiriman_dari` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `hasil_pemeriksaan_usg_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hasil_pemeriksaan_usg_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for hasil_pemeriksaan_usg_gynecologi
-- ----------------------------
DROP TABLE IF EXISTS `hasil_pemeriksaan_usg_gynecologi`;
CREATE TABLE `hasil_pemeriksaan_usg_gynecologi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `diagnosa_klinis` varchar(50) NOT NULL,
  `kiriman_dari` varchar(50) NOT NULL,
  `uterus` varchar(200) DEFAULT NULL,
  `parametrium` varchar(200) DEFAULT NULL,
  `ovarium` varchar(200) DEFAULT NULL,
  `doppler` varchar(200) DEFAULT NULL,
  `kesimpulan` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `hasil_pemeriksaan_usg_gynecologi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hasil_pemeriksaan_usg_gynecologi_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
