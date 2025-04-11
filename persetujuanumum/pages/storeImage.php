<?php
    require_once('../../conf/conf.php');
    $nosurat           = $_POST["nosurat"];
    $pengobatan_kepada = validTeks4($_POST["pengobatan_kepada"],20);
    $nilai_kepercayaan = validTeks4($_POST["nilai_kepercayaan"],50);
    $persetujuan_pengobatan = validTeks4($_POST["persetujuan_pengobatan"],50);
    $pelepasan_informasi = validTeks4($_POST["pelepasan_informasi"],50);
    $keluarga = validTeks4($_POST["keluarga"],50);
    $privacy = validTeks4($_POST["privacy"],50);
    $nama_privacy = validTeks4($_POST["nama_privacy"],50);
    $profesi = validTeks4($_POST["profesi"],50);
    $kelas_rawat = validTeks4($_POST["kelas_rawat"],50);
    
    
    
    if(file_exists(host()."webapps/persetujuanumum/pages/upload/".$nosurat.".jpeg")){
        @unlink(host()."webapps/persetujuanumum/pages/upload/".$nosurat.".jpeg");
    }
    
    $img                    = $_POST["signature"];
    $folderPath             = "upload/";
    $image_parts            = explode(";base64,", $img);
    $image_type_aux         = explode("image/", $image_parts[0]);
    $image_type             = $image_type_aux[1];
    $image_base64           = base64_decode($image_parts[1]);
    $fileName               = $nosurat."PSU.jpeg";
    $file                   = $folderPath . $fileName;
    file_put_contents($file, $image_base64);
    
    Tambah3("surat_persetujuan_umum_pembuat_pernyataan","'".$nosurat."','pages/upload/$fileName'");
    Ubah2("surat_persetujuan_umum","pengobatan_kepada='$pengobatan_kepada',nilai_kepercayaan='$nilai_kepercayaan', persetujuan_pengobatan='$persetujuan_pengobatan', pelepasan_informasi='$pelepasan_informasi',keluarga='$keluarga',privacy='$privacy',nama_privacy='$nama_privacy',profesi='$profesi',kelas_rawat='$kelas_rawat'  where no_surat='$nosurat'");
?>
<head>
    <title>SIMKES Khanza</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css" />
    <style type="text/css">
        #results { padding: 0px; background:#EEFFEE; width: 490; height: 390 }
    </style>
</head>
<html xmlns="http://www.w3.org/1999/xhtml">
    <body><center>Proses Pengambilan Persetujuan Umum Pasien/Keluarga Pasien Sudah Selesai ..!! <br><a href='../login.php?iyem=<?=encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e")?>' class='btn btn-secondary' >Kembali</a></center></body>
</html>

