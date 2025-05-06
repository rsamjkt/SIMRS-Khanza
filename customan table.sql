/*
 Navicat Premium Data Transfer

 Source Server         : serverDevAtta-ygakseIP
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.121:3306
 Source Schema         : sikrsamlegacy2

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 06/05/2025 16:12:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for catatan_keseimbangan_cairan
-- ----------------------------
DROP TABLE IF EXISTS `catatan_keseimbangan_cairan`;
CREATE TABLE `catatan_keseimbangan_cairan` (
  `no_rawat` varchar(17) NOT NULL,
  `tgl_perawatan` date NOT NULL,
  `jam_rawat` time NOT NULL,
  `nama_obat` varchar(100) NOT NULL,
  `infus` varchar(4) DEFAULT NULL,
  `tranfusi` varchar(4) NOT NULL,
  `minum` varchar(4) DEFAULT NULL,
  `urine` varchar(4) DEFAULT NULL,
  `drain` varchar(4) DEFAULT NULL,
  `ngt` varchar(4) NOT NULL,
  `iwl` varchar(4) NOT NULL,
  `keseimbangan` varchar(4) NOT NULL,
  `keterangan` varchar(200) NOT NULL,
  `nip` varchar(20) NOT NULL,
  PRIMARY KEY (`no_rawat`,`tgl_perawatan`,`jam_rawat`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  CONSTRAINT `catatan_keseimbangan_cairan_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `catatan_keseimbangan_cairan_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for catatan_observasi_bayi
-- ----------------------------
DROP TABLE IF EXISTS `catatan_observasi_bayi`;
CREATE TABLE `catatan_observasi_bayi` (
  `no_rawat` varchar(17) NOT NULL COMMENT 'Nomor Registrasi Rawat (Foreign Key ke reg_periksa)',
  `tgl_perawatan` date NOT NULL COMMENT 'Tanggal Observasi',
  `jam_rawat` time NOT NULL COMMENT 'Jam Observasi',
  `hr` smallint(5) unsigned DEFAULT NULL COMMENT 'Heart Rate (Denyut Nadi) per menit',
  `rr` tinyint(3) unsigned DEFAULT NULL COMMENT 'Respiratory Rate (Frekuensi Nafas) per menit',
  `suhu` decimal(4,1) DEFAULT NULL COMMENT 'Suhu Tubuh (Celsius)',
  `spo2` tinyint(3) unsigned DEFAULT NULL COMMENT 'Saturasi Oksigen (%)',
  `td` varchar(8) DEFAULT NULL COMMENT 'Tekanan Darah (jika diukur)',
  `gcs` varchar(10) DEFAULT NULL COMMENT 'Glasgow Coma Scale (jika diukur/dinilai)',
  `retraksi_dada` varchar(50) DEFAULT NULL COMMENT 'Deskripsi Retraksi Dada (Tidak Ada, Ringan, Sedang, Berat, Jenisnya)',
  `ogt_residu` varchar(50) DEFAULT NULL COMMENT 'Residu OGT/NGT (Jumlah, Warna, Karakter)',
  `asi_jumlah` varchar(50) DEFAULT NULL COMMENT 'Asupan ASI (cth: Menyusu Langsung, 15 menit, 30 cc via dot, Tidak Ada)',
  `pasi_jumlah` varchar(50) DEFAULT NULL COMMENT 'Asupan PASI/Formula (cth: 30 cc, 1 oz, Tidak Ada)',
  `bak_status` varchar(30) DEFAULT NULL COMMENT 'Status Buang Air Kecil (Ada, Tidak Ada, Jumlah, Warna)',
  `bab_status` varchar(30) DEFAULT NULL COMMENT 'Status Buang Air Besar (Ada, Tidak Ada, Konsistensi, Warna, Mekonium)',
  `ikterik_status` varchar(50) DEFAULT NULL COMMENT 'Status Ikterik/Kuning (Tidak Ada, Kramer 1-5, Sampai Mana)',
  `nch` varchar(100) DEFAULT NULL COMMENT 'Catatan Alat Bantu Nafas (Nasal Cannula, Headbox, CPAP, dll) beserta setting jika ada',
  `nip` varchar(20) NOT NULL COMMENT 'NIP Petugas yang mencatat (Foreign Key ke petugas)',
  PRIMARY KEY (`no_rawat`,`tgl_perawatan`,`jam_rawat`) USING BTREE,
  KEY `idx_bayi_no_rawat` (`no_rawat`) USING BTREE,
  KEY `idx_bayi_nip` (`nip`) USING BTREE,
  KEY `idx_bayi_tgl_jam` (`tgl_perawatan`,`jam_rawat`) USING BTREE,
  CONSTRAINT `fk_observasi_bayi_petugas` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_observasi_bayi_rawat` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC COMMENT='Tabel Pencatatan Observasi Khusus Bayi';

-- ----------------------------
-- Table structure for surat_pemilihan_dpjp
-- ----------------------------
DROP TABLE IF EXISTS `surat_pemilihan_dpjp`;
CREATE TABLE `surat_pemilihan_dpjp` (
  `no_surat` varchar(20) NOT NULL COMMENT 'Nomor Unik Surat Pemilihan DPJP (Primary Key)',
  `no_rawat` varchar(17) NOT NULL COMMENT 'Nomor Registrasi Rawat Pasien (Link ke registrasi)',
  `tanggal` date NOT NULL COMMENT 'Tanggal Surat Pemilihan Dibuat',
  `no_rkm_medis` varchar(15) NOT NULL COMMENT 'Nomor Rekam Medis Pasien (Verifikasi)',
  `nama_pj` varchar(100) NOT NULL COMMENT 'Nama lengkap penandatangan surat (Pasien/Wali)',
  `alamat_pj` text DEFAULT NULL COMMENT 'Alamat penandatangan surat',
  `bertindak_atas` enum('Diri Sendiri','Suami','Istri','Anak','Ayah','Ibu','Kakak','Adik','Wali','Saudara','Lainnya') NOT NULL COMMENT 'Hubungan penandatangan dengan pasien',
  `kd_dokter` varchar(20) NOT NULL COMMENT 'Kode Dokter DPJP yang dipilih (Link ke tabel dokter)',
  `nip` varchar(20) NOT NULL COMMENT 'NIP Petugas RS yang memproses/menyaksikan (Link ke tabel petugas)',
  PRIMARY KEY (`no_surat`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  KEY `no_rkm_medis_dpjp` (`no_rkm_medis`) USING BTREE,
  KEY `kd_dokter_dpjp` (`kd_dokter`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='Tabel data surat pemilihan DPJP, struktur mirip persetujuan umum v2';

-- ----------------------------
-- Table structure for surat_pemilihan_dpjp_bukti
-- ----------------------------
DROP TABLE IF EXISTS `surat_pemilihan_dpjp_bukti`;
CREATE TABLE `surat_pemilihan_dpjp_bukti` (
  `no_surat` varchar(20) NOT NULL COMMENT 'FK ke surat_pemilihan_dpjp.no_surat',
  `photo` varchar(500) DEFAULT NULL COMMENT 'Path/Nama file bukti/scan surat',
  PRIMARY KEY (`no_surat`) USING BTREE,
  CONSTRAINT `fk_surat_pemilihan_dpjp_bukti` FOREIGN KEY (`no_surat`) REFERENCES `surat_pemilihan_dpjp` (`no_surat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='Tabel bukti/scan untuk Surat Pemilihan DPJP';

-- ----------------------------
-- Table structure for surat_pernyataan_shk
-- ----------------------------
DROP TABLE IF EXISTS `surat_pernyataan_shk`;
CREATE TABLE `surat_pernyataan_shk` (
  `no_pernyataan` varchar(25) NOT NULL COMMENT 'Nomor unik surat pernyataan SHK (Primary Key)',
  `no_rawat` varchar(17) NOT NULL COMMENT 'Nomor rawat pasien bayi (Foreign Key ke reg_periksa)',
  `tgl_pernyataan` date NOT NULL COMMENT 'Tanggal surat pernyataan dibuat',
  `tgl_pengambilan_sample` date NOT NULL COMMENT 'Tanggal sampel SHK diambil',
  `jam_pengambilan_sample` time NOT NULL COMMENT 'Jam sampel SHK diambil',
  `tempat_pengambilan` varchar(100) NOT NULL COMMENT 'Lokasi/tempat pengambilan sampel',
  `nip_petugas` varchar(20) NOT NULL COMMENT 'NIP petugas yang mengambil sampel/membuat surat (Foreign Key ke petugas)',
  `nama_ibu` varchar(50) DEFAULT NULL COMMENT 'Nama ibu bayi (bisa null jika diambil dari DB)',
  `nama_ayah` varchar(50) DEFAULT NULL COMMENT 'Nama ayah bayi (bisa null jika diambil dari DB)',
  `alamat_orangtua` varchar(200) DEFAULT NULL COMMENT 'Alamat lengkap orang tua/wali (bisa null jika diambil dari DB)',
  `no_telp_orangtua` varchar(30) DEFAULT NULL COMMENT 'Nomor telepon/HP orang tua/wali (bisa null jika diambil dari DB)',
  PRIMARY KEY (`no_pernyataan`),
  KEY `idx_spr_shk_no_rawat` (`no_rawat`),
  KEY `idx_spr_shk_tgl_pernyataan` (`tgl_pernyataan`),
  KEY `idx_spr_shk_nip_petugas` (`nip_petugas`),
  CONSTRAINT `fk_spr_shk_nip_petugas` FOREIGN KEY (`nip_petugas`) REFERENCES `petugas` (`nip`) ON UPDATE CASCADE,
  CONSTRAINT `fk_spr_shk_no_rawat` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='Tabel data utama Surat Pernyataan Pengambilan Sampel SHK';

-- ----------------------------
-- Table structure for surat_pernyataan_shk_bukti
-- ----------------------------
DROP TABLE IF EXISTS `surat_pernyataan_shk_bukti`;
CREATE TABLE `surat_pernyataan_shk_bukti` (
  `no_pernyataan` varchar(25) NOT NULL COMMENT 'FK ke surat_pernyataan_shk.no_pernyataan',
  `photo` varchar(500) DEFAULT NULL COMMENT 'Path/Nama file bukti/scan surat SHK',
  PRIMARY KEY (`no_pernyataan`) USING BTREE,
  CONSTRAINT `fk_surat_pernyataan_shk_bukti` FOREIGN KEY (`no_pernyataan`) REFERENCES `surat_pernyataan_shk` (`no_pernyataan`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci COMMENT='Tabel bukti/scan untuk Surat Pernyataan Pengambilan Sampel SHK';

SET FOREIGN_KEY_CHECKS = 1;
