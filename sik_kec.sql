/*
 Navicat Premium Data Transfer

 Source Server         : produ
 Source Server Type    : MySQL
 Source Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 Source Host           : 10.0.2.5:3306
 Source Schema         : sik

 Target Server Type    : MySQL
 Target Server Version : 100339 (10.3.39-MariaDB-0ubuntu0.20.04.2)
 File Encoding         : 65001

 Date: 11/04/2025 14:39:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for kabupaten
-- ----------------------------
DROP TABLE IF EXISTS `kabupaten`;
CREATE TABLE `kabupaten` (
  `kd_kab` int(11) NOT NULL AUTO_INCREMENT,
  `nm_kab` varchar(60) NOT NULL,
  PRIMARY KEY (`kd_kab`),
  UNIQUE KEY `nm_kab` (`nm_kab`)
) ENGINE=InnoDB AUTO_INCREMENT=520 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for kecamatan
-- ----------------------------
DROP TABLE IF EXISTS `kecamatan`;
CREATE TABLE `kecamatan` (
  `kd_kec` int(11) NOT NULL AUTO_INCREMENT,
  `nm_kec` varchar(60) NOT NULL,
  PRIMARY KEY (`kd_kec`),
  UNIQUE KEY `nm_kec` (`nm_kec`)
) ENGINE=InnoDB AUTO_INCREMENT=788 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for kelurahan
-- ----------------------------
DROP TABLE IF EXISTS `kelurahan`;
CREATE TABLE `kelurahan` (
  `kd_kel` int(11) NOT NULL AUTO_INCREMENT,
  `nm_kel` varchar(60) NOT NULL,
  PRIMARY KEY (`kd_kel`),
  UNIQUE KEY `nm_kel` (`nm_kel`)
) ENGINE=InnoDB AUTO_INCREMENT=1101010285 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- ----------------------------
-- Table structure for propinsi
-- ----------------------------
DROP TABLE IF EXISTS `propinsi`;
CREATE TABLE `propinsi` (
  `kd_prop` int(11) NOT NULL AUTO_INCREMENT,
  `nm_prop` varchar(30) NOT NULL,
  PRIMARY KEY (`kd_prop`),
  UNIQUE KEY `nm_prop` (`nm_prop`)
) ENGINE=InnoDB AUTO_INCREMENT=607 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

SET FOREIGN_KEY_CHECKS = 1;
