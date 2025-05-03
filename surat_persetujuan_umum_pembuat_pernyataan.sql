DROP TABLE IF EXISTS `surat_pemilihan_dpjp_bukti`;
CREATE TABLE `surat_pemilihan_dpjp_bukti` (
  `no_surat` varchar(20) NOT NULL COMMENT 'FK ke surat_pemilihan_dpjp.no_surat',
  `photo` varchar(500) DEFAULT NULL COMMENT 'Path/Nama file bukti/scan surat',
  PRIMARY KEY (`no_surat`) USING BTREE,
  -- Constraint linking this table to the main DPJP selection table
  CONSTRAINT `fk_surat_pemilihan_dpjp_bukti` FOREIGN KEY (`no_surat`) REFERENCES `surat_pemilihan_dpjp` (`no_surat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci ROW_FORMAT=COMPACT COMMENT='Tabel bukti/scan untuk Surat Pemilihan DPJP';
