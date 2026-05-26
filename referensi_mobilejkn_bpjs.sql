/*
 Navicat Premium Dump SQL

 Source Server         : Prod
 Source Server Type    : MariaDB
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.5:3306
 Source Schema         : sikrs4m2106

 Target Server Type    : MariaDB
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 22/05/2026 08:33:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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

SET FOREIGN_KEY_CHECKS = 1;
