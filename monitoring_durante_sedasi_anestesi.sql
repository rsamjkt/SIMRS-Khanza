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

 Date: 18/12/2025 11:30:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for monitoring_durante_sedasi_anestesi
-- ----------------------------
DROP TABLE IF EXISTS `monitoring_durante_sedasi_anestesi`;
CREATE TABLE `monitoring_durante_sedasi_anestesi` (
  `no_rawat` varchar(20) DEFAULT NULL,
  `kd_dokter` varchar(20) NOT NULL,
  `tanggal` date NOT NULL,
  `waktu_pemantauan` time DEFAULT NULL,
  `oksigen` varchar(10) DEFAULT NULL,
  `n2o` varchar(10) DEFAULT NULL,
  `air` varchar(10) DEFAULT NULL,
  `catatan_mulai_tanggal` date DEFAULT NULL,
  `catatan_mulai_anestesi` time DEFAULT NULL,
  `catatan_mulai_operasi` time DEFAULT NULL,
  `gas` varchar(100) DEFAULT NULL,
  `obat_ke_1` varchar(25) DEFAULT NULL,
  `obat_ke_2` varchar(25) DEFAULT NULL,
  `obat_ke_3` varchar(25) DEFAULT NULL,
  `cairan_1` varchar(25) DEFAULT NULL,
  `cairan_2` varchar(25) DEFAULT NULL,
  `cairan_3` varchar(25) DEFAULT NULL,
  `saturasi_oksigen` varchar(10) DEFAULT NULL,
  `fio2` varchar(10) DEFAULT NULL,
  `end_tidal_co2` varchar(10) DEFAULT NULL,
  `ekg` varchar(10) DEFAULT NULL,
  `cvp_pcvp` varchar(10) DEFAULT NULL,
  `suhu` varchar(10) DEFAULT NULL,
  `urin` varchar(10) DEFAULT NULL,
  `circuit` varchar(10) DEFAULT NULL,
  `jackson_rees_circuit` varchar(10) DEFAULT NULL,
  `anestesi` varchar(10) DEFAULT NULL,
  `operasi` varchar(10) DEFAULT NULL,
  `tekanan_manset_bp` varchar(10) DEFAULT NULL,
  `tekanan_arterial` varchar(10) DEFAULT NULL,
  `tekanan_arteri_rata_rata` varchar(10) DEFAULT NULL,
  `nadi` varchar(10) DEFAULT NULL,
  `pernapasan_spontan` varchar(10) DEFAULT NULL,
  `pernapasan_bantuan` varchar(10) DEFAULT NULL,
  `pernapasan_terkontrol` varchar(10) DEFAULT NULL,
  `pembebat` varchar(10) DEFAULT NULL,
  `frekuensi_pernapasan` varchar(10) DEFAULT NULL,
  `volume_tidal` varchar(10) DEFAULT NULL,
  `peak_pressure` varchar(10) DEFAULT NULL,
  `peep` varchar(10) DEFAULT NULL,
  `komplikasi_perioperatif` varchar(10) DEFAULT NULL,
  `catatan_selesai_tanggal` date DEFAULT NULL,
  `catatan_selesai_anestesi` time DEFAULT NULL,
  `catatan_selesai_operasi` time DEFAULT NULL,
  `antibiotik_profilaksis` varchar(25) DEFAULT NULL,
  `jam_antibiotik_profilaksis` time DEFAULT NULL,
  `catatan_pasca_operasi` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
