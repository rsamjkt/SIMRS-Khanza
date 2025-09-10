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

 Date: 10/09/2025 08:45:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for penilaian_awal_keperawatan_ralan_bayi
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_awal_keperawatan_ralan_bayi`;
CREATE TABLE `penilaian_awal_keperawatan_ralan_bayi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `informasi` enum('Autoanamnesis','Alloanamnesis') NOT NULL,
  `td` varchar(8) NOT NULL DEFAULT '',
  `nadi` varchar(5) NOT NULL DEFAULT '',
  `rr` varchar(5) NOT NULL,
  `suhu` varchar(5) NOT NULL DEFAULT '',
  `gcs` varchar(5) NOT NULL,
  `bb` varchar(5) NOT NULL DEFAULT '',
  `tb` varchar(5) NOT NULL DEFAULT '',
  `lp` varchar(5) NOT NULL DEFAULT '',
  `lk` varchar(5) NOT NULL DEFAULT '',
  `ld` varchar(5) NOT NULL DEFAULT '',
  `keluhan_utama` varchar(150) NOT NULL DEFAULT '',
  `rpd` varchar(100) NOT NULL DEFAULT '',
  `rpk` varchar(100) NOT NULL,
  `rpo` varchar(100) NOT NULL,
  `alergi` varchar(25) NOT NULL DEFAULT '',
  `anakke` varchar(4) NOT NULL,
  `darisaudara` varchar(4) NOT NULL,
  `caralahir` enum('Spontan','Sectio Caesaria','Lain-Lain') NOT NULL,
  `ket_caralahir` varchar(30) NOT NULL,
  `umurkelahiran` enum('Cukup Bulan','Kurang Bulan') NOT NULL,
  `kelainanbawaan` enum('Tidak Ada','Ada') NOT NULL,
  `ket_kelainan_bawaan` varchar(30) NOT NULL,
  `usiatengkurap` varchar(15) NOT NULL,
  `usiaduduk` varchar(15) NOT NULL,
  `usiaberdiri` varchar(15) NOT NULL,
  `usiagigipertama` varchar(15) NOT NULL,
  `usiaberjalan` varchar(15) NOT NULL,
  `usiabicara` varchar(15) NOT NULL,
  `usiamembaca` varchar(15) NOT NULL,
  `usiamenulis` varchar(15) NOT NULL,
  `gangguanemosi` varchar(50) NOT NULL,
  `alat_bantu` enum('Tidak','Ya') NOT NULL,
  `ket_bantu` varchar(50) NOT NULL DEFAULT '',
  `prothesa` enum('Tidak','Ya') NOT NULL,
  `ket_pro` varchar(50) NOT NULL,
  `adl` enum('Mandiri','Dibantu') NOT NULL,
  `status_psiko` enum('Tenang','Takut','Tempertantrum','Cemas','Depresi','Lain-lain') NOT NULL,
  `ket_psiko` varchar(70) NOT NULL,
  `hub_keluarga` enum('Baik','Tidak Baik') NOT NULL,
  `pengasuh` enum('Orang Tua','Kakek/Nenek','Keluarga Lainnya') NOT NULL,
  `ket_pengasuh` varchar(40) NOT NULL,
  `ekonomi` enum('Baik','Cukup','Kurang') NOT NULL,
  `budaya` enum('Tidak Ada','Ada') NOT NULL,
  `ket_budaya` varchar(50) NOT NULL,
  `edukasi` enum('Orang Tua','Keluarga') NOT NULL,
  `ket_edukasi` varchar(50) NOT NULL,
  `berjalan_a` enum('Ya','Tidak') NOT NULL,
  `berjalan_b` enum('Ya','Tidak') NOT NULL,
  `berjalan_c` enum('Ya','Tidak') NOT NULL,
  `hasil` enum('Tidak beresiko (tidak ditemukan a dan b)','Resiko rendah (ditemukan a/b)','Resiko tinggi (ditemukan a dan b)') NOT NULL,
  `lapor` enum('Ya','Tidak') NOT NULL,
  `ket_lapor` varchar(15) NOT NULL,
  `sg1` enum('Tidak','Ya') NOT NULL,
  `nilai1` enum('0','1') NOT NULL,
  `sg2` enum('Tidak','Ya') NOT NULL,
  `nilai2` enum('0','1') NOT NULL,
  `sg3` enum('Tidak','Ya') NOT NULL,
  `nilai3` enum('0','1') NOT NULL,
  `sg4` enum('Tidak','Ya') NOT NULL,
  `nilai4` enum('0','1') NOT NULL,
  `total_hasil` tinyint(4) NOT NULL,
  `wajah` enum('Tersenyum/tidak ada ekspresi khusus','Terkadang meringis/menarik diri','Sering menggetarkan dagu dan mengatupkan rahang') NOT NULL,
  `nilaiwajah` enum('0','1','2') NOT NULL,
  `kaki` enum('Gerakan normal/relaksasi','Tidak tenang/tegang','Kaki dibuat menendang/menarik') NOT NULL,
  `nilaikaki` enum('0','1','2') NOT NULL,
  `aktifitas` enum('Tidur posisi normal, mudah bergerak','Gerakan menggeliat/berguling, kaku','Melengkungkan punggung/kaku menghentak') NOT NULL,
  `nilaiaktifitas` enum('0','1','2') NOT NULL,
  `menangis` enum('Tidak menangis (mudah bergerak)','Mengerang/merengek','Menangis terus menerus, terisak, menjerit') NOT NULL,
  `nilaimenangis` enum('0','1','2') NOT NULL,
  `bersuara` enum('Bersuara normal/tenang','Tenang bila dipeluk, digendong/diajak bicara','Sulit untuk menenangkan') NOT NULL,
  `nilaibersuara` enum('0','1','2') NOT NULL,
  `hasilnyeri` tinyint(4) NOT NULL,
  `nyeri` enum('Tidak Ada Nyeri','Nyeri Akut','Nyeri Kronis') NOT NULL,
  `lokasi` varchar(50) NOT NULL,
  `durasi` varchar(25) NOT NULL,
  `frekuensi` varchar(25) NOT NULL,
  `nyeri_hilang` enum('Minum Obat','Istirahat','Mendengar Music','Berubah Posisi/Tidur','Lain-lain') NOT NULL,
  `ket_nyeri` varchar(40) NOT NULL,
  `pada_dokter` enum('Tidak','Ya') NOT NULL,
  `ket_dokter` varchar(15) NOT NULL,
  `rencana` varchar(200) NOT NULL,
  `nip` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  CONSTRAINT `penilaian_awal_keperawatan_ralan_bayi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_awal_keperawatan_ralan_bayi_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
