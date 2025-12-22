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

 Date: 10/12/2025 14:49:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for catatan_anestesi_sedasi
-- ----------------------------
DROP TABLE IF EXISTS `catatan_anestesi_sedasi`;
CREATE TABLE `catatan_anestesi_sedasi` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime NOT NULL,
  `kd_dokter_bedah` varchar(20) NOT NULL,
  `kd_dokter_anestesi` varchar(20) NOT NULL,
  `diagnosa_pre_bedah` varchar(50) NOT NULL,
  `tindakan_jenis_pembedahan` varchar(50) NOT NULL,
  `diagnosa_pasca_bedah` varchar(50) NOT NULL,
  `pre_induksi_jam` varchar(10) DEFAULT NULL,
  `pre_induksi_kesadaran` enum('Compos Mentis','Somnolence','Sopor','Coma','Alert','Confusion','Voice','Pain','Unresponsive','Apatis','Delirium') DEFAULT NULL,
  `pre_induksi_td` varchar(8) DEFAULT NULL,
  `pre_induksi_nadi` varchar(5) DEFAULT NULL,
  `pre_induksi_rr` varchar(5) DEFAULT NULL,
  `pre_induksi_suhu` varchar(5) DEFAULT NULL,
  `pre_induksi_o2` varchar(5) DEFAULT NULL,
  `pre_induksi_tb` varchar(5) DEFAULT NULL,
  `pre_induksi_bb` varchar(5) DEFAULT NULL,
  `pre_induksi_rhesus` enum('+','-') DEFAULT NULL,
  `pre_induksi_hb` varchar(5) DEFAULT NULL,
  `pre_induksi_ht` varchar(5) DEFAULT NULL,
  `pre_induksi_leko` varchar(5) DEFAULT NULL,
  `pre_induksi_trombo` varchar(5) DEFAULT NULL,
  `pre_induksi_btct` varchar(5) DEFAULT NULL,
  `pre_induksi_gds` varchar(5) DEFAULT NULL,
  `pre_induksi_lainlain` varchar(30) DEFAULT NULL,
  `teknik_alat_hiopotensi` enum('Ya','Tidak') DEFAULT NULL,
  `teknik_alat_tci` enum('Ya','Tidak') DEFAULT NULL,
  `teknik_alat_cpb` enum('Ya','Tidak') DEFAULT NULL,
  `teknik_alat_ventilasi` enum('Ya','Tidak') DEFAULT NULL,
  `teknik_alat_broncoskopy` enum('Ya','Tidak') DEFAULT NULL,
  `teknik_alat_glidescopi` enum('Ya','Tidak') DEFAULT NULL,
  `teknik_alat_usg` enum('Ya','Tidak') DEFAULT NULL,
  `teknik_alat_stimulator_saraf` enum('Ya','Tidak') DEFAULT NULL,
  `teknik_alat_lainlain` varchar(100) DEFAULT NULL,
  `monitoring_ekg` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_ekg_keterangan` varchar(50) DEFAULT NULL,
  `monitoring_arteri` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_arteri_keterangan` varchar(50) DEFAULT NULL,
  `monitoring_cvp` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_cvp_keterangan` varchar(50) DEFAULT NULL,
  `monitoring_etco` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_stetoskop` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_nibp` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_ngt` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_bis` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_cath_a_pulmo` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_spo2` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_kateter` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_temp` enum('Ya','Tidak') DEFAULT NULL,
  `monitoring_lainlain` varchar(100) DEFAULT NULL,
  `status_fisik_asa` enum('1','2','3','4','5','E') DEFAULT NULL,
  `status_fisik_alergi` enum('Tidak','Ya') NOT NULL,
  `status_fisik_alergi_keterangan` varchar(50) DEFAULT NULL,
  `status_fisik_penyulit_sedasi` varchar(150) DEFAULT NULL,
  `perencanaan_lanjut` enum('Ya','Tidak') DEFAULT NULL,
  `perencanaan_lanjut_sedasi` enum('Sedang','Dalam','Tidak','Lain-lain') DEFAULT NULL,
  `perencanaan_lanjut_sedasi_keterangan` varchar(30) DEFAULT NULL,
  `perencanaan_lanjut_spinal` enum('Ya','Tidak') DEFAULT NULL,
  `perencanaan_lanjut_anestesi_umum` enum('Ya','Tidak') DEFAULT NULL,
  `perencanaan_lanjut_anestesi_umum_keterangan` varchar(30) DEFAULT NULL,
  `perencanaan_lanjut_blok_perifer` enum('Ya','Tidak') DEFAULT NULL,
  `perencanaan_lanjut_blok_perifer_keterangan` varchar(30) DEFAULT NULL,
  `perencanaan_lanjut_epidural` enum('Ya','Tidak') DEFAULT NULL,
  `perencanaan_batal` enum('Ya','Tidak') DEFAULT NULL,
  `perencanaan_batal_alasan` varchar(150) DEFAULT NULL,
  `nip_perawat_ok` varchar(20) DEFAULT NULL,
  `nip_perawat_anestesi` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`tanggal`) USING BTREE,
  KEY `nip_perawat_ok` (`nip_perawat_ok`) USING BTREE,
  KEY `kd_dokter_anestesi` (`kd_dokter_anestesi`) USING BTREE,
  KEY `kd_dokter_bedah` (`kd_dokter_bedah`) USING BTREE,
  KEY `nip_perawat_anestesi` (`nip_perawat_anestesi`) USING BTREE,
  CONSTRAINT `catatan_anestesi_sedasi_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catatan_anestesi_sedasi_ibfk_2` FOREIGN KEY (`nip_perawat_ok`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catatan_anestesi_sedasi_ibfk_3` FOREIGN KEY (`nip_perawat_anestesi`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catatan_anestesi_sedasi_ibfk_4` FOREIGN KEY (`kd_dokter_anestesi`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catatan_anestesi_sedasi_ibfk_5` FOREIGN KEY (`kd_dokter_bedah`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
