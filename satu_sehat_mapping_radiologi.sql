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

 Date: 09/12/2025 13:36:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_mapping_radiologi
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_mapping_radiologi`;
CREATE TABLE `satu_sehat_mapping_radiologi` (
  `kd_jenis_prw` varchar(15) NOT NULL,
  `code` varchar(15) DEFAULT NULL,
  `system` varchar(100) NOT NULL,
  `display` varchar(80) DEFAULT NULL,
  `sampel_code` varchar(15) NOT NULL,
  `sampel_system` varchar(100) NOT NULL,
  `sampel_display` varchar(80) NOT NULL,
  PRIMARY KEY (`kd_jenis_prw`),
  CONSTRAINT `satu_sehat_mapping_radiologi_ibfk_1` FOREIGN KEY (`kd_jenis_prw`) REFERENCES `jns_perawatan_radiologi` (`kd_jenis_prw`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of satu_sehat_mapping_radiologi
-- ----------------------------
BEGIN;
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('J000067', '36572-6', 'http://loinc.org', 'XR Thorax AP', '36572-6', 'http://snomed.info/sct', 'XR Thorax AP');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251001', '30745-5', 'http://loinc.org', 'XR Chest 1 View', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251002', '36626-3', 'http://loinc.org', 'XR Skull AP', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251003', '37630-0', 'http://loinc.org', 'XR Skull AP and Lateral', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251004', '24921-9', 'http://loinc.org', 'XR Skull OM (Waters) view', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251005', '103472-7', 'http://loinc.org', 'XR Nasal bones 1 or 2 Views', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251006', '24834-4', 'http://loinc.org', 'XR Nasal bones Views', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251007', '24830-2', 'http://loinc.org', 'XR Mastoid Views', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251008', '38563-2', 'http://loinc.org', 'XR Cervical spine AP', 'T-11501', 'SRT', 'Cervical spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251009', '38576-4', 'http://loinc.org', 'XR Cervical spine AP and Lateral', 'T-11501', 'SRT', 'Cervical spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251010', '38577-2', 'http://loinc.org', 'XR Cervical spine AP and Lateral and Oblique', 'T-11501', 'SRT', 'Cervical spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251011', '38867-5', 'http://loinc.org', 'XR Thoracic spine AP', 'T-11502', 'SRT', 'Thoracic spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251012', '38868-3', 'http://loinc.org', 'XR Thoracic spine AP and Lateral', 'T-11502', 'SRT', 'Thoracic spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251013', '38869-1', 'http://loinc.org', 'XR Thoracic spine AP and Lateral and Oblique', 'T-11502', 'SRT', 'Thoracic spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251014', '38855-0', 'http://loinc.org', 'XR Lumbar spine AP', 'T-11503', 'SRT', 'Lumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251015', '38856-8', 'http://loinc.org', 'XR Lumbar spine AP and Lateral', 'T-11503', 'SRT', 'Lumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251016', '38857-6', 'http://loinc.org', 'XR Lumbar spine AP and Lateral and Oblique', 'T-11503', 'SRT', 'Lumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251017', '38631-1', 'http://loinc.org', 'XR Thoracolumbar spine AP', 'T-D00F8', 'SRT', 'Thoracolumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251018', '38633-7', 'http://loinc.org', 'XR Thoracolumbar spine AP and Lateral', 'T-D00F8', 'SRT', 'Thoracolumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251019', '103315-8', 'http://loinc.org', 'XR Lumbar spine and Sacrum AP and Lateral', 'T-11503', 'SRT', 'Lumbar/Lumbosacral');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251020', '35059-0', 'http://loinc.org', 'XR Abdomen 1 View', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251021', '36637-0', 'http://loinc.org', 'XR Abdomen 2 Views', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251022', '36638-8', 'http://loinc.org', 'XR Abdomen 3 Views', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251023', '30745-5', 'http://loinc.org', 'XR Chest 1 View', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251024', '30750-3', 'http://loinc.org', 'XR Chest 2 Views', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251025', '42009-1', 'http://loinc.org', 'XR Chest Apical lordotic view', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251026', '37971-9', 'http://loinc.org', 'XR Clavicle AP', 'T-12310', 'SRT', 'Clavicle');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251027', '36635-4', 'http://loinc.org', 'XR Shoulder AP', 'T-15410', 'SRT', 'Shoulder joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251028', '36914-3', 'http://loinc.org', 'XR Shoulder AP and Lateral', 'T-15410', 'SRT', 'Shoulder joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251029', '37632-6', 'http://loinc.org', 'XR Humerus AP', 'T-12410', 'SRT', 'Humerus');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251030', '36652-0', 'http://loinc.org', 'XR Humerus AP and Lateral', 'T-12410', 'SRT', 'Humerus');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251031', '36659-1', 'http://loinc.org', 'XR Forearm AP and Lateral', 'T-12402', 'SRT', 'Forearm');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251032', '36643-8', 'http://loinc.org', 'XR Wrist AP and Lateral', 'T-15680', 'SRT', 'Wrist joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251033', '36641-2', 'http://loinc.org', 'XR Hand AP and Lateral', 'T-15750', 'SRT', 'Hand');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251034', '36639-6', 'http://loinc.org', 'XR Elbow AP and Lateral', 'T-15430', 'SRT', 'Elbow joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251035', '30764-1', 'http://loinc.org', 'XR Pelvis 1 or 2 Views', 'T-74000', 'SRT', 'Pelvis');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251036', '37998-2', 'http://loinc.org', 'XR Femur AP', 'T-12710', 'SRT', 'Femur');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251037', '36650-4', 'http://loinc.org', 'XR Femur AP and Lateral', 'T-12710', 'SRT', 'Femur');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251038', '36661-7', 'http://loinc.org', 'XR Tibia & Fibula AP and Lateral', 'T-12770', 'SRT', 'Lower leg');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251039', '36640-4', 'http://loinc.org', 'XR Ankle AP and Lateral', 'T-15710', 'SRT', 'Ankle joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251040', '36642-0', 'http://loinc.org', 'XR Foot AP and Lateral', 'T-15750', 'SRT', 'Foot');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251041', '36647-0', 'http://loinc.org', 'XR Knee AP and Lateral', 'T-15720', 'SRT', 'Knee joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251042', '37967-7', 'http://loinc.org', 'XR Calcaneus AP and Lateral', 'T-15730', 'SRT', 'Calcaneus');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R251043', '24558-9', 'http://loinc.org', 'US Abdomen', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252001', '30745-5', 'http://loinc.org', 'XR Chest 1 View', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252002', '36626-3', 'http://loinc.org', 'XR Skull AP', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252003', '37630-0', 'http://loinc.org', 'XR Skull AP and Lateral', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252004', '24921-9', 'http://loinc.org', 'XR Skull OM (Waters) view', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252005', '103472-7', 'http://loinc.org', 'XR Nasal bones 1 or 2 Views', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252006', '24834-4', 'http://loinc.org', 'XR Nasal bones Views', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252007', '24830-2', 'http://loinc.org', 'XR Mastoid Views', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252008', '38563-2', 'http://loinc.org', 'XR Cervical spine AP', 'T-11501', 'SRT', 'Cervical spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252009', '38576-4', 'http://loinc.org', 'XR Cervical spine AP and Lateral', 'T-11501', 'SRT', 'Cervical spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252010', '38577-2', 'http://loinc.org', 'XR Cervical spine AP and Lateral and Oblique', 'T-11501', 'SRT', 'Cervical spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252011', '38867-5', 'http://loinc.org', 'XR Thoracic spine AP', 'T-11502', 'SRT', 'Thoracic spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252012', '38868-3', 'http://loinc.org', 'XR Thoracic spine AP and Lateral', 'T-11502', 'SRT', 'Thoracic spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252013', '38869-1', 'http://loinc.org', 'XR Thoracic spine AP and Lateral and Oblique', 'T-11502', 'SRT', 'Thoracic spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252014', '38855-0', 'http://loinc.org', 'XR Lumbar spine AP', 'T-11503', 'SRT', 'Lumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252015', '38856-8', 'http://loinc.org', 'XR Lumbar spine AP and Lateral', 'T-11503', 'SRT', 'Lumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252016', '38857-6', 'http://loinc.org', 'XR Lumbar spine AP and Lateral and Oblique', 'T-11503', 'SRT', 'Lumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252017', '38631-1', 'http://loinc.org', 'XR Thoracolumbar spine AP', 'T-D00F8', 'SRT', 'Thoracolumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252018', '38633-7', 'http://loinc.org', 'XR Thoracolumbar spine AP and Lateral', 'T-D00F8', 'SRT', 'Thoracolumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252019', '103315-8', 'http://loinc.org', 'XR Lumbar spine and Sacrum AP and Lateral', 'T-11503', 'SRT', 'Lumbar/Lumbosacral');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252020', '35059-0', 'http://loinc.org', 'XR Abdomen 1 View', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252021', '36637-0', 'http://loinc.org', 'XR Abdomen 2 Views', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252022', '36638-8', 'http://loinc.org', 'XR Abdomen 3 Views', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252023', '30745-5', 'http://loinc.org', 'XR Chest 1 View', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252024', '30750-3', 'http://loinc.org', 'XR Chest 2 Views', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252025', '42009-1', 'http://loinc.org', 'XR Chest Apical lordotic view', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252026', '37971-9', 'http://loinc.org', 'XR Clavicle AP', 'T-12310', 'SRT', 'Clavicle');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252027', '36635-4', 'http://loinc.org', 'XR Shoulder AP', 'T-15410', 'SRT', 'Shoulder joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252028', '36914-3', 'http://loinc.org', 'XR Shoulder AP and Lateral', 'T-15410', 'SRT', 'Shoulder joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252029', '37632-6', 'http://loinc.org', 'XR Humerus AP', 'T-12410', 'SRT', 'Humerus');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252030', '36652-0', 'http://loinc.org', 'XR Humerus AP and Lateral', 'T-12410', 'SRT', 'Humerus');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252031', '36659-1', 'http://loinc.org', 'XR Forearm AP and Lateral', 'T-12402', 'SRT', 'Forearm');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252032', '36643-8', 'http://loinc.org', 'XR Wrist AP and Lateral', 'T-15680', 'SRT', 'Wrist joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252033', '36641-2', 'http://loinc.org', 'XR Hand AP and Lateral', 'T-15750', 'SRT', 'Hand');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252034', '36639-6', 'http://loinc.org', 'XR Elbow AP and Lateral', 'T-15430', 'SRT', 'Elbow joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252035', '30764-1', 'http://loinc.org', 'XR Pelvis 1 or 2 Views', 'T-74000', 'SRT', 'Pelvis');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252036', '37998-2', 'http://loinc.org', 'XR Femur AP', 'T-12710', 'SRT', 'Femur');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252037', '36650-4', 'http://loinc.org', 'XR Femur AP and Lateral', 'T-12710', 'SRT', 'Femur');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252038', '36661-7', 'http://loinc.org', 'XR Tibia & Fibula AP and Lateral', 'T-12770', 'SRT', 'Lower leg');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252039', '36640-4', 'http://loinc.org', 'XR Ankle AP and Lateral', 'T-15710', 'SRT', 'Ankle joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252040', '36642-0', 'http://loinc.org', 'XR Foot AP and Lateral', 'T-15750', 'SRT', 'Foot');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252041', '36647-0', 'http://loinc.org', 'XR Knee AP and Lateral', 'T-15720', 'SRT', 'Knee joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252042', '37967-7', 'http://loinc.org', 'XR Calcaneus AP and Lateral', 'T-15730', 'SRT', 'Calcaneus');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R252043', '24558-9', 'http://loinc.org', 'US Abdomen', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253002', '36626-3', 'http://loinc.org', 'XR Skull AP', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253003', '37630-0', 'http://loinc.org', 'XR Skull AP and Lateral', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253004', '24921-9', 'http://loinc.org', 'XR Skull OM (Waters) view', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253005', '103472-7', 'http://loinc.org', 'XR Nasal bones 1 or 2 Views', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253006', '24834-4', 'http://loinc.org', 'XR Nasal bones Views', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253007', '24830-2', 'http://loinc.org', 'XR Mastoid Views', 'T-D1100', 'SRT', 'Head');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253008', '38563-2', 'http://loinc.org', 'XR Cervical spine AP', 'T-11501', 'SRT', 'Cervical spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253009', '38576-4', 'http://loinc.org', 'XR Cervical spine AP and Lateral', 'T-11501', 'SRT', 'Cervical spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253010', '38577-2', 'http://loinc.org', 'XR Cervical spine AP and Lateral and Oblique', 'T-11501', 'SRT', 'Cervical spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253011', '38867-5', 'http://loinc.org', 'XR Thoracic spine AP', 'T-11502', 'SRT', 'Thoracic spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253012', '38868-3', 'http://loinc.org', 'XR Thoracic spine AP and Lateral', 'T-11502', 'SRT', 'Thoracic spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253013', '38869-1', 'http://loinc.org', 'XR Thoracic spine AP and Lateral and Oblique', 'T-11502', 'SRT', 'Thoracic spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253014', '38855-0', 'http://loinc.org', 'XR Lumbar spine AP', 'T-11503', 'SRT', 'Lumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253015', '38856-8', 'http://loinc.org', 'XR Lumbar spine AP and Lateral', 'T-11503', 'SRT', 'Lumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253016', '38857-6', 'http://loinc.org', 'XR Lumbar spine AP and Lateral and Oblique', 'T-11503', 'SRT', 'Lumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253017', '38631-1', 'http://loinc.org', 'XR Thoracolumbar spine AP', 'T-D00F8', 'SRT', 'Thoracolumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253018', '38633-7', 'http://loinc.org', 'XR Thoracolumbar spine AP and Lateral', 'T-D00F8', 'SRT', 'Thoracolumbar spine');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253019', '103315-8', 'http://loinc.org', 'XR Lumbar spine and Sacrum AP and Lateral', 'T-11503', 'SRT', 'Lumbar/Lumbosacral');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253020', '35059-0', 'http://loinc.org', 'XR Abdomen 1 View', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253021', '36637-0', 'http://loinc.org', 'XR Abdomen 2 Views', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253022', '36638-8', 'http://loinc.org', 'XR Abdomen 3 Views', 'T-D4000', 'SRT', 'Abdomen');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253023', '30745-5', 'http://loinc.org', 'XR Chest 1 View', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253024', '30750-3', 'http://loinc.org', 'XR Chest 2 Views', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253025', '42009-1', 'http://loinc.org', 'XR Chest Apical lordotic view', 'T-D3000', 'SRT', 'Chest');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253026', '37971-9', 'http://loinc.org', 'XR Clavicle AP', 'T-12310', 'SRT', 'Clavicle');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253027', '36635-4', 'http://loinc.org', 'XR Shoulder AP', 'T-15410', 'SRT', 'Shoulder joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253028', '36914-3', 'http://loinc.org', 'XR Shoulder AP and Lateral', 'T-15410', 'SRT', 'Shoulder joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253029', '37632-6', 'http://loinc.org', 'XR Humerus AP', 'T-12410', 'SRT', 'Humerus');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253030', '36652-0', 'http://loinc.org', 'XR Humerus AP and Lateral', 'T-12410', 'SRT', 'Humerus');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253031', '36659-1', 'http://loinc.org', 'XR Forearm AP and Lateral', 'T-12402', 'SRT', 'Forearm');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253032', '36643-8', 'http://loinc.org', 'XR Wrist AP and Lateral', 'T-15680', 'SRT', 'Wrist joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253033', '36641-2', 'http://loinc.org', 'XR Hand AP and Lateral', 'T-15750', 'SRT', 'Hand');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253034', '36639-6', 'http://loinc.org', 'XR Elbow AP and Lateral', 'T-15430', 'SRT', 'Elbow joint');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253035', '30764-1', 'http://loinc.org', 'XR Pelvis 1 or 2 Views', 'T-74000', 'SRT', 'Pelvis');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253036', '37998-2', 'http://loinc.org', 'XR Femur AP', 'T-12710', 'SRT', 'Femur');
INSERT INTO `satu_sehat_mapping_radiologi` (`kd_jenis_prw`, `code`, `system`, `display`, `sampel_code`, `sampel_system`, `sampel_display`) VALUES ('R253037', '36650-4', 'http://loinc.org', 'XR Femur AP and Lateral', 'T-12710', 'SRT', 'Femur');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
