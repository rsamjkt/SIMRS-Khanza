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

 Date: 05/07/2025 10:52:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for surat_pernyataan_pembiayaan_naik_kelas
-- ----------------------------
DROP TABLE IF EXISTS `surat_pernyataan_pembiayaan_naik_kelas`;
CREATE TABLE `surat_pernyataan_pembiayaan_naik_kelas` (
  `no_surat` varchar(20) NOT NULL COMMENT 'Nomor unik surat pernyataan',
  `no_rawat` varchar(17) NOT NULL COMMENT 'Nomor rawat pasien, FK ke reg_periksa',
  `tanggal` date DEFAULT NULL COMMENT 'Tanggal pembuatan surat pernyataan',
  `nama_pj` varchar(50) DEFAULT NULL COMMENT 'Nama pembuat pernyataan/penanggung jawab',
  `no_ktppj` varchar(20) DEFAULT NULL COMMENT 'Nomor KTP/SIM pembuat pernyataan',
  `pendidikan_pj` enum('TS','TK','SD','SMP','SMA','SLTA/SEDERAJAT','D1','D2','D3','D4','S1','S2','S3','-') DEFAULT NULL COMMENT 'Pendidikan terakhir pembuat pernyataan',
  `alamatpj` varchar(100) DEFAULT NULL COMMENT 'Alamat pembuat pernyataan',
  `no_telppj` varchar(30) DEFAULT NULL COMMENT 'Nomor telepon pembuat pernyataan',
  `ruang` varchar(40) DEFAULT NULL COMMENT 'Nama ruang/kamar yang dipilih',
  `kelas` enum('Kelas 1','Kelas 2','Kelas 3','Kelas Utama','Kelas VIP','Kelas VVIP') DEFAULT NULL COMMENT 'Kelas perawatan yang dipilih',
  `hubungan` enum('Suami','Istri','Anak','Ayah','Ibu','Saudara','Keponakan','Diri Saya') DEFAULT NULL COMMENT 'Hubungan pembuat pernyataan dengan pasien',
  `hak_kelas` enum('Kelas 1','Kelas 2','Kelas 3','Kelas Utama','Kelas VIP','Kelas VVIP','-') DEFAULT NULL COMMENT 'Hak kelas perawatan pasien sesuai penjamin',
  `nama_alamat_keluarga_terdekat` varchar(130) DEFAULT NULL COMMENT 'Informasi keluarga terdekat',
  `bayar_secara` varchar(30) DEFAULT NULL COMMENT 'Metode pembayaran/pembiayaan',
  `nip` varchar(20) DEFAULT NULL COMMENT 'NIP petugas RS yang membuat/memverifikasi, FK ke petugas',
  PRIMARY KEY (`no_surat`),
  KEY `idx_no_rawat` (`no_rawat`),
  KEY `idx_tanggal` (`tanggal`),
  KEY `idx_nip` (`nip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for surat_pernyataan_pembiayaan_naik_kelas_bukti
-- ----------------------------
DROP TABLE IF EXISTS `surat_pernyataan_pembiayaan_naik_kelas_bukti`;
CREATE TABLE `surat_pernyataan_pembiayaan_naik_kelas_bukti` (
  `no_surat` varchar(20) NOT NULL COMMENT 'Nomor surat pernyataan, FK ke surat_pernyataan_pembiayaan_naik_kelas',
  `photo` varchar(255) DEFAULT NULL COMMENT 'Nama file atau path gambar bukti persetujuan',
  PRIMARY KEY (`no_surat`),
  CONSTRAINT `fk_spnkb_pernyataan` FOREIGN KEY (`no_surat`) REFERENCES `surat_pernyataan_pembiayaan_naik_kelas` (`no_surat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
