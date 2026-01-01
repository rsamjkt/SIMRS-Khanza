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

 Date: 01/01/2026 14:24:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for riwayat_jabatan
-- ----------------------------
DROP TABLE IF EXISTS `riwayat_jabatan`;
CREATE TABLE `riwayat_jabatan` (
  `id` int(11) NOT NULL,
  `jabatan` varchar(50) NOT NULL,
  `tmt_pangkat` date NOT NULL,
  `tmt_pangkat_yad` date NOT NULL,
  `pejabat_penetap` varchar(50) NOT NULL,
  `nomor_sk` varchar(25) NOT NULL,
  `tgl_sk` date NOT NULL,
  `dasar_peraturan` varchar(50) NOT NULL,
  `masa_kerja` int(11) NOT NULL,
  `bln_kerja` int(11) NOT NULL,
  `berkas` varchar(500) NOT NULL,
  PRIMARY KEY (`id`,`jabatan`),
  KEY `jnj_jabatan` (`jabatan`) USING BTREE,
  CONSTRAINT `riwayat_jabatan_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for riwayat_naik_gaji
-- ----------------------------
DROP TABLE IF EXISTS `riwayat_naik_gaji`;
CREATE TABLE `riwayat_naik_gaji` (
  `id` int(11) NOT NULL,
  `pangkatjabatan` varchar(50) NOT NULL,
  `gapok` double NOT NULL,
  `tmt_berkala` date NOT NULL,
  `tmt_berkala_yad` date NOT NULL,
  `no_sk` varchar(25) NOT NULL,
  `tgl_sk` date NOT NULL,
  `masa_kerja` int(11) NOT NULL,
  `bulan_kerja` int(11) NOT NULL,
  `berkas` varchar(500) NOT NULL,
  PRIMARY KEY (`id`,`pangkatjabatan`,`gapok`),
  CONSTRAINT `riwayat_naik_gaji_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for riwayat_pendidikan
-- ----------------------------
DROP TABLE IF EXISTS `riwayat_pendidikan`;
CREATE TABLE `riwayat_pendidikan` (
  `id` int(11) NOT NULL,
  `pendidikan` enum('SD','SMP','SMA','SMK','D I','D II','D III','D IV','S1','S2','S3','Post Doctor') NOT NULL,
  `sekolah` varchar(50) NOT NULL,
  `jurusan` varchar(40) NOT NULL,
  `thn_lulus` year(4) NOT NULL,
  `kepala` varchar(50) NOT NULL,
  `pendanaan` enum('Biaya Sendiri','Biaya Instansi Sendiri','Lembaga Swasta Kerjasama','Lembaga Swasta Kompetisi','Lembaga Pemerintah Kerjasama','Lembaga Pemerintah Kompetisi','Lembaga Internasional') NOT NULL,
  `keterangan` varchar(50) NOT NULL,
  `status` varchar(40) NOT NULL,
  `berkas` varchar(500) NOT NULL,
  PRIMARY KEY (`id`,`pendidikan`,`sekolah`),
  CONSTRAINT `riwayat_pendidikan_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for riwayat_penelitian
-- ----------------------------
DROP TABLE IF EXISTS `riwayat_penelitian`;
CREATE TABLE `riwayat_penelitian` (
  `id` int(11) NOT NULL,
  `jenis_penelitian` varchar(30) NOT NULL,
  `peranan` varchar(30) NOT NULL,
  `judul_penelitian` varchar(60) NOT NULL,
  `judul_jurnal` varchar(60) NOT NULL,
  `tahun` year(4) NOT NULL,
  `biaya_penelitian` double DEFAULT NULL,
  `asal_dana` varchar(30) NOT NULL,
  `berkas` varchar(500) NOT NULL,
  PRIMARY KEY (`id`,`judul_penelitian`,`tahun`),
  CONSTRAINT `riwayat_penelitian_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for riwayat_penghargaan
-- ----------------------------
DROP TABLE IF EXISTS `riwayat_penghargaan`;
CREATE TABLE `riwayat_penghargaan` (
  `id` int(11) NOT NULL,
  `jenis` varchar(30) NOT NULL,
  `nama_penghargaan` varchar(60) NOT NULL,
  `tanggal` date NOT NULL,
  `instansi` varchar(40) NOT NULL,
  `pejabat_pemberi` varchar(40) NOT NULL,
  `berkas` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`,`nama_penghargaan`,`tanggal`),
  CONSTRAINT `riwayat_penghargaan_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for riwayat_seminar
-- ----------------------------
DROP TABLE IF EXISTS `riwayat_seminar`;
CREATE TABLE `riwayat_seminar` (
  `id` int(11) NOT NULL,
  `tingkat` enum('Local','Regional','Nasional','Internasional') NOT NULL,
  `jenis` enum('WORKSHOP','SIMPOSIUM','SEMINAR','FGD','PELATIHAN','LAINNYA') NOT NULL,
  `nama_seminar` varchar(50) NOT NULL,
  `peranan` varchar(40) NOT NULL,
  `mulai` date NOT NULL,
  `selesai` date NOT NULL,
  `penyelengara` varchar(50) NOT NULL,
  `tempat` varchar(50) NOT NULL,
  `berkas` varchar(500) NOT NULL,
  PRIMARY KEY (`id`,`nama_seminar`,`mulai`),
  KEY `id` (`id`) USING BTREE,
  CONSTRAINT `riwayat_seminar_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for riwayat_surat_peringatan
-- ----------------------------
DROP TABLE IF EXISTS `riwayat_surat_peringatan`;
CREATE TABLE `riwayat_surat_peringatan` (
  `id` int(11) NOT NULL,
  `jenis` varchar(30) NOT NULL,
  `nama_peringatan` varchar(60) NOT NULL,
  `tanggal` date NOT NULL,
  `berkas` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`,`nama_peringatan`,`tanggal`) USING BTREE,
  CONSTRAINT `riwayat_surat_peringatan_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
