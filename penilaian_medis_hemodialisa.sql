/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 17/03/2025 13:54:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for penilaian_medis_hemodialisa
-- ----------------------------
DROP TABLE IF EXISTS `penilaian_medis_hemodialisa`;
CREATE TABLE `penilaian_medis_hemodialisa` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal` datetime DEFAULT NULL,
  `kd_dokter` varchar(20) DEFAULT NULL,
  `anamnesis` enum('Autoanamnesis','Alloanamnesis') DEFAULT NULL,
  `hubungan` varchar(30) DEFAULT NULL,
  `ruangan` varchar(50) DEFAULT NULL,
  `alergi` varchar(100) DEFAULT NULL,
  `nyeri` enum('Tidak Nyeri','Nyeri Ringan','Nyeri Sedang','Nyeri Berat','Nyeri Sangat Berat','Nyeri Tak Tertahankan') DEFAULT NULL,
  `status_nutrisi` varchar(100) DEFAULT NULL,
  `hipertensi` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_hipertensi` varchar(30) DEFAULT NULL,
  `diabetes` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_diabetes` varchar(30) DEFAULT NULL,
  `batu_saluran_kemih` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_batu_saluran_kemih` varchar(30) DEFAULT NULL,
  `operasi_saluran_kemih` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_operasi_saluran_kemih` varchar(30) DEFAULT NULL,
  `infeksi_saluran_kemih` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_infeksi_saluran_kemih` varchar(30) DEFAULT NULL,
  `bengkak_seluruh_tubuh` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_bengkak_seluruh_tubuh` varchar(30) DEFAULT NULL,
  `urin_berdarah` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_urin_berdarah` varchar(30) DEFAULT NULL,
  `penyakit_ginjal_laom` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_penyakit_ginjal_laom` varchar(30) DEFAULT NULL,
  `penyakit_lain` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_penyakit_lain` varchar(30) DEFAULT NULL,
  `konsumsi_obat_nefro` enum('Tidak','Ya') DEFAULT NULL,
  `keterangan_konsumsi_obat_nefro` varchar(30) DEFAULT NULL,
  `dialisis_pertama` date NOT NULL,
  `pernah_cpad` enum('Tidak','Ya') NOT NULL,
  `tanggal_cpad` date NOT NULL,
  `pernah_transplantasi` enum('Tidak','Ya') NOT NULL,
  `tanggal_transplantasi` date NOT NULL,
  `keadaan_umum` enum('Sehat','Sakit Ringan','Sakit Sedang','Sakit Berat') NOT NULL,
  `kesadaran` enum('Compos Mentis','Apatis','Somnolen','Sopor','Koma') NOT NULL,
  `nadi` varchar(5) NOT NULL,
  `bb` varchar(5) NOT NULL,
  `td` varchar(8) NOT NULL,
  `suhu` varchar(5) NOT NULL,
  `napas` varchar(5) NOT NULL,
  `tb` varchar(5) NOT NULL,
  `hepatomegali` enum('Tidak','Ya') NOT NULL,
  `splenomegali` enum('Tidak','Ya') NOT NULL,
  `ascites` enum('Tidak','Ya') NOT NULL,
  `edema` enum('Tidak','Ya') NOT NULL,
  `whezzing` enum('Tidak','Ya') NOT NULL,
  `ronchi` enum('Tidak','Ya') NOT NULL,
  `ikterik` enum('Tidak','Ya') NOT NULL,
  `tekanan_vena` enum('Normal','Meningkat') NOT NULL,
  `anemia` enum('Tidak','Ya') NOT NULL,
  `kardiomegali` enum('Tidak','Ya') NOT NULL,
  `bising` enum('Tidak','Ya') NOT NULL,
  `thorax` enum('Tidak','Ya') NOT NULL,
  `tanggal_thorax` date NOT NULL,
  `ekg` enum('Tidak','Ya') NOT NULL,
  `tanggal_ekg` date NOT NULL,
  `bno` enum('Tidak','Ya') NOT NULL,
  `tanggal_bno` date NOT NULL,
  `usg` enum('Tidak','Ya') NOT NULL,
  `tanggal_usg` date NOT NULL,
  `renogram` enum('Tidak','Ya') NOT NULL,
  `tanggal_renogram` date NOT NULL,
  `biopsi` enum('Tidak','Ya') NOT NULL,
  `tanggal_biopsi` date NOT NULL,
  `ctscan` enum('Tidak','Ya') NOT NULL,
  `tanggal_ctscan` date NOT NULL,
  `arteriografi` enum('Tidak','Ya') NOT NULL,
  `tanggal_arteriografi` date NOT NULL,
  `kultur_urin` enum('Tidak','Ya') NOT NULL,
  `tanggal_kultur_urin` date NOT NULL,
  `laborat` enum('Tidak','Ya') NOT NULL,
  `tanggal_laborat` date NOT NULL,
  `hematokrit` varchar(30) NOT NULL,
  `hemoglobin` varchar(30) NOT NULL,
  `leukosit` varchar(30) NOT NULL,
  `trombosit` varchar(30) NOT NULL,
  `hitung_jenis` varchar(30) NOT NULL,
  `ureum` varchar(30) NOT NULL,
  `urin_lengkap` varchar(30) NOT NULL,
  `kreatinin` varchar(30) NOT NULL,
  `cct` varchar(30) NOT NULL,
  `sgot` varchar(30) NOT NULL,
  `sgpt` varchar(30) NOT NULL,
  `ct` varchar(30) NOT NULL,
  `asam_urat` varchar(30) NOT NULL,
  `hbsag` enum('Non Reaktif','Reaktif') NOT NULL,
  `anti_hcv` enum('Non Reaktif','Reaktif') NOT NULL,
  `edukasi` varchar(1000) NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `penilaian_medis_hemodialisa_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penilaian_medis_hemodialisa_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
