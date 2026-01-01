/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy5

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 26/12/2025 10:08:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for assesment_medis_bayi_baru_lahir
-- ----------------------------
DROP TABLE IF EXISTS `assesment_medis_bayi_baru_lahir`;
CREATE TABLE `assesment_medis_bayi_baru_lahir` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `alloanamnesa` text DEFAULT NULL,
  `G` varchar(2) DEFAULT NULL,
  `P` varchar(2) DEFAULT NULL,
  `A` varchar(2) DEFAULT NULL,
  `minggu` varchar(2) DEFAULT NULL,
  `tempat_persalinan` enum('IGD','VK','OK') DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `jam_lahir` time DEFAULT NULL,
  `jenis_persalinan` enum('Pervaginam','SC') DEFAULT NULL,
  `indikasi` text DEFAULT NULL,
  `jenis_kelamin` enum('laki-laki','perempuan') NOT NULL,
  `keadaan` enum('hidup','mati') NOT NULL,
  `keadaan_lahir_hidup` varchar(15) DEFAULT NULL,
  `bayi_merintih` varchar(10) NOT NULL,
  `bayi_sesak` varchar(10) NOT NULL,
  `berat_badan` varchar(11) DEFAULT NULL,
  `panjang_badan` varchar(11) DEFAULT NULL,
  `lingkar_dada` varchar(11) DEFAULT NULL,
  `lingkar_kepala` varchar(11) DEFAULT NULL,
  `lingkar_perut` varchar(11) DEFAULT NULL,
  `lingkar_lengan_atas` varchar(11) DEFAULT NULL,
  `apgar_1_menit_total` varchar(11) DEFAULT NULL,
  `apgar_5_menit_total` varchar(11) DEFAULT NULL,
  `resusitasi_mengeringkan` varchar(10) NOT NULL,
  `resusitasi_vtp` varchar(10) NOT NULL,
  `resusitasi_kompresi` varchar(10) NOT NULL,
  `resusitasi_obat` varchar(10) NOT NULL,
  `resusitasi_obat_keterangan` text DEFAULT NULL,
  `kulit` enum('Normal','Tidak Normal') DEFAULT NULL,
  `kulit_keterangan` text DEFAULT NULL,
  `paru` enum('Normal','Tidak Normal') DEFAULT NULL,
  `paru_keterangan` text DEFAULT NULL,
  `extremitas_atas` enum('Normal','Tidak Normal') DEFAULT NULL,
  `extremitas_atas_keterangan` text DEFAULT NULL,
  `THT` enum('Normal','Tidak Normal') DEFAULT NULL,
  `THT_keterangan` text DEFAULT NULL,
  `jantung` enum('Normal','Tidak Normal') DEFAULT NULL,
  `jantung_keterangan` text DEFAULT NULL,
  `extremitas_bawah` enum('Normal','Tidak Normal') DEFAULT NULL,
  `extremitas_bawah_keterangan` text DEFAULT NULL,
  `mulut` enum('Normal','Tidak Normal') DEFAULT NULL,
  `mulut_keterangan` text DEFAULT NULL,
  `dada` enum('Normal','Tidak Normal') DEFAULT NULL,
  `dada_keterangan` text DEFAULT NULL,
  `abdomen` enum('Normal','Tidak Normal') DEFAULT NULL,
  `abdomen_keterangan` text DEFAULT NULL,
  `reflex_hisap` enum('Normal','Tidak Normal') DEFAULT NULL,
  `reflex_hisap_keterangan` text DEFAULT NULL,
  `leher` enum('Normal','Tidak Normal') DEFAULT NULL,
  `leher_keterangan` text DEFAULT NULL,
  `genitalia` enum('Normal','Tidak Normal') DEFAULT NULL,
  `genitalia_keterangan` text DEFAULT NULL,
  `anus` enum('Normal','Tidak Normal') DEFAULT NULL,
  `anus_keterangan` text DEFAULT NULL,
  `BAK` enum('Sudah','Belum') DEFAULT NULL,
  `BAB` enum('Sudah','Belum') DEFAULT NULL,
  `meninggal_post_partum_jam` varchar(10) DEFAULT NULL,
  `meninggal_post_partum_menit` varchar(10) DEFAULT NULL,
  `meninggal_post_partum` varchar(100) DEFAULT NULL,
  `sebab_lahir_mati` text DEFAULT NULL,
  `diagnosa_kerja` text DEFAULT NULL,
  `penatalaksanaan` text DEFAULT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
