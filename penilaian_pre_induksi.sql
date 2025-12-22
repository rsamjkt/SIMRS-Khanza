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

 Date: 11/12/2025 10:12:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for penilaian_pre_induksi
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_pre_induksi`;
CREATE TABLE `penilaian_pre_induksi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `tensi` varchar(8) DEFAULT NULL,
  `nadi` varchar(5) DEFAULT NULL,
  `rr` varchar(5) DEFAULT NULL,
  `suhu` varchar(5) DEFAULT NULL,
  `ekg` varchar(50) DEFAULT NULL,
  `lain_lain` varchar(50) DEFAULT NULL,
  `asesmen` enum('Sesuai Asesmen Pre Sedasi/Anestesi','Tidak Sesuai Asesmen Pre Sedasi/Anestesi') DEFAULT NULL,
  `perencanaan` varchar(300) DEFAULT NULL,
  `infus_perifier` varchar(300) DEFAULT NULL,
  `cvc` varchar(70) DEFAULT NULL,
  `posisi` enum('Supine','Lithotomi','Lateral','Prone','Perlindungan Mata','Kanan','Kiri','Lain-lain') DEFAULT NULL,
  `premedikasi` enum('Oral','IM','IV') DEFAULT NULL,
  `premedikasi_keterangan` varchar(50) DEFAULT NULL,
  `induksi` enum('Intravena','Inhalasi') DEFAULT NULL,
  `induksi_keterangan` varchar(70) DEFAULT NULL,
  `face_mask_no` varchar(20) DEFAULT NULL,
  `nasopharing_no` varchar(20) DEFAULT NULL,
  `ett_no` varchar(20) DEFAULT NULL,
  `ett_jenis` varchar(20) DEFAULT NULL,
  `ett_viksasi` varchar(25) DEFAULT NULL,
  `lma_no` varchar(20) DEFAULT NULL,
  `lma_jenis` varchar(20) DEFAULT NULL,
  `tracheostomi` varchar(60) DEFAULT NULL,
  `bronchoscopi_fiberoptik` varchar(60) DEFAULT NULL,
  `glidescopi` varchar(60) DEFAULT NULL,
  `lain_lain_tatalaksana` varchar(100) DEFAULT NULL,
  `intubasi_sesudah_tidur` enum('Ya','Tidak') DEFAULT NULL,
  `intubasi_oral` enum('Ya','Tidak') DEFAULT NULL,
  `intubasi_tracheostomi` enum('Ya','Tidak') DEFAULT NULL,
  `intubasi_keterangan` varchar(200) DEFAULT NULL,
  `sulit_ventilasi` varchar(100) DEFAULT NULL,
  `sulit_intubasi` varchar(100) DEFAULT NULL,
  `ventilasi` varchar(100) DEFAULT NULL,
  `teknik_regional_jenis` varchar(100) DEFAULT NULL,
  `teknik_regional_lokasi` varchar(40) DEFAULT NULL,
  `teknik_regional_jenis_jarum` varchar(30) DEFAULT NULL,
  `teknik_regional_kateter` enum('Ya','Tidak') DEFAULT NULL,
  `teknik_regional_kateter_viksasi` varchar(40) DEFAULT NULL,
  `teknik_regional_obat_obatan` varchar(400) DEFAULT NULL,
  `teknik_regional_komplikasi` varchar(200) DEFAULT NULL,
  `teknik_regional_hasil` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_pre_induksi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_pre_induksi_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
