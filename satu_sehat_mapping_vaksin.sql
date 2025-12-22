/*
 Navicat Premium Data Transfer

 Source Server         : produ
 Source Server Type    : MySQL
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.5:3306
 Source Schema         : sikrs4m2106

 Target Server Type    : MySQL
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 09/12/2025 14:24:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for satu_sehat_mapping_vaksin
-- ----------------------------
DROP TABLE IF EXISTS `satu_sehat_mapping_vaksin`;
CREATE TABLE `satu_sehat_mapping_vaksin` (
  `kode_brng` varchar(15) NOT NULL,
  `vaksin_code` varchar(15) DEFAULT NULL,
  `vaksin_system` varchar(100) NOT NULL,
  `vaksin_display` varchar(80) DEFAULT NULL,
  `route_code` varchar(30) DEFAULT NULL,
  `route_system` varchar(100) DEFAULT NULL,
  `route_display` varchar(80) DEFAULT NULL,
  `dose_quantity_code` varchar(15) DEFAULT NULL,
  `dose_quantity_system` varchar(80) DEFAULT NULL,
  `dose_quantity_unit` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`kode_brng`) USING BTREE,
  CONSTRAINT `satu_sehat_mapping_vaksin_ibfk_1` FOREIGN KEY (`kode_brng`) REFERENCES `databarang` (`kode_brng`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of satu_sehat_mapping_vaksin
-- ----------------------------
BEGIN;
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000008109', '02', 'http://hl7.org/fhir/sid/cvx', 'Polio, oral (OPV)', 'PO', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Oral', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000008110', '19', 'http://hl7.org/fhir/sid/cvx', 'BCG', 'ID', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intradermal', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000008111', '146', 'http://hl7.org/fhir/sid/cvx', 'DTaP-IPV-Hib-HepB (hexavalent)', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000008181', '116', 'http://hl7.org/fhir/sid/cvx', 'Rotavirus, pentavalent (RotaTeq)', 'PO', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Oral', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000008473', '19', 'http://hl7.org/fhir/sid/cvx', 'BCG', 'ID', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intradermal', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000008474', '04', 'http://hl7.org/fhir/sid/cvx', 'Measles/Rubella (MR)', 'SC', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Subcutaneous', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000008658', '102', 'http://hl7.org/fhir/sid/cvx', 'DTP-Hib-Hep B (pentavalent)', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000009030', '93', 'http://hl7.org/fhir/sid/cvx', 'Pneumococcal Conjugate, unspecified formulation', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000009118', '120', 'http://hl7.org/fhir/sid/cvx', 'DTaP-IPV-Hib', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B000009181', '215', 'http://hl7.org/fhir/sid/cvx', 'Pneumococcal conjugate, 15-valent (PCV15)', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B00146', '08', 'http://hl7.org/fhir/sid/cvx', 'Hep B, adolescent or pediatric', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B00147', '08', 'http://hl7.org/fhir/sid/cvx', 'Hep B, adolescent or pediatric', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B00372', '177', 'http://hl7.org/fhir/sid/cvx', 'Pneumococcal conjugate, 10-valent (PCV10)', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B00532', '08', 'http://hl7.org/fhir/sid/cvx', 'Hep B, adolescent or pediatric', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B00930', '119', 'http://hl7.org/fhir/sid/cvx', 'Rotavirus, monovalent (Rotarix)', 'PO', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Oral', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B00987', '102', 'http://hl7.org/fhir/sid/cvx', 'DTP-Hib-Hep B (pentavalent)', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B10000233', '146', 'http://hl7.org/fhir/sid/cvx', 'DTaP-IPV-Hib-HepB (hexavalent)', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B10000275', '10', 'http://hl7.org/fhir/sid/cvx', 'Polio, inactivated (IPV)', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B10000334', '04', 'http://hl7.org/fhir/sid/cvx', 'Measles/Rubella (MR)', 'SC', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Subcutaneous', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('B10000909', '133', 'http://hl7.org/fhir/sid/cvx', 'Pneumococcal conjugate, 13-valent (PCV13)', 'IM', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Intramuscular', 'mL', 'http://unitsofmeasure.org', 'mL');
INSERT INTO `satu_sehat_mapping_vaksin` (`kode_brng`, `vaksin_code`, `vaksin_system`, `vaksin_display`, `route_code`, `route_system`, `route_display`, `dose_quantity_code`, `dose_quantity_system`, `dose_quantity_unit`) VALUES ('V0001', '04', 'http://hl7.org/fhir/sid/cvx', 'Measles/Rubella (MR)', 'SC', 'http://terminology.hl7.org/CodeSystem/v3-RouteOfAdministration', 'Subcutaneous', 'mL', 'http://unitsofmeasure.org', 'mL');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
