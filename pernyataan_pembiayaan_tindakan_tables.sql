-- Tabel antrian mobile signature untuk SuratPernyataanPembiayaanTindakan
CREATE TABLE IF NOT EXISTS `antripernyataanpembiayaantindakan` (
  `no_surat` varchar(30) NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  PRIMARY KEY (`no_surat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Tabel penyimpanan foto/tanda tangan untuk SuratPernyataanPembiayaanTindakan
CREATE TABLE IF NOT EXISTS `surat_pernyataan_pembiayaan_tindakan_pembuat_pernyataan` (
  `no_surat` varchar(30) NOT NULL,
  `photo` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`no_surat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
