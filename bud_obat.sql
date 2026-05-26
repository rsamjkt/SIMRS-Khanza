CREATE TABLE IF NOT EXISTS `bud_obat` (
  `tgl_perawatan` date NOT NULL,
  `jam` time NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  `kode_brng` varchar(15) NOT NULL,
  `bud` date DEFAULT NULL,
  PRIMARY KEY (`tgl_perawatan`,`jam`,`no_rawat`,`kode_brng`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `bud_racikan` (
  `tgl_perawatan` date NOT NULL,
  `jam` time NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  `no_racik` varchar(2) NOT NULL,
  `bud` date DEFAULT NULL,
  PRIMARY KEY (`tgl_perawatan`,`jam`,`no_rawat`,`no_racik`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
