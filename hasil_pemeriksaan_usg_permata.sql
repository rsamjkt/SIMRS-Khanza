/*
 Navicat Premium Data Transfer

 Source Server         : PermataIbunda
 Source Server Type    : MariaDB
 Source Server Version : 100420 (10.4.20-MariaDB)
 Source Host           : 172.26.69.18:3306
 Source Schema         : sik

 Target Server Type    : MariaDB
 Target Server Version : 100420 (10.4.20-MariaDB)
 File Encoding         : 65001

 Date: 30/03/2025 03:26:50
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
  `diagnosa_klinis` varchar(50) DEFAULT NULL,
  `kiriman_dari` varchar(50) DEFAULT NULL,
  `hta` varchar(40) DEFAULT NULL,
  `kantong_gestasi` varchar(6) DEFAULT NULL,
  `ukuran_bokongkepala` varchar(6) DEFAULT NULL,
  `jenis_prestasi` varchar(30) DEFAULT NULL,
  `diameter_biparietal` varchar(6) DEFAULT NULL,
  `panjang_femur` varchar(6) DEFAULT NULL,
  `lingkar_abdomen` varchar(6) DEFAULT NULL,
  `tafsiran_berat_janin` varchar(6) DEFAULT NULL,
  `usia_kehamilan` varchar(15) DEFAULT NULL,
  `plasenta_berimplatansi` varchar(50) DEFAULT NULL,
  `derajat_maturitas` enum('0','1','2','3') DEFAULT NULL,
  `jumlah_air_ketuban` enum('Cukup','Berkurang') DEFAULT NULL,
  `indek_cairan_ketuban` varchar(40) DEFAULT NULL,
  `kelainan_kongenital` varchar(60) DEFAULT NULL,
  `peluang_sex` enum('Laki-laki','Perempuan','-') DEFAULT NULL,
  `kesimpulan` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `hasil_pemeriksaan_usg_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hasil_pemeriksaan_usg_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
