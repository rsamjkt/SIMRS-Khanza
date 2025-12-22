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

 Date: 19/11/2025 13:16:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bpjs_prb
-- ----------------------------
DROP TABLE IF EXISTS `bpjs_prb`;
CREATE TABLE `bpjs_prb` (
  `no_sep` varchar(40) NOT NULL,
  `prb` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`no_sep`),
  CONSTRAINT `bpjs_prb_ibfk_1` FOREIGN KEY (`no_sep`) REFERENCES `bridging_sep` (`no_sep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_resep_apotek_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `bridging_resep_apotek_bpjs`;
CREATE TABLE `bridging_resep_apotek_bpjs` (
  `no_sep` varchar(40) DEFAULT NULL,
  `no_sep_apotek` varchar(40) NOT NULL,
  `tgl_sep` datetime NOT NULL,
  `kdpoli` varchar(15) NOT NULL,
  `nmpoli` varchar(50) NOT NULL,
  `kdjenis` enum('1. Obat PRB','2. Obat Kronis Blm Stabil','3. Obat Kemoterapi') DEFAULT NULL,
  `nota_piutang` varchar(20) DEFAULT NULL,
  `id_user_sep` varchar(50) DEFAULT NULL,
  `tgl_resep` datetime DEFAULT NULL,
  `tgl_pelayanan` datetime DEFAULT NULL,
  `kodedpjp` varchar(10) NOT NULL,
  `nmdpjp` varchar(100) NOT NULL,
  `iterasi` enum('0. Non Iterasi','1. Iterasi') NOT NULL,
  `no_kartu` varchar(25) NOT NULL,
  `nama_pasien` varchar(100) NOT NULL,
  `kdppkrujukan` varchar(12) NOT NULL,
  `nmppkpelayanan` varchar(200) NOT NULL,
  `byTagRsp` double NOT NULL,
  `byVerRsp` double NOT NULL,
  `status` enum('Piutang','Non Piutang') NOT NULL,
  PRIMARY KEY (`no_sep_apotek`) USING BTREE,
  KEY `no_sep` (`no_sep`) USING BTREE,
  CONSTRAINT `bridging_resep_apotek_bpjs_ibfk_1` FOREIGN KEY (`no_sep`) REFERENCES `bridging_sep` (`no_sep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_resep_apotek_bpjs_nonracikan
-- ----------------------------
DROP TABLE IF EXISTS `bridging_resep_apotek_bpjs_nonracikan`;
CREATE TABLE `bridging_resep_apotek_bpjs_nonracikan` (
  `no_sep_apotek` varchar(40) NOT NULL,
  `kode_brng` varchar(15) DEFAULT NULL,
  `signa1` varchar(5) DEFAULT NULL,
  `signa2` varchar(5) DEFAULT NULL,
  `jml_obat` double DEFAULT NULL,
  `jho` double DEFAULT NULL,
  `catatan` varchar(40) DEFAULT NULL,
  KEY `no_sep_apotek` (`no_sep_apotek`) USING BTREE,
  KEY `kode_brng` (`kode_brng`) USING BTREE,
  CONSTRAINT `bridging_resep_apotek_bpjs_nonracikan_ibfk_1` FOREIGN KEY (`no_sep_apotek`) REFERENCES `bridging_resep_apotek_bpjs` (`no_sep_apotek`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bridging_resep_apotek_bpjs_nonracikan_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `maping_obat_apotek_bpjs` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_resep_apotek_bpjs_racikan
-- ----------------------------
DROP TABLE IF EXISTS `bridging_resep_apotek_bpjs_racikan`;
CREATE TABLE `bridging_resep_apotek_bpjs_racikan` (
  `no_sep_apotek` varchar(40) NOT NULL,
  `nomor_racik` varchar(3) DEFAULT NULL,
  `kode_brng` varchar(15) DEFAULT NULL,
  `signa1` varchar(5) DEFAULT NULL,
  `signa2` varchar(5) DEFAULT NULL,
  `jml_obat` double DEFAULT NULL,
  `permintaan` double DEFAULT NULL,
  `jho` double DEFAULT NULL,
  `catatan` varchar(40) DEFAULT NULL,
  KEY `no_sep_apotek` (`no_sep_apotek`) USING BTREE,
  KEY `kode_brng` (`kode_brng`) USING BTREE,
  CONSTRAINT `bridging_resep_apotek_bpjs_racikan_ibfk_1` FOREIGN KEY (`no_sep_apotek`) REFERENCES `bridging_resep_apotek_bpjs` (`no_sep_apotek`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bridging_resep_apotek_bpjs_racikan_ibfk_2` FOREIGN KEY (`kode_brng`) REFERENCES `maping_obat_apotek_bpjs` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_rujukan_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `bridging_rujukan_bpjs`;
CREATE TABLE `bridging_rujukan_bpjs` (
  `no_sep` varchar(40) NOT NULL,
  `tglRujukan` date DEFAULT NULL,
  `tglRencanaKunjungan` date NOT NULL,
  `ppkDirujuk` varchar(20) DEFAULT NULL,
  `nm_ppkDirujuk` varchar(100) DEFAULT NULL,
  `jnsPelayanan` enum('1','2') DEFAULT NULL,
  `catatan` varchar(200) DEFAULT NULL,
  `diagRujukan` varchar(10) DEFAULT NULL,
  `nama_diagRujukan` varchar(400) DEFAULT NULL,
  `tipeRujukan` enum('0. Penuh','1. Partial','2. Rujuk Balik') DEFAULT NULL,
  `poliRujukan` varchar(15) DEFAULT NULL,
  `nama_poliRujukan` varchar(50) DEFAULT NULL,
  `no_rujukan` varchar(40) NOT NULL,
  `user` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`no_rujukan`) USING BTREE,
  KEY `no_sep` (`no_sep`) USING BTREE,
  CONSTRAINT `bridging_rujukan_bpjs_ibfk_1` FOREIGN KEY (`no_sep`) REFERENCES `bridging_sep` (`no_sep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_rujukan_bpjs_khusus
-- ----------------------------
DROP TABLE IF EXISTS `bridging_rujukan_bpjs_khusus`;
CREATE TABLE `bridging_rujukan_bpjs_khusus` (
  `no_rujukan` varchar(40) NOT NULL,
  `nokapst` varchar(25) DEFAULT NULL,
  `nmpst` varchar(100) DEFAULT NULL,
  `tglrujukan_awal` date DEFAULT NULL,
  `tglrujukan_berakhir` date DEFAULT NULL,
  PRIMARY KEY (`no_rujukan`) USING BTREE,
  CONSTRAINT `bridging_rujukan_bpjs_khusus_ibfk_1` FOREIGN KEY (`no_rujukan`) REFERENCES `bridging_rujukan_bpjs` (`no_rujukan`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_rujukan_bpjs_khusus_diagnosa
-- ----------------------------
DROP TABLE IF EXISTS `bridging_rujukan_bpjs_khusus_diagnosa`;
CREATE TABLE `bridging_rujukan_bpjs_khusus_diagnosa` (
  `no_rujukan` varchar(40) NOT NULL,
  `status` enum('P','S') DEFAULT NULL,
  `kode_diagnosa` varchar(10) NOT NULL,
  `nama_diagnosa` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`no_rujukan`,`kode_diagnosa`) USING BTREE,
  CONSTRAINT `bridging_rujukan_bpjs_khusus_diagnosa_ibfk_1` FOREIGN KEY (`no_rujukan`) REFERENCES `bridging_rujukan_bpjs` (`no_rujukan`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_rujukan_bpjs_khusus_prosedur
-- ----------------------------
DROP TABLE IF EXISTS `bridging_rujukan_bpjs_khusus_prosedur`;
CREATE TABLE `bridging_rujukan_bpjs_khusus_prosedur` (
  `no_rujukan` varchar(40) NOT NULL,
  `kode_prosedur` varchar(10) NOT NULL,
  `nama_prosedur` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`no_rujukan`,`kode_prosedur`) USING BTREE,
  CONSTRAINT `bridging_rujukan_bpjs_khusus_prosedur_ibfk_1` FOREIGN KEY (`no_rujukan`) REFERENCES `bridging_rujukan_bpjs` (`no_rujukan`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_srb_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `bridging_srb_bpjs`;
CREATE TABLE `bridging_srb_bpjs` (
  `no_sep` varchar(40) NOT NULL,
  `no_srb` varchar(10) NOT NULL,
  `tgl_srb` date DEFAULT NULL,
  `alamat` varchar(200) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `kodeprogram` varchar(3) DEFAULT NULL,
  `namaprogram` varchar(70) DEFAULT NULL,
  `kodedpjp` varchar(10) DEFAULT NULL,
  `nmdpjp` varchar(100) DEFAULT NULL,
  `user` varchar(25) DEFAULT NULL,
  `keterangan` varchar(100) DEFAULT NULL,
  `saran` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`no_sep`,`no_srb`) USING BTREE,
  CONSTRAINT `bridging_srb_bpjs_ibfk_1` FOREIGN KEY (`no_sep`) REFERENCES `bridging_sep` (`no_sep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_srb_bpjs_obat
-- ----------------------------
DROP TABLE IF EXISTS `bridging_srb_bpjs_obat`;
CREATE TABLE `bridging_srb_bpjs_obat` (
  `no_sep` varchar(40) DEFAULT NULL,
  `no_srb` varchar(10) DEFAULT NULL,
  `kd_obat` varchar(15) DEFAULT NULL,
  `nm_obat` varchar(80) DEFAULT NULL,
  `jumlah` double DEFAULT NULL,
  `signa1` varchar(30) DEFAULT NULL,
  `signa2` varchar(30) DEFAULT NULL,
  KEY `no_sep` (`no_sep`) USING BTREE,
  CONSTRAINT `bridging_srb_bpjs_obat_ibfk_1` FOREIGN KEY (`no_sep`) REFERENCES `bridging_sep` (`no_sep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_surat_kontrol_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `bridging_surat_kontrol_bpjs`;
CREATE TABLE `bridging_surat_kontrol_bpjs` (
  `no_sep` varchar(40) DEFAULT NULL,
  `tgl_surat` date NOT NULL,
  `no_surat` varchar(40) NOT NULL,
  `tgl_rencana` date DEFAULT NULL,
  `kd_dokter_bpjs` varchar(20) DEFAULT NULL,
  `nm_dokter_bpjs` varchar(50) DEFAULT NULL,
  `kd_poli_bpjs` varchar(15) DEFAULT NULL,
  `nm_poli_bpjs` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`no_surat`) USING BTREE,
  KEY `bridging_surat_kontrol_bpjs_ibfk_1` (`no_sep`) USING BTREE,
  CONSTRAINT `bridging_surat_kontrol_bpjs_ibfk_1` FOREIGN KEY (`no_sep`) REFERENCES `bridging_sep` (`no_sep`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for bridging_surat_pri_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `bridging_surat_pri_bpjs`;
CREATE TABLE `bridging_surat_pri_bpjs` (
  `no_rawat` varchar(17) DEFAULT NULL,
  `no_kartu` varchar(25) DEFAULT NULL,
  `tgl_surat` date NOT NULL,
  `no_surat` varchar(40) NOT NULL,
  `tgl_rencana` date DEFAULT NULL,
  `kd_dokter_bpjs` varchar(20) DEFAULT NULL,
  `nm_dokter_bpjs` varchar(50) DEFAULT NULL,
  `kd_poli_bpjs` varchar(15) DEFAULT NULL,
  `nm_poli_bpjs` varchar(40) DEFAULT NULL,
  `diagnosa` varchar(130) NOT NULL,
  `no_sep` varchar(40) NOT NULL,
  PRIMARY KEY (`no_surat`) USING BTREE,
  KEY `no_rawat` (`no_rawat`) USING BTREE,
  CONSTRAINT `bridging_surat_pri_bpjs_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for maping_obat_apotek_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `maping_obat_apotek_bpjs`;
CREATE TABLE `maping_obat_apotek_bpjs` (
  `kode_brng` varchar(15) NOT NULL,
  `kode_brng_apotek_bpjs` varchar(15) NOT NULL,
  `nama_brng_apotek_bpjs` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`kode_brng_apotek_bpjs`) USING BTREE,
  KEY `kode_brng` (`kode_brng`) USING BTREE,
  CONSTRAINT `maping_obat_apotek_bpjs_ibfk_1` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for maping_poli_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `maping_poli_bpjs`;
CREATE TABLE `maping_poli_bpjs` (
  `kd_poli_rs` varchar(5) NOT NULL,
  `kd_poli_bpjs` varchar(15) NOT NULL,
  `nm_poli_bpjs` varchar(40) NOT NULL,
  PRIMARY KEY (`kd_poli_rs`),
  UNIQUE KEY `kd_poli_bpjs` (`kd_poli_bpjs`) USING BTREE,
  CONSTRAINT `maping_poli_bpjs_ibfk_1` FOREIGN KEY (`kd_poli_rs`) REFERENCES `poliklinik` (`kd_poli`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for referensi_mobilejkn_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `referensi_mobilejkn_bpjs`;
CREATE TABLE `referensi_mobilejkn_bpjs` (
  `nobooking` varchar(15) NOT NULL,
  `no_rawat` varchar(17) DEFAULT NULL,
  `nomorkartu` varchar(25) DEFAULT NULL,
  `nik` varchar(30) DEFAULT NULL,
  `nohp` varchar(15) DEFAULT NULL,
  `kodepoli` varchar(15) DEFAULT NULL,
  `pasienbaru` enum('0','1') NOT NULL,
  `norm` varchar(15) DEFAULT NULL,
  `tanggalperiksa` date DEFAULT NULL,
  `kodedokter` varchar(20) DEFAULT NULL,
  `jampraktek` varchar(12) DEFAULT NULL,
  `jeniskunjungan` enum('1 (Rujukan FKTP)','2 (Rujukan Internal)','3 (Kontrol)','4 (Rujukan Antar RS)') DEFAULT NULL,
  `nomorreferensi` varchar(40) NOT NULL,
  `nomorantrean` varchar(15) NOT NULL,
  `angkaantrean` varchar(5) NOT NULL,
  `estimasidilayani` varchar(15) NOT NULL,
  `sisakuotajkn` int(11) NOT NULL,
  `kuotajkn` int(11) NOT NULL,
  `sisakuotanonjkn` int(11) NOT NULL,
  `kuotanonjkn` int(11) NOT NULL,
  `status` enum('Belum','Checkin','Batal','Gagal') NOT NULL,
  `validasi` datetime NOT NULL,
  `statuskirim` enum('Belum','Sudah') NOT NULL,
  PRIMARY KEY (`nobooking`) USING BTREE,
  KEY `norm` (`norm`) USING BTREE,
  CONSTRAINT `referensi_mobilejkn_bpjs_ibfk_1` FOREIGN KEY (`norm`) REFERENCES `pasien` (`no_rkm_medis`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for referensi_mobilejkn_bpjs_batal
-- ----------------------------
DROP TABLE IF EXISTS `referensi_mobilejkn_bpjs_batal`;
CREATE TABLE `referensi_mobilejkn_bpjs_batal` (
  `no_rkm_medis` varchar(15) NOT NULL,
  `no_rawat_batal` varchar(17) DEFAULT NULL,
  `nomorreferensi` varchar(40) NOT NULL,
  `tanggalbatal` datetime NOT NULL,
  `keterangan` varchar(200) DEFAULT NULL,
  `statuskirim` enum('Sudah','Belum') NOT NULL,
  `nobooking` varchar(15) NOT NULL,
  PRIMARY KEY (`nobooking`) USING BTREE,
  KEY `no_rkm_medis` (`no_rkm_medis`) USING BTREE,
  CONSTRAINT `referensi_mobilejkn_bpjs_batal_ibfk_1` FOREIGN KEY (`no_rkm_medis`) REFERENCES `pasien` (`no_rkm_medis`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `referensi_mobilejkn_bpjs_batal_ibfk_2` FOREIGN KEY (`nobooking`) REFERENCES `referensi_mobilejkn_bpjs` (`nobooking`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for referensi_mobilejkn_bpjs_taskid
-- ----------------------------
DROP TABLE IF EXISTS `referensi_mobilejkn_bpjs_taskid`;
CREATE TABLE `referensi_mobilejkn_bpjs_taskid` (
  `no_rawat` varchar(17) NOT NULL,
  `taskid` enum('1','2','3','4','5','6','7','99') NOT NULL,
  `waktu` datetime DEFAULT NULL,
  PRIMARY KEY (`no_rawat`,`taskid`) USING BTREE,
  CONSTRAINT `referensi_mobilejkn_bpjs_taskid_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for rvp_klaim_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `rvp_klaim_bpjs`;
CREATE TABLE `rvp_klaim_bpjs` (
  `no_rawat` varchar(17) NOT NULL,
  `tanggal_rvp` date DEFAULT NULL,
  `nip` varchar(20) DEFAULT NULL,
  `totalpiutang` double DEFAULT NULL,
  `uangmuka` double DEFAULT NULL,
  `sudahdibayar` double DEFAULT NULL,
  `sisapiutang` double DEFAULT NULL,
  `tarifinacbg` double DEFAULT NULL,
  `dibayarbpjs` double DEFAULT NULL,
  `persenbayar` double DEFAULT NULL,
  `rugi` double DEFAULT NULL,
  `lebih` double DEFAULT NULL,
  `materialralan` double DEFAULT NULL,
  `bhpralan` double DEFAULT NULL,
  `tarif_tindakandrralan` double DEFAULT NULL,
  `tarif_tindakanprralan` double DEFAULT NULL,
  `ksoralan` double DEFAULT NULL,
  `menejemenralan` double DEFAULT NULL,
  `biaya_rawatralan` double DEFAULT NULL,
  `materialranap` double DEFAULT NULL,
  `bhpranap` double DEFAULT NULL,
  `tarif_tindakandrranap` double DEFAULT NULL,
  `tarif_tindakanprranap` double DEFAULT NULL,
  `ksoranap` double DEFAULT NULL,
  `menejemenranap` double DEFAULT NULL,
  `biaya_rawatranap` double DEFAULT NULL,
  `bagian_rslabralan` double DEFAULT NULL,
  `bhplabralan` double DEFAULT NULL,
  `tarif_perujuklabralan` double DEFAULT NULL,
  `tarif_tindakan_dokterlabralan` double DEFAULT NULL,
  `tarif_tindakan_petugaslabralan` double DEFAULT NULL,
  `ksolabralan` double DEFAULT NULL,
  `menejemenlabralan` double DEFAULT NULL,
  `biayalabralan` double DEFAULT NULL,
  `bagian_rslabranap` double DEFAULT NULL,
  `bhplabranap` double DEFAULT NULL,
  `tarif_perujuklabranap` double DEFAULT NULL,
  `tarif_tindakan_dokterlabranap` double DEFAULT NULL,
  `tarif_tindakan_petugaslabranap` double DEFAULT NULL,
  `ksolabranap` double DEFAULT NULL,
  `menejemenlabranap` double DEFAULT NULL,
  `biayalabranap` double DEFAULT NULL,
  `bagian_rsradiologiralan` double DEFAULT NULL,
  `bhpradiologiralan` double DEFAULT NULL,
  `tarif_perujukradiologiralan` double DEFAULT NULL,
  `tarif_tindakan_dokterradiologiralan` double DEFAULT NULL,
  `tarif_tindakan_petugasradiologiralan` double DEFAULT NULL,
  `ksoradiologiralan` double DEFAULT NULL,
  `menejemenradiologiralan` double DEFAULT NULL,
  `biayaradiologiralan` double DEFAULT NULL,
  `bagian_rsradiologiranap` double DEFAULT NULL,
  `bhpradiologiranap` double DEFAULT NULL,
  `tarif_perujukradiologiranap` double DEFAULT NULL,
  `tarif_tindakan_dokterradiologiranap` double DEFAULT NULL,
  `tarif_tindakan_petugasradiologiranap` double DEFAULT NULL,
  `ksoradiologiranap` double DEFAULT NULL,
  `menejemenradiologiranap` double DEFAULT NULL,
  `biayaradiologiranap` double DEFAULT NULL,
  `jmdokteroperasiralan` double DEFAULT NULL,
  `jmparamedisoperasiralan` double DEFAULT NULL,
  `bhpoperasiralan` double DEFAULT NULL,
  `pendapatanoperasiralan` double DEFAULT NULL,
  `jmdokteroperasiranap` double DEFAULT NULL,
  `jmparamedisoperasiranap` double DEFAULT NULL,
  `bhpoperasiranap` double DEFAULT NULL,
  `pendapatanoperasiranap` double DEFAULT NULL,
  `obatlangsung` double DEFAULT NULL,
  `obatralan` double DEFAULT NULL,
  `hppobatralan` double DEFAULT NULL,
  `obatranap` double DEFAULT NULL,
  `hppobatranap` double DEFAULT NULL,
  `returobat` double DEFAULT NULL,
  `tambahanbiaya` double DEFAULT NULL,
  `potonganbiaya` double DEFAULT NULL,
  `kamar` double DEFAULT NULL,
  `reseppulang` double DEFAULT NULL,
  `harianranap` double DEFAULT NULL,
  `registrasi` double DEFAULT NULL,
  `no_sep` varchar(40) NOT NULL,
  `kd_rek` varchar(15) NOT NULL,
  `kd_rek_kontra` varchar(15) NOT NULL,
  `service` double NOT NULL,
  `ppn_obat` double NOT NULL,
  PRIMARY KEY (`no_rawat`) USING BTREE,
  KEY `nip` (`nip`) USING BTREE,
  KEY `kd_rek` (`kd_rek`) USING BTREE,
  KEY `kd_rek_kontra` (`kd_rek_kontra`) USING BTREE,
  CONSTRAINT `rvp_klaim_bpjs_ibfk_1` FOREIGN KEY (`no_rawat`) REFERENCES `reg_periksa` (`no_rawat`) ON UPDATE CASCADE,
  CONSTRAINT `rvp_klaim_bpjs_ibfk_2` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`) ON UPDATE CASCADE,
  CONSTRAINT `rvp_klaim_bpjs_ibfk_3` FOREIGN KEY (`kd_rek`) REFERENCES `rekening` (`kd_rek`) ON UPDATE CASCADE,
  CONSTRAINT `rvp_klaim_bpjs_ibfk_4` FOREIGN KEY (`kd_rek_kontra`) REFERENCES `rekening` (`kd_rek`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for skdp_bpjs
-- ----------------------------
DROP TABLE IF EXISTS `skdp_bpjs`;
CREATE TABLE `skdp_bpjs` (
  `tahun` year(4) NOT NULL,
  `no_rkm_medis` varchar(15) DEFAULT NULL,
  `diagnosa` varchar(50) NOT NULL,
  `terapi` varchar(200) NOT NULL,
  `alasan1` varchar(50) DEFAULT NULL,
  `alasan2` varchar(50) DEFAULT NULL,
  `rtl1` varchar(50) DEFAULT NULL,
  `rtl2` varchar(50) DEFAULT NULL,
  `tanggal_datang` datetime DEFAULT NULL,
  `tanggal_rujukan` datetime NOT NULL,
  `no_antrian` varchar(6) NOT NULL,
  `kd_dokter` varchar(20) DEFAULT NULL,
  `status` enum('Menunggu','Sudah Periksa','Batal Periksa') NOT NULL,
  PRIMARY KEY (`tahun`,`no_antrian`),
  KEY `no_rkm_medis` (`no_rkm_medis`) USING BTREE,
  KEY `kd_dokter` (`kd_dokter`) USING BTREE,
  CONSTRAINT `skdp_bpjs_ibfk_1` FOREIGN KEY (`no_rkm_medis`) REFERENCES `pasien` (`no_rkm_medis`) ON UPDATE CASCADE,
  CONSTRAINT `skdp_bpjs_ibfk_2` FOREIGN KEY (`kd_dokter`) REFERENCES `dokter` (`kd_dokter`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
