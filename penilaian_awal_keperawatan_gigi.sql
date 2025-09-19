/*
 Navicat Premium Data Transfer

 Source Server         : ci-zero
 Source Server Type    : MySQL
 Source Server Version : 100338 (10.3.38-MariaDB-0ubuntu0.20.04.1-log)
 Source Host           : 192.168.196.251:3306
 Source Schema         : sikci2

 Target Server Type    : MySQL
 Target Server Version : 100338 (10.3.38-MariaDB-0ubuntu0.20.04.1-log)
 File Encoding         : 65001

 Date: 13/09/2025 11:35:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for penilaian_awal_keperawatan_gigi
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_awal_keperawatan_gigi`;
CREATE TABLE `penilaian_awal_keperawatan_gigi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `informasi` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `rr` varchar(5) NOT NULL,
  `suhu` varchar(5) NOT NULL DEFAULT '',
  `bb` varchar(5) NOT NULL DEFAULT '',
  `tb` varchar(5) NOT NULL DEFAULT '',
  `bmi` varchar(10) NOT NULL,
  `keluhan_utama` varchar(150) NOT NULL DEFAULT '',
  `riwayat_penyakit` enum('Tidak Ada','Diabetes Melitus','Hipertensi','Penyakit Jantung','HIV','Hepatitis','Haemophilia','Lain-lain') DEFAULT NULL,
  `ket_riwayat_penyakit` varchar(30) NOT NULL,
  `alergi` varchar(25) NOT NULL DEFAULT '',
  `riwayat_perawatan_gigi` enum('Tidak','Ya, Kapan') NOT NULL,
  `ket_riwayat_perawatan_gigi` varchar(50) NOT NULL DEFAULT '',
  `kebiasaan_sikat_gigi` enum('1x','2x','3x','Mandi','Setelah Makan','Sebelum Tidur') NOT NULL,
  `kebiasaan_lain` enum('Tidak ada','Minum kopi/teh','Minum alkohol','Bruxism','Menggigit pensil','Mengunyah 1 sisi rahang','Merokok','Lain-lain') DEFAULT NULL,
  `ket_kebiasaan_lain` varchar(30) NOT NULL,
  `obat_yang_diminum_saatini` varchar(100) DEFAULT NULL,
  `alat_bantu` enum('Tidak','Ya') NOT NULL,
  `ket_alat_bantu` varchar(30) NOT NULL,
  `prothesa` enum('Tidak','Ya') NOT NULL,
  `ket_pro` varchar(50) NOT NULL,
  `status_psiko` enum('Tenang','Takut','Cemas','Depresi','Lain-lain') NOT NULL,
  `ket_psiko` varchar(70) NOT NULL,
  `hub_keluarga` enum('Baik','Tidak Baik') NOT NULL,
  `tinggal_dengan` enum('Sendiri','Orang Tua','Suami / Istri','Lainnya') NOT NULL,
  `ket_tinggal` varchar(40) NOT NULL,
  `ekonomi` enum('Baik','Cukup','Kurang') NOT NULL,
  `budaya` enum('Tidak Ada','Ada') NOT NULL,
  `ket_budaya` varchar(50) NOT NULL,
  `edukasi` enum('Pasien','Keluarga') NOT NULL,
  `ket_edukasi` varchar(50) NOT NULL,
  `berjalan_a` enum('Ya','Tidak') NOT NULL,
  `berjalan_b` enum('Ya','Tidak') NOT NULL,
  `berjalan_c` enum('Ya','Tidak') NOT NULL,
  `hasil` enum('Tidak beresiko (tidak ditemukan a dan b)','Resiko rendah (ditemukan a/b)','Resiko tinggi (ditemukan a dan b)') NOT NULL,
  `lapor` enum('Ya','Tidak') NOT NULL,
  `ket_lapor` varchar(15) NOT NULL,
  `nyeri` enum('Tidak Ada Nyeri','Nyeri Akut','Nyeri Kronis') NOT NULL,
  `lokasi` varchar(50) NOT NULL,
  `skala_nyeri` enum('0','1','2','3','4','5','6','7','8','9','10') NOT NULL,
  `durasi` varchar(25) NOT NULL,
  `frekuensi` varchar(25) NOT NULL,
  `nyeri_hilang` enum('Istirahat','Medengar Musik','Minum Obat','Tidak ada nyeri','Lain-lain') NOT NULL,
  `ket_nyeri` varchar(40) NOT NULL,
  `pada_dokter` enum('Tidak','Ya') NOT NULL,
  `ket_dokter` varchar(15) NOT NULL,
  `kebersihan_mulut` enum('Baik','Cukup','Kurang') NOT NULL,
  `mukosa_mulut` enum('Normal','Pigmentasi','Radang') NOT NULL,
  `karies` enum('Ada','Tidak') NOT NULL,
  `karang_gigi` enum('Ada','Tidak') NOT NULL,
  `gingiva` enum('Normal','Radang') NOT NULL,
  `palatum` enum('Normal','Radang') NOT NULL,
  `rencana` varchar(200) NOT NULL,
  `nip` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  CONSTRAINT `penilaian_awal_keperawatan_gigi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_awal_keperawatan_gigi_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
